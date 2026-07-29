<template>
	<view class="tool_content">
		<!-- <view class="author">
			<image class="img" src="../../static/mine1.png" ></image>
			<view class="add" >+</view>
		</view> -->
		<u-icon name="heart" color="#ffffff" size="40" class="heart"></u-icon>
		<!-- <div class="number" >100</div> -->
		<u-icon name="chat" color="#ffffff" size="40" class="chat"></u-icon>
		<!-- <div class="number">100</div> -->
		<!-- <div class="iconf iconfont icon-fenxiang1">
			<div class="number">100</div>
		</div> -->
		<!-- <view class="rote">
			<image :class="[{img:true},{route:true},{routeStatus:status}]" src="../../static/logo.png" mode=""></image>
		</view> -->
	</view>
</template>
status
<script>
	export default {
		props:{
			status:{
				type:Boolean,
				default:true,
			}
		}
	}
</script>

<style scoped>
	.tool_content {
		width: 100%;
		height: 7%;
		bottom: 0px;
		display:flex;
		flex-direction: row;
	}
	.heart {
		bottom: 0px;
		width: 20%;
		height: 5%;
	}
	.chat {
		bottom: 0px;
		width: 20%;
		height: 5%;
	}
	.number {
		width: 20%;
		height: 5%;
	}
</style>
