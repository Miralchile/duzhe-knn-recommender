<template>
	<view class="container">
		<view style="position: relative;margin-top: 10rpx;margin-left: 1%;margin-bottom: 13px; display: flex;">
			<textarea style="border-radius: 65px;" stymaxlength="600" @keyup.enter="sendText()" auto-height="true" v-model="text" @focus="showKeyBoard()" class="input-area"></textarea>
			
			<image @tap="openDrawer(1)" src="@/static/social/icon/emoji.png" style="height: 54rpx;width: 54rpx;margin-left: 5%;margin-top: 10rpx;margin-bottom: 1.5%;"/>
			
			<view @tap="sendText()" :class="[isDisable?'send-btn-disable':'send-btn-able','send-btn']">
				<image src="../../../static/social/img/send_btn.png" style="width: 25px;height: 25px;margin-top: 6px;"></image>
			</view> 
		</view>
		
		<view class="icon-container">
			<image class="input-icon" @tap="openDrawer(1)" src="@/static/social/icon/emoji.png"></image>
			<image class="input-icon" @tap="selectImage()" src="@/static/social/icon/image.png"></image>
			<image class="input-icon" @tap="selectVideo()" src="@/static/social/icon/video.png"></image>
			<!-- #ifdef APP-PLUS -->
			<lsj-upload
				style="width: 55rpx;height: 55rpx;hebackground-color: red;margin-left: 0rpx;"
				class="input-icon"
				ref="lsjUpload"
				childId="upload1"
				:count="1"
				:size="maxFileMB"
				:option="fileOption"
				@change="fileOnChange"
			>
				<!-- <image  class="input-icon" src="@/static/social/icon/file.png"></image> -->
			</lsj-upload>
			<!-- #endif -->
			<!-- #ifdef H5 -->
			<!-- <image class="input-icon" @tap="selectFile()" src="@/static/social/icon/file.png"></image> -->
			<!-- #endif -->
			<!-- <image class="input-icon" @tap="openDrawer(5)" src="@/static/social/icon/microphone.png"></image> --> 
		</view>
		<view class="emoji-container" v-show="showDrawer===1">
			<image class="emoji" v-for="(emoji,index) in emojiList" :key="index" @tap="text += emoji.alt" :src="'/static/social/emoji/big/'+emoji.url"></image>
			<view style="height: 100rpx;"></view>
			<view style="position: fixed;z-index: 100;right: 20rpx;bottom: 30rpx;display: flex">
				<view class="btn">
					<view @tap="deleteAEmoji()">×</view>
				</view>
				<view class="btn">
					<view @tap="showDrawer = 0">关闭</view>
				</view>
			</view>
		</view>
		<view class="recorder-container" v-show="showDrawer===5">
			 <view style="position: relative;">
				 <view style="position: absolute;width: 90%;top: 50rpx;margin-left: 5%;">
					 <audioPlayer :src='recorder.localUrl' v-if='recorder' :autoplay="false" :continue="false"></audioPlayer>
				 </view>
				
					 <image v-show="recorder===null" class="record-btn" @click='handlerOnCahnger' :src="!status?'/static/social/icon/record-start.png':'/static/social/icon/record-pause.png'"></image>
					 <text v-show="recorder===null && status">{{audioTime+'s'}}</text>
		
				<view style="display: flex;position: fixed;width: 90%;margin-left: 4%;bottom: 50rpx;" v-show="recorder!=null">
					<u-button @tap="audioCancel()" text="取消"  size="normal"></u-button>
					<u-button style="margin-left: 2%;" @tap="sendAudio()" text="发送"  size="normal" class="send-btn-able" type="primary"></u-button>
				</view>
			    <mumu-recorder ref='recorder' @success='handlerSuccess' @error='handlerError'></mumu-recorder>
				<view style="position: fixed;z-index: 100;right: 20rpx;bottom: 30rpx;display: flex">
					<view class="btn">
						<view @tap="showDrawer = 0">关闭</view>
					</view>
				</view>
			 </view>
		</view>
		<view class="image-container" v-show="selectedImageimage">
			<u-upload  
			    :fileList="fileList1" 
			    @afterRead="afterRead" 
				@delete="deletePic" 
				name="1" 
				multiple
				:maxCount="9" 
				:previewFullImage="true" 
				deletable 
				uploadText="图片">
			</u-upload>
			<view style="display: flex;position: fixed;width: 90%;margin-left: 4%;bottom: 50rpx;">
				<!-- <image @tap="selectImage()" style="width: 80%;height: 70%;overflow: hidden;margin-top: -50%;border-radius: 2%;" :src="selectedImage"></image> -->			
				<u-button style="margin-right: 2%;" @tap="imageCancel()" text="取消"  size="normal"></u-button>
				<u-button style="margin-left: 2%;" @tap="sendImage()" text="发送"  size="normal" class="send-btn-able" type="primary"></u-button>
			</view>
			
		</view>
		<view class="video-container" v-show="selectedVideo">
			<u-upload
				:fileList="fileList2"
				@afterRead="afterRead"
				@delete="deletePic"
				name="2"
				multiple
				:maxCount="10"
				accept="video"
				deletable
				uploadText="视频"
			></u-upload>
			<!-- <video style="width: 80%;height: 70%;overflow: hidden;margin-top: 1%;border-radius: 2%;" :src="selectedVideo"></video> -->
			<view style="display: flex;position: fixed;width: 90%;margin-left: 4%;bottom: 50rpx;">
				<u-button @tap="videoCancel()" text="取消"  size="normal"></u-button>
				<u-button style="margin-left: 2%;" @tap="sendVideo()" text="发送"  size="normal" class="send-btn-able" type="primary"></u-button>
			</view>
		</view>
		<view class="file-container" v-show="selectedFile">
			<view>
				<!-- <image style="width: 100rpx;height: 100rpx;margin-top: 60rpx;" src="@/static/social/icon/clip.png"></image> -->
				<view style="display: block;">
					<u-upload
						:fileList="fileList3"
						@afterRead="afterRead"
						@delete="deletePic"
						name="3"
						size="(selectedFile.size/1048576).toFixed(2)+'MB'"
						multiple
						:maxCount="10"
						:previewFullImage="true"
						accept="file"
						deletable
						uploadText="文件"
					></u-upload>
					<!-- <view>{{selectedFile===null?'':selectedFile.name}}</view>
					<view>{{selectedFile===null?'':(selectedFile.size/1048576).toFixed(2)+'MB'}}</view> -->
				</view>
			</view>
			<view style="display: flex;position: fixed;width: 90%;margin-left: 4%;bottom: 50rpx;">
				<u-button @tap="fileCancel()" text="取消"  size="normal"></u-button>
				<u-button style="margin-left: 2%;" @tap="sendFile()" text="发送"  size="normal" class="send-btn-able" type="primary"></u-button>
			</view>
			
		</view>
		<view :style="{'height':keyboradHeight+'px'}"></view>
	</view>
</template>

<script>
	import emojiList from '@/static/social/emoji/emojiList.js';
	// import MumuRecorder from '@/uni_modules/mumu-recorder/components/mumu-recorder/mumu-recorder.vue';
	import audioPlayer from '@/components/social/imt-audio/imt-audio.vue';
	import timeUtil from '@/utils/social/timeUtil.js';
	import requestUrl from '@/api/social/url.js';
	import $store from '@/store/modules/social';
	export default{
		name: 'inputBoxOff',
		props:{
			message:String,
			text:String,
			maxImageMB:{
				type: Number,
				default: 5
			},
			maxVideoMB:{
				type: Number,
				default: 10
			},
			maxFileMB:{
				type: Number,
				default: 10
			},
			fileExtensions:{
				type: Array,
				default: () => ['.zip','.doc','.txt','.pdf','.doc','.docx','.ppt','.exe','.html','.js','.css','.rar','.xls','.png','.jpg','.mp3','.mp4','.wav','.gif']
			},
		},
		components:{
			audioPlayer
		},
		watch:{
			text: function(newData,oldData){
				if(newData!== ''){
					this.isDisable = false;
					if(newData[newData.length-1]==='\n'){
						this.text = this.text.replace('\n', ' ')
					}
				}else this.isDisable = true
			},
			showDrawer: function(newData,oldData){
				if(oldData === 5){
					if(this.status){
						this.$refs.recorder.stop();
						this.status = !this.status;
						this.recorder = null;
						this.$refs.recorder.reSet();
					}
				}
				if(newData !==5 ) this.audioCancel()
				if(newData !==2 ) this.imageCancel()
				if(newData !==3 ) this.videoCancel()
				if(newData !==4 ) this.fileCancel()
				switch(newData){
					case 0: this.height = 0; break;
					case 1: this.height = 600; break;
					case 2: 
					case 3: 
					case 4: this.height = 500; break;
					case 5: this.height = 450; break;
				}
				this.$emit('bottomHeight', this.height+150);
			}
		},
		mounted() {
			// #ifdef APP-PLUS
			//  监听键盘高度变化	
			uni.onKeyboardHeightChange(res => {
				if(res.height === 0){
					 this.keyboradHeight = 0;
					 this.$emit('keyboradHeight', 20);
				}else{
					this.$emit('keyboradHeight', 90);
					if(this.showDrawer === 0){
						this.keyboradHeight = 35
					}
					else{
						this.showDrawer = 0;
						this.keyboradHeight = res.height
					}			  
				}
			})
			
			// #endif
		},
		created() {
			this.emojiList = emojiList;
		},
		data(){
			return{
				
				selectedImageimage: false,
				selectedVideo: false,
				selectedFile: false,
				fileList1: [],
				fileList2: [],
				fileList3: [],
				picurl: [],
				
				isDisable: true,
				text: '',
				showDrawer: 0,
				emojiList: [],
				status: false,
				recorder: null,
				selectedImage: null,
				selectedVideo: null,
				selectedFile: null,
				audioTime: 0,
				fileOption: {
					url: 'null'
				},
				keyboradHeight: 0,
				height: 0,
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
										//this.picurl.push(url)
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
			showKeyBoard(){
				this.showDrawer = 0
			},
			//发送文件消息
			sendFile(){
				// #ifdef APP-PLUS
				// uni.showToast({
				// 	icon:'none',
				// 	title:'手机端暂时不行'
				// })
				// return
				// #endif
				let that = this
				uni.showLoading({
					title:'正在上传文件'
				})
				var regExp = /(.*\/)*([^.]+).*/ig;
				name = this.picurl.replace(regExp,"$2");
				console.log(name);
				console.log(this.picurl.size);
				let fileString = {
				    name: name,
					size: (this.picurl.size/1048576).toFixed(2)+'MB',
					url: this.picurl.download
				}
				
				let message = {
					type: 'file',
					time: timeUtil.getFormatTime(new Date()),
					content: JSON.stringify(fileString)
				}
				that.$emit('sendMessage', message);
				that.fileCancel();
				
				// uni.uploadFile({
				// 	url: requestUrl.getUrl()+'ry-vue/file/upload', //文件上传接口
				// 	filePath: this.selectedFile.url,
				// 	name: 'file',
				// 	fail(res) {
				// 		console.log(res)
				// 	},
				// 	success: (res) => {
				// 		console.log("----------------------------")
				// 		console.log(res)
				// 		uni.hideLoading();
				// 		let response = JSON.parse(res.data);
				// 		if(!response.success){
				// 			uni.showToast({
				// 				icon:'error',
				// 				title:response.Message
				// 			})
				// 		}else{
				// 			let fileString = {
				// 				name: response.data.name,
				// 				size: response.data.size,
				// 				url: response.data.download
				// 			}
							
				// 			let message = {
				// 				type: 'file',
				// 				time: timeUtil.getFormatTime(new Date()),
				// 				content: JSON.stringify(fileString)
				// 			}
				// 			that.$emit('sendMessage', message);
				// 			that.fileCancel();
				// 		}
				// 	}
				// });
			},
			//发送视频消息
			sendVideo(){
				let that = this
				uni.showLoading({
					title:'正在上传视频'
				})
				let message = {
					type: 'video',
					time: timeUtil.getFormatTime(new Date()),
					content:  this.picurl
				}
				that.$emit('sendMessage', message);
				that.videoCancel();
				that.selectedVideo = false;
				$store.dispatch('getPersonMessage')
				$store.dispatch('getGroupMessage')
			},
			//发送图片消息
			sendImage(){
				let that = this
				// uni.showLoading({
				// 	title:'正在上传图片'
				// })
				let imageString = {
					"originUrl":  this.picurl,
					"compressUrl": this.picurl
				}
				let message = {
					type: 'image',
					time: timeUtil.getFormatTime(new Date()),
					content: JSON.stringify(imageString)
				}
				if(this.picurl){
					uni.request({
						url:'http://localhost:8080/scanImage',
						method:'POST',
						data:{
							image:this.picurl
						},
						success:res =>{
							console.log(res,"res")
							if(res.data.state != "pass"){
								uni.showToast({
									icon:'error',
									title:'检测到图片不合法'
								})
							}else{
								that.$emit('sendMessage', message);
									that.imageCancel();
									that.selectedImageimage = false;
									$store.dispatch('getPersonMessage')
									$store.dispatch('getGroupMessage')
								}
							},
						})
				}
				
		},
			//发送语音消息
			sendAudio(){
				// #ifdef APP-PLUS
				uni.showToast({
					icon:'none',
					title:'手机端暂时不行'
				})
				return
				// #endif
				let that = this
				uni.showLoading({
					title:'正在上传音频'
				})
				//console.log(this.recorder)
				uni.uploadFile({
					url: requestUrl.getUrl()+'ry-vue/audio/upload', //音频上传接口
					//filePath: that.recorder.localUrl,
					file: that.recorder.data,
					name: 'audio',
					success: (res) => {
						uni.hideLoading();
						let response = JSON.parse(res.data);
						if(!response.success){
							uni.showToast({
								icon:'error',
								title:response.Message
							})
						}else{
							let audioString = {
								url: requestUrl.getUrl() + response.data.url,
								duration: that.audioTime
							}
							//console.log(audioString)
							let message = {
								type: 'audio',
								time: timeUtil.getFormatTime(new Date()),
								content: JSON.stringify(audioString)
							}
							that.$emit('sendMessage', message);
							that.recorder = null,
							that.$refs.recorder.reSet();
							that.audioTime = 0;
						}
					}
				});
			},
			///发送文字
			sendText(){
				if(this.isDisable) return;
				if(this.text.replace(/ /g,'') === ''){
					uni.showToast({
						icon:'none',
						title:'请输入有效内容'
					})
					this.text = '';
					return;
				}
				let message = {
					type: 'text',
					time: timeUtil.getFormatTime(new Date()),
					content: this.text
				}
				if(this.text){ 
				     // uni.request({
				     //  url:'http://localhost:8080/scanText',
				     //  method:'POST',
				     //  data:{
				     //   text:this.text
				     //  },
				     //  success:res =>{
				     //   console.log(res,"res")
				     //   if(res.data.state == "block"){
				     //    uni.showToast({
				     //     icon:'error',
				     //     title:'检测到不合法内容'
				     //    })
				     //   }else{
				        this.$emit('sendMessage', message);
				        this.text = '';
				        $store.dispatch('getPersonMessage')
				        $store.dispatch('getGroupMessage')
				     //   }
				     //  },
				     // })
				    }
				console.log(message);
			},
			
			openDrawer(index){
				if(this.showDrawer === index)	this.showDrawer = 0;
				else this.showDrawer = index
			},
			deleteAEmoji(){
				if(this.text==='' || this.text.lastIndexOf('[') === '-1') return;
				this.text = this.text.slice(0, this.text.lastIndexOf('['));
			},
			handlerSave() {
			    let tag = document.createElement('a')
			    tag.href = this.recorder.localUrl
			    tag.download = '录音'
			    tag.click()
			},
			handlerOnCahnger() {
			    if (this.status) {
			        this.$refs.recorder.stop()
			    } else {
			        this.$refs.recorder.start()
					setTimeout(() => {
						this.handleAudioTime()
					}, 1000)
			    }
			    this.status = !this.status
			},
			handleAudioTime(){
				if(this.status){
					this.audioTime ++;
					setTimeout(() => {
						this.handleAudioTime()
					}, 1000)
				}
			},
			handlerSuccess(res) {
				//console.log(res)
				if(res.duration<1){
					uni.showToast({
						icon:'error',
						title:'录音时长太短！'
					})
					this.$refs.recorder.reSet();
					return
				}
				if(res.duration>60){
					uni.showToast({
						icon:'error',
						title:'录音时长太长！'
					})
					this.$refs.recorder.reSet();
					return
				}
			    this.recorder = res
			},
			handlerError(code) {
			    switch (code) {
			        case '101':
			            uni.showModal({
			                content: '当前浏览器版本较低，请更换浏览器使用，推荐在微信中打开。'
			            })
			            break;
			        case '201':
			            uni.showModal({
			                content: '麦克风权限被拒绝，请刷新页面后授权麦克风权限。'
			            })
			            break;
			        // default:
			        //     uni.showModal({
			        //         content: '未知错误，请刷新页面重试'
			        //     })
			        //     break;
			    }
			},
			audioCancel(){
				this.recorder = null,
				this.$refs.recorder.reSet();
				this.audioTime = 0;
				if(this.showDrawer === 5) this.showDrawer = 0;
			},
			imageCancel(){
				this.selectedImageimage = null;
				if(this.showDrawer === 2) this.showDrawer = 0;
			},
			videoCancel(){
				this.selectedVideo = null;
				if(this.showDrawer === 3) this.showDrawer = 0;
			},
			fileCancel(){
				this.selectedFile = null;
				// #ifdef APP-PLUS
				this.$refs.lsjUpload.reSet()
				// #endif	
				if(this.showDrawer === 4) this.showDrawer = 0;
			},
			selectImage(){
				if(this.selectedImageimage == false){
					this.selectedImageimage = true;
				}else{
					this.selectedImageimage = false;
				}
			},
			selectVideo(){
				if(this.selectedVideo == false){
					this.selectedVideo = true;
				}else{
					this.selectedVideo = false;
				}
			},
			selectFile(){
				//var self = this;
				// #ifdef H5
				if(this.selectedFile == false){
					this.selectedFile = true;
				}else{
					this.selectedFile = false;
				}
				// uni.chooseFile({
				//   count: 1, //默认100
				//   extension: this.fileExtensions,
				// 	success: function (res) {
				// 		if(self.maxFileMB*1048576 < res.tempFiles[0].size){
				// 			uni.showToast({
				// 				icon:'none',
				// 				title:'文件请限制在'+self.maxFileMB+'MB以内'
				// 			})
				// 		}else{
				// 			self.showDrawer = 4;
				// 			self.$nextTick(()=>{
				// 				self.selectedFile = {
				// 					name: res.tempFiles[0].name,
				// 					size: res.tempFiles[0].size,
				// 					url: res.tempFilePaths[0]
				// 				}
				// 			})
				// 			// console.log(self.selectedFile)
				// 		}
					
				// 	}
				// });
				// #endif
			},
			fileOnChange(files){
				this.showDrawer = 4;
				files.forEach((key,val)=>{
				   this.selectedFile = {
					   name: key.name,
					   size: key.size,
					   url: key.path
				   }
				})
			},
		}
	}
</script>

<style lang="scss" scoped>
	.container{
		position: fixed;
		bottom: 0;
		width: 100%;
		background-color:#FFFFFF;
	}
	.input-area{
		margin-left: 1%;
		padding: 10rpx;
		background-color: #efefef;
		border-radius: 20rpx;
		width: 72%;
		max-height: 180rpx;
		overflow-y: scroll;
		overflow-x: hidden;
	}
	.send-btn{
		width: 10%;
		height: 60rpx;
		font-weight: 540;
		font-size: 30rpx;
		font-family: Verdana, Geneva, Tahoma, sans-serif;
		color: rgb(255,254,255);
		border-radius: 20rpx;
		text-align: center;
		position: absolute;
		right: 2%;
		bottom: 14%;
		line-height:60rpx;
		 
	}
	.send-btn-disable{
		background-color: #ffffff;
	}
	.send-btn-able{
		background-color: #ffffff;
	}
	.send-btn-able:active{
		background-color: #ffffff;
	}
	.icon-container{
		//margin-top: -10rpx;
		margin-top: 10rpx;
		margin-bottom: 0rpx;
		padding-left: 20rpx;
		.input-icon{
			width: 55rpx;
			height: 55rpx;
			margin-left: 20rpx;
			margin-right: 30rpx;
		}
	}
	.emoji-container{
		padding-left: 10rpx;
		width: 100%;
		height: 600rpx;
		background-color: #eaeaea;
		overflow: scroll;
		overflow-x: hidden;
		position: relative;
		.btn{
			text-align: center;
			line-height: 80rpx;
			width: 100rpx;
			height: 80rpx;
			border-radius: 20rpx;
			background-color: #ffffff;
			margin-left: 20rpx;
		}
		.btn:active{
			background-color: #cfcfcf;
		}
		.emoji{
			width: 50rpx;
			height: 50rpx;
			margin: 20rpx;
		}
	}

	.recorder-container{
		padding-left: 10rpx;
		width: 100%;
		height: 450rpx;
		background-color: #eaeaea;
		text-align: center;
		.record-btn{
			width: 200rpx;
			height: 200rpx;
			margin-top: 100rpx;
		}
		.btn{
			text-align: center;
			line-height: 80rpx;
			width: 100rpx;
			height: 80rpx;
			border-radius: 20rpx;
			background-color: #ffffff;
			margin-left: 20rpx;
		}
		.btn:active{
			background-color: #cfcfcf;
		}
	}
	.image-container{
		padding-left: 10rpx;
		width: 100%;
		height: 500rpx;
		background-color: #eaeaea;
		text-align: center;
	}
	.video-container{
		padding-left: 10rpx;
		width: 100%;
		height: 500rpx;
		background-color: #eaeaea;
		text-align: center;
	}
	.file-container{
		padding-left: 10rpx;
		width: 100%;
		height: 500rpx;
		background-color: #eaeaea;
		text-align: center;
	}
</style>