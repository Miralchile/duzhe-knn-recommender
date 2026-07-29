<template>
	<view>
		<view class="content-new" @click="create()">
			<text>创建读友圈</text>
			<image src="/static/social/img/arror.png" @click="create()"></image>
		</view>
		<view class="content-new" @click="circlelist()">
			<text>我加入的圈</text>
			<image src="/static/social/img/arror.png" @click="circlelist()"></image>
		</view>
		<!-- <block v-for="(item,index) in friendlist" :key="index">
			<view class="content-view" @click="talk()">
				<view class="content-img">
					<image :src="item.pic" mode="aspectFill"></image>
				</view>
				<view class="content-title">
					<text class="content-text">{{item.friend}}</text>
				</view>
			</view>
		</block> -->
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			create(){
				uni.navigateTo({
					url:"../social/components/circle/create"
				})
			},
			circlelist(){
				uni.navigateTo({
					url:"../social/components/circle/circlelist"
				})
			}
		}
	}
</script>

<style scoped>
 @import url("@/common/social/friend.css");
	.content-new image{
		width: 30upx;
		height: 30upx;
		display: flex;
		float: right;
		padding-top: 10upx;
		justify-content: space-between;
		margin: 25upx 0;
	}
</style>
