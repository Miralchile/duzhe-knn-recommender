<template>
	<view>
		<!--搜索框-->
		<view class="search-cont">
			<view class="search" @click="search()">
				<image src="/static/social/icon/search.png" mode="aspectFill" class="search-img" width='40rpx' height='40rpx' ></image>
				<input  placeholder="搜索" />
		</view>
		</view>
		<view class="square-content">
			<view class="square-content-item" v-for="item in contentList">
			 <view class="content-item" >
				<view class="content-item-title">
					{{item.title}}
				</view>
			</view>
		     </view>
	</view>
</view>
	
</template>

<script>
	export default {
		data() {
			return {
				contentList:[
					{title:'教育'},
					{title:'美食'},
					{title:'娱乐'},
					{title:'学习'},
					{title:'其他'},
			]
		}
	}
	}
</script>

<style>
	/*搜索*/
	.search-img{
		margin: auto 0 auto 20rpx;
		width: 40rpx;
		height: 40rpx;
	}
	.addfriend-img{
		justify-content: flex-end;
		margin:  40rpx 80rpx 40rpx 40rpx; 
		width: 40rpx;
		height: 40rpx;
	}
	.search{
		height: 70rpx;
		line-height: 70rpx;
		width: 100%;
		display: flex;
		flex-direction: flex;
		background: #eff3f4;
		border-radius: 10rpx;
	}
	.search-input{
		height: 70rpx;
		line-height: 70rpx;
		width: 100%;
		font-size: 70rpx;
		align-items: center;
	}
	.search-cont{
		display: flex;
		justify-content: space-between;
		height: 70rpx;
		align-items: center;
	}
	.square-content{
		display: flex;
		flex-direction:row;
		justify-content: space-around;
		flex-wrap: wrap;
	}
   .square-content-item{
    margin-top:30rpx ;
	width: 200rpx;
	height: 100rpx;
	color: #4fa3f9;
	border: 1px solid #f9f9f9;
	border-radius: 8rpx;
}

   .content-item{
	display: flex;
	padding-top: 30rpx;
	flex-direction:column;
	align-items:center;
}
.content-item-title{
	text-align: center;
}
.content-item-new-topic{
	color:#6b644d ;
	margin-top: 10rpx;
}
   .content-item-jion-topic{
	width: 140rpx;
	height: 40rpx;
	margin-top: 20rpx;
	text-align: center;
	color: #1ed76d;
	border: 1px solid #1ed76d;
}
</style>
