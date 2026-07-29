<template>
	
</template>

<script>
export default {
	name: 'articleList',
	props: {
		list: {
			type: Array,
			default: function(e) {
				return [];
			}
		}
	},
	methods: {
		clickIcon(){
			
		}
	}
};
</script>

<style lang="scss" scoped>
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
				margin: 20rpx 20rpx 20rpx 20rpx ;
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
						.time{
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
					.view {
					}
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
			.line {
			}
		}
	}
}
</style>
