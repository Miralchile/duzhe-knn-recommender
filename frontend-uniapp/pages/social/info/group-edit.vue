<template>
	<view>
		<view class="title">
				<!-- <text>群名称和群头像</text> -->
		</view>
		<view class="name ">
			<text>群名称</text>
			<input style="font-family: PingFangHK-Regular;font-size: 12px;font-weight: normal;line-height: 20px;letter-spacing: 0px; margin-top: 40px;margin-left: -42px;color: #909090;" v-model="name" placeholder="填写群名称(2-10个字)"/>
		</view>
		<view class="divider"></view>
		<view class="introduction">
			<text>群签名</text>
			<textarea maxlength="50" style="font-family: PingFangHK-Regular;font-size: 12px;font-weight: normal;line-height: 20px;letter-spacing: 0px; margin-top: 13px;;color: #909090;" v-model="introduction" placeholder="简单介绍一下这个群吧(50字之内)"></textarea>
		</view>
		<view class="divider"></view>
			<view style="display: flex;justify-content: center;height: 80px;margin: 10px;">
				<text style="margin-top: 8px;font-family: PingFangHK-Regular;font-size: 14px;font-weight: normal;line-height: 20px;letter-spacing: 0px;margin-left: 6px;">群头像：</text>
				<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple
					:maxCount="1" :previewFullImage="true" deletable uploadText="群头像"></u-upload>
			</view>

		<view>
			<button style="color: #429955; " @tap="operate()" class="btn" :disabled="name==''||name.length>10">{{groupId===-1?'立即创建':'提交修改'}}</button>
		</view>
	</view>
</template>

<script>
	import {mapGetters} from 'vuex';
	import requestUrl from '@/api/social/url.js';
	import $store from '@/store/modules/social/test.js';
	import userRequest from '@/api/social/user.js';
	export default{
		computed:{
			...mapGetters(['loginUserInfo'])
		},
		data(){
			return {
				
				fileList1: [],
				picurl: "",
				
				
				groupId: -1,
				name:'',
				upload:'',
				introduction:''
			}
		},
		onUnload() {
			$store.state.editingGroupInfo = null
		},
		onLoad(options) {
			switch(options.action){
				case 'new':
					uni.setNavigationBarTitle({
						title:'新建群聊'
					})
				break;
				case 'edit':
					if($store.state.editingGroupInfo == null)
					{
						uni.redirectTo({
							url:'/pages/social/list/group-list'
						})
					}
					uni.setNavigationBarTitle({
						title:'编辑群聊'
					})
					//console.log($store.state.editingGroupInfo)
					this.groupId = $store.state.editingGroupInfo.id,
					this.name = $store.state.editingGroupInfo.name,
					this.introduction = $store.state.editingGroupInfo.introduction
				break;
				default:
				console.log('重新')
				uni.reLaunch({
					url:'/pages/social/social'
				})
			}
		},
		methods:{
			
			
			// 新增图片
			async afterRead(event) {
				// 当设置 multiple 为 true 时, file 为数组格式，否则为对象格式
				let lists = [].concat(event.file)
				let fileListLen = this[`fileList${event.name}`].length
				lists.map((item) => {
					this[`fileList${event.name}`].push({
						...item,
						status: 'uploading',
						message: '上传中'
					})
				})
				for (let i = 0; i < lists.length; i++) {
					const result = await this.uploadFilePromise(lists[i].url)
					let item = this[`fileList${event.name}`][fileListLen]
					this[`fileList${event.name}`].splice(fileListLen, 1, Object.assign(item, {
						status: 'success',
						message: '',
						url: result
					}))
					fileListLen++
				}
			},
			uploadFilePromise(url) {
				return new Promise((resolve, reject) => {
					let a = uni.uploadFile({
						url: 'http://localhost:8080/common/upload',
						filePath: url,
						name: 'file',
						formData: {
							user: 'test'
						},
						success: (res) => {
							
							console.log(res);
							url = JSON.parse(res.data).fileName;
							
							// console.log(eval(res.data));
			
							console.log("要存的url为：" + url);
							this.picurl = url;
			
							setTimeout(() => {
								resolve(res.data.data)
							}, 1000)
						}
					});
				})
			},
			deletePic(event) {
				this[`fileList${event.name}`].splice(event.index, 1)
			},
			
			selectAvatar: function() {
				let that = this;
				uni.chooseImage({
					count: 1,
					sizeType: ['original', 'compressed'],
					sourceType: ['album'],
					success: function(res) {
						that.upload = res.tempFilePaths[0];
					}
				})
			},
			async createWithUpload(url){
				if(this.groupId!=-1){
					uni.showLoading({
						title: '修改...'
					})
					let param = {
					  id: this.groupId,
					  name:this.name,
					  introduction:this.introduction
					}
					if(url!='') param.avatar = requestUrl.getUrl() + url;
					//console.log(param)
					const { content: res } = await userRequest.updateGroup(param);
					//console.log(res)
					uni.hideLoading();
					uni.showToast({
						title:'修改成功！',
						icon:'success'
					})
				}else{
					//默认头像
					uni.showLoading({
						title: '注册中...'
					})
					let param = {
					  id: this.loginUserInfo.userId,
					  name:this.name,
					  avatar: requestUrl.getUrl()+url,
					  introduction:this.introduction
					}
					//console.log(param)
					const { content: res } = await userRequest.createGroup(param);
					//console.log(res)
					uni.hideLoading();
					uni.showToast({
						title:'创建成功！',
						icon:'success'
					})
				}
				//重新获取一遍群信息
				$store.dispatch('getFriendList');
				setTimeout(function(){
					uni.navigateBack();
				},500)
			},
			
			async edit(){
				if(this.name.length>10||this.name.length<2){
					uni.showToast({
						title:'群名称2~10个字！',
						icon:'none'
					})
					return;
				}
				if(this.introduction.length>50){
					uni.showToast({
						title:'简介字也太多了吧！',
						icon:'none'
					})
					return;
				}
				if(this.upload!=''){
					uni.showLoading({
						title:'正在上传头像···',
					})
					let that = this;
					uni.uploadFile({
					    url: requestUrl.getUrl()+'ry-vue/image/upload',
					    filePath: that.upload,
					    name: 'image',
					    formData: {
					        image: 'groupAvatar'
					    },
					    success: res => {
							uni.hideLoading()
					        //console.log(res)
							let data = JSON.parse(res.data)
							//console.log(data)
							that.createWithUpload(data.data.compressUrl)
					    },
					    fail: err => {
					        uni.hideLoading()
					        //console.log('请求失败_______________', err);
					    }
					});
				}else{
					this.createWithUpload('')
				}
			},
			
			operate(){
				
				// var uid = getApp().globalData.uid;
				// console.log(uid);
				if(this.groupId==-1){
					uni.request({
						url: 'http://localhost:8080/ry-vue/group/create',
						method: 'POST',
						
						data: {
							
							"id" : this.loginUserInfo.userId,
							"name" : this.name,
							"avatar" : this.picurl,
							"introduction" : this.introduction,
							
						},
						success: res => {
							uni.showToast({
								icon:'success',
								title:'创建成功'
							}),
							uni.navigateTo({
								url:'/pages/social/list/group-list'
							}),
							console.log(id);
							//$store.dispatch('getGroupList')
							console.log(res);
							
						},
						fail: () => {
							uni.showToast({
								icon:'error',
								title:'创建失败'
							})
						},
						complete: () => {}
					});
				}else{
					uni.request({
						url: 'http://localhost:8080/ry-vue/group/update',
						method: 'POST',
						
						data: {
							
							"id" : this.groupId,
							"name" : this.name,
							"avatar" : this.picurl,
							"introduction" : this.introduction,
							
						},
						success: res => {
							uni.showToast({
								icon:'success',
								title:'修改成功'
							}),
							uni.navigateTo({
								url:'/pages/social/list/group-list'
							}),
							console.log(id);
							//$store.dispatch('getGroupList')
							console.log(res);
							
						},
						fail: () => {
							uni.showToast({
								icon:'error',
								title:'修改失败'
							})
						},
						complete: () => {}
					});
				}
			
				
				
				
				
				
				// if(this.groupId==-1){
				// 	this.create()
				// }else{
				// 	this.edit()
				// }
			},
			
			async create(){
				// if(this.upload==''){
				// 	uni.showToast({
				// 		title:'请选择头像！',
				// 		icon:'none'
				// 	})
				// 	return;
				// }
				if(this.name.length>10||this.name.length<2){
					uni.showToast({
						title:'群名称2~10个字！',
						icon:'none'
					})
					return;
				}
				if(this.introduction.length>100){
					uni.showToast({
						title:'简介字也太多了吧！',
						icon:'none'
					})
					return;
				}
				uni.hideKeyboard();
				// if(this.upload==''){
				// 	uni.showToast({
				// 		title:'请选择图片',
				// 		icon:'none',
				// 		duration:1000
				// 	});
				// 	return;
				// }
				uni.showLoading({
					title:'正在上传头像···',
				})
				let that = this;
				uni.uploadFile({
				    url: requestUrl.getUrl()+'ry-vue/image/upload',
				    filePath: that.upload,
				    name: 'image',
				    formData: {
				        image: 'groupAvatar'
				    },
				    success: res => {
						uni.hideLoading()
				        //console.log(res)
						let data = JSON.parse(res.data)
						//console.log(data)
						that.createWithUpload(data.data.compressUrl)
				    },
				    fail: err => {
				        uni.hideLoading()
				        console.log('请求失败_______________', err);
				    }
				});
			}
			
		}
	}
</script>
<style lang="scss">
	page {
	  background-color: #fff;
	}
</style>
<style lang="scss" scoped>
	.introduction{
		margin: 30rpx;
	}
	.introduction textarea{
		width: 100%;
		height: 40rpx;
	}
	.title{
		margin-top: 50rpx;
		margin-left: 30rpx;
	}
	.title text{
		font-size: 40rpx;
		font-weight: 900;
	}
	.name{
		height: 65px;
		display: flex;
		margin-top: 60rpx;

	}
	.name text{
		margin-top: 15px;
		margin-left: 15px;
		font-family: PingFangHK-Regular;
		font-size: 14px;
		font-weight: normal;
		line-height: 20px;
		letter-spacing: 0px;
		
	}
	.name input{
		padding-top: 10rpx;
		margin-bottom: 0;
		margin-left: 20rpx;
	}
	.divider{
		margin-top: 20rpx;
		margin-left: 4%;
		margin-right: 6%;
		border-bottom: 3rpx solid gray;
	}
	.avatar-container{
		margin-top: 30rpx;
		margin-left: 30rpx;
	}
	.avatar-container text{
		font-size: 35rpx;
	}
	.images{
		margin-top: 30rpx;
		margin-left: 20rpx;
		margin-right: 20rpx;
	}
	.image{
		margin-right: 35rpx;
		border-radius: 50%;
		width: 130rpx;
		height: 130rpx
	}
	.image-selected{
		margin-right: 35rpx;
		border-radius: 50%;
		width: 105rpx;
		height: 105rpx;
		border: #00aaff solid 15rpx;
	}
	.btn{
		margin-top: 400rpx;
		margin-left: 40rpx;
		margin-right: 40rpx;
		font-family: PingFangHK-Regular;
		font-size: 14px;
		font-weight: normal;
		letter-spacing: 0em;
	}
</style>