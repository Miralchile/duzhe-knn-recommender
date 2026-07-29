<template>
	<view class="page">
		<view class="content">

			<view class="list" v-if="list.length > 0">
				<view :tag="item.id" class="item" v-for="(item, index) in list" :key="index" hover-class="none">
					<view class="info">

						<view class="photo">
							<image :src="item.folderCoverImage" mode="aspectFill"></image>
						</view>

						<view class="text" @click="clickFolder(item.id)">
							<view class="title">{{ item.folderName }}</view>
							<view class="other">
								<view class="left">
									<view class="time">创建时间: {{formattedDate(item.createdby)}}</view>
								</view>
								<view class="right view">
									<text>收藏夹</text>
								</view>
							</view>
						</view>
						<u-icon name="list-dot" color="#959595" size="40rpx" style="margin-right: 10rpx;"
							@click="clickIcon"></u-icon>
						<view>
							<u-popup :show="show" @close="close">
								<view>
									<u-button type="primary" :plain="true" :hairline="true" text="编辑"></u-button>
									<u-button type="warning" :plain="true" :hairline="true" text="删除"
										@click="clickDelete(item.id)"></u-button>
									<u-button type="info" :plain="true" :hairline="true" text="取消" @click="close()">
									</u-button>
								</view>
							</u-popup>
						</view>
					</view>
				</view>
			</view>

			<view class="edit-button-content">
				<view class="edit-button" @click="clickAddFolder">
					<text style="color: #55aaff">+ 添加收藏夹</text>
				</view>
			</view>

		</view>
	</view>
</template>

<script>
	import {
		getFolderListByCurrentUser,
		deleteFolderById
	} from '@/api/collection.js'
	export default {
		data() {
			return {
				show: false,
				input: '',
				list: [],
				currentSliderIndex: '',
				slider: [],
				totalPage: 0
			}
		},
		mounted() {
			getFolderListByCurrentUser().then((res) => {
				this.list = res
			})
		},
		onPullDownRefresh() {
			uni.showLoading({
				title: '刷新中'
			});
			this.loadData();
		},
		methods: {
			clickFolder(folderId) {
				console.log(folderId)
				uni.navigateTo({
					url: '/pages/mine/collection/collection?folderId=' + folderId
				});
			},
			clickDelete(id) {
				deleteFolderById(id).finally(() => {
					getFolderListByCurrentUser().then((res) => {
						this.list = res
					})
				});
				this.show = false
			},
			clickIcon() {
				this.show = true
			},
			close() {
				this.show = false
			},
			clickAddFolder() {
				uni.navigateTo({
					url: '/pages/mine/collection/addAndEditFolder'
				});
			},
			formattedDate(date) {
				var dateTime = new Date(date)
				return dateTime.getFullYear() + "-" + dateTime.getMonth() + 1 + "-" + dateTime.getDate()
			}
		}
	}
</script>
<style lang="scss" scoped>
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
			//box-shadow: 0 0 12px #dddee1;
		}

		// .current {
		// 	width: 97%;
		// 	height: 350rpx;
		// 	margin: 1.5% 1.5%;
		// 	transition: all 0.2s ease-in 0s;
		// }
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
					//background-image: linear-gradient(to right, rgba(0, 0, 0, 0.3), rgba(0, 0, 0, 0.3));
					//background-image: linear-gradient(90deg,rgba(255, 181, 19,.8),rgba(255, 181, 19,.2));
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

	/*文章列表*/
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

					.title {
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
</style>
