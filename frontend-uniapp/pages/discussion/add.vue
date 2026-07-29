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
						<input class="edit-input" v-model="addDiscussionForm.title" placeholder="输入标题" />
					</view>
					<view class="edit-item">
						<view class="edit-name">
							<view class="must">
								*
							</view>
							讨论内容
						</view>
						<view style="display: flex;white-space: nowrap;" @click="editContent">
						    <input class="edit-input" placeholder="请详细描述你的问题" v-model="addDiscussionForm.content" />
						    <u-icon name="arrow-right" size="30rpx" color="#000000"></u-icon>
						</view>
					</view>
					<view class="edit-item">
						<view>
							<view class="edit-name">
								图片描述
							</view>
							<view style="color: #aaaaaa;">(至多五张)</view>
						</view>
						
						<u-upload
							:fileList="fileList"
							@afterRead="afterRead"
							@delete="deletePic"
							name="1"
							width="100"
							:maxCount="4"
							mode="center"
						></u-upload>
					</view>
					<view class="edit-item">
						<view class="edit-name">
							<view class="must">
								*
							</view>
							分类
						</view>
						<view style="display: flex;white-space: nowrap;">
							<input class="edit-input" v-model="sort" placeholder="选择分类"  @click="editPicker" disabled/>
							<u-icon name="arrow-right" size="30rpx" color="#000000"></u-icon>
						</view>
					</view>
				</view>
				<view class="edit-button-content">
					<view class="edit-button save" @click="clickSave()">
						保存
					</view>
					<view class="edit-button" @click="handleOnDel()">
						取消
					</view>
				</view>
				<u-popup :show="this.showContentEdit" :round="10" mode="bottom" style="height: 100%;">
					<view>
						<view class="head">
							<view class="btn left" @tap="cancelEditContent">取消</view>
							<view class="btn right" @tap="saveEditContent">确认</view>
						</view>
						<view class="container">
							<u--textarea style="height: 100%" autoHeight v-model="content" 
							maxlength="-1" placeholder="请输入内容"></u--textarea>
						</view>
					</view>
				</u-popup>
				<u-picker :show="showPicker" ref="uPicker" keyName="name" :columns="columns" @cancel="cancelPicker"
					@confirm="confirmPicker" @change="changeHandler"></u-picker>
				<view>
					<u-modal :show="showConfirmSave" content='请确认是否发布此提问' @cancel="showConfirmSave=false"
					showCancelButton confirmText="确认发布" @confirm="handleOnSave()"></u-modal>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
    import { uploadImage } from '@/api/imageCreate.js';
	import { addDiscussion } from '@/api/discussion.js'
	import {
		getAllCategory,
		getAllChannelByCategoryId
	} from '@/api/channel.js'
	
	export default {
		data() {
			return {
				index :0,
				showConfirmSave: false,
				showPicker: false,
				columns: [],
				fileList:[],
				showContentEdit: false,
				content: undefined,
				sort:undefined,
				addDiscussionForm:{
					content:undefined,
					title:undefined,
					imageUrlList:[],
					category: undefined,
					channel: undefined,
				}
			}
		},
		methods: {
			
			cancelPicker() {
				this.showPicker = false
			},
			
			confirmPicker(e) {
				this.addDiscussionForm.category = e.value[0].name
				this.addDiscussionForm.channel = e.value[1].name
				this.sort = this.addDiscussionForm.category + "/" + this.addDiscussionForm.channel
				this.showPicker = false
			},
			
			async editPicker() {
				await getAllCategory().then((res) => {
						let categoryList = res
						for (var i = 0; i < categoryList.length; i++) {
							categoryList[i].name = categoryList[i].categoryName
						}
						this.columns = [categoryList]
					}),
					await getAllChannelByCategoryId().then((res) => {
						let channelList = res[0]
						for (var i = 0; i < channelList.length; i++) {
							channelList[i].name = channelList[i].channelName
						}
						this.columnsData = res
						let picker = this.$refs.uPicker
						picker.setColumnValues(1, channelList)
					})
				this.showPicker = !this.showPicker
			},
			
			changeHandler(e) {
				const {
					columnIndex,
					value,
					values, // values为当前变化列的数组内容
					index,
					picker = this.$refs.uPicker
				} = e
				if (columnIndex === 0) {
					let channelList = this.columnsData[index]
					for (var i = 0; i < channelList.length; i++) {
						channelList[i].name = channelList[i].channelName
					}
					picker.setColumnValues(1, channelList)
				}
			},
			
			// 删除图片
			deletePic() {
				this.fileList.pop()
				this.addDiscussionForm.imageUrlList.pop()
			},
			
			// 新增图片
			async afterRead(event) {
				await uploadImage(event.file.url).then((res)=>{
					this.fileList = this.fileList.concat(event.file)
					this.addDiscussionForm.imageUrlList = this.addDiscussionForm
					.imageUrlList.concat(JSON.parse(res[1].data).imagePath)
				})
			},
			
			editContent() {
				this.showContentEdit = true
				this.content = this.addDiscussionForm.content
			},
			
			cancelEditContent() {
				this.showContentEdit = false
			},
			
			saveEditContent() {
				this.showContentEdit = false
				this.addDiscussionForm.content = this.content
			},
			
			handleOnDel() {
				
			},
			clickSave(){
				this.showConfirmSave = true
			},
			
			async handleOnSave() {
				await addDiscussion(this.addDiscussionForm)
				uni.showToast({
					title: '操作成功',
					icon: 'success'
				})
				setTimeout(() => {
					uni.switchTab({
						url: '/pages/index/index'
					})
				}, 1000)
			}
		}
	}
</script>

<style lang="scss">
	.container {
		box-sizing: border-box;
		display: flex;
		flex-direction: column;
		height: 1150rpx;
		box-sizing: border-box;
		/* padding-top: 30rpx; */
	}
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
