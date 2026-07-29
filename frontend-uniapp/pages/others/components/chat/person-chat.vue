<template>
	<view>
		<view>
			<messageBox ref="messageBox"></messageBox>
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
	import timeUtil from '@/utils/social/timeUtil.js';
	import inputBox from '@/components/social/inputBox/inputBox.vue';
	import messageBox from '@/components/social/messageBox/messageBox.vue';
	import {mapActions,mapGetters} from 'vuex';
	import $store from '@/store/modules/social';
	import $storeState from '@/store/modules/social/test.js';
	import userRequest from '@/api/social/user.js';
	export default {
		components:{
			inputBox: inputBox,
			messageBox: messageBox
		},
		data() {
			return {
				emoji: '',
				index: 0,
			}
		},
		onUnload() {
			console.log('退出聊天窗口')
			$storeState.state.chattingUserInfo = null
		},
		computed:{
			...mapGetters(['loginUserInfo', 'chattingUserInfo','personMessage','onlineArray'])
		},
		onNavigationBarButtonTap(e) {
			if(e.index === 0){
				uni.navigateTo({
					url:'/pages/social/info/friend-info?id='+$store.state.chattingUserInfo.chattingUserId
				})
			}
		},
		watch:{
			onlineArray(){
				this.showOnline()
			}
		},
		onPageScroll(h) {
			//this.$refs.inputBox.showDrawer = 0
			if(h.scrollTop==0){
				this.$refs.messageBox.getHistoryMsg()
			}
		},
		onLoad() {	
			if(this.chattingUserInfo==null){
				uni.switchTab({
					url:'/pages/social/social'
				})
				return
			}
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
		methods: {
			//查看在线人数
			showOnline(){
				let tip = this.onlineArray.indexOf(this.chattingUserInfo.chattingUserId)===-1?'离线':'在线'
				uni.setNavigationBarTitle({
					title:this.chattingUserInfo.name+'('+tip+')'
				})
			},
			async clearUnread(){
				let res = await userRequest.clearUnread({
					myId: this.loginUserInfo.userId,
					friendId: this.chattingUserInfo.chattingUserId
				})
				//console.log(res)
				for(let i=0;i<$store.state.sessionList.length;i++){
					if($store.state.sessionList[i].sessionId==this.chattingUserInfo.sessionId){
						$store.state.sessionList[i].unread = 0
						break
					}
				}
				$store.dispatch('countUnreadMessage')
			},
			 handleMessage(message){
				  //console.log('正在构造消息...',message)
				 let m = {
					 //原本应该是userId
					 senderId : this.loginUserInfo.userId,
					 receiverId : this.chattingUserInfo.chattingUserId,
					 sendTime: message.time,
					 content: message.content,
					 messageType: message.type,
					 sessionId: this.chattingUserInfo.sessionId
				 }
				 let msg = {
					 type: 'person-message',
					 data: m
				 }
				 let _this = this
				 uni.sendSocketMessage({
				 	data: JSON.stringify(msg),
					fail() {
						uni.showToast({
							title:'发送失败',
							icon:'error'
						})
					},
					success() {
						// setTimeout(()=>{
						// 	_this.clearUnread();
						// },300)
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
	
