<template>
	<view>
		<youScroll ref="scroll" @onPullDown="onPullDown">
			<view v-for="(item, index) in items" style="padding:30rpx;margin-bottom:15rpx;background-color: #fff;"
				:key="index">
				<view class="infobox" @click="toDetail(item._id)">
					<view class="title">{{item.title}}</view>
					<view style="display:flex;padding-bottom: 10rpx;">
						<view class="headimg">
							<img src="http://first-bucket20201002.oss-cn-hangzhou.aliyuncs.com/img/img/avatar/me.jpg"
								style="height: 100%; width: 100%;" />
						</view>
						<view>
							<view class="name">root1</view>
							<view style="margin-top: 5rpx;flex: 1;padding-left: 30rpx;color: rgb(179, 179, 179);font-size:20rpx">
								0人回答
							</view>
						</view>
					</view>
					<view class="content" style="overflow: hidden;text-overflow: ellipsis;white-space: normal;
				display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical;">{{item.content}}</view>
					<view class="bottominfo">
						<view style="flex:1">求助</view>
						<view style="flex:2;text-align:right">
							{{formatTimestamp(item.createdBy)}} 发布
						</view>
					</view>
				</view>
			</view>
		</youScroll>
		<view class="add" @click="toAdd">提问</view>
	</view>
</template>

<script>
	import youScroll from '@/components/you-scroll.vue'
	import {
		getDiscussionRecommendList
	} from '@/api/discussion.js'
	export default {
		components: {
			youScroll,
		},
		data() {
			return {
				items: [],
				pageNo: 1
			}
		},
		mounted() {

			getDiscussionRecommendList(this.pageNo).then((res) => {
				this.items = res
			})
		},
		methods: {
			onPullDown(done) {
				setTimeout(() => {
					this.pageNo = this.pageNo + 1
					getDiscussionRecommendList(this.pageNo).then((res) => {
						this.items = res
					})
					done();
				}, 1000)
			},
			toAdd() {
				uni.navigateTo({
					url: '/pages/discussion/add'
				});
			},
			toDetail(id) {
				uni.navigateTo({
					url: '/pages/discussion/detail?id=' + id
				});
			},
			addZero(value) {
				return value < 10 ? `0${value}` : value;
			},
			formatTimestamp(timestamp) {
				const date = new Date(parseInt(timestamp));
				const year = date.getFullYear();
				const month = this.addZero(date.getMonth() + 1);
				const day = this.addZero(date.getDate());
				const hours = this.addZero(date.getHours());
				const minutes = this.addZero(date.getMinutes());
				const seconds = this.addZero(date.getSeconds());
				return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
			}
		}
	}
</script>

<style lang="scss">
	.infobox {
		border-bottom: 1px solid #aea9a9
	}

	.title {
		font-size: 30rpx;
		color: rgb(80, 80, 80);
		line-height: 50rpx
	}

	.name {
		margin-top: 5rpx;
		flex: 1;
		padding-left: 30rpx;
		color: rgb(110, 110, 110);
	}

	.headimg {
		width: 80rpx;
		height: 80rpx;
		border-radius: 50%;
		overflow: hidden;
		display: flex;
	}

	.content {
		height: 120rpx;
		color: rgb(124, 124, 124)
	}

	.bottominfo {
		display: flex;
		line-height: 100rpx;
		color: rgb(168, 168, 168)
	}

	.add {
		padding-left: 10rpx;
		padding-top: 20rpx;
		position: fixed;
		background-color: #1296db;
		color: white;
		right: 20px;
		width: 80rpx;
		height: 80rpx;
		border-radius: 50%;
		overflow: hidden;
		bottom: 200rpx;
	}
</style>