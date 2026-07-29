<template>
	<view>
		<view class="top">
			<!-- <image class="background" style="width: 100%;" mode="aspectFill" @tap="showActionSheetBg(loginUserInfo.background)" :src="loginUserInfo.background"></image> -->
			<image class="background" style="width: 100%;" mode="aspectFill" @tap="preViewImage(personInfo.background)" src="/static/social/img/reader.jpg"></image>
			<image class="avatar" @tap="showActionSheet(loginUserInfo.avatar)" :src="'http://localhost:8080'+loginUserInfo.avatar"></image>
			<view class="btn" @tap="gotoPage(2)">编辑信息</view>
		</view>
		<view class="middle">
			<view class="name-container">
				<text selectable="true" class="name" style="white-space: nowrap; font-family: Source Han Sans CN;font-size: 15px;font-weight: 500;line-height: 16px;letter-spacing: 0em;">{{loginUserInfo.nickName}}</text>
				<image class="icon-gender" :src="loginUserInfo.gender===1?'/static/social/icon/gender-male.png':'/static/social/icon/gender-female.png'"></image>
			</view>
			<view class="text-creatTime" style="color: #FFFFFF; white-space: nowrap;margin-top: 10px;">
				注册时间:
				<text selectable="true" style="white-space: nowrap; color: #FFFFFF;margin-left: 10%;">{{loginUserInfo.createTime}}</text>
			</view>
			<view class="text" style="white-space: nowrap;font-family: Source Han Sans CN;font-size: 15px;font-weight: 500;line-height: 16px;letter-spacing: 0em;">
				个人信息
				<image class="icon-email" src="/static/social/icon/mail.png"></image>
				<view  class="person-email">{{loginUserInfo.email}}</view>
				<image class="icon-phonenumber" src="/static/social/icon/phonenumber.png"></image>
				<view selectable="true" class="person-phonenumber">{{loginUserInfo.phonenumber}}</view>
			</view>
<!-- 			<view @click="qrcode()">
				我的二维码
			</view> -->
		</view>

		<view class="bottom">
			<view class="btn" @tap="gotoPage(0)">加好友</view>
			<view class="btn1" @tap="gotoPage(1)">进空间</view>
		</view>
		
		
	</view>
</template>

<script>
	import userRequest from '@/api/social/user.js';
	import requestUrl from '@/api/social/url.js';
	import {mapGetters} from 'vuex';
	import timeUtil from '@/utils/social/timeUtil.js';
	import $store from '@/store/modules/social';
	export default{
		computed:{
			...mapGetters(['loginUserInfo'])
		},
		data(){
			return{
				show : false,
				popupData: {
					overlay: true,
					mode: 'center',
					borderRadius: '',
					closeable: true,
					closeOnClickOverlay: true
				},
				message: '',
				notation: '',
				avatarType: 0,
			}
		},
		methods:{
			async updateAvater(data){
				let param;
				if(this.avatarType===0){
					param = {
						id: this.loginUserInfo.userId,
						avatar: requestUrl.getUrl()+data.data.compressUrl,
					}
				}else{
					param = {
						id: this.loginUserInfo.userId,
						background: requestUrl.getUrl()+data.data.url,
					}
				}
				let res = await userRequest.update(param)
				uni.showToast({
					icon:'none',
					title:'上传成功！'
				})
				$store.state.loginUserInfo = res.data;
				uni.setStorageSync('LOGIN_USER_INFO', res.data);
				//console.log($store.state.loginUserInfo)
			},
			chooseYourAvatar(){
				let that = this
				uni.chooseImage({
					count: 1, //默认9
					sizeType: ['original', 'compressed'], //可以指定是原图还是压缩图，默认二者都有
					sourceType: ['album','camera'], //从相册选择
					success: function (res) {
						//console.log(res.tempFilePaths[0])
						if(3*1048576 < res.tempFiles[0].size){
							uni.showToast({
								icon:'none',
								title:'图片请限制在'+3+'MB以内'
							})
						}else{
							uni.uploadFile({
								url: that.avatarType===0?requestUrl.getUrl()+'ry-vue/image/uploadAvatar':requestUrl.getUrl()+'ry-vue/image/upload', // 仅为示例，非真实的接口地址
								filePath: res.tempFilePaths[0],
								name: 'image',
								formData: {
									userId: that.loginUserInfo.userId
								},
								success: (res) => {
									let data = JSON.parse(res.data)
									if(!data.success) uni.showToast({
										title:'图片上传失败！',
										icon:'none'
									})
									else{
										that.updateAvater(data)
									}
								}
							});
						}
					}
				});
			},
			getFormatTime(time){
				return timeUtil.getFormatTime(time);
			},
			showActionSheet(url){
				let that = this;
				uni.showActionSheet({
					itemList: ['查看大图', '上传头像'],
					success: function (res) {
						//console.log('选中了第' + (res.tapIndex + 1) + '个按钮');
						if(res.tapIndex===0){
							that.preViewImage(url)
						}else if(res.tapIndex===1){
							that.avatarType = 0;
							that.chooseYourAvatar();
						}
					},
					fail: function (res) {
						//console.log(res.errMsg);
					}
				});
			},
			showActionSheetBg(url){
				let that = this;
				uni.showActionSheet({
					itemList: ['查看大图', '上传背景墙'],
					success: function (res) {
						//console.log('选中了第' + (res.tapIndex + 1) + '个按钮');
						if(res.tapIndex===0){
							that.preViewImage(url)
						}else if(res.tapIndex===1){
							that.avatarType = 1;
							that.chooseYourAvatar();
						}
					},
					fail: function (res) {
						//console.log(res.errMsg);
					}
				});
			},
			gotoPage(i){
				switch(i){
					case 0:
					uni.showToast({
						title:'不允许添加自己为好友',
						icon:'none'
					})
					break;
					case 1:
					uni.navigateTo({
						url:'/pages/social/components/moment/my-world'
					})
					break;
					case 2:
					uni.navigateTo({
						url:'/pages/mine/info/edit'
					})
					break;
				}
			},
			qrcode(){
				uni.navigateTo({
					url:'/pages/social/info/person-qrcode?id='+this.loginUserInfo.userId,
						success: res => {
							console.log("打开生成二维码页面成功");
						},
						fail: () => {
							console.log("打开生成二维码页面失败");
						},
						complete: () => {}
				})
			},
			change(e){
				//console.log(this.message)
			},
			openMethod(){
				//console.log('打开了')
			},
			preViewImage(url){
				uni.previewImage({
					urls:[url]
				})
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
	page{
		//background-color: #e2e2e2;
	}
	.top{
		position: absolute;
		left: 0px;
		top: 0px;
		width: 375px;
		height: 260px;
		mix-blend-mode: multiply;
		opacity: 1;
		/* position: relative;
		height: 500rpx; */
		.background{
			// z-index: -1;
			/* height: 400rpx; */
			background: linear-gradient(180deg, rgba(255,255,255,0.00) 1%, rgba(21,21,21,0.76) 100%);
		}
		.avatar{
			/* border-radius: 50%;
			width: 150rpx;
			height: 150rpx;
			position: absolute;
			bottom: 0;
			left: 50rpx; */
			position: absolute;
			left: 32px;
			top: 98px;
			width: 48px;
			height: 48px;
			opacity: 1;
			border-radius: 50%
		}
		.btn{
			/* position: absolute;
			bottom: 15rpx;
			right: 50rpx;
			background-color: royalblue;
			padding: 15rpx;
			color: white;
			border-radius: 8rpx;
			font-weight: 600; */
			position: absolute;
			bottom: 65rpx;
			right: 360rpx;
			border-radius: 56px;
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			padding: 7px 27px;
			color: #FFFFFF;
			background: rgba(0, 199, 119, 0.47);
		}
		.btn:active{
			background-color: royalblue;
		}
	}
	
	.middle{
		/* padding: 50rpx; */
		position: absolute;
		left: 90px;
		top: 104px;
		width: 90px;
		height: 16px;
		opacity: 1;
		.name-container{
			display: flex;
			.name{
				/* font-weight: 900;
				font-size: 35rpx; */
				font-family: Source Han Sans CN;
				font-size: 15px;
				font-weight: 500;
				line-height: 16px;
				letter-spacing: 0em;
				
				color: #FFFFFF;
			}
			.icon-gender{
				position: absolute;
				left: 105px;
				top: 1px;
				width: 16px;
				height: 16px;
				opacity: 1;
			}
			.icon{
				margin-left: 5rpx;
				width: 40rpx;
				height: 40rpx;
			}
		}
		.text{
			/* margin-top: 30rpx;
			color: gray;
			letter-spacing: 1rpx; */
			// font-size: 35rpx;
			position: absolute;
			left: -55px;
			top: 170px;
			width: 60px;
			height: 14px;
			opacity: 1;
			
			font-family: Source Han Sans CN;
			font-size: 15px;
			font-weight: 500;
			line-height: 14px;
			letter-spacing: 0em;
			
			color: #343333;
		}
		.person-email{
			margin-top: 15px;
			margin-left: 30px;
		}
		.icon-email{
			top: 35px;
			margin-left: -60px;
			width: 40rpx;
			height: 40rpx;
		}
		.icon-phonenumber{
			top: 20px;
			margin-left: 0px;
			width: 40rpx;
			height: 40rpx;
		}
		.person-phonenumber{
			margin-top: 0px;
			margin-left: 30px;
		}
	}
	.bottom{
		position: absolute;
		padding: 50rpx;
		display: flex;
		width: 100%;
		bottom: 0;
		.btn{
			position: absolute;
			left: 35px;
			top: -20px;
			width: 120px;
			height: 44px;
			border-radius: 4px;
			opacity: 1;
			
			/* 自动布局 */
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			padding: 7px 27px;
			color: #FFFFFF;
			background: #429955;
		}
		.btn1{
			position: absolute;
			left: 220px;
			top: -20px;
			width: 120px;
			height: 44px;
			border-radius: 4px;
			opacity: 1;
			
			/* 自动布局 */
			display: flex;
			flex-direction: column;
			justify-content: center;
			align-items: center;
			padding: 7px 27px;
			color: #FFFFFF;
			background: #EFAA33;
		}
	}
	.u-popup-slot {
		padding: 50rpx;
		width: 200px;
		height: 150px;
		@include flex;
		justify-content: center;
		align-items: center;
		display: block;
	}
</style>