<template>
	<view>
		<view class="edit-content">
			<view class="edit-item">
				<view class="edit-name">
					<view class="must">
						*
					</view>
					标题
				</view>
				<input class="edit-input" v-model="addImageWorkForm.title" placeholder="输入标题" />
			</view>
			<view class="edit-item">
				<view class="edit-name">
					<view class="must">
						*
					</view>
					内容
				</view>
				<input class="edit-input" v-model="contentInput" placeholder="编辑内容"/>
				<u-icon name="arrow-right" size="30rpx" color="#000000" @click="editContent"></u-icon>
			</view>
			<view class="edit-item">
				<view class="edit-name">
					<view class="must">
					</view>
					分类
				</view>
				<input class="edit-input" v-model="sort" placeholder="选择分类" />
				<u-icon name="arrow-right" size="30rpx" color="#000000" ></u-icon>
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
		</view>
		<view class="edit-button-content">
			<view class="edit-button save" @click="handleOnSave">
				保存
			</view>
			<view class="edit-button" @click="handleOnDel">
				取消
			</view>
		</view>
		<u-popup :show="this.showContentEdit" :round="10" mode="bottom" style="height: 100%;">
			<view>
				<view class="head">
				    <view class="btn left" @tap="cancel">取消</view>
				    <view class="btn right" @tap="save">确认</view>
				</view>
				<jinEdit :html="addImageWorkForm.content" ref="jinEdit" class="editor" placeholder="请输入内容"
				  uploadFileUrl="/#" style="height: 1150rpx;"></jinEdit>
			</view>
		</u-popup>
	</view>
</template>

<script>
	import jinEdit from "@/components/jin-edit/jin-edit.vue"
	import { uploadImage,addImageWork } from '@/api/imageCreate.js'
	export default {
		components:{
			jinEdit
		},
		data() {
			return {
				fileList:[],
				isDefault: false,
				showContentEdit:false,
				contentInput:undefined,
				sort:undefined,
				addImageWorkForm:{
					content:undefined,
					title:undefined,
					coverImage:undefined
				}
			}
		},
		methods: {
			// 删除图片
			deletePic() {
				this.fileList = []
				this.addImageWorkForm.coverImage = undefined
			},
			// 新增图片
		    async afterRead(event) {
				await uploadImage(event.file.url).then((res)=>{
					this.addImageWorkForm.coverImage = JSON.parse(res).imagePath
				})
				this.fileList = [].concat(event.file)
			},
			
			uploadImg(imageUrl,callback){
				uploadImage(imageUrl).then((res) => {
				    callback(res.data[1].imagePath)
			    });
			},
			
			cancel(){
				this.showContentEdit = false
			},
			
			save(){
				this.showContentEdit = false;
				this.$refs.jinEdit.editorCtx.getContents({
				success: res => {
					this.addImageWorkForm.content = res.html;
					this.contentInput = res.text;
				} 
			})
				
			},
			
			editContent(){
				this.showContentEdit = true
			},
			
			/**
			 * 是否默认联系人变更
			 */
			handleChangeDefault(e) {
				this.isDefault = e.detail.value
			},
			/**
			 * 点击删除
			 */
			handleOnDel() {
				
			},
			/**
			 * 点击保存
			 */
			async handleOnSave() {
				await addImageWork(this.addImageWorkForm)
				uni.showToast({
					title: '创建成功',
					icon: 'none'
				})
				uni.navigateTo({
					url: '/pages/mine/index'
				})
			}
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
