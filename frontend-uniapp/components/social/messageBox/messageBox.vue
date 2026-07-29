<template>
	<view >
		<!-- #ifdef APP-PLUS -->
		<view class="background-color"></view>
		<!-- #endif -->
		<view class="tips color_fff size_12 align_c" :class="{ 'show':ajax.loading }">{{ajax.loadText}}</view>
		<view class="box-1" id="list-box" :style="{'padding-bottom':bottomHeight+'rpx'}">
			<view class="talk-list" :style="{'padding-bottom':keyboardHeight+'rpx'}">
				<view class="message-list" v-for="(item,index) in personMessage[s_index].list" :key="index" :id="`msg-${item.id}`">
					<view v-show="index===0||timeShowAble(personMessage[s_index].list[index-1].sendTime,personMessage[s_index].list[index].sendTime)" class="time">{{timeShow(item.sendTime)}}</view>
					<view v-if="item.isWithdrawn===1 && item.senderId == loginUserInfo.userId" class="time" @click="reEdit()">你撤回了一条消息 重新编辑</view>
					<view v-if="item.isWithdrawn===1 && item.senderId != loginUserInfo.userId" class="time">对方撤回了一条消息</view>
					<!-- <view v-if="item.isWithdrawn===0" class="item flex_col" :class=" item.senderId == loginUserInfo.userId ? 'push':'pull' "> -->
					<view v-if="item.isWithdrawn===0 && item.isShowMessage == false" class="time">你删除了一条消息</view>	
					<view v-if="item.isWithdrawn===0 && item.isShowMessage == true" class="item flex_col" :class=" item.senderId == loginUserInfo.userId ? 'push':'pull' ">
						<image @tap="gotoPersonInfo(item.senderId)" :src="item.senderId == loginUserInfo.userId ? 'http://localhost:8080'+loginUserInfo.avatar:'http://localhost:8080'+chattingUserInfo.avatar" mode="aspectFill" class="pic"></image>
						<view class="content" @longpress="onLongPress($event, item)">
							<rich-text v-if="item.messageType==='text'" :nodes="replaceEmoji(item.content)"></rich-text>
							<view v-if="item.messageType==='audio'">
								<view v-if="item.senderId == loginUserInfo.userId" @tap="playAudio(item.content)" style="display: flex;">{{showAudioDuration(item.content)+"''"}}<image style="height: 45rpx;width: 45rpx;" src="@/static/social/icon/audio-push.png"></image></view>
								<view v-if="item.senderId != loginUserInfo.userId" @tap="playAudio(item.content)"  style="display: flex;"><image style="height: 45rpx;width: 45rpx;" src="@/static/social/icon/audio-pull.png"></image>{{showAudioDuration(item.content)+"''"}}</view>
							</view>
							<image v-if="item.messageType==='image'" mode="aspectFill" class="message-image" :src="showImage(item.content,'compress')" @tap="showImage(item.content,'origin')"></image>
							<video v-if="item.messageType==='video'" class="message-video" :src="item.content"></video>
							<view v-if="item.messageType==='file'">
								<image src="@/static/social/icon/clip.png" style="width: 40rpx;height: 40rpx;"></image>
								<text style="display: block;">附件：{{JSON.parse(item.content).name}}</text>
								<text>大小：{{JSON.parse(item.content).size}}</text>
								<button style="background-color: deepskyblue;" @tap="downloadFile(item.content)">点击下载</button>
							</view>
						</view>
					</view>
					<!-- </view> -->
				</view>
			</view>
		</view>
		
		
		<view class="shade" v-show="showShade" @tap="hidePop">
			<view class="pop" :style="popStyle" :class="{'show':showPop}">
				<view v-for="(item,index) in popButton" :key="index" @tap="pickerMenu" :data-index="index">{{item}}</view>
			</view>
		</view>
		
	</view>
</template>

<script>
	import EmojiUtil from '@/static/social/emoji/replaceEmoji.js';
	import {mapGetters,mapActions} from 'vuex';
	import timeUtil from '@/utils/social/timeUtil.js';
	import $store from '@/store/modules/social';
	import userRequest from '@/api/social/user.js';
	import requestUrl from '@/api/social/url.js';

	
	export default {
		data() {
			return {
				ajax:{
					rows:20,	//每页数量
					page:1,	//页码
					flag:true,	// 请求开关
					loading:true,	// 加载中
					loadText:'正在获取消息'
				},
				content:'',
				bottomHeight: 150,
				keyboardHeight: 20,
				
				s_index: 0,
				
				/* 窗口尺寸 */
				winSize: {},
				/* 显示遮罩 */
				showShade: false,
				/* 显示操作弹窗 */
				showPop: false,
				/* 弹窗按钮列表 */
				popButton: ["复制","撤回","删除"],
				/* 弹窗定位样式 */
				popStyle: "",
				/* 选择的用户下标 */
				pickerUserIndex: -1,
				selectedMessage: {},
				
				
				lastPosition:null,
			}
		},
		computed:{
			...mapGetters(['personMessage','chattingUserInfo','loginUserInfo','lastUpdateSession'])
		},
		mounted() {
			this.$nextTick(()=>{
				this.goToBottom();
			});
		},
		created() {
			this.getWindowSize();
			console.log('聊天页面初始化')
			for(let i=0;i<this.personMessage.length;i++){
				if(this.personMessage[i].sessionId==this.chattingUserInfo.sessionId){
					this.s_index = i;
					break;
				}
			}
			if(this.personMessage[this.s_index].list.length!=0) this.lastPosition = this.personMessage[this.s_index].list[0].id
		},
		watch:{
			lastUpdateSession(newSession,oldSession){
				console.log(newSession)
				if(newSession.id==this.chattingUserInfo.sessionId){
					this.$nextTick(()=>{
						this.goToBottom();
					})
				}
			},
			lastPosition:function(newData, oldData){
				if(oldData==null) return
				let selector = `#msg-${oldData}`;
				console.log(selector)
				// 数据挂载后执行，不懂的请自行阅读 Vue.js 文档对 Vue.nextTick 函数说明。
				this.$nextTick(()=>{
					// 设置当前滚动的位置
					this.setPageScrollTo(selector);
					//this.hideLoadTips(true);
				})
			}
		},
		methods: {
			//重新编辑
			reEdit(){
				for(let i=0;i<this.personMessage.length;i++){
					if(this.personMessage[i].sessionId==this.chattingUserInfo.sessionId){
						this.s_index = i;
						break
					}
				}
				let message = this.personMessage[this.s_index].list[this.personMessage[this.s_index].list.length-1].content;
				console.log(message)
				this.handReEdit(message)
			
			},
			handReEdit(message){
				this.$emit('update-message',message)
			},			
			gotoPersonInfo(senderId){
				if(senderId==this.loginUserInfo.userId)
				{
					uni.navigateTo({
						url:'/pages/social/info/person-info'
					})
				}else{
					uni.navigateTo({
						url:'/pages/social/info/friend-info?id='+this.chattingUserInfo.chattingUserId
					})
				}
			},
			// 设置页面滚动位置
			setPageScrollTo(selector){
				console.log('页面滚动。。。')
				let view = uni.createSelectorQuery().in(this).select(selector);
				view.boundingClientRect((res) => {
					uni.pageScrollTo({
					    scrollTop:res.top - 30,	// -30 为多显示出大半个消息的高度，示意上面还有信息。
					    duration: 0
					});
				}).exec();
			},
			showLoadTips(str){
				this.ajax.loadText = str;
				this.ajax.loading = true
				setTimeout(()=>{
					this.ajax.loading = false;
				},800);
			},
			async getHistoryMsg(){
				//console.log(this.personMessage[this.s_index])
				if(!this.personMessage[this.s_index].hasNextPage){
					this.showLoadTips('已获取全部聊天记录')
					return;
				}
				this.showLoadTips('正在拉取历史消息...')
				// this.$store.dispatch('getHistory');
				let res = await userRequest.getPeronMessage({
					sessionId:this.chattingUserInfo.sessionId,
					pageNum:this.personMessage[this.s_index].pageNum+1,
					pageSize:this.personMessage[this.s_index].pageSize,
					lastMessageId:this.personMessage[this.s_index].lastMessageId,
					currentUserId:this.loginUserInfo.userId
				})
				console.log(res)
				let page = res.data.pageInfo
				let current = Number(page.current)
				let total = Number(page.total)
				let size = Number(page.size)
				$store.state.personMessage[this.s_index].hasNextPage = current*size<total
				$store.state.personMessage[this.s_index].pageNum = current
				this.lastPosition = page.records[0].id
				$store.state.personMessage[this.s_index].list = page.records.reverse().concat($store.state.personMessage[this.s_index].list)
				
			},
			//文件下载
			downloadFile(content){
				let t = JSON.parse(content);
				let that = this;
				uni.showModal({
					title:'提示',
					content:'是否下载该文件',
					confirmText:'确定',
					confirmColor:'#D94B4D',
					cancelText:'取消',
					success: (res) => {
						if(res.confirm){
							if(res.confirm){
								//#ifdef H5				
								window.location.href=requestUrl.getUrl()+'ry-vue/file/download?url='+t.url+'&name='+t.name;								
								//#endif
								//#ifdef APP-PLUS
								plus.runtime.openURL(requestUrl.getUrl()+'ry-vue/file/download?url='+t.url+'&name='+t.name);
								//#endif
							}
						}
					}
				})
			
				
			},
			//撤回消息
			//显示图片showImage
			showImage(imageString, type){
				// console.log(imageString)
				let image = JSON.parse(imageString);
				if(type==='compress') return image.compressUrl;
				else{
					uni.previewImage({
						urls:[image.originUrl]
					})
				}
			},
			//播放语音
			playAudio(audioString){
				//console.log(audioString)
				let audio = JSON.parse(audioString);
				var music = null;
				uni.showLoading({
					title:'正在播放音频...'
				})
				music = uni.createInnerAudioContext(); //创建播放器对象
				music.src = audio.url;
				music.play(); //执行播放
				music.onEnded(() => {
				    //播放结束
				    music = null;
					uni.hideLoading();
				});
			},
			///显示语音时长
			showAudioDuration(audioString){
				let audio = JSON.parse(audioString)
				return audio.duration;
			},
			///替换emoji
			replaceEmoji(text){
				return EmojiUtil.replaceEmoji(text);
			},
			///滑到底部
			goToBottom(){
				uni.pageScrollTo({
					scrollTop: 999999,
					duration: 0
				})
				this.hideLoadTips(true)
			},
			///显示时间的样式
			timeShow(time){
				return timeUtil.timeShowFormat(time);
			},
			///是否显示时间
			timeShowAble(time1, time2){
				if(timeUtil.TimeDifferenceSeconds(time1, time2) > 300)	return true;
				return false;
			},
			setBottomHeigth(height){
				this.bottomHeight = height;
				this.$nextTick(()=>{
					uni.pageScrollTo({
						scrollTop: 99999999,
						duration: 0
					})
				})
			},
			setKeyboardHeight(height){
				this.keyboardHeight = height;
				this.$nextTick(()=>{
					uni.pageScrollTo({
						scrollTop: 99999999,
						duration: 0
					})
				})
			},
			// 隐藏加载提示
			hideLoadTips(flag){
				if(flag){
					this.ajax.loadText = '消息获取成功';
					setTimeout(()=>{
						this.ajax.loading = false;
					},300);
				}else{
					this.ajax.loading = true;
					this.ajax.loadText = '正在获取消息';
				}
			},
			/* 获取窗口尺寸 */
			getWindowSize() {
				uni.getSystemInfo({
					success: (res) => {
						this.winSize = {
							"witdh": res.windowWidth,
							"height": res.windowHeight
						}
					}
				})
			},
			/* 长按监听 */
			onLongPress(e, message) {
				this.selectedMessage = message;
				let [touches, style] = [e.touches[0], ""];
				/* 因 非H5端不兼容 style 属性绑定 Object ，所以拼接字符 */
				if (touches.clientY > (this.winSize.height / 2)) {
					style = `bottom:${this.winSize.height-touches.clientY}px;`;
				} else {
					style = `top:${touches.clientY}px;`;
				}
				if (touches.clientX > (this.winSize.witdh / 2)) {
					style += `right:${this.winSize.witdh-touches.clientX}px`;
				} else {
					style += `left:${touches.clientX}px`;
				}
			
				this.popStyle = style;
				this.showShade = true;
				this.$nextTick(() => {
					setTimeout(() => {
						this.showPop = true;
					}, 10);
				});
			},
			/* 隐藏弹窗 */
			hidePop() {
				this.showPop = false;
				this.pickerUserIndex = -1;
				setTimeout(() => {
					this.showShade = false;
				}, 250);
			},
			/* 选择菜单 */
			pickerMenu(e) {
				
				console.log(e);
				let index = Number(e.currentTarget.dataset.index);
				switch(index){
					case 0:
					uni.setClipboardData({
						data:this.selectedMessage.content,
						success: function () {
							uni.showToast({
								title:'复制成功',
								icon:'none'
							})
						}
					})
					break;
					case 1:
					this.withdrawMessage();
					break;
					case 2:
					this.deleteMessage();
					break;
				}
			
				/* 
				 因为隐藏弹窗方法中会将当前选择的用户下标还原为-1,
				 如果行的菜单方法存在异步情况，请在隐藏之前将该值保存，或通过参数传入异步函数中
				 */
				this.hidePop();
			},
			
			deleteMessage(){
				let id =  this.selectedMessage.id
				// console.log(id+"11111111");
				let that = this
				uni.showModal({
					cancelText:'取消',
					confirmText:'删除',
					title:'确认删除',
					success(res) {
						if(res.confirm){
							that.postDelete(id)
						}
					}
				})
			},
			async postDelete(id){
				let res = await userRequest.deleteMessage({
					id:id,
				})
				uni.showToast({
					title:'删除成功',
					icon:'success'
				})
				$store.dispatch('getPersonMessage')
			},
			withdrawMessage(){
				if(this.selectedMessage.senderId!=this.loginUserInfo.userId){
					uni.showToast({
						title:'仅可以撤回自己的消息',
						icon:'none'
					})
					return;
				} 
				let seconds = timeUtil.TimeDifferenceSeconds(this.selectedMessage.sendTime, new Date());
				if(seconds>180){ //超过三分钟不可以撤回
					uni.showToast({
						icon:'none',
						title:'仅可以撤回三分钟以内的消息'
					})
					return;
				}
				let message = {
					type: 'person-withdraw',
					data: {
						sessionId: this.chattingUserInfo.sessionId,
						messageId: this.selectedMessage.id,
						senderId: this.loginUserInfo.userId,
						receiverId: this.chattingUserInfo.chattingUserId
					}
				}
				uni.sendSocketMessage({
					data: JSON.stringify(message),
					fail() {
						uni.showToast({
							icon:'error',
							title:'撤回失败'
						})
					}
				})
			},
		}
	}
</script>
<style lang="scss">
	
	page {
	  background-color: #fff;
	  // background-image: url('@/static/social/bg.webp');
	  font-size: 28rpx;
	  box-sizing: border-box;
	  color: #000000;
	  letter-spacing: 0;
	  word-wrap: break-word;
	}
	
</style>
<style lang="scss" scoped>
	@import "@/components/social/messageBox/global.scss";

	// page{
	// 	background-color: #333;
	// 	font-size: 28rpx;
	// }
	.background-color{
		position: fixed;
		//background-color: #E0E0E0  ;
		background-image: url('@/static/social/bg.webp');
		z-index: -1;
		width: 100%;
		height: 100%;
		top: -1rpx;
	}
	.text-show{
		/* padding: 10p;
		display: flex;
		align-items: center;
		word-wrap:break-word; */
		display: -webkit-box;
		overflow: hidden;
		text-overflow: ellipsis;
		word-wrap: break-word;
		white-space: normal !important;
		-webkit-box-orient: vertical;
		height: 100%;
		letter-spacing: 0.08em;
		vertical-align: text-bottom;
		font-size: 10;
		.emoji-text{
			width: 50rpx;
			height: auto;
			margin-bottom: -12rpx;
		}
	}
	
	/* 加载数据提示 */
	.tips{
		position: fixed;
		left: 0;
		top:var(--window-top);
		width: 100%;
		z-index: 9;
		background-color: rgba(0,0,0,0.15);
		height: 72rpx;
		line-height: 72rpx;
		transform:translateY(-80rpx);
		transition: transform 0.3s ease-in-out 0s;
		
		&.show{
			transform:translateY(0);
		}
	}
	.box-1{
		z-index: 1;
		width: 100%;
		height: auto;
		padding-bottom: 150rpx;
		box-sizing: content-box;
		
		/* 兼容iPhoneX */
		margin-bottom: 0;  
		margin-bottom: constant(safe-area-inset-bottom);  
		margin-bottom: env(safe-area-inset-bottom);  
	}
	.talk-list{
		padding-bottom: 20rpx;
		z-index: 1;
		/* 消息项，基础类 */
		.message-list{
			
		}
		.message-image{
			width: 300rpx;
			height: 200rpx;
		}
		.message-video{
			width: 400rpx;
			height: 300rpx;
			z-index: 0;
		}
		.time{
			color: #BEBEBE;
			margin-left: auto;
			margin-right: auto;
			text-align: center;
			font-size: 25rpx;
			margin-top: 20rpx;
			z-index: 2;
		}

		.item{
			padding: 20rpx 20rpx 0 20rpx;
			align-items:flex-start;
			align-content:flex-start;
			color: #333;
			
			.pic{
				width: 92rpx;
				height: 92rpx;
				border-radius: 50%;
				border: #fff solid 1px;
			}
			
			.content{
				
				border-radius: 7px;
				max-width: 500rpx;
				word-break: break-all;
				position: relative;
				
				.text-show{
					padding: 20rpx;
					font-size: 13px;
					line-height: 1.8;
					letter-spacing: 0.16em;
				}
			}
			
			/* 收到的消息 */
			&.pull{
				.content{
					margin-left: 32rpx;
					background-color: #EFEFEF;
					
					&::after{
						content: '';
						display: block;
						width: 0;
						height: 0;
						border-top: 16rpx solid transparent;
						border-bottom: 16rpx solid transparent;
						/* border-right: 20rpx solid #EFEFEF; */
						position: absolute;
						top: 30rpx;
						left: -18rpx;
					}
				}
			}
			
			/* 发出的消息 */
			&.push{
				/* 主轴为水平方向，起点在右端。使不修改DOM结构，也能改变元素排列顺序 */
				flex-direction: row-reverse;
				
				.content{
					margin-right: 32rpx;
					background-color: #BCEE68;
					
					&::after{
						content: '';
						display: block;
						width: 0;
						height: 0;
						border-top: 16rpx solid transparent;
						border-bottom: 16rpx solid transparent;
						/* border-left: 20rpx solid #BCEE68; */
						position: absolute;
						top: 30rpx;
						right: -18rpx;
					}
				}
			}
		}
	}
	/* 遮罩 */
	.shade {
		position: fixed;
		z-index: 100;
		top: 0;
		right: 0;
		bottom: 0;
		left: 0;
		-webkit-touch-callout: none;
	
		.pop {
			position: fixed;
			z-index: 101;
			width: 200upx;
			box-sizing: border-box;
			font-size: 28upx;
			text-align: left;
			color: #333;
			background-color: #fff;
			box-shadow: 0 0 5px rgba(0, 0, 0, 0.5);
			line-height: 80upx;
			transition: transform 0.15s ease-in-out 0s;
			user-select: none;
			-webkit-touch-callout: none;
			transform: scale(0, 0);
	
			&.show {
				transform: scale(1, 1);
			}
	
			&>view {
				padding: 0 20upx;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
				user-select: none;
				-webkit-touch-callout: none;
	
				&:active {
					background-color: #f3f3f3;
				}
			}
		}
	}
</style>