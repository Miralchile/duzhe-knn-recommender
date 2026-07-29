<template>
	<view>
		<view>
			<groupMessageBox ref="messageBox"></groupMessageBox>
		</view>
		
		<view>
			<inputBox
			@sendMessage="handleMessage" 
			@bottomHeight="handleBottomHeight"
			@keyboradHeight="handleKeyboradHeight"
			 ref="inputBox"></inputBox>
		</view>
		
	</view>
</template>

<script>
	import groupMessageBox from '@/components/social/messageBox/groupMessageBox.vue';
	import timeUtil from '@/utils/social/timeUtil.js';
	import inputBox from '@/components/social/inputBox/inputBox.vue';
	import {mapActions,mapGetters} from 'vuex';
	import userRequest from '@/api/social/user.js';
	import $store from '@/store/modules/social';
	export default {
		components:{
			inputBox: inputBox,
			groupMessageBox: groupMessageBox
		},
		data() {
			return {
				emoji: '',
				
			}
		},
		watch:{
			onlineArray(){
				this.showOnline()
			}
		},
		computed:{
			...mapGetters(['loginUserInfo', 'chattingGroupInfo','onlineArray'])
		},
		onPageScroll(h) {
			//this.$refs.inputBox.showDrawer = 0
			if(h.scrollTop===0){
				this.$refs.messageBox.getHistoryMsg()
			}
		},
		onUnload(){
			console.log('退出群聊天窗口')
			$store.state.chattingGroupInfo = null
		},
		onLoad() {
			//console.log(this.chattingGroupInfo)
			
			///清空消息未读
			this.clearUnread()
			this.showOnline()
		},
		// 下拉刷新
		onPullDownRefresh(){
		//刷新数据之后停止刷新
			setTimeout(()=>{
				uni.stopPullDownRefresh()
			},1000) 
		},
		onNavigationBarButtonTap(e) {
			if(e.index === 0){
				uni.navigateTo({
					url:'/pages/social/info/group-info?id='+$store.state.chattingGroupInfo.sessionId
				})
			}
		},
		methods: {
			//查看在线人数
			async showOnline(){
				let res = await userRequest.groupMember({id:this.chattingGroupInfo.sessionId})
				let count = 0
				let total = res.data.length
				let memberArr = []
				res.data.forEach(item=>{
					memberArr.push(Number(item.user_id))
				})
				this.onlineArray.forEach(item=>{
					console.log(Number(item))
					if(memberArr.indexOf(Number(item))!==-1){
						count++
					}
				})
				uni.setNavigationBarTitle({
					title:this.chattingGroupInfo.name+'(在线'+count+'/'+total+')'
				})
			},
			async clearUnread(){
				let res = await userRequest.clearGroupUnread({
					userId: this.loginUserInfo.userId,
					groupId: this.chattingGroupInfo.sessionId
				})
				for(let i=0;i<$store.state.sessionList.length;i++){
					if($store.state.sessionList[i].sessionId==this.chattingGroupInfo.sessionId){
						$store.state.sessionList[i].unread = 0
						break
					}
				}
				$store.dispatch('countUnreadMessage')
			},
			 handleMessage(message){
				 
				 let m = {
					 senderId : this.loginUserInfo.userId,
					 senderName : this.loginUserInfo.nickName,
					 avatar: this.loginUserInfo.avatar,
					 sendTime: message.time,
					 content: message.content,
					 messageType: message.type,
					 groupId: this.chattingGroupInfo.sessionId
				 }
				 let msg = {
					 type: 'group-message',
					 data: m
				 }
				 uni.sendSocketMessage({
				 	data: JSON.stringify(msg),
					fail(res) {
						uni.showToast({
							icon:'error',
							title:'发送失败'
						})
					}
				 })
			 },
			 handleBottomHeight(height){
				 this.$refs.messageBox.setBottomHeigth(height)
			 },
			 handleKeyboradHeight(height){
				 // console.log(height)
				 this.$refs.messageBox.setKeyboardHeight(height)
			 }
		},
		created() {
			//console.log(timeUtil.getFormatTime(new Date()))
		
		}
	}
</script>

<style lang="scss">
	
