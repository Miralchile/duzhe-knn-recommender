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
						<input class="edit-input" v-model="addVideoWorkForm.title" placeholder="输入标题" />
					</view>
					<view class="edit-item">
						<view class="edit-name">
							<view class="must">
							</view>
							简介
						</view>
						<view style="display: flex;white-space: nowrap;" @click="editContent">
						    <input class="edit-input" v-model="addVideoWorkForm.introduction" placeholder="编辑内容" />
						    <u-icon name="arrow-right" size="30rpx" color="#000000"></u-icon>
						</view>
					</view>
					<view class="edit-item">
						<view class="edit-name">
							<view class="must">
								*
							</view>
							分类
						</view>
						<view style="display: flex;white-space: nowrap;" @click="editPicker">
						    <input class="edit-input" v-model="sort" placeholder="选择分类" disabled />
						    <u-icon name="arrow-right" size="30rpx" color="#000000"></u-icon>
						</view>
					</view>
					<view class="edit-item">
						<view class="edit-name">
							标签
						</view>
						<view style="display: flex;white-space: nowrap;">
							<view class="edit-input" style="display: flex;white-space: nowrap;">
								<view v-for="(item, index) in addVideoWorkForm.tagList" :key="index">
									<u-tag :text="item" size="mini" style="margin-right: 10rpx;"
									 shape="square" color="#767676" bgColor="#dfdfdf" borderColor="#dfdfdf" 
									 closable @close="closeTag(index)"></u-tag>
								</view>
							</view>
							<u-icon name="plus" size="30rpx" color="#000000" @click="addTag"></u-icon>
						</view>
					</view>
					<view class="edit-item">
						<view class="edit-name">
							<view class="must">
								*
							</view>
							视频
						</view>
						<view>
							<u-upload v-if="showVideoUpload"  @afterRead="afterReadVideo" name="1"
								width="129" :maxCount="1" accept="video"></u-upload>
							<view v-if="!showVideoUpload" style="width: 237rpx;height: 147rpx;position: relative;margin-bottom: 18rpx;">
								<video :src="addVideoWorkForm.url" style="width: 100%;height: 100%;" ></video>
								<u-icon name="close-circle-fill" style="position: absolute;top: 0;right: 0;z-index: 100;" @click="clickDeleteVideo"></u-icon>
							</view>
						</view>
					</view>
					<u-line-progress v-if="progressValue!=100"
						style="position: relative;bottom: 30rpx;width: 400rpx;left: 150rpx;" :percentage="progressValue"
						:showText="false" activeColor="#55aaff"></u-line-progress>
					<u-line-progress v-if="progressValue==100"
						style="position: relative;bottom: 30rpx;width: 400rpx;left: 150rpx;" :percentage="progressValue"
						:showText="false"></u-line-progress>
					<view class="edit-item">
						<view class="edit-name">
							<view class="must">
								*
							</view>
							封面图
						</view>
						<u-upload v-if="showCoverImageUpload" :fileList="fileList" @afterRead="afterReadImage" @delete="deletePic" name="1"
							width="129" :maxCount="1" mode="center"></u-upload>
						<view v-if="!showCoverImageUpload" style="width: 237rpx;height: 147rpx;position: relative;">
							<image style="width: 100%;height: 100%;background-color: #e8e8e8;" :src="this.addVideoWorkForm.coverImage" mode="aspectFit"></image>
							<u-icon name="close-circle-fill" style="position: absolute;top: 0;right: 0;" @click="clickDeleteGenerateCoverImage"></u-icon>
						</view>
						
						<view style="position: absolute; right: 50rpx;">
							<u-button size="mini" type="primary" text="自动生成封面图" shape="circle" :plain="true" :disabled="disabled" @click="clickGenerateCoverImage"></u-button>
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
							<u-textarea v-model="content" maxlength="-1" placeholder="请输入内容"></u-textarea>
						</view>
					</view>
				</u-popup>
				<u-popup :show="this.showTagAdd" :round="10" mode="bottom" style="height: 100%;">
					<view>
						<view class="head">
						    <view class="btn left" @tap="cancelAddTag">取消</view>
						    <view class="btn right" @tap="saveAddTag">确认</view>
						</view>
						<view>
							<u-textarea v-model="tagValue" placeholder="请输入内容" ></u-textarea>
						</view>
					</view>
				</u-popup>
				<u-picker :show="showPicker" ref="uPicker" keyName="name" :columns="columns" @cancel="cancelPicker"
					@confirm="confirmPicker" @change="changeHandler"></u-picker>
				<view>
					<u-modal :show="showConfirmSave" content='如果已编辑完成, 请确认保存作品' @cancel="showConfirmSave=false"
					showCancelButton confirmText="确认保存" @confirm="handleOnSave()"></u-modal>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import {
		uploadImage,addVideoWork,generateCoverImage
	} from '@/api/videoCreate.js'
	import {
		getAllCategory,
		getAllChannelByCategoryId
	} from '@/api/channel.js'
	import {
		getVideoWorkById
	} from '@/api/videoPlay.js'
	export default {
		data() {
			return {
				showConfirmSave: false,
				showVideoUpload: true,
				showCoverImageUpload: true,
				disabled:true,
				src:undefined,
				showPicker: false,
				columns: [],
				columnsData: [],
				fileList: [],
				progressValue: undefined,
				showContentEdit: false,
				content: undefined,
				value: undefined,
				videoMD5:undefined,
				showTagAdd:false,
				tagValue:undefined,
				addVideoWorkForm: {
					introduction: undefined,
					title: undefined,
					coverImage: undefined,
					url: undefined,
					category: undefined,
					channel: undefined,
					tagList:[],
					_id: undefined
				},
				sort: undefined
			}
		},
		
		onLoad(option){
			if(option.id != null && option.id != undefined && option.id != ''){
				getVideoWorkById(option.id).then((res)=>{
					this.addVideoWorkForm.category = res.category
					this.addVideoWorkForm.introduction = res.introduction
					this.addVideoWorkForm.title = res.title
					this.addVideoWorkForm.url = res.url
					this.addVideoWorkForm.coverImage = res.coverImage
					this.addVideoWorkForm.channel = res.channel
					this.addVideoWorkForm.tagList = res.tagList
					this.showVideoUpload = false
					this.showCoverImageUpload = false
					this.sort = this.addVideoWorkForm.category + "/" + this.addVideoWorkForm.channel
					this.addVideoWorkForm._id = res._id
				})
			}
		},
		
		methods: {
			
			clickGenerateCoverImage(){
				generateCoverImage(this.videoMD5).then((res)=>{
					this.addVideoWorkForm.coverImage = res.coverImagePath
					this.showCoverImageUpload = false
				})
			},
			
			clickDeleteGenerateCoverImage(){
				this.addVideoWorkForm.coverImage = undefined
				this.showCoverImageUpload = true
			},
			
			cancelPicker() {
				this.showPicker = false
			},

			confirmPicker(e) {
				this.addVideoWorkForm.category = e.value[0].name
				this.addVideoWorkForm.channel = e.value[1].name
				this.sort = this.addVideoWorkForm.category + "/" + this.addVideoWorkForm.channel
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

			cancelEditContent() {
				this.showContentEdit = false
			},

			saveEditContent() {
				this.showContentEdit = false
				this.addVideoWorkForm.introduction = this.content
			},
			
			addTag(){
				this.showTagAdd = true
			},
			
			closeTag(index){
				this.addVideoWorkForm.tagList.splice(index,1)
			},
			
			cancelAddTag(){
				this.showTagAdd = false
			},
			
			saveAddTag(){
				    this.showTagAdd = false;
				    this.addVideoWorkForm.tagList.push(this.tagValue)
					this.tagValue = undefined
			},

			editContent() {
				this.showContentEdit = true
				this.content = this.addVideoWorkForm.introduction
			},
			
			
			// 删除图片
			deletePic() {
				this.fileList = []
				this.addVideoWorkForm.coverImage = undefined
			},
			
			async afterReadImage(event) {
				await uploadImage(event.file.url).then((res) => {
					this.addVideoWorkForm.coverImage = JSON.parse(res[1].data).imagePath
				})
				this.fileList = [].concat(event.file)
			},
			// 新增
			afterReadVideo(event) {
				var that = this
				let token = uni.getStorageSync('Authorization')
				this.configUrl = 'http://localhost' // 这个是要另外获取
				// 每个这样的地方都要去改
				const uploadTask = uni.uploadFile({
					url: this.configUrl + '/duzhe/uploadVideo',
					filePath: event.file.url,
					name: 'file',
					header: {
						"Authorization": token,
					},
					success (res) {
						that.addVideoWorkForm.url = JSON.parse(res.data).videoPath
						that.videoMD5 = JSON.parse(res.data).videoMD5
						that.src = JSON.parse(res.data).videoPath
						that.disabled = false
						that.showVideoUpload = false
					}
				});
				uploadTask.onProgressUpdate(res => {
					this.progressValue = res.progress
					if (this.progressValue == 100) {
						uni.showToast({
							title: '上传成功',
							icon: 'none'
						})
					}
				})
			},
			
			clickDeleteVideo(){
				this.addVideoWorkForm.url = undefined
				this.showVideoUpload = true
				this.addVideoWorkForm.url = undefined
				this.progressValue = 0
				this.disabled = true
			},
			
			handleOnDel() {
				
			},
			clickSave(){
				this.showConfirmSave = true
			},
			/**
			 * 保存
			 */
			async handleOnSave() {
				await addVideoWork(this.addVideoWorkForm)
				uni.showToast({
					title: '操作成功',
					icon: 'success'
				})
				
				setTimeout(() => {
					uni.switchTab({
						url: '/pages/mine/index'
					})
				}, 1000)
			}
		}
	}
</script>

<style lang="scss" scoped>
	
	.container {
		box-sizing: border-box;
		display: flex;
		flex-direction: column;
		height: 600rpx;
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
