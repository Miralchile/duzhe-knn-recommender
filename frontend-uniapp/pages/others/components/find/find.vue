<template>
	<view>
		<view class="content-new" @click="seat()">
			<text>预约占座</text>
			<image src="/static/social/img/arror.png" @click="seat()"></image>
		</view>
		<view class="content-new" @click="nearby()">
			<text>附近的人</text>
			<image src="/static/social/img/arror.png" @click="nearby()"></image>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				
			}
		},
		methods: {
			seat(){
				uni.navigateTo({
					url:'../social/components/find/seat'
				})
			},
			nearby(){
				uni.navigateTo({
					url:'../social/components/find/nearby'
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
