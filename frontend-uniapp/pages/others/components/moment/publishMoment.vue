<template>
	<view>
		<view>
			<!--弹出公告-->
			<!--  <uni-popup :show="show">
				   <view class="gonggao" :class="donghua">
					   <image src="/static/social/img/gonggao.png" mode="aspectFit"></image>
					   <view class="zhuyi">1.涉及黄色，政治，广告及骚扰信息，涉及黄色，政治，广告及骚扰信息</view>
					   <view class="zhuyi">2.涉及黄色，政治，广告及骚扰信息，涉及黄色，政治，广告及骚扰信息</view>
					   <view class="zhuyi">3.涉及黄色，政治，广告及骚扰信息，涉及黄色，政治，广告及骚扰信息</view>
					   <view class="zhuyi">一经核实将被封禁，情节严重者永久封禁！</view>
					   <button type="default" @tap="ttt">我知道了</button>
				   </view>
			</uni-popup> -->
			<view style="margin-top: 40rpx;">
			    <textarea v-model="text" @keyup="checkText()" maxlength="1000" 
				placeholder="在这里说出你的想法吧~(限制1000字)" 
				class="text-area"></textarea>
			</view>
			<view class="middle">
				<view class="images">
					<u-upload :fileList="fileList1" @afterRead="afterRead" @delete="deletePic" name="1" multiple
						:maxCount="9" :previewFullImage="true" deletable uploadText="图片"></u-upload>
					<image class="image" @longpress="removeImage(index)" v-for="(url,index) in selected_images" :key="index" :src="url"></image>
					<!-- <image class="image" @tap="addImage()" v-show="selected_images.length<9" src="/static/social/icon/video-collect.png"></image> -->
				</view>
			</view>
		</view>
		<u-row style="padding-left: 20rpx;margin-top: 80rpx;">
		    <u-col span="10">
		        <view>{{authority?'权限:所有人可见':'权限:仅好友可见'}}</view>
		    </u-col>
		    <u-col span="2">
		        <u-switch v-model="authority" activeColor="#5AC725" asyncChange @change="asyncChange(0, authority)" ></u-switch>
		    </u-col>
		</u-row>
		
		<!-- <view class="limit">
			<block>
				<view class="permission">
					<view class="permision-image">
						<image src="../../../../static/img/people.png"></image>
					</view>
					<view @tap="changeyinsi" class="permission-title">
								   {{yinsi}}
					</view>
					<view style="float: right;">
						<image src="../../../../static/img/arror.png"></image>
					</view>
				</view>
			</block>
		</view> -->   
		   	
			<view style="margin-top:40rpx;">
				<button type="primary"  class='bt' @tap="preDeliver()">发布</button>
			</view>
	</view>
</template>
<script>
	import uniPopup from '@/pages/social/components/moment/uni-popup.vue'
	import URL from '@/api/social/url.js';
	import {mapGetters} from 'vuex';
	import worldRequest from '@/api/social/world.js';
	import $store from '@/store/modules/social';
	//let arry=['所有人可见','仅自己可见','不给谁看','给部分人看']
		var sourceType = [
			['camera'],
			['album'],
			['camera', 'album']
		];
	export default {
		// components:{
		// 	uniPopup
		// },
	 	data() {
			return{
				
			fileList1: [],
			picurl: [],
				
			selected_images:[],
			upload_images:[],
			hasUploaded:false,//有没有上传
			authority: true,
			anti_shake:false,
			
			//donghua:'animated zoomInDown',
			//yinsi:'所有人可见',
			show:true,
			text:'',
			
			// VideoOfImagesShow: true, // 页面图片或视频数量超出后，拍照按钮隐藏
			// imageList: [], //存放图片的地址
			// srcVideo: [],//视频存放的地址
			// sourceType: ['拍摄', '相册', '拍摄或相册'],
			// sourceTypeIndex: 2,
			// cameraList: [{ value: 'back', name: '后置摄像头', checked: 'true' }, { value: 'front', name: '前置摄像头' }],
			// cameraIndex: 0,//上传视频时的数量
		}
	},
	computed:{
		...mapGetters(['token','user','loginUserInfo'])
	},
	onLoad() {
				uni.setNavigationBarTitle({
					title: "发表动态"
				})
			},
	// onUnload() {
	// 	(this.imageList = []),(this.sourceTypeIndex = 2),(this.sourceType = ['拍摄','相册','拍摄或相册']);
	// },
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
					url: 'http://localhost/dev-api/common/upload',
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
		
		ttt(){
			this.donghua='animated zoomInDown'
			this.show=false
		},
		asyncChange(i, authority) {
			this.authority = !authority
		},
		//上传图片
		uploadImage(){
			let that = this;
			// 开始上传图片
			let len = this.selected_images.length;
			for(let i=0;i<len;i++){
				uni.showLoading({
					title:'正在上传图片'+(i+1)+'/'+len
				})
			    uni.uploadFile({
						 url: URL.getUrl()+'ry-vue/image/upload',
					     filePath: that.selected_images[i],
					     name: 'image',
					     formData: {
					         image: that.selected_images[i]
					     },
					     success: res => {
							 let response = JSON.parse(res.data);
							 //console.log(response)
							 if(!response.success){
							 	uni.showToast({
							 		icon:'error',
							 		title:response.Message
							 	})
							 }else{
								that.upload_images.push(URL.getUrl()+response.data.url)
								//如果是最后一张照片就可以
								if(i+1===len){
									that.hasUploaded = true;
									uni.showLoading({
										title:'正在发布'
									})
									setTimeout(()=>{
										that.deliver();
									},1000)
								}
							 }
					     },
					     fail: err => {
							 uni.hideLoading();
					         uni.showToast({
					         	title:'图片上传失败~',
								icon:'error',
								duration:1000
					         });
							 return;
					     }
					});
				
				uni.hideLoading();
			}
		},
		preDeliver(){
			if(this.anti_shake){
				return
				uni.showToast({
					title:'点击太快了~',
					icon:'none'
				})
			}
			this.anti_shake = true
			setTimeout(()=>{
				this.anti_shake = false
			},3000)
			this.deliver()
		},
		async deliver(){
			if(this.text.trim().length<1){
				uni.showToast({
					title:'文字是必须的~',
					icon:'none'
				})
				return;
			}
			if(this.selected_images.length!=0 && this.hasUploaded===false){
				await this.uploadImage();
			}
			else{
				//console.log(this.upload_images)
				// const { content: res } = await worldRequest.publish({
				// 	authority:this.authority?'0':'1',
				// 	content:this.text,
				// 	imgList:this.upload_images.toString(),
				// 	id: $store.state.loginUserInfo.userId
				// });
				uni.hideLoading();
				
				var uid = getApp().globalData.uid;
				console.log(uid);
				
				uni.request({
					url: 'http://localhost:8080/ry-vue/world/publish',
					method: 'POST',
					
					data: {
						"authority":this.authority?'0':'1',
						"content":this.text,
						"imgList":this.picurl.toString(),
						"id": $store.state.loginUserInfo.userId
					},
					success: res => {
						uni.showToast({
							icon:'success',
							title:'创建成功'
						}),
						$store.state.publishSuccessFlag = true
						setTimeout(()=>{
							uni.navigateBack({
								animationType:'slide-out-left'
							})
						},400)
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
				
				uni.showToast({
					title:res,
					icon:'success',
					duration:800
				})
				uni.showToast({
					icon:'success',
					title:'发布成功！'
				})
				$store.state.publishSuccessFlag = true
				setTimeout(()=>{
					uni.navigateBack({
						animationType:'slide-out-left'
					})
				},400)
			}
		},
		checkText(){
			this.text = this.text.trim();
			if(this.text.length>1000){
				uni.showToast({
					title:'最多1000个字哦~',
					icon:'none'
				})
				this.text = this.text.substring(0,1000);
			}
		},
		addImage(){
			let that = this;
			uni.chooseImage({
				count: 6 - that.selected_images.length,
				sizeType: ['original', 'compressed'],
				sourceType: ['album'],
				success: function(res) {
					for(let i=0;i<res.tempFilePaths.length;i++){
						if(3*1048576 < res.tempFiles[i].size){
							uni.showToast({
								icon:'none',
								title:'图片请限制在'+3+'MB以内'
							})
						}else{
							that.selected_images.push(res.tempFilePaths[i]);	
						}
					}
				}
			});
		},
		removeImage(index){
			let that = this;
			uni.showModal({
				title:'提示',
				content:'删除此照片？',
				confirmText:'确定',
				confirmColor:'#D94B4D',
				cancelText:'取消',
				success: (res) => {
					if(res.confirm){
						that.selected_images.splice(index,1);
					}
				}
			})
		}
	}
}
	
</script>
<style lang="scss">
	.common{
		margin-top: 200rx;
	}
	
	.gonggao{
		width: 500upx;
		background-color: #FFFFFF;
		border-radius: 10upx;
		padding: 30upx 40upx;
	}
	.gonggao image{
		width: 90%;
		height: 200rpx;
	}
	.gonggao button{
		background-color: #FFE934;
		margin-top: 40upx;
		border-radius: 10upx;
	}
	.zhuyi{
		text-align: left;
	}
	.images{
		margin-left: 20rpx;
		margin-right: 20rpx;
		display: grid;
		grid: 210rpx / auto auto auto;
	}
	.image{
			border-radius: 10rpx;
			width: 200rpx;
			height: 200rpx;
		}
	.text-area{
		padding: 5rpx;
		font-family: Arial, Helvetica, sans-serif;
		border-radius: 10rpx;
		width: 95%;
		height: 300rpx;
		background-color: "#787878";
		margin: 20rpx;
	}
 //    .limit{
	// 	margin-top: 20upx;
	// }
	// .permission{
	// 	display: flex;
	// 	justify-content: space-between;
	// 	margin-top: 20upx;
	// }
	// .permission image{
	// 	width: 40upx;
	// 	height: 40upx;
	// }
	// .permission-image image{
	// 	width: 50upx;
	// 	height: 50upx;
	// 	padding-top: 10upx;
	// }
	// .permission-title{
	// 	width: 100%;
	// 	padding-left: 15upx;
	// 	font-size: 35upx;
	// 	color: black;
	// }
</style>