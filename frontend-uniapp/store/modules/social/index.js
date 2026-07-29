import Vue from 'vue'
import Vuex from 'vuex'
import messageRequest from '@/api/social/message.js';
import $store from '@/store/modules/social/test.js';
import $storeApi from '@/store/modules/social';
import friendRequest from '@/api/social/friend.js';
import stringUtil from '@/utils/social/stringUtil.js';
import userRequest from '@/api/social/user.js';
import { fail } from 'assert';
Vue.use(Vuex)
const store = new Vuex.Store({
	state: {
		////关于系统
		systemConfig: uni.getStorageSync('SYSTEM_CONFIG') || {
			vibrate: true,  //是否开启手机震动
			bell: true, ///是否开启铃声通知
		},
		onlineArray:[],
		///////////////////////////
		isSocketOpen: false, //是否连接websocket
	
		///////////登录用户信息
		token: uni.getStorageSync('TOKEN')||'',
		loginUserInfo: uni.getStorageSync('LOGIN_USER_INFO')||{},
		////////////////////////用户会话模块////////////////////////////
		personMessage:[],////好友聊天记录
		groupMessage:[], ///群聊天记录
		lastUpdateSession:{id:'',count:0},
		editingGroupInfo:null,
		/////////////////////////好友信息模块///////////////////////////////////////
		friendList:[],
		friendListShow: [],
		sessionList:[],   //会话列表
		groupList: [],
		totalUnread:{
			message: 0,
			notice: 0
		},
		chattingUserInfo:null,
		chattingGroupInfo:null,
		noticeList:[],
		///////////动态模块
		publishSuccessFlag: false
	},
	getters: {
		onlineArray:state=>{
			return state.onlineArray
		},
		lastUpdateSession:state=>{
			return state.lastUpdateSession
		},
		personMessage:state=>{
			return state.personMessage
		},
		groupMessage:state=>{
			return state.groupMessage
		},
		noticeList:state=>{
			return state.noticeList
		},
		totalUnread:state=>{
			return state.totalUnread
		},
		groupList:state=>{
			return state.groupList
		},
		sessionList:state=>{
			return state.sessionList
		},
		friendListShow:state=>{
			return state.friendListShow
		},
		friendList:state =>{
			return state.friendList;
		},
		systemConfig:state=>{
			return state.systemConfig;
		},
		isSocketOpen: state =>{
			return state.isSocketOpen;
		},
		title: state => {
			return state.title
		},
		messageList: state => {
			return state.messageList
		},
		loginUserInfo: state => {
			return state.loginUserInfo
		},
		chattingUserInfo: state => {
			return state.chattingUserInfo
		},
		chattingGroupInfo: state => {
			return state.chattingGroupInfo
		},


	},
	mutations:{
		_setFriendList(state,list){
			state.friendList = list
		}
	},
	actions: {
		//////////////////////////消息模块////////////////////////////
		
		/////////////////////////会话模块///////////////////////////
		initSessionList(){
			$store.state.totalUnread.message = 0
			$store.state.sessionList = [];
			for(let i=0;i<$store.state.friendList.length;i++){
				let session = {
					type:'person',
					avatar:$store.state.friendList[i].avatar,
					sessionId: $store.state.friendList[i].session_id,
					name: $store.state.friendList[i].notation,
					lastMessage: $store.state.friendList[i].last_message,
					isHidden:$store.state.friendList[i].is_hidden,
					unread:$store.state.friendList[i].unread,
					updateTime:$store.state.friendList[i].update_time,
					chattingUserId:$store.state.friendList[i].friend_id
				}
				$store.state.totalUnread.message += session.unread
				$store.state.sessionList.push(session)
			}
			for(let i=0;i<$store.state.groupList.length;i++){
				let session = {
					type:'group',
					avatar:$store.state.groupList[i].avatar,
					sessionId: $store.state.groupList[i].group_id,
					name: $store.state.groupList[i].name,
					lastMessage: $store.state.groupList[i].last_message,
					isHidden:$store.state.groupList[i].is_hidden,
					unread:$store.state.groupList[i].unread,
					updateTime:$store.state.groupList[i].update_time
				}
				$store.state.totalUnread.message += session.unread
				session.updateTime=session.updateTime.replace('T', " ")
				$store.state.sessionList.push(session)
			}
			$storeApi.dispatch('sortSessionList')
		},
		////////////////统计所有的未读消息
		countUnreadMessage(){
			$store.state.totalUnread.message = 0
			for(let i=0;i<$store.state.sessionList.length;i++){
				$store.state.totalUnread.message += $store.state.sessionList[i].unread;
			}
		},
		///按照时间顺序排序session
		sortSessionList(){
			for(let i=0;i<$store.state.sessionList.length;i++){
				for(let j=i+1;j<$store.state.sessionList.length;j++){
					if($store.state.sessionList[j].updateTime>$store.state.sessionList[i].updateTime){
						let t = $store.state.sessionList[i]
						$store.state.sessionList[i] = $store.state.sessionList[j]
						$store.state.sessionList[j] = t
					}
				}
			}
		},
		////////////////////////获取通知消息//////////////////////
		async getNoticeList(){
			let res = await userRequest.getNotice({
				id:$store.state.loginUserInfo.userId
			})
			
			// $store.state.totalUnread.notice = res.data.count
			// $store.state.noticeList = res.data.noticeList
			//console.log($store.state.noticeInfo)
		},
		////////////////////////获取用户聊天记录//////////////////
		async getPersonMessage(){
			
			for(let i=0;i<$store.state.friendList.length;i++){
				let session = {
					sessionId: $store.state.friendList[i].session_id,
					pageNum:1,
					pageSize: 20,
					hasNextPage:false,
					lastMessageId:0,   ///用来防止加载历史消息时出问题
					list:[]
				}
				$store.state.personMessage.push(session)
			}
			///
			for(let i=0;i<$store.state.personMessage.length;i++){
				let res = await userRequest.getPeronMessage({
					sessionId: $store.state.personMessage[i].sessionId,
					pageNum: $store.state.personMessage[i].pageNum,
					pageSize: $store.state.personMessage[i].pageSize,
					currentUserId:this.getters.loginUserInfo.userId
				})
				// let data = res.data
				// for(let i=0;i<$store.state.personMessage.length;i++){
				// 	if($store.state.personMessage[i].sessionId==data.sessionId){
				// 		let current = Number(data.pageInfo.current)
				// 		let total = Number(data.pageInfo.total)
				// 		let size = Number(data.pageInfo.size)
				// 		$store.state.personMessage[i].lastMessageId=data.pageInfo.records.length!=0?data.pageInfo.records[0].id:0
				// 		$store.state.personMessage[i].list = data.pageInfo.records.reverse()
				// 		$store.state.personMessage[i].pageNum = current
				// 		$store.state.personMessage[i].hasNextPage = current*size<total
				// 		break;
				// 	}
				// }
			}
		},
		//////////获取群聊天记录
		async getGroupMessage(){
			for(let i=0;i<$store.state.groupList.length;i++){
				let session = {
					groupId: $store.state.groupList[i].group_id,
					pageNum:1,
					pageSize: 20,
					hasNextPage:false,
					lastMessageId:0,   ///用来防止加载历史消息时出问题
					list:[]
				}
				$store.state.groupMessage.push(session)
			}
			///
			for(let i=0;i<$store.state.groupMessage.length;i++){
				let res = await userRequest.getGroupMessage({
					groupId: $store.state.groupMessage[i].groupId,
					pageNum: $store.state.groupMessage[i].pageNum,
					pageSize: $store.state.groupMessage[i].pageSize,
					currentSenderId:this.getters.loginUserInfo.userId
				})
				let data = res.data
				for(let i=0;i<$store.state.groupMessage.length;i++){
					if($store.state.groupMessage[i].groupId==data.groupId){
						let current = Number(data.pageInfo.current)
						let total = Number(data.pageInfo.total)
						let size = Number(data.pageInfo.size)
						$store.state.groupMessage[i].lastMessageId=data.pageInfo.records.length!=0?data.pageInfo.records[0].id:0
						$store.state.groupMessage[i].list = data.pageInfo.records.reverse()
						$store.state.groupMessage[i].pageNum = current
						$store.state.groupMessage[i].hasNextPage = current*size<total
						break;
					}
				}
			}
		},
		/////////////////////////登录用户模块/////////////////////////
		///获取群聊列表
		async getGroupList(){
			let res = await userRequest.getGroup({id:this.getters.loginUserInfo.userId})
			$store.state.groupList = res.data;
			
			this.dispatch('initSessionList')
			this.dispatch('getGroupMessage');
		},
		///登录之后获取好友列表
		async getFriendList(){
			let res = await friendRequest.getList({myId:this.getters.loginUserInfo.userId})
			$store.state.friendList = res.data
			this.dispatch('getGroupList');
			this.dispatch('initFriendListShow');
			this.dispatch('getPersonMessage');
		},
		initFriendListShow(){
			$store.state.friendListShow = []
			for(let i=0;i<=28;i++){
				let t = []
				$store.state.friendListShow.push(t)
			}
			for(let i=0;i<$store.state.friendList.length;i++){
				//console.log(this.getters.friendList[i])
				let t = {
					id: $store.state.friendList[i].friend_id,
					name: $store.state.friendList[i].notation,
					url: $store.state.friendList[i].avatar
				}
				let num = stringUtil.getFirstLetter($store.state.friendList[i].notation).charCodeAt(0)
				
				$store.state.friendListShow[1].push(t)
				// if(num>=65&&num<=90)
				// {
				// 	$store.getters.friendListShow[num-63].push(t)
				// }
				// else{
				// 	$store.state.friendListShow[1].push(t)
				// }
				
			}
		},
		async clearUnread(context,param){
			let res = await userRequest.clearUnread(param)
			console.log('已清除未读标记')
		},
		async clearGroupUnread(context,param){
			let res = await userRequest.clearGroupUnread(param)
			console.log('已清除未读标记')
		},
		/////
	}
	
})
export default store