<template>
	<view>
		<view style="height: 350rpx;">
			<image class="background" mode="aspectFill" style="" :src="loginUserInfo.background"></image>
			<view style="position: relative;">
				<view class="top-container" @tap="gotoPage(0)">
					<view class="avatar-container" >
						<image class="avatar" :src="loginUserInfo.avatar"></image>
					</view>
					<view style="margin-left: 10rpx;padding: 5rpx;display: block;">
						<view class="name">{{loginUserInfo.nickName}}</view>
						<view v-show="isSocketOpen" style="font-size: 25rpx;color: lawngreen;">在线</view>
						<view v-show="!isSocketOpen" style="font-size: 25rpx;color: red;">离线(请重启app或者刷新网页)</view>
					</view>
				</view>
			</view>
		</view>
		<view>
			<view class="item" @tap="gotoPage(0)">
				<image class="icon" src="/static/icon/person-info.png"></image>
				<view class="text">个人主页</view>
			</view>
			<view class="item" @tap="gotoPage(2)">
				<image class="icon" src="/static/icon/network.png"></image>
				<view class="text">我的空间</view>
			</view>
			<view class="item" @tap="gotoPage(1)">
				<image class="icon" src="/static/icon/setting.png"></image>
				<view class="text">软件设置</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {mapGetters} from 'vuex';
	export default {
		computed:{
			...mapGetters(['loginUserInfo','isSocketOpen'])
		},
		name:"sideview",
		data() {
			return {
				
			};
		},
		methods:{
			gotoPage(index){
				switch(index){
					case 0:
						uni.navigateTo({
							url:'/pages/social/info/person-info'
						})
						break;
					case 1:
					 uni.navigateTo({
					 	url:'/pages/login/config'
					 })
					 break;
					 case 2:
					 uni.navigateTo({
					    url:'/pages/social/components/moment/my-world'
					 })
					 break;
				}
			}
		}
	}
</script>

<style lang="scss" scoped>
	.item{
		display: flex;
		margin-top: 50rpx;
		margin-left: 20rpx;
		.icon{
			width: 40rpx;
			height: 40rpx;
			margin-right: 20rpx;
		}
		.text{
			text-align: center;
			line-height: 40rpx;
		}
	}
	
	.background{
		position: fixed;
		z-index: -1;
		height: 350rpx;
		width: 100%;
		top: -1rpx;
	}
	.top-container{
		position: absolute;
		top: 220rpx;
		left: 20rpx;
		z-index: 10;
		display: flex;
		.avatar-container{
			width: 102rpx;
			height: 102rpx;
			padding: 1rpx;
			background-color: #fff;
			border-radius: 50%;
			text-align: center;
			.avatar{
				width: 100rpx;
				height: 100rpx;
				border-radius: 50%;
			}
		}
		.name{
			font-size: 40rpx;
			color: #fff;
			font-family: "Cambria, Cochin, Georgia, Times, 'Times New Roman', serif";
			word-break: break-all;
		}
	}


</style>