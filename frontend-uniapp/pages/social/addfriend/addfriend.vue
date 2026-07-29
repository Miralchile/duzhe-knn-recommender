<template>
	<view class="search-cont">
		<view class="search">
			<image src="/static/social/img/search.png" mode="widthFix" class="search-img"></image>
				<input type="text" placeholder-class="inputclass" confirm-type="search"
				focus="true"
				placeholder="请输入账号/用户名"
				v-model="searchdata"
				@confirm="onKeyInput"/>
		</view>
		<view class="search-code" @click="search()">
			搜索
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				//搜索内容
				searchdata:''
			}
		},
		
		methods: {
			//点击搜索按钮触发
			search(){
				console.log(this.searchdata)
				// let searchkey = this.searchdata
				// this.searchData(searchkey)
			},
			//搜索键盘的触发
			onKeyInput(e){
				console.log(e.detail.value)
				// let searchkey = e.detail.value
				// this.searchData(searchkey)
			},
			//发送请求搜索
			searchData(searchkey){
				let data = {
					searchdata
				}
			}
		}
	}
</script>

<style scoped>
	@import url(/common/social/friend.css);
    .content-left{margin: 0 20upx;}
	.search{
		height: 70upx;
		line-height: 70upx;
		width: 100%;
		display: flex;
		flex-direction: row;
		background: #f8f8f8;
		border-radius: 20rpx;
	}
	.search-img{
		margin: auto 0 auto 20upx;
		width: 40upx;
		height: 40upx;
	}
	.search-input{
		height: 70upx;
		line-height: 70upx;
		width: 100%;
		font-size: 30upx;
		color: #666666;
		margin: 30upx !important;
		padding-left: 50upx;
	}
	.search-cont{
		display: flex;
		justify-content: space-between;
		height: 70upx;
		align-items: center;
	}
	.search-code{
		width: 150upx;
		height: 50upx;
		text-align: center;
		font-size: 30upx;
	}
</style>
