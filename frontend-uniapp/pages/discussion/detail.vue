<template>
	<view class="detail">
		<view class="detail-title">
			问题：{{title}}
		</view>
		<view
			style="background-color: #fafafa;position: relative;top:30rpx; width: 600rpx; margin: 0 auto;height: 120rpx;">
			<u-row style="padding: 20rpx;">
				<u-col span="2">
					<view>
						<img class="yunshu"
							src="http://first-bucket20201002.oss-cn-hangzhou.aliyuncs.com/img/img/avatar/me.jpg" />
					</view>
				</u-col>
				<u-col span="7.5">
					<view>
						<span class="author">root1</span><br />
						<span class="time">{{formatTimestamp(createdBy)}}</span>
					</view>
				</u-col>
			</u-row>
		</view>
		<view class="detail-content">
			<text>{{content}}</text>
		</view>
		<view style="margin-top: 30rpx;">
			<view style="display: flex;justify-content: flex-start;">
				<image v-for="(imageUrl, index) in this.imageUrlList" style="height: 200rpx;width: 200rpx;"
				 @click="previewImage(index)" mode="aspectFill" :src="imageUrl"></image>
			</view>
		</view>
		<view class="top-bar-detail flex-js-ac" style="margin-top: 30rpx;">
		
			<view class="reply-box flex-jc-ac-col">
				<view @click="topBarCheck(1)" class="reply-text" :class="{'top-bar-select' : tobarIndex == 1}">
					最佳回答
				</view>
			</view>
			<view class="reply-box flex-jc-ac-col">
				<view @click="topBarCheck(2)" class="reply-text" :class="{'top-bar-select' : tobarIndex == 2}">
					全部回答
				</view>
			</view>
			
		</view>
		<view class="reply-box-2" v-if="tobarIndex == 1">
			<discussionBestAnswer :message="this.id"></discussionBestAnswer>
		</view>
		<view class="reply-box-2" v-if="tobarIndex == 2">
			<discussionAnswer :message="this.id"></discussionAnswer>
		</view>
	</view>
</template>

<script>
	import parser from '@/components/parser/parser.vue';
	import discussionAnswer from '@/pages/discussion/discussionAnswer.vue';
	import discussionBestAnswer from '@/pages/discussion/discussionBestAnswer.vue';
	import { getDiscussionById } from '@/api/discussion.js'
	
	export default {
		components: {
			parser,
			discussionAnswer,
			discussionBestAnswer
		},
		data() {
			return {
				id: undefined,
				content: undefined,
				showChat: false,
				tobarIndex:1,
				content: undefined,
				title: undefined,
				imageUrlList: [],
				createdBy: undefined
			}
		},
		onLoad(option) {
			this.id = option.id
			getDiscussionById(this.id).then((res)=>{
				this.createdBy = res.createdBy
				this.content = res.content
				this.title = res.title
				this.imageUrlList = res.imageUrlList
			})
		},
		methods: {
			topBarCheck(index) {
				this.tobarIndex = index
			},
			previewImage(index){
				uni.previewImage({
				    current: index,
				    urls: this.imageUrlList
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
				return `${year}-${month}-${day}`;
			}
		}
	};
</script>

<style>
	@import '@/components/hm-news-detail/index.response.css';

	.detail {
		padding: 40rpx 32rpx;
		background: #fff;
	}

	.detail-title {
		font-size: 44rpx;
		color: #262626;
		line-height: 70rpx;
		font-weight: bold;
	}

	.picture {
		display: flex;
		margin-right: 30rpx;
	}

	.detail-header {
		margin-top: 32rpx;
		display: flex;
		flex-direction: row;
		justify-content: space-between;
		align-items: center;

		.detail-header-content {
			width: 100%;
			padding: 10px;
			display: flex;
			flex-direction: column;
			justify-content: space-between;
			font-size: 12px;

			.detail-author {
				font-size: 30rpx;
				color: #333;
			}

			.detail-info {
				margin-top: 32rpx;
				display: flex;
				flex-direction: row;
				justify-content: space-between;
				align-items: center;

				text {
					margin-right: 10px;
					font-size: 30rpx;
					color: #999;
				}
			}
		}
	}

	.detail-content {
		overflow-y: auto;
		bored: 1px red solid;
		margin-top: 56rpx;
		overflow: hidden;
		color: #262626;
		font-size: 30rpx;

	}

	.detail-bottom_icons {
		margin-top: 56rpx;
		display: flex;
		flex-shrink: 0;
		padding: 0px 10px;


	}

	.dianzan {
		display: flex;
		justify-content: center;
		/* 水平居中 */
		align-items: center;
		/* 垂直居中 */
		width: 125rpx;
		border-radius: 25px;
		border: 1px solid #ccc
	}

	.zhuanfa {
		display: flex;
		justify-content: center;
		/* 水平居中 */
		align-items: center;
		/* 垂直居中 */
		width: 125rpx;
		border-radius: 25px;
		border: 1px solid #ccc
	}

	.shouchang {
		display: flex;
		justify-content: center;
		/* 水平居中 */
		align-items: center;
		/* 垂直居中 */
		width: 125rpx;
		border-radius: 25px;
		border: 1px solid #ccc
	}
	
	.top-bar-detail {
		background-color: #FFFFFF;
		height: 75rpx;
		padding-left: 0rpx;
		border-bottom: #EEEEEE 2rpx solid;
	}
	
	.reply-box {
		margin-left: 45rpx;
	}
	
	.top-bar-select {
		color: #00aaff;
		border-bottom: #00aaff 5rpx solid;
	}
</style>
