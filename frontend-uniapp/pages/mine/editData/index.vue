<template>
	<view style="background-color: #e8e8e8;height: 100vh;overflow: hidden;">
		<view style="margin-top: 20rpx; width: 100%;">
			<view style="background-color: #ffffff; width: 100%;display: flex;
			align-items: center; height: 80rpx;border: 1rpx solid #d3d3d3;border-bottom-width: 0;">
				<text style="margin-left: 20rpx;">
					账号资料
				</text>
				<u-icon name="arrow-right" size="30rpx" color="#838383" style="position: absolute;right: 20rpx;"></u-icon>
			</view> 
			
			<view style="background-color: #ffffff; width: 100%;display: flex;
			align-items: center; height: 80rpx;border: 1rpx solid #d3d3d3;border-bottom-width: 0;">
				<text style="margin-left: 20rpx;">
					隐私政策
				</text>
				<u-icon name="arrow-right" size="30rpx" color="#838383" style="position: absolute;right: 20rpx;"></u-icon>
			</view>
			
			<view style="background-color: #ffffff; width: 100%;display: flex;
			align-items: center; height: 80rpx;border: 1rpx solid #d3d3d3;">
				<text style="margin-left: 20rpx;">
					用户协议
				</text>
				<u-icon name="arrow-right" size="30rpx" color="#838383" style="position: absolute;right: 20rpx;"></u-icon>
			</view>
			
			<view style="background-color: #ffffff; width: 100%;display: flex;
			align-items: center; height: 80rpx;border: 1rpx solid #d3d3d3;">
				<text style="margin-left: 20rpx;">
					权限设置
				</text>
				<u-icon name="arrow-right" size="30rpx" color="#838383" style="position: absolute;right: 20rpx;"></u-icon>
			</view>
		</view>
		
		<view style="margin-top: 20rpx; width: 100%;overflow: hidden;">
			<view style="background-color: #ffffff; width: 100%;display: flex;
			align-items: center; height: 80rpx;border: 1rpx solid #d3d3d3;border-bottom-width: 0;">
				<text style="margin-left: 20rpx;">
					播放设置
				</text>
				<u-icon name="arrow-right" size="30rpx" color="#838383" style="position: absolute;right: 20rpx;"></u-icon>
			</view> 
			
			<view style="background-color: #ffffff; width: 100%;display: flex;
			align-items: center; height: 80rpx;border: 1rpx solid #d3d3d3;border-bottom-width: 0;">
				<text style="margin-left: 20rpx;">
					夜间模式
				</text>
			</view>
			
			<view style="background-color: #ffffff; width: 100%;display: flex;
			align-items: center; height: 80rpx;border: 1rpx solid #d3d3d3;">
				<text style="margin-left: 20rpx;">
					其他设置
				</text>
				<u-icon name="arrow-right" size="30rpx" color="#838383" style="position: absolute;right: 20rpx;"></u-icon>
			</view>
		</view>
		
		<view style="margin-top: 20rpx; width: 100%;overflow: hidden;">
			<view style="background-color: #ffffff; width: 100%;display: flex;
			align-items: center; height: 80rpx;border: 1rpx solid #d3d3d3;border-bottom-width: 0;">
				<text style="margin-left: 20rpx;">
					清理缓存
				</text>
				<u-icon name="arrow-right" size="30rpx" color="#838383" style="position: absolute;right: 20rpx;"></u-icon>
			</view> 
			
			<view @click="handleLogout" style="background-color: #ffffff; width: 100%;display: flex;
			align-items: center; height: 80rpx;border: 1rpx solid #d3d3d3;border-bottom-width: 0;">
				<text style="margin-left: 20rpx;" @click="handleLogout">
					退出登录
				</text>
			</view>
			
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		onLoad(){
		},
		methods: {
			handleLogout(){
				uni.navigateTo({
					url:"/pages/login/login"
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	
</style>