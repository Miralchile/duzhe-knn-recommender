<template>
	<view class="detail">
		<view class="detail-title">
			{{title}}
		</view>
		<view
			style="background-color: #fafafa;position: relative;top:30rpx; width: 600rpx; margin: 0 auto;height: 120rpx;">
			<u-row style="padding: 20rpx;">
				<u-col span="2">
					<view @click="clickAvatar()">
						<img class="yunshu"
							:src="this.authorAvatarUrl" />
					</view>
				</u-col>
				<u-col span="7.5">
					<view>
						<span class="author">{{authorName}}</span>
						<u-text mode="date" :text="this.createdBy" size="14" type="信息" class="time"></u-text>
					</view>
				</u-col>
				<u-col span="2.5">
					<view>
						<u-tag text="+关注"></u-tag>
					</view>
				</u-col>
			</u-row>
		</view>
		
		<view class="detail-content">
			<parser :html="this.content"></parser>
		</view>
		
		<view style="margin-top: 56rpx;padding: 0px 10px;">
			<u-row>
				<u-col span="4">
					<button class="dianzan" type="default" style="width:180rpx" @click="thumbUp">
						<u-icon name="thumb-up" size="64rpx" v-if="!isThumb"></u-icon>
						<u-icon name="thumb-up-fill" size="64rpx" color="#ffaaff" v-if="isThumb"></u-icon>
						<text style="font-size: 30rpx">{{thumbNum}}</text>
					</button>
				</u-col>
				<u-col span="4">
					<button class="shouchang" type="default" style="width:180rpx" @click="clickCollect">
						<u-icon name="star" size="64rpx" v-if="!isCollection"></u-icon>
						<u-icon name="star" size="64rpx" color="#ffaa00" v-if="isCollection"></u-icon>
						<text style="font-size: 30rpx">收藏</text>
					</button>
				</u-col>
				<u-col span="4">
					<button class="zhuanfa" type="default" style="width:180rpx" @click="handleShareImg(articleDetail)">
						<u-icon name="share" size="64rpx"></u-icon>
						<text style="font-size: 26rpx">转发</text>
					</button>
				</u-col>
			</u-row>
		</view>
		
		<!-- 分类 -->
		<view style="margin-top: 50rpx;display: flex;flex-wrap: wrap;">
			<text style="position: relative;left: 30rpx;top:3rpx;width: 120rpx;font-size: 27rpx;
			color: #7c7c7c;">分类:</text>
			<uni-tag :text="category" style="width: 112rpx;text-align: center;margin-right: 33rpx;
			background-color: #55aaff;color: #ffffff;border-radius: 10px;" />
			<uni-tag :text="channel" style="width: 112rpx;text-align: center;margin-right: 33rpx;
			background-color: #55aaff;color: #ffffff;border-radius: 10px;" />
		</view>
		
		<!-- 标签 -->
		<view style="margin-top: 20rpx;display: flex;flex-wrap: wrap;">
			<text style="position: relative;left: 30rpx;width: 120rpx;font-size: 27rpx;
			color: #7c7c7c;top:3rpx;">标签:</text>
			<uni-tag v-for="(item, index) in tagList" :key="index" :text="item" 
			style="width: 112rpx;text-align: center;margin-right: 33rpx;
			background-color: #dfdfdf;color: #000000;border-radius: 10px;" />
		</view>
		<view class="border-class">
		</view>
		
		<view class="top-bar-detail flex-js-ac" style="margin-top: 30rpx;display:flex;flex-wrap: nowrap;">
		
			<view class="reply-box flex-jc-ac-col">
				<view @click="topBarCheck(1)" class="reply-text" :class="{'top-bar-select' : tobarIndex == 1}">
					评论
				</view>
			</view>
		
			<view class="reply-box flex-jc-ac-col">
				<view @click="topBarCheck(2)" class="reply-text" :class="{'top-bar-select' : tobarIndex == 2}">
					相关推荐
				</view>
			</view>
		</view>
		
		<view class="reply-box-2" v-show="tobarIndex == 1">
			<imageWorkComment v-if="this.showChat" :message="this.id"></imageWorkComment>
		</view>
		
		<view v-show="tobarIndex == 2">
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
		</view>
		
		<u-popup :show="isShowCollectionFolder" @close="close">
			<view class="page" style="height:600rpx;overflow:scroll;">
				<view class="content">
					<view class="list" v-if="folderList.length > 0">
						<view :tag="item.id" class="item" v-for="(item, index) in folderList" :key="index"
							hover-class="none">
							<view class="info" @click="selectFolder(item.id)">
								<view class="photo">
									<image :src="item.folderCoverImage" mode="aspectFill"></image>
								</view>
								<view class="text">
									<view class="folder-title">{{ item.folderName }}</view>
									<view class="other">
										<view class="right view">
											<text>作品数 {{item.workNum}}</text>
										</view>
									</view>
								</view>
							</view>
						</view>
					</view>
				</view>
			</view>
		</u-popup>
		
	</view>
</template>

<script>
	import parser from '@/components/parser/parser.vue';
	import UniShare from '@/uni_modules/uni-share/js_sdk/uni-share.js';
	import imageWorkComment from './imageWorkComment.vue';
	import { getCurrentUser } from '@/api/mine.js';
	import {
		getImageWorkById,
		addOrDeleteImageWorkThumbById,
		checkIsImageWorkThumb,
		getImageWorkThumbNumById,
		getImageWorkCommentNumById
	} from '@/api/imageContext';
	import {
		getFolderListByCurrentUser,
		addColletionToFolder,
		checkIsCollection,
		deleteCollectionByUserIdAndWorkId
	} from '@/api/collection.js';
	import {
		getRelation
	} from '@/api/videoPlay.js'
	const uniShare = new UniShare();
	export default {
		onBackPress({
			from
		}) {
			console.log(from);
			if (from == 'backbutton') {
				this.$nextTick(function() {
					uniShare.hide()
				})
				return uniShare.isShow;
			}
		},
		components: {
			parser,
			imageWorkComment
		},
		data() {
			return {
				items: [],
				loadStatus: [false, false, false, false, false, false, false, false],
				showRelation: false,
				tobarIndex: 1,
				content: undefined,
				title: undefined,
				id: undefined,
				commentNum: 0,
				thumbNum: 0,
				isThumb: false,
				createdBy: undefined,
				authorName: undefined,
				authorAvatarUrl: undefined,
				views: 0,
				folderList: [],
				category: undefined,
				channel:undefined,
				tagList:[],
				isCollection: false,
				isShowCollectionFolder: false,
				thumbUpForm:{
					id:undefined
				},
				collectionForm: {
					workId: undefined,
					folderId: undefined
				},
				userId: undefined,
				showChat: false
			}
		},
		onLoad(option) {
			getImageWorkById(option.id).then((res) => {
				let data = res
				this.title = data.title;
				this.createdBy = data.createdBy;
				this.authorName = data.authorName;
				this.authorAvatarUrl = data.authorAvatarUrl;
				this.userId = data.author;
				this.id = data._id;
				this.views = data.views;
				this.content = data.content;
				this.category = data.category;
				this.channel = data.channel;
				this.tagList = data.tagList;
				this.showChat = true;
			});
			checkIsImageWorkThumb(option.id).then((res) => {
				this.isThumb = res;
			});
			getImageWorkThumbNumById(option.id).then((res) => {
				this.thumbNum = res;
			});
			getImageWorkCommentNumById(option.id).then((res) => {
				this.commentNum = res;
			});
			checkIsCollection(option.id).then((res) => {
				this.isCollection = res;
			});
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
			loadImage(index) {
				this.$set(this.loadStatus, index, true);
			},
			clickAvatar(){
				uni.navigateTo({
					url: '/pages/others/index?userId=' + this.userId
				});
			},
			topBarCheck(index) {
				this.tobarIndex = index
				if (index == 2) {
					getRelation(this.category).then((res) => {
						this.items = res;
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
					this.showRelation = true;
				}
			},
			thumbUp() {
				this.thumbUpForm.id = this.id
				addOrDeleteImageWorkThumbById(this.thumbUpForm).then(() => {
					checkIsImageWorkThumb(this.id).then((res) => {
						if (this.isThumb == true) {
							uni.showToast({
								title: "取消点赞",
								icon: "success"
							})
						} else {
							uni.showToast({
								title: "点赞成功",
								icon: "success"
							})
						}
						this.isThumb = res;
					});
					getImageWorkThumbNumById(this.id).then((res) => {
						this.thumbNum = res;
					});
				});
			},
			clickCollect() {
				if(this.isCollection == false){
					getFolderListByCurrentUser().then((res) => {
						this.folderList = res
					})
					this.isShowCollectionFolder = true
				} else{
					deleteCollectionByUserIdAndWorkId(this.id).then(()=>{
						uni.showToast({
							title: "取消收藏"
						})
						this.isCollection = false;
					})
				}
			},
			selectFolder(folderId) {
				this.collectionForm.workId = this.id
				this.collectionForm.folderId = folderId
				addColletionToFolder(this.collectionForm).then(() => {
					this.isShowCollectionFolder = false
					uni.showToast({
						title: '收藏成功',
						icon: 'success'
					})
					this.isCollection = true
				})
			},
			close() {
				this.isShowCollectionFolder = false
			},
		}
	};
</script>

<style lang="scss" scoped>
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
	
	.reply-text {
		font-size: 26rpx;
		padding: 0rpx 0rpx;
		width:120rpx;
	}
	
	.top-bar-select {
		color: #00aaff;
		border-bottom: #00aaff 5rpx solid;
	}
	
	page {
		height: 100%;
	}
	
	.page {
		display: flex;
		flex-direction: column;
		overflow: hidden;
		height: 100%;
		background-color: #f5f6f7;
	}
	
	.content {
		flex: 1;
		margin-top: 10rpx;
		width: 100%;
		position: relative;
	}
	
	.swiper {
		margin: 0rpx 0rpx 0;
		height: 400rpx;
	
		image {
			height: 400rpx;
			width: 100%;
			border-radius: 0rpx;
		}
	
		.item {
			position: relative;
	
			.title {
				position: absolute;
				left: 0;
				bottom: 0;
				display: flex;
				justify-content: center;
				width: 100%;
	
				text {
					margin: 7% 6%;
					display: flex;
					align-items: center;
					color: #fff;
					display: -webkit-box;
					text-overflow: ellipsis;
					word-break: break-all;
					-webkit-line-clamp: 2;
					-webkit-box-orient: vertical;
					overflow: hidden;
					font-size: 34rpx;
					padding: 14rpx 20rpx;
					max-width: 610rpx;
					line-height: 1.3;
					border-radius: 5rpx;
				}
			}
		}
	}
	
	.edit-button-content {
		margin: 80rpx 0;
	
		.edit-button {
			width: 690rpx;
			height: 80rpx;
			font-size: 26rpx;
			border-radius: 80rpx;
			text-align: center;
			line-height: 80rpx;
			margin: 30rpx auto 0;
			font-weight: bold;
			border: 1rpx solid #dcdee0;
			background: #fff;
			border: 4rpx dashed #55aaff;
		}
	
	}
	
	.list {
		margin-top: 2rpx;
	
		.item {
			padding: 30rpx 24rpx 0 24rpx;
	
			.info {
				border: 5rpx #000000;
				background-color: #FFFFFF;
				display: flex;
				flex-direction: row;
				justify-content: space-between;
	
				// padding-bottom: 4rpx;
				.text {
					flex-grow: 1;
					flex-shrink: 1;
					display: flex;
					flex-direction: column;
					margin: 20rpx 20rpx 20rpx 20rpx;
	
					.folder-title {
						flex-grow: 1;
						flex-shrink: 1;
						font-size: 28rpx;
						line-height: 1.5;
						overflow: hidden;
						height: 60rpx;
						margin-bottom: 8rpx;
					}
	
					.other {
						flex-grow: 1;
						flex-shrink: 1;
						display: flex;
						align-items: center;
						color: #999;
						line-height: normal;
	
						.left {
							display: flex;
							flex-grow: 1;
							flex-shrink: 1;
	
							.source {
								font-size: 24rpx;
								overflow: hidden;
								width: 140rpx;
							}
	
							.time {
								display: flex;
								font-size: 24rpx;
								align-items: center;
							}
						}
	
						.right {
							display: flex;
							flex-direction: row;
							justify-content: center;
							align-items: center;
							flex-grow: 0;
							flex-shrink: 0;
							margin-right: 10rpx;
	
							text {
								color: #96BD46;
								font-size: 26rpx;
							}
						}
	
						.view {}
					}
				}
	
				.photo {
					image {
						padding: 15rpx;
						height: 100%;
						width: 180rpx;
					}
				}
			}
	
			.line {
				display: bock;
				width: 100%;
				height: 1rpx;
				margin-top: 22rpx;
				background: #e8e8e8;
			}
	
			&:last-child {
				.line {}
			}
		}
	}
	
	.top2 {
		background-color: #ffffff;
		height: 100%;
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		width: 100%;
		align-items: center;
		border-radius: 5px;
		margin-top: 15rpx;
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
