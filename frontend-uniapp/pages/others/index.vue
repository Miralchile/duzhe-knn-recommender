<template>
	<view>
		<view class="head">
			<view class="info">
				<view class="user">
					<image :src="avatarUrl"></image>
					<view class="name">
						<text>{{userName}}</text>
					    <text>ID: weixin-technology</text>
					</view>
				</view>
				<view class="btn" v-if="!isFollowFlag" @click="clickFollow()">关注作者</view>
				<view class="btnGrey" v-if="isFollowFlag" @click="clickCancelFollow()">已关注</view>
			</view>
		</view>
		<view class="menu-box">
			<view class="region">
				<view class="menu-row">
					<view class="cell">
						<view class="count">{{page.total}}</view>
						<view class="name">作品数</view>
					</view>
					<view class="cell">
						<view class="count">0</view>
						<view class="name">关注者</view>
					</view>
					<view class="cell">
						<view class="count">0</view>
						<view class="name">已关注</view>
					</view>
				</view>
			</view>
		</view>
		<view class="top2" >
			<view class="card2" v-for="(item,index) in items" :key="index">
				<view v-if="item.type == 1" style="height: 100%;" @click="gotoVideo(item._id)">
					<view style="width: 100%;height: 65%;">
						<view v-if="!loadStatus[index]" class="loading-container">
							<i class="loading-icon"></i>
						</view>
						<image mode="aspectFit" :src="item.coverImageUrlNew" @load="loadImage(index)"
							class="card2-img"></image>
						<u--text color="white" :text="item.durationString" style="position: relative;left:240rpx;bottom: 85rpx;
				z-index: 5;height: 15%;width: 25%;background-color: black;opacity: 0.5;" align="center"></u--text>
					</view>
					<view>
						<view
							style="position: relative;top:13rpx;left: 12rpx;overflow: hidden;text-overflow: ellipsis;">
							<span v-html="item.title" style="position: relative; font-size: 13px;overflow：hidden;text-overflow：ellipsis;
							white-space: nowrap;"></span>
						</view>
						<view style="position: relative;top: 28rpx;z-index: 5;">
							<uni-tag :circle="true" :text="item.category" size="small"
								style="color:#000000;border:0rpx;font-size: 10px;background-color: #ebebeb;margin-left: 15rpx;" />
							<uni-tag :circle="true" :text="item.channel" size="small"
								style="color:#000000;border:0rpx;font-size: 10px;background-color: #ebebeb;margin-left: 15rpx;" />
						</view>
					</view>
				</view>
		
				<view v-if="item.type == 2" style="height: 100%;" @click="gotoImage(item._id)">
					<view style="width: 100%;height: 65%;">
						<view v-if="!loadStatus[index]" class="loading-container">
							<i class="loading-icon"></i>
						</view>
						<image v-show="loadStatus[index]" :src="item.coverImageUrlNew" @load="loadImage(index)"
							class="card2-img"></image>
						<uni-tag v-show="loadStatus[index]" :circle="true" text="图文" type="primary" style="position: relative;left:259rpx;bottom: 87rpx;
				        z-index: 5;height: 15%;width: 22%;text-align: center;" size="small" />
					</view>
					<view>
						<view
							style="position: relative;top:13rpx;left: 12rpx;overflow: hidden;text-overflow: ellipsis;width:95%;">
							<span v-html="item.title" style="position: relative; font-size: 13px;overflow：hidden;text-overflow：ellipsis;
							white-space: nowrap;"></span>
						</view>
						<view style="position: relative;top: 28rpx;z-index: 5;">
							<uni-tag :circle="true" :text="item.category" size="small"
								style="color:#000000;border:0rpx;font-size: 10px;background-color: #ebebeb;margin-left: 15rpx;" />
							<uni-tag :circle="true" :text="item.channel" size="small"
								style="color:#000000;border:0rpx;font-size: 10px;background-color: #ebebeb;margin-left: 15rpx;" />
						</view>
					</view>
				</view>
			</view>
		</view>
		<pagePagination :total="page.total" style="margin-top:20rpx" :showAround="true" :btnText="true"
			:forceEllipses="true" @change="change" :pageSize="page.pageSize" :currentPage="page.currentPage">
		</pagePagination>
	</view>
</template>
<script>
	import { getOthersById, getOthersWorkListTotal, isFollowThisUser, followThisUser, cancelFollowThisUser } from '@/api/others.js'
	import { getCurrentUser } from '@/api/mine.js'
	import pagePagination from '@/components/page-pagination/page-pagination.vue'
	export default {
		components: {
			pagePagination
		},
		data() {
			return {
				isFollowFlag: false,
				currentUserId: undefined,
				userId: undefined,
				userName: undefined,
				avatarUrl: undefined,
				createdby: undefined,
			    updatedby: undefined,
				items: [],
				loadStatus: [false, false, false, false, false, false, false, false],
				page: {
					total: 10, //总页数
					pageSize: 6, //每页条数
					currentPage: 1 //当前页
				}
			}
		},
		onLoad(option) {
			getOthersById(option.userId,this.page.currentPage,this.page.pageSize).then((res)=>{
				this.userId = res.id
				this.userName = res.userName
				this.avatarUrl = res.avatarUrl
				this.createdby = res.createdby
				this.updatedby = res.updatedby
				this.items = res.workList
				for (var i = 0; i < this.items.length; i++) {
					var duration = this.items[i].duration
					var seconds = Math.floor(duration / 1000);
					var hours = Math.floor(seconds / 3600); // 计算小时数
					seconds %= 3600; // 去掉小时数后剩余的部分转换为分钟
					var minutes = Math.floor(seconds / 60); // 计算分钟数
					var seconds = seconds % 60; // 去掉分钟数后剩余的秒数
					hours = ("0" + hours).slice(-2);
					minutes = ("0" + minutes).slice(-2);
					seconds = ("0" + seconds).slice(-2);
					this.items[i].durationString = minutes + ':' + seconds
					this.items[i].coverImageUrlNew = this.items[i].coverImage + "?t=" + (new Date().getTime())
				}
			});
			getOthersWorkListTotal(option.userId).then((res)=>{
				this.page.total = res
			});
			isFollowThisUser(option.userId).then((res)=>{
				this.isFollowFlag = res
			});
			getCurrentUser().then((res) => {
				this.currentUserId = res.id
			})
		},
		methods: {
			gotoVideo(id) {
				uni.navigateTo({
					url: '/pages/works/videoPlay?id=' + id
				});
			},
			gotoImage(id) {
				uni.navigateTo({
					url: '/pages/works/imageContext?id=' + id
				});
			},
			clickFollow(){
				if(this.currentUserId == this.userId){
					uni.showToast({
						title: "只能关注其他用户",
						icon: "none"
					})
				} else{
					followThisUser(this.userId).then(()=>{
						isFollowThisUser(this.userId).then((res)=>{
							this.isFollowFlag = res
						})
						uni.showToast({
							title: '关注成功',
							icon: 'none'
						})
					})
				}
			},
			clickCancelFollow(){
				cancelFollowThisUser(this.userId).then(()=>{
					isFollowThisUser(this.userId).then((res)=>{
						this.isFollowFlag = res
					})
					uni.showToast({
						title: '取消关注',
						icon: 'none'
					})
				})
			},
			loadImage(index) {
				this.$set(this.loadStatus, index, true);
			},
			change(currentPage, type){
				getOthersById(this.userId, currentPage,this.page.pageSize).then((res) => {
					this.items = res.workList
					for (var i = 0; i < this.items.length; i++) {
						var duration = this.items[i].duration
						var seconds = Math.floor(duration / 1000);
						var hours = Math.floor(seconds / 3600); // 计算小时数
						seconds %= 3600; // 去掉小时数后剩余的部分转换为分钟
						var minutes = Math.floor(seconds / 60); // 计算分钟数
						var seconds = seconds % 60; // 去掉分钟数后剩余的秒数
						hours = ("0" + hours).slice(-2);
						minutes = ("0" + minutes).slice(-2);
						seconds = ("0" + seconds).slice(-2);
						this.items[i].durationString = minutes + ':' + seconds
						this.items[i].coverImageUrlNew = this.items[i].coverImage + "?t=" + (new Date().getTime())
					}
				})
			},
		}
	}
</script>

<style lang="scss">
	page {
		background-color: #e8e8e8;
	}

	.head {
		position: relative;
		width: 100%;
		height: 300rpx;
		background-color: #55aaff;
	}

	.head .info {
		padding-top: 70rpx;
		width: 100%;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.head .info .user {
		padding-left: 50rpx;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.head .info .user image {
		width: 120rpx;
		height: 120rpx;
	}

	.head .info .user .name {
		padding-left: 20rpx;
		font-size: 28rpx;
		font-weight: 600;
		color: #FFFFFF;
		display: flex;
		justify-content: center;
		align-items: flex-start;
		flex-direction: column;
	}

	.head .info .user .name text {
		line-height: 40rpx;
	}

	.head .info .btn {
		text-align: center;
		background-color: #ff557f;
		color: #FFFFFF;
		font-size: 26rpx;
		width: 150rpx;
		height: 70rpx;
		line-height: 80rpx;
		border-radius: 10rpx;
		margin-right: 40rpx;
	}
	
	.head .info .btnGrey {
		text-align: center;
		background-color: #afafaf;
		color: #FFFFFF;
		font-size: 26rpx;
		width: 150rpx;
		height: 70rpx;
		line-height: 80rpx;
		border-radius: 10rpx;
		margin-right: 40rpx;
	}

	.menu-box {
		position: absolute;
		top: 200rpx;
		width: 100%;
	}

	.region {
		background-color: #FFFFFF;
		margin: 30rpx;
		border-radius: 10rpx;
	}

	.region .title {
		font-size: 32rpx;
		font-weight: bold;
		padding: 30rpx;
	}

	.region .menu-row {
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.region .menu-row .cell {
		width: 25%;
		margin-left: 35rpx;
		margin-right: 35rpx;
		height: 110rpx;
		display: flex;
		justify-content: center;
		align-items: center;
		flex-direction: column;
	}

	.region .menu-row .cell .count {
		font-size: 35rpx;
	}

	.region .menu-row .cell .name {
		font-size: 27rpx;
		color: #8a8a8a;
	}
	
	.loading-container {
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		background-color: #e8e8e8;
	}
	
	.loading-icon {
		border-radius: 50%;
		width: 40px;
		height: 40px;
		border: 4px solid #ccc;
		border-top-color: #2196f3;
		animation: spin 1s ease-in-out infinite;
	}
	
	.top2 {
		background-color: #e8e8e8;
		height: 100%;
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		width: 100%;
		align-items: center;
		border-radius: 5px;
		margin-top: 45rpx;
	}
	
	.card2 {
		background-color: #FFFFFF;
		margin-left: 3%;
		margin-top: 20rpx;
		display: flex;
		flex-direction: column;
		width: 45.5%;
		height: 300rpx;
		border-radius: 5px;
	}
	
	.card2-img {
		border-radius: 5px 5px 0 0;
		margin-bottom: 30rpx;
		width: 100%;
		height: 100%;
		background-color: #e8e8e8;
	}
	
</style>
