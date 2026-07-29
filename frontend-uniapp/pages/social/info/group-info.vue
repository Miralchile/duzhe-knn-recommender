<template>
	<view>
		<view class="top">
			<image class="background" style="width: 100%;" mode="aspectFill" src="/static/social/img/group/group-info.png"></image>
			<image class="avatar" @tap="preViewImage(groupInfo.avatar)" :src="groupInfo.avatar"></image>
			<view v-if="loginUserInfo.userId==groupInfo.ownerId" class="btn-edit" @tap="gotoPage('edit')">编辑群聊</view>
		</view>
		<view class="middle">
			<view class="name-container">
				<text selectable="true" class="name">{{groupInfo.name}}</text>
				<text selectable="true" class="name1">{{groupInfo.id}}</text>
				<!-- <image class="icon" :src="personInfo.gender===1?'../../static/icon/gender-male.png':'../../static/icon/gender-female.png'"></image> -->
			</view>
			<view class="mail-container">
				<!-- <image class="icon" src="../../static/icon/mail.png"></image>
				<text selectable="true">{{personInfo.email}}</text> -->
				<!-- <image class="icon" src="/static/social/icon/calendar.png"></image> -->
				<text>{{showTime(groupInfo.createTime)}}</text>
			</view>
			<view class="text" style="font-size: 15px;">群成员</view>
			<view class="mail-member" @tap="gotoPage('member')">
				<u-avatar-group 
				            :urls="urls"
				            size="35"
				            gap="-0.2">
				</u-avatar-group>
			</view>
			<view class="text-introduction">
				<view style="padding-bottom: 10px;font-size: 15px;font-family: Source Han Sans CN;">群简介</view>
				<text selectable="true">{{groupInfo.introduction}}</text>
			</view>
		</view>

		<view class="bottom">
			<view @tap="gotoChat()" v-if="isMember" class="btn-message">发消息</view>
			<view @tap="joinGroup()" v-if="!isMember" class="btn-message">入群聊</view>
			<view v-if="loginUserInfo.userId==groupInfo.ownerId" class="btn" @click="deleteGroup()">解散群聊</view>
			<view v-if="isMember&&loginUserInfo.userId!=groupInfo.ownerId" class="btn" @click="exitGroup()">退出群聊</view>
		</view>
		
		
	</view>
</template>

<script>
	import userRequest from '@/api/social/user.js';
	import timeUtil from '@/utils/social/timeUtil.js';
	import {mapGetters} from 'vuex';
	import $store from '@/store/modules/social';
	export default{
		computed:{
			...mapGetters(['loginUserInfo','groupList','sessionList'])
		},
		data(){
			return{
				groupInfo:{},
				show : false,
				popupData: {
					overlay: true,
					mode: 'center',
					borderRadius: '',
					closeable: true,
					closeOnClickOverlay: true
				},
				message: '',
				notation: '',
				urls:[],
				isMember:false
			}
		},
		onLoad(options) {
			let id = options.id;
			this.getGroupInfo(id)
		},
		methods:{
			//解散群聊
			deleteGroup(){
				let id =  this.groupInfo.id
					console.log(id+"11111111");
					let that = this
					uni.showModal({
						cancelText:'取消',
						confirmText:'解散',
						title:'确认解散当前读友圈吗',
						success(res) {
							if(res.confirm){
								that.postDelete(id)
							}
							//console.log(id)
						}
					})
				},
				async postDelete(id){
					console.log(id)
					let res = await userRequest.deleteGroup({
						id:id,
					})
					console.log(id)
					uni.showToast({
						title:'已退出',
						icon:'success'
					})
					$store.dispatch('getGroupList')
					uni.navigateBack({
						url:"/pages/social/list/group-list"
					})
				},
			
			
			//退出群聊
			exitGroup(){
				let groupId  =  this.groupInfo.id
				let userId = this.loginUserInfo.userId
					// console.log(id+"11111111")
					// console.log(userId)
					let that = this
					uni.showModal({
						cancelText:'取消',
						confirmText:'退出',
						title:'确认退出当前读友圈吗',
						success(res) {
							if(res.confirm){
								that.postExit(groupId,userId)
							}
							//console.log(id)
						}
					})
			},	
				async postExit(groupId,userId){
					console.log(groupId,userId)
					let res = await userRequest.exitGroup({
						groupId:groupId,
						userId:userId
					})
					uni.showToast({
						title:'已退出',
						icon:'success'
					})
					$store.dispatch('getGroupList')
					uni.navigateBack({
						url:"/pages/social/components/chat/chatlist"
					})
				},
			
			gotoChat(){
				//console.log(this.personInfo)
				if(!this.isMember){
					return
				}
				//console.log(this.sessionList)
				for(let i=0;i<this.sessionList.length;i++){
					if(this.sessionList[i].sessionId==this.groupInfo.id)
					{
						$store.state.chattingGroupInfo = this.sessionList[i]
						uni.navigateTo({
							url:'/pages/social/components/chat/group-chat'
						})
						break
					}
				}
			},
			joinGroup(){
				///构造websocket消息
				let m = {
					 userId : this.loginUserInfo.userId,
					 groupId : this.groupInfo.id
				}
				let msg = {
					 type: 'join-group',
					 data: m
				}
				uni.sendSocketMessage({
					data: JSON.stringify(msg),
					fail(res) {
						uni.showToast({
							icon:'error',
							title:'发送失败'
						})
					},
					success() {
						uni.navigateBack();
					}
				})
			},
			showTime(time){
				return timeUtil.getFormatTime(time)
			},
			async getGroupInfo(id){
				let res = await userRequest.groupInfo({id:id})
				let res1 = await userRequest.groupMember({id:id})
				//console.log(res,res1)
				this.groupInfo = res.data;
				let users = res1.data
				this.urls = []
				for(let i=0;i<users.length;i++){
					this.urls.push('http://localhost:8080'+users[i].avatar)
				}
				for(let i=0;i<this.groupList.length;i++){
					if(this.groupList[i].group_id===this.groupInfo.id){
						this.isMember = true
						break
					}
				}
			},
			gotoPage(i){
				switch(i){
					case 'edit':
					//console.log(this.groupInfo)
					$store.state.editingGroupInfo = this.groupInfo
					uni.navigateTo({
						url:'/pages/social/info/group-edit?action=edit'
					})
					break;
					case 'member':
					uni.navigateTo({
						url:'/pages/social/list/member-list?id='+this.groupInfo.id
					})
					break;
				}
			},
			change(e){
				//console.log(this.message)
			},
			openMethod(){
				//console.log('打开了')
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
	page{
		//background-color: #e2e2e2;
	}
	.top{
		position: relative;
		height: 500rpx;
		.background{
			// z-index: -1;
			height: 530rpx;
		}
		.avatar{
			border-radius: 50%;
			position: absolute;
			left: 32px;
			top: 98px;
			width: 52px;
			height: 52px;
			opacity: 1;
/* 			width: 150rpx;
			height: 150rpx;
			position: absolute;
			bottom: 0;
			left: 50rpx; */
		}
		.btn-edit{
			/* 容器 956 */
			
			position: absolute;
			left: 80px;
			top: 197px;
			width: 110px;
			height: 28px;
			border-radius: 56px;
			opacity: 1;
			
			/* 自动布局 */
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			padding: 7px 27px;
			background: rgba(0, 199, 119, 0.47);
			font-family: PingFangHK-Regular;
			
			font-size: 13px;
			font-weight: normal;
			line-height: normal;
			letter-spacing: 0em;
			
			color: #FFFFFF;
		}
		.btn{
			position: absolute;
			bottom: 15rpx;
			right: 50rpx;
			background-color: royalblue;
			padding: 15rpx;
			color: white;
			border-radius: 8rpx;
			font-weight: 600;
		}
		.btn:active{
			background-color: royalblue;
		}
	}
	
	.middle{
		padding: 50rpx;
		.name-container{
			position: absolute;
			left: 90px;
			top: 104px;
			width: 75px;
			height: 16px;
			opacity: 1;
			display: flex;
			flex-direction: column;

			.name{
				font-family: PingFangHK-Medium;
				font-size: 15px;
				font-weight: normal;
				line-height: 16px;
				letter-spacing: 0em;
				color: #FFFFFF;
			}
			.name1{
				font-size: 13px;
				font-weight: normal;
				line-height: 2;
				letter-spacing: 0em;
				color: #FFFFFF;
			}
			.icon{
				margin-left: 5rpx;
				width: 40rpx;
				height: 40rpx;
			}
		}
		.mail-container{
			/* margin-top: 20rpx;
			display: flex; */
			position: absolute;
			white-space: nowrap;
			left: 90px;
			top: 145px;
			width: 103px;
			height: 12px;
			opacity: 1;
			.icon{
				margin-left: 5rpx;
				width: 40rpx;
				height: 40rpx;
				margin-right: 5rpx;
			}
			text{
				font-family: PingFangHK-Regular;
				font-size: 12px;
				font-weight: normal;
				line-height: 12px;
				letter-spacing: 0em;
				color: #FFFFFF;
				/* font-weight: 500;
				font-size: 25rpx;
				margin-right: 30rpx; */
			}
		}
		.text{
			position: absolute;
			left: 28px;
			top: 410px;
			width: 45px;
			height: 14px;
			opacity: 1;
			
			font-family: Source Han Sans CN;
			font-size: 15px;
			font-weight: 500;
			line-height: 14px;
			letter-spacing: 0em;
			
			color: #343333;
		}
		.mail-member{
			position: absolute;
			left: 22px;
			top: 450px;
			width: 59px;
			height: 72px;
			opacity: 1;
			
			
			/* 自动布局 */
			/* display: flex;
			flex-direction: column;
			align-items: center;
			padding: 0px;
			gap: 8px; */
		}
		.text-introduction{
			position: absolute;
			left: 28px;
			top: 290px;
			width: 335px;
			height: 32px;
			opacity: 1;
			text{
				font-family: Source Han Sans CN;
				font-size: 14px;
				font-weight: 500;
				line-height: 14px;
				letter-spacing: 0.05em;
				color: #343333;
				line-height: 1.5;
			}
			
			/* margin-top: 30rpx;
			color: grey;
			letter-spacing: 1rpx; */
			// font-size: 35rpx;
		}
	}
	.bottom{
		position: absolute;
		padding: 50rpx;
		display: flex;
		width: 100%;
		bottom: 0;
		.btn-message{
			position: absolute;
			left: 35px;
			top: -20px;
			width: 120px;
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
		}
		.btn{
			position: absolute;
			left: 220px;
			top: -20px;
			width: 120px;
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