<template>
	<view>
		<view style="color: darkgrey;margin-left: 20rpx;font-size: 14px;margin-top: 20rpx; font-family: PingFangHK-Regular;font-weight: normal;">历史通知消息</view>
		<u-index-list :indexList="indexList">
			<view slot="header" class="list">
				<view v-for="(item,index) in noticeList" :key="index">
					<u-row class="list__item" customStyle="margin-bottom: -1px; margin-top:3px;widdth:100%;">
					    <u-col @tap="openDetal(item)" span="1.5">
					        <u-avatar shape="circle" size="40" :icon="showIcon(item.type)" fontSize="26" bgColor="#96BD46"></u-avatar>
					    </u-col>
					    <u-col @tap="openDetal(item)" span="6" class="list__item__user-name">
					        <text class="list__item__user-name" style="font-size: 25rpx;">{{showDetail(item)}}</text>
					    </u-col>
						<u-col span="3">
						    <view class="buttons" style="width: 40%;display: flex;">
								<u-button @tap="check(item.id)" v-if="!item.isRead&&item.type==='reject'" type="primary" size="small" customStyle="width:20%">已读</u-button>
								<u-button @tap="deleteNotice(item.id)" v-if="item.isRead" style="background-color:  #909399;color: #fff; left: 80%" size="small" customStyle="width:20%;">删除</u-button>
						    	<u-button @tap="agree(item)" v-if="!item.isRead&&item.type!=='reject'" type="success" size="small" customStyle="width:20%">同意</u-button>
						    	<u-button @tap="reject(item.id)" v-if="!item.isRead&&item.type!=='reject'" type="error" size="small" customStyle="width:20%;margin-left:20rpx;">拒绝</u-button>
						    </view>
						</u-col>
					</u-row>
					<u-line></u-line>
				</view>
			
				
			</view>
		</u-index-list>
		
	</view>
</template>

<script>
	import {mapGetters} from 'vuex';
	import userRequest from '@/api/social/user.js';
	// import $store from '@/store/modules/social/test.js';
	import $store from '@/store/modules/social';
	export default{
		computed:{
			...mapGetters(['noticeList','loginUserInfo'])
		},
		data(){
			return{
				indexList:['']
			}
		},
		onPullDownRefresh() {
			///从新获取消息列表
			$store.dispatch('getNoticeList')
			setTimeout(()=>{
				uni.stopPullDownRefresh()
			},100)
		},
		onLoad() {
			console.log('进入通知页面')
			console.log(this.noticeList)
			console.log(this.loginUserInfo.nickName)
		},
		methods:{
			async check(id){
				let res = await userRequest.checkNotice({
					id:id
				});
				$store.dispatch('getNoticeList')
			},
			async reject(id){
				let res = await userRequest.rejectNotice({
					id:id,
					senderName:this.loginUserInfo.nickName
				});
				uni.showToast({
					title:'操作成功',
					icon:'success'
				})
				$store.dispatch('getNoticeList')
			},
			async deleteNotice(id){
				let res = await userRequest.deleteNotice({id:id})
				uni.showToast({
					title:'删除成功',
					icon:'success'
				})
				$store.dispatch('getNoticeList')
			},
			agree(item){
				if(item.type=='person-apply'){
					this.agreePersonApply(item.id)
					return
				}
			},
			agreePersonApply(id){
				///构造websocket消息
				let msg = {
					 type: 'person-apply-agree',
					 data: {
						 id:id
					 }
				}
				uni.sendSocketMessage({
					data: JSON.stringify(msg),
					fail(res) {
						uni.showToast({
							icon:'error',
							title:'操作失败'
						})
					}
				})
			},
			showIcon(type){
				if(type=='person-apply')	return 'man-add-fill';
				else if(type=='group-invite') return 'chat-fill';
				else return 'person-delete-fill';
			},
			openDetal(item){
				if(item.type==='person-apply'){
					uni.navigateTo({
						url:'/pages/info/friend-info?id='+item.senderId
					})
					return
				}
			},
			showDetail(item){
				let info = JSON.parse(item.information)
				switch(item.type){
					case 'person-apply':
					return '验证信息:'+info.applyMessage
					break;
					case 'group-invite':
					return '入群邀请(来自:'+info.senderName+')'
					break;
					case 'reject':
					return '拒绝消息(来自:'+info.senderName+')'
					break;
				}
			},
			test1(){
				console.log('test1')
			},
			test2(){
				console.log('test2')
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
	.list__item__user-name{
		overflow: scroll;
		word-break: break-all;
		display: block;
		max-height: 40rpx;
	}
	.list__item:active{
		
	}
	.press-area:active{
		background-color: #e8e8e8;
	}
	.press-area{
			height: 100%
	}
	.list {
		
		&__item {
			@include flex;
			padding: 6px 12px;
			align-items: center;
			
			&__avatar {
				height: 40px;
				width: 40px;
				border-radius: 3px;
			}
			
			&__user-name {
				font-size: 16px;
				margin-left: 10px;
				color: $u-main-color;
			}
		}
		
		&__footer {
			color: $u-tips-color;
			font-size: 14px;
			text-align: center;
			margin: 15px 0;
		}
	}
	.u-safe-area-inset--bottom{
		height: 150rpx;
		text-align: center;
	}
</style>