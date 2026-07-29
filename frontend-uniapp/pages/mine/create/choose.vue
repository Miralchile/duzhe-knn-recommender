<template>
	<view>
		<view style="display: flex;justify-content: center;" @click="createImage">
			<view style="margin-top:30rpx;height: 200rpx; width: 700rpx;background-color: #f5f5f5;
			border: 1rpx solid #dfdfdf;display: flex;align-items: center;border-radius: 10px;">
				<u-icon name="photo-fill" size="60rpx" color="#55aaff" style="margin-left: 30rpx;"></u-icon>
				<u-text text="创建图文" size="20" color="#222222" :bold="true"></u-text>
				<u-icon name="arrow-right" size="40rpx" color="#000000" style="position: absolute;right: 40rpx;"></u-icon>
			</view>
		</view>
		
		<view style="display: flex;justify-content: center;" @click="createVideo">
			<view style="margin-top:30rpx;height: 200rpx; width: 700rpx;background-color: #ffffff;
			border: 1rpx solid #dadada;display: flex;align-items: center;border-radius: 10px;">
				<u-icon name="camera-fill" size="60rpx" color="#00aa00" style="margin-left: 30rpx;"></u-icon>
				<u-text text="上传视频" size="20" color="#222222" :bold="true"></u-text>
				<u-icon name="arrow-right" size="40rpx" color="#000000" style="position: absolute;right: 40rpx;"></u-icon>
			</view>
		</view>
		
		<view style="display: flex;justify-content: center;" @click="manageWork">
			<view style="margin-top:30rpx;height: 200rpx; width: 700rpx;background-color: #f5f5f5;
			border: 1rpx solid #dfdfdf;display: flex;align-items: center;border-radius: 10px;">
				<u-icon name="file-text-fill" size="60rpx" color="#ffaa00" style="margin-left: 25rpx;margin-right: 5rpx;"></u-icon>
				<u-text text="作品管理" size="20" color="#222222" :bold="true"></u-text>
				<u-icon name="arrow-right" size="40rpx" color="#000000" style="position: absolute;right: 40rpx;"></u-icon>
			</view>
		</view>
		
		<view style="display: flex;justify-content: center;">
			<view>
				<hr style="border-top: 1px solid #ccc;margin-top:100rpx; width:700rpx;" />
				<u-checkbox :customStyle="{marginBottom: '8px'}"
				            label="我已阅读并同意隐私政策"
							shape="circle"
							labelSize="12"
							size="12"
							checked
							labelColor="#aaaaaa"
							style="margin-top: 20rpx;margin-left: 190rpx;">
				</u-checkbox>
			</view>
			
		</view>
		
		<!-- 底部信息 -->
		
		
	</view>
</template>

<script>
	export default {
		data() {
			return {}
		},
		methods:{
			createImage(){
				uni.navigateTo({
					url: '/pages/mine/create/imageCreate'
				});
			},
			createVideo(){
				uni.navigateTo({
					url: '/pages/mine/create/videoCreate'
				});
			},
			manageWork(){
				uni.navigateTo({
					url: '/pages/mine/create/manageWork'
				});
			}
		}
	}
</script>

<style>
</style>
