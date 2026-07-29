<template>
	<view style="background-color: #ececec; height:100vh">
		<view style="position: relative; top:30rpx">
			<view>
				<view class="edit-content">
					<view class="edit-item">
						<view class="edit-name">
							<view class="must">
								*
							</view>
							标题
						</view>
						<input class="edit-input" v-model="addFolderForm.folderName" placeholder="输入标题" />
					</view>
					
					<view class="edit-item">
						<view class="edit-name">
							<view class="must">
								*
							</view>
							封面图
						</view>
						<u-upload
							:fileList="fileList"
							@afterRead="afterRead"
							@delete="deletePic"
							name="1"
							width="129"
							:maxCount="1"
							mode="center"
						></u-upload>
					</view>
					
					<view class="edit-item">
						<view class="edit-name">
							默认封面
						</view>
					    <u-switch size="28" v-model="value" inactiveColor="#c5c5c5" style="margin-left: 400rpx;"></u-switch>
					</view>
				</view>
				
				<view class="edit-button-content">
					<view class="edit-button save" @click="handleOnSave">
						保存
					</view>
					<view class="edit-button" @click="handleOnDel">
						取消
					</view>
				</view>
				
			</view>
		</view>
	</view>
</template>

<script>
	
	import { getFolderListByCurrentUser,uploadFolderCoverImage,addFolder } from '@/api/collection.js'
	
	export default {
		data() {
			return {
				fileList:[],
				value:undefined,
				addFolderForm:{
					folderName:undefined,
					folderCoverImage:undefined
				}
			}
		},
		
		methods: {
			// 删除图片
			deletePic() {
				this.fileList = []
				this.addFolderForm.folderCoverImage = undefined
			},
			// 新增图片
			async afterRead(event) {
				await uploadFolderCoverImage(event.file.url).then((res)=>{
					this.addFolderForm.folderCoverImage = JSON.parse(res[1].data).imagePath
				})
				this.fileList = [].concat(event.file)
			},
			
			async handleOnSave() {
				await addFolder(this.addFolderForm)
				
				uni.showToast({
					title: '创建成功',
					icon: 'success'
				})
				
				setTimeout(() => {
					uni.navigateTo({
						url: '/pages/mine/index'
					})
				}, 1000)
				
			}
			
			// uploadImg(imageUrl,callback){
			// 	uploadFolderCoverImage(imageUrl).then((res) => {
			// 	    callback(res.imagePath)
			//     });
			// }
		}
	}
	
</script>


<style lang="scss">
	.head {
	    display: flex;
	    justify-content: space-between;
	    width: 100%;
	    height: 100%;
	    border-bottom: 1px #eee solid;
	    // box-shadow: 1px 0 2px rgba(0, 0, 0, 0.1);
	    background: #fff;
	    .btn {
	        display: block;
	        width: 150upx;
	        height: 80upx;
	        line-height: 80upx;
	        font-size: 30upx;
	        color: #666;
	        padding-left: 20upx;
	        text-align: center;
	        &.left {
	            float: left;
	        }
	        &.right {
	            float: right;
	        }
	    }
	}
	.edit-content {
		color: #333;
		font-size: 26rpx;
		background: #fff;
		border-radius: 10rpx;
		width: 710rpx;
		padding: 0rpx 20rpx;
		box-sizing: border-box;
		margin: 20rpx auto 0;

		.edit-item {
			display: flex;
			padding: 30rpx 10rpx;
			border-bottom: 1rpx solid #ebedf0;

			.edit-name {
				width: 140rpx;
				display: flex;

				.must {
					color: red;
					margin-right: 4rpx;
				}
			}

			.edit-input {
				font-size: 26rpx;
				width: 480rpx;
				flex: 1;
			}
		}

		.edit-item:last-of-type {
			border-bottom: none;
		}
	}

	.edit-default {
		color: #333;
		font-size: 26rpx;
		background: #fff;
		width: 710rpx;
		margin: 20rpx auto 0;
		box-sizing: border-box;
		padding: 20rpx;
		display: flex;
		justify-content: space-between;
		align-items: center;
	}

	.edit-button-content {
		margin: 80rpx 0;

		.edit-button {
			color: #333;
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

			&.save {
				background: #1989fa;
				color: #fff;
				border: none;
				border: 1rpx solid #1989fa;
			}
		}

	}
</style>