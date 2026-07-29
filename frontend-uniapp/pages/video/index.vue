<template>
	<view>
		<!--swiper实现整屏划动播放视频-->
		<swiper circular vertical duration="200" @change="changed"
			:style="{height: screenHeight-navBarHeight +'px'}">
			<block v-for="(item,index) in displaySwiperList" :key="index">
				<swiper-item @click="cencel">
					<!-- v-if="index==changeIndex" 只渲染当前页的视频，能够有效解决数组不断追加后引起黑屏的问题 -->
					<video v-if="index==changeIndex && isLodinged && temp" :src="item.url" autoplay="true" controls="true"
						custom-cache="false" loop="false" enable-play-gesture="true" enable-progress-gesture="true"
						show-center-play-btn="true" @timeupdate="timeupdate"
						 id="video1" ref="video1" show-mute-btn="true" page-gesture="true" title="视频播放" enable-danmu>
					</video>
				</swiper-item>
			</block>
		</swiper>
		<view class="reply-box-2" v-if="showChat">
		</view>
	</view>
</template>
 
<script>
	import TabBar from '../../components/tarBar/tarBar.vue'
	import { followThisUser, cancelFollowThisUser} from '@/api/others.js'
	import {
		videoPage
	} from '@/api/videoPlay.js'
	
	import { addVideoWorkThumbById,getVideoWorkDanmuById,addVideoWorkDanmuById } from '@/api/videoPlay';
	
	import { collect,checkIsCollect,getCollectNumById } from '@/api/collect';
	export default {
		components: {
			TabBar,
			
		},
		data() {
			return {
				screenHeight: 0,
				statusBarHeight: 0,
				navBarHeight: 0,
				originList: [], // 源数据
				displaySwiperList: [], // swiper需要的数据
				displayIndex: 0, // 用于显示swiper的真正的下标数值只有：0，1，2。
				originIndex: 0, // 记录源数据的下标
				changeIndex: 0, //控制video是否渲染
				page: 0, // 视频分页
				num: 0,
				danmuList:[],
				flag: true,
				isLodinged:false,
				pageNo: 1,
				showChat: false,
				danmuForm:{
					text:undefined,
					id:undefined,
					time:undefined
				},
				current : 0,
				temp: true
			}
		},
		
		
		onLoad() {
			
			/* 获取系统信息 */
			wx.getSystemInfo({
				success: (res) => {
					// 获取屏幕高度
					this.screenHeight = res.screenHeight
					// 获取状态栏高度
					this.statusBarHeight = res.statusBarHeight
					// 通过操作系统 确定自定义导航栏高度  
					if (res.system.substring(0, 3) == "iOS") {
						this.navBarHeight = 42
					} else {
						this.navBarHeight = 40
					}
				}
			})
			
			// 调用函数
			this.getPageID()
 
		},
 
		methods: {
			timeupdate(e){
				this.current = e.detail.currentTime;
			},
			
			//收藏
			collectUp(){
				var bl = this.displaySwiperList[this.displayIndex].collect?false:true;
				collect(this.displaySwiperList[this.displayIndex]._id).then(() => {
					this.displaySwiperList[this.displayIndex].collect  = bl;
					this.originList[this.originIndex].collect = bl;
				});
			},
			
			//点赞
			thumbUp(){
				var bl = this.displaySwiperList[this.displayIndex].thumb?false:true;
				addVideoWorkThumbById({id:this.displaySwiperList[this.displayIndex]._id}).then(() => {
					this.displaySwiperList[this.displayIndex].thumb  = bl;
					this.originList[this.originIndex].thumb = bl;
				});
			},
			//关注
			clickFollow(){
				var bl = this.displaySwiperList[this.displayIndex].follow?false:true;
				followThisUser(this.displaySwiperList[this.displayIndex].uid).then(()=>{
					this.displaySwiperList[this.displayIndex].follow  = bl
					this.originList[this.originIndex].follow = bl
					this.updateDate(this.originList[this.originIndex].uid,bl);
				})
			},
			//取关
			clickCancelFollow(){
				var bl = this.displaySwiperList[this.displayIndex].follow?false:true;
				cancelFollowThisUser(this.displaySwiperList[this.displayIndex].uid).then(()=>{
					this.displaySwiperList[this.displayIndex].follow  = bl
					this.originList[this.originIndex].follow = bl
					this.updateDate(this.originList[this.originIndex].uid,bl);
				})
			},
			//更新关注状态， 因为可能同一次请求  多个相同作者
			updateDate(uid,bl){
				for(let i of this.displaySwiperList){
					if(i.uid ==uid){
						i.follow=bl
					}
				}
				for(let i of this.originList){
					if(i.uid ==uid){
						i.follow=bl
					}
				}
			},
			
			//查询分页信息
			getPageID() {
				this.getVideoList(this.pageNo)
			},
			danmuConfirm(e){
				console.log('弹幕',e)
				let videoContext  = uni.createVideoContext(`video1`,this)
				console.log(videoContext)
				
				videoContext.sendDanmu({
				    text: e,
				    color: "#ffffff"
				});
				this.danmuForm.id = this.displaySwiperList[this.displayIndex]._id
				this.danmuForm.time = this.current
				this.danmuForm.text = e
				this.showChat = false
				
				
			},
			// 获取视频数据 
			getVideoList(pageID) {
				videoPage(pageID).then((res) => {
					console.log("this.res.data",res.data)
					this.originList = this.originList.concat(res.data)
					//解决首次加载页面的时候没有画面的问题
					console.log("this.originList",this.originList)
					if (this.flag) {
						this.flag = false
						this.initSwiperData(0)
					}
				})
			},
			//滑动
			changed(event) {
				this.temp = false
				console.log("event",event)
				let {
					current
				} = event.detail;
				let originListLength = this.originList.length;
				this.changeIndex = current;
				console.log("current",current)
				// console.log(this.displayIndex,current)
				// 如果两者的差为2或者-1则是向后滑动
				if (this.displayIndex - current == 2 || this.displayIndex - current == -1) {
					this.originIndex = this.originIndex + 1 == originListLength ? 0 : this.originIndex + 1;
					this.displayIndex = this.displayIndex + 1 == 3 ? 0 : this.displayIndex + 1;
 
					this.initSwiperData(this.originIndex);
					//如果滑到最后一条，请求新数据
					this.num++
					console.log('num',this.num,this.originList.length)
					if (this.num + 5 >= this.originList.length) {
						this.pageNo +=1;
						this.getPageID()
					}
				}
				// 如果两者的差为-2或者1则是向前滑动
				else if (this.displayIndex - current == -2 || this.displayIndex - current == 1) {
					this.originIndex = this.originIndex - 1 == -1 ? originListLength - 1 : this.originIndex - 1;
					this.displayIndex = this.displayIndex - 1 == -1 ? 2 : this.displayIndex - 1;
					this.initSwiperData(this.originIndex);
 
					if (this.num > 0) {
						this.num--
					}
				}
				this.setTemp()
			},
			
			async setTemp(){
				let that = this
				setTimeout(function() {
					that.temp = true
				}, 1000); 
			},
 
			async initSwiperData(originIndex = this.originIndex) {
				this.isLodinged = false;
				console.log("originIndex",originIndex)
				let originListLength = this.originList.length;
				let displayList = [];
				displayList[this.displayIndex] = this.originList[originIndex];
				displayList[this.displayIndex - 1 == -1 ? 2 : this.displayIndex - 1] = this.originList[originIndex - 1 == -
					1 ? originListLength - 1 : originIndex - 1];
				displayList[this.displayIndex + 1 == 3 ? 0 : this.displayIndex + 1] = this.originList[originIndex + 1 ==
					originListLength ? 0 : originIndex + 1];
				this.displaySwiperList = displayList;
				this.isLodinged = true;
			},
			cencel(){
				this.showChat = false
			},
			showComment(){
				this.showChat = true
			}
			
			
			
		
		
		}
	}
</script>
 
<style>
	swiper {
		width: 100%;
		background: #000
	}
 
	swiper-item {
		height: 100%;
		width: 100%
	}
 
	video {
		height: 96%;
		width: 100%
	}
 
	.video-text {
		position: absolute;
		margin-left: 32rpx;
		width: 580rpx;
		bottom: 200rpx;
		z-index: 9999;
	}
 
	.tips {
		width: 560rpx;
		font-size: 26rpx;
		color: #ffffff;
	}
	.right{
		position: fixed;
		right : 20px;
		bottom: 140px;
		z-index:999;
		width:40px;
	}
	.reply-box-2{
		width:100%;
		height:500px;
		position: fixed;
		bottom:0;
		z-index:1000;
	}
	.overlay{
		height:100% !important;
	}
</style>
