<template>
	<view>
		<view class="top">
			<image class="background" style="width: 100%; height: 540rpx;" mode="aspectFill" @tap="preViewImage(personInfo.background)" src="/static/social/img/reader.jpg"></image>
			<image class="avatar" @tap="preViewImage(personInfo.avatar)" :src="'http://localhost:8080'+personInfo.avatar" mode="aspectFill"></image>
			<view v-show="isFriend" @tap="show1 = true" class="btn">修改备注</view>
			<!-- <view v-show="isFriend" @click="clearMessage" class="btn1">清空记录</view> -->
			<view v-show="isFriend" @click="handleDelete()"
			class="btn1"  style="white-space: nowrap;">删除好友</view>
		</view>
		
		<view class="middle">
			<view class="name-container">
				<text selectable="true" class="name">{{personInfo.nickName}}</text>
				<image class="icon-gender" :src="personInfo.gender===1?'/static/social/icon/gender-male.png':'/static/social/icon/gender-female.png'"></image>
			</view>
			<view v-if="isFriend" class="name-container" style=" white-space: nowrap;margin-top: 10px;font-family: Source Han Sans CN;font-size: 15px;font-weight: 500;line-height: 16px;letter-spacing: 0em;color: #FFFFFF;">
				备注名:
				<text selectable="true" class="name-notation">{{personInfo.notation}}</text>
			</view>
			<view class="text">
				个人信息
				<image class="icon-email" src="/static/social/icon/mail.png"></image>
				<view  class="person-email">{{personInfo.email}}</view>
				<image class="icon-phonenumber" src="/static/social/icon/phonenumber.png"></image>
				<view selectable="true" class="person-phonenumber">{{personInfo.phonenumber}}</view>
				<!-- <text selectable="true">{{personInfo.introduction}}</text> -->
			</view>
		</view>

		<view class="bottom">
			<view class="btn" @tap="gotoChat()" v-show="isFriend">发消息</view>
			<view  v-show="!isFriend" class="btn" @tap="show=true">加好友</view>
			<view @tap="gotoPage('space')" class="btn1">进空间</view>
			<!-- <view v-show="isFriend" @click="handleDelete()"
			class="btn2"  style="white-space: nowrap;">删除好友</view> -->
		</view>
		
		<u-popup
			customStyle="border-radius: 20rpx;"
			:safeAreaInsetBottom="true"
			:safeAreaInsetTop="true"
			:mode="popupData.mode"
			:show="show"
			:round="popupData.round"
			:overlay="popupData.overlay"
			:borderRadius="popupData.borderRadius"
			:closeable="popupData.closeable"
			:closeOnClickOverlay="popupData.closeOnClickOverlay"
			@close="show = false"
			@open="openMethod"
		>
			<view
				class="u-popup-slot"
				:style="{
					width: ['bottom', 'top'].includes(popupData.mode) ? '750rpx' : '200px',
					marginTop: ['left', 'right'].includes(popupData.mode) ? '480rpx' : '0',
				}"
			>
			  <u-input
			    placeholder="验证信息"
			    border="surround"
			    v-model="message"
			    @change="change"
				customStyle="width: 90%;margin-top:10rpx;"
			  ></u-input>
			  <u-input
			    placeholder="设置备注"
			    border="surround"
			    v-model="notation"
			    @change="change"
			  				customStyle="width: 90%;margin-top:20rpx;"
			  ></u-input>
				<u-button
					type="success"
					text="发送"
					customStyle="width: 200rpx;margin-top:30rpx;"
					@click="sendApply()"
				></u-button>
			</view>
		</u-popup>
		
		<u-popup
			customStyle="border-radius: 20rpx;"
			:safeAreaInsetBottom="true"
			:safeAreaInsetTop="true"
			:mode="popupData.mode"
			:show="show1"
			:round="popupData.round"
			:overlay="popupData.overlay"
			:borderRadius="popupData.borderRadius"
			:closeable="popupData.closeable"
			:closeOnClickOverlay="popupData.closeOnClickOverlay"
			@close="show1 = false"
			@open="openMethod"
		>
			<view
				class="u-popup-slot"
				:style="{
					width: ['bottom', 'top'].includes(popupData.mode) ? '750rpx' : '200px',
					marginTop: ['left', 'right'].includes(popupData.mode) ? '480rpx' : '0',
				}"
			>
			  <u-input
			    placeholder="设置备注"
			    border="surround"
			    v-model="notation1"
			    @change="change"
			  	customStyle="width: 90%;margin-top:20rpx;"
			  ></u-input>
				<u-button
					type="success"
					text="确定"
					customStyle="width: 200rpx;margin-top:30rpx;"
					@click="edit()"
				></u-button>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import userRequest from '@/api/social/user.js';
	import friendRequest from '@/api/social/friend.js';
	import {mapGetters} from 'vuex';
	import $store from '@/store/modules/social';
	
	export default{
		computed:{
			...mapGetters(['friendList','loginUserInfo','sessionList','chattingUserInfo'])
		},
		data(){
			return{
				personInfo:{},
				show : false,
				show1 : false,
				allData:[],
				popupData: {
					overlay: true,
					mode: 'center',
					borderRadius: '',
					closeable: true,
					closeOnClickOverlay: true
				},
				message: '',
				notation: '',
				isFriend:false, 
				notation1:'',
				friendId:0
			}
		},
		onLoad(options){			
			let id = options.id;
			this.getPersonInfo(id),
			console.log(this.personInfo)
			console.log(this.chattingUserInfo)
		},
		methods:{
			//清空消息
			clearMessage(){
							let sessionId = this.chattingUserInfo.sessionId;
							console.log(sessionId+"11111111");
							let that = this
							uni.showModal({
								cancelText:'取消',
								confirmText:'清空',
								title:'确认清空消息吗？',
								success(res) {
									if(res.confirm){
										that.postClear(sessionId)
									}
								}
							})
						},
						async postClear(sessionId){
							let res = await userRequest.clearMessage({
								sessionId:sessionId,
							})
							uni.showToast({
								title:'删除成功',
								icon:'success'
							})
							$store.dispatch('getPersonMessage')
						},
			
			    /** 删除好友操作 */
			    async handleDelete() {
			     	let sessionId = this.chattingUserInfo.sessionId;
			     	 console.log(sessionId+"11111111");
			     	let that = this
			     	uni.showModal({
			     		cancelText:'取消',
			     		confirmText:'删除',
			     		title:'确认删除',
			     		success(res) {
			     			if(res.confirm){
			     				that.postDelete(sessionId)
			     			}
			     		}
			     	})
			     },
			     async postDelete(sessionId){
			     	let res = await friendRequest.deleteSession({
			     		sessionId:sessionId
			     	})
			     	uni.showToast({
			     		title:'删除成功',
			     		icon:'success'
			     	})
					$store.dispatch('getFriendList')
					uni.navigateTo({
						url:'/pages/social/components/friend/friend'
					})
			     },		    
			
			
			gotoChat(){
				//console.log(this.personInfo)
				if(!this.isFriend){
					return
				}
				//console.log(this.sessionList)
				for(let i=0;i<this.sessionList.length;i++){
					if(this.sessionList[i].chattingUserId==this.personInfo.userId)
					{
						$store.state.chattingUserInfo = this.sessionList[i]
						uni.navigateTo({
							url:'/pages/social/components/chat/person-chat'
						})
						break
					}
				}
			},
			///发送好友申请
			sendApply(){
				if(this.notation.trim()===''){
					uni.showToast({
						title:'备注不允许为空',
						icon:'none'
					})
					return
				}
				if(this.notation.trim().length>10||this.message.trim().length>10){
					uni.showToast({
						title:'备注/验证信息不要超过10个字',
						icon:'none'
					})
					return
				}
				///构造websocket消息
				let m = {
					 senderId : this.loginUserInfo.userId,
					 senderName : this.loginUserInfo.nickName,
					 receiverId : this.personInfo.userId,
					 notation: this.notation.trim(),
					 applyMessage: this.message.trim()
				}
				let msg = {
					 type: 'person-apply',
					 data: m
				}
				let _this = this
				uni.sendSocketMessage({
					data: JSON.stringify(msg),
					fail(res) {
						uni.showToast({
							icon:'error',
							title:'发送失败'
						})
					},
					success() {
						uni.showToast({
							icon:'success',
							title:'发送成功'
						})
						_this.show = false
					}
				})
			},
			gotoPage(where){
				if(where==='space'){
					uni.navigateTo({
						url:'/pages/social/components/moment/person-world?id='+this.personInfo.userId
					})
					return
				}
			},
			async edit(){
				if(this.notation1===''){
					uni.showToast({
						icon:'none',
						title:'请填写备注'
					})
					return 
				}
				let res = await friendRequest.edit({
					id:this.friendId,
					notation: this.notation1.trim()
				})
				//console.log(res)
				$store.dispatch('getFriendList');
				uni.showToast({
					icon:'success',
					title:'修改成功！'
				})
				this.show1 = false;
				this.getPersonInfo(this.personInfo.userId)
			},
			async getPersonInfo(id){
				let res = await userRequest.info({userId:id});
				// console.log(res)
				this.personInfo = res.data;
				this.checkIfFrend(id);
			},
			checkIfFrend(id){
				for(let i=0;i<this.friendList.length;i++){
					if(this.friendList[i].friend_id==id){
						this.isFriend = true;
						this.friendId = this.friendList[i].id;
						this.personInfo.notation = this.friendList[i].notation;
						this.notation1 = this.personInfo.notation
						break;
					}
				}
			},
			toEit(){
				uni.navigateTo({
					url:'/pages/social/info/person-edit'
				})
			},
			change(e){
				//console.log(e)
			},
			openMethod(){
				if(this.personInfo.userId==this.loginUserInfo.userId){
					uni.showToast({
						title:'不允许添加自己为好友',
						icon:'none'
					})
					this.show = false
					return
				}
				this.notation = this.personInfo.nickName
				this.message = '你好!我是'+this.loginUserInfo.nickName
				console.log('打开了窗口')
			},
			preViewImage(url){
				uni.previewImage({
					urls:[url]
				})
			}
		}
	}
</script>
<style lang="scss">
	page {
	  background-color: #fff;
	}
</style>
<style lang="scss" scoped>
	
	.top{
		position: absolute;
		left: 0px;
		top: 0px;
		width: 375px;
		height: 260px;
		mix-blend-mode: multiply;
		opacity: 1;
		/* position: relative;
		height: 500rpx; */
		.background{
			// z-index: -1;
			/* height: 400rpx; */
			background: linear-gradient(180deg, rgba(255,255,255,0.00) 1%, rgba(21,21,21,0.76) 100%);
		}
		.avatar{
			/* border-radius: 50%;
			width: 150rpx;
			height: 150rpx;
			position: absolute;
			bottom: 0;
			left: 50rpx; */
			position: absolute;
			left: 32px;
			top: 98px;
			width: 48px;
			height: 48px;
			opacity: 1;
			border-radius: 50%
			
			/* box-sizing: border-box;
			border: 1px solid #FFFFFF; */
		}
		.btn{
			position: absolute;
			bottom: 65rpx;
			right: 370rpx;
			border-radius: 56px;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			padding: 7px 27px;
			color: #FFFFFF;
			background: rgba(0, 199, 119, 0.47);
		}
		.btn:active{
			background-color: royalblue;
		}
		.btn1{
			position: absolute;
			bottom: 65rpx;
			right: 100rpx;

			border-radius: 56px;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			padding: 7px 27px;
			color: #FFFFFF;
			background: rgba(239, 170, 51, 0.6);

		}
		.btn1:active{
			background-color: royalblue;
		}
	}
	
	.middle{
		/* padding: 50rpx; */
		position: absolute;
		left: 90px;
		top: 104px;
		width: 90px;
		height: 16px;
		opacity: 1;
		.name-container{
			display: flex;

			.name{
				/* font-weight: 900;
				font-size: 35rpx; */
				font-family: Source Han Sans CN;
				font-size: 15px;
				font-weight: 500;
				line-height: 16px;
				letter-spacing: 0em;
				
				color: #FFFFFF;
			}
			.name-notation{
				margin-left: 5px;
				font-family: Source Han Sans CN;
				font-size: 15px;
				font-weight: 500;
				line-height: 16px;
				letter-spacing: 0em;
				
				color: #FFFFFF;
			}
			.icon-gender{
				position: absolute;
				left: 105px;
				top: 1px;
				width: 16px;
				height: 16px;
				opacity: 1;
			}
			.icon{
				margin-left: 5rpx;
				width: 40rpx;
				height: 40rpx;
			}
		}
		.mail-container{
			margin-top: 20rpx;
			display: flex;
			.icon{
				margin-left: 5rpx;
				width: 40rpx;
				height: 40rpx;
				margin-right: 5rpx;
			}
			text{
				font-weight: 500;
				font-size: 25rpx;
				margin-right: 30rpx;
			}
		}
		.text{
			position: absolute;
			left: -55px;
			top: 185px;
			width: 60px;
			height: 14px;
			opacity: 1;
			
			font-family: Source Han Sans CN;
			font-size: 15px;
			font-weight: 500;
			line-height: 14px;
			letter-spacing: 0em;
			
			color: #343333;
			/* margin-top: 30rpx;
			color: grey;
			letter-spacing: 1rpx; */
			// font-size: 35rpx;
		}
		.person-email{
			margin-top: 15px;
			margin-left: 30px;
		}
		.icon-email{
			top: 35px;
			margin-left: 0px;
			width: 40rpx;
			height: 40rpx;
		}
		.icon-phonenumber{
			top: 20px;
			margin-left: 0px;
			width: 40rpx;
			height: 40rpx;
		}
		.person-phonenumber{
			margin-top: 0px;
			margin-left: 30px;
		}
	}
	.bottom{
		position: absolute;
		padding: 50rpx;
		display: flex;
		width: 100%;
		bottom: 10px;
		.btn{
			/* background-color: royalblue;
			width: 40%;
			height: 70rpx;
			color: #fff;
			font-weight: 900;
			text-align: center;
			line-height: 70rpx;
			margin-right: 7%;
			border-radius: 35rpx; */
			position: static;
			margin-left: 2%;
			left: 0px;
			top: 0px;
			width: 100px;
			height: 44px;
			border-radius: 4px;
			opacity: 1;
			
			/* 自动布局 */
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			padding: 7px 27px;
			color: #FFFFFF;
			background: #429955;
			
			z-index: 0;
		}
		.btn1{
			position: static;
			margin-left: 35%;
			left: 120px;
			top: 0px;
			width: 100px;
			height: 44px;
			border-radius: 4px;
			opacity: 1;
			
			/* 自动布局 */
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			padding: 7px 27px;
			color: #FFFFFF;
			background: #4DAD9F;
			
			z-index: 1;
		}
		.btn2{
			position: static;
			margin-left: 5%;
			left: 240px;
			top: 0px;
			width: 100px;
			height: 44px;
			border-radius: 4px;
			opacity: 1;
			
			/* 自动布局 */
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			padding: 7px 27px;
			color: #FFFFFF;
			background: #EFAA33;
			
			z-index: 2;
		}
	}
	.u-popup-slot {
		padding: 50rpx;
		width: 200px;
		height: 150px;
		justify-content: center;
		align-items: center;
		display: block;
	}
</style>