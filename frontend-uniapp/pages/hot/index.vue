<template>
	<view>
		<!--  轮播图  -->
		<view class="carousel-section">
			<!-- 标题栏和状态栏占位符 -->
			<view class="titleNview-placing"></view>
			<swiper class="carousel" circular  autoplay="true">
				<swiper-item v-for="(item, index) in carouselList" :key="index" class="carousel-item" @click="navToDetailPage({title: '轮播广告'})">
					<image :src="item.imageUrl" style="height: 100%; width: 100%;"></image>
				</swiper-item>
			</swiper>
		</view>
		<!-- 简闻 -->
		<view class="simple-news-title" style="margin-top: 30rpx;">
			<view class="news-title">
				 视频
			</view>
			<view class="news-content">
				<view class="news-type">
					 热议
				</view>
				<view class="news-msg">
					公布,刘亦菲主唱' 花木兰'刘亦菲主唱' 花木兰'刘亦菲主唱' 花木兰'
				</view>
			</view>
			<view class="more">
				<text class="text-gray cuIcon-right"></text>
			</view>
		</view>
		<!-- news -->
		<view class="news-box">
			<view class="news" v-for="(item, index) in videoList" :key="index" @click="gotoVideo(item._id)">
				<image class="news-pic" :src="item.coverImage" mode=""></image>
				<text class="news-desc">{{item.title}}</text>
			</view>
		</view>
		
		<!-- 简闻 -->
		<view class="simple-news-title" style="margin-top: 30rpx;">
			<view class="news-title">
				 图文
			</view>
			<view class="news-content">
				<view class="news-type">
					 热议
				</view>
				<view class="news-msg">
					公布,刘亦菲主唱' 花木兰'刘亦菲主唱' 花木兰'刘亦菲主唱' 花木兰'
				</view>
			</view>
			<view class="more">
				<text class="text-gray cuIcon-right"></text>
			</view>
		</view>
		<!-- news -->
		<view class="news-box">
			<view class="news" v-for="(item, index) in imageList" :key="index" @click="gotoImage(item._id)">
				<image class="news-pic" :src="item.coverImage" mode=""></image>
				<text class="news-desc">{{item.title}}</text>
			</view>
		</view>
		
		<!-- end -->
	</view>
</template>

<script>
	import uniNavBar from '../../components/uni-ui/uni-nav-bar/uni-nav-bar.vue'
	import { getHotVideoWork, getHotImageWork } from '@/api/hot.js'
	
	export default {
		components: {
			uniNavBar
		},
		data() {
			return {
				swiperCurrent:0,
				swiperLength:6,
				carouselList:[{"id":"3","postscript":"123","imageUrl":"https://reader-station.oss-cn-hangzhou.aliyuncs.com/images/6a7d6ef2-3fb9-4976-8d73-0fd671b79b66image-20230212095559183.png","background":"rgb(183, 73, 69)"},
				{"id":"4","postscript":"123","imageUrl":"https://reader-station.oss-cn-hangzhou.aliyuncs.com/images/9e9ef5e7-ca56-4e03-b119-f919f28f88a670641416d2aeecd89c905643c73bb2b1.jpeg","background":"rgb(255,215,0)"},
				{"id":"6","postscript":"123","imageUrl":"https://reader-station.oss-cn-hangzhou.aliyuncs.com/images/c7051533-4e3b-4ae5-9247-5359e62172b33b9963ccab5b03ff71a696981fee2c17.jpeg","background":"rgb(202,235,216)"},
				{"id":"7","postscript":"123","imageUrl":"https://reader-station.oss-cn-hangzhou.aliyuncs.com/images/ae3537ad-ba6a-4295-980d-7c3dd238b527e101baa2869408ee1bc84e51e12754e1.jpeg","background":"rgb(218,112,214)"},
				{"id":"11","postscript":"123","imageUrl":"https://reader-station.oss-cn-hangzhou.aliyuncs.com/images/86d79961-9965-49c4-97ff-781f8bf6f03236d7f0fdf59f19a56e06b2a59089a3ad.jpeg","background":"rgb(225,255,255)"},
				{"id":"12","postscript":"123","imageUrl":"https://reader-station.oss-cn-hangzhou.aliyuncs.com/images/1c9c1d02-4415-48af-8ff9-a0e8089d164f634316bc9a1b27e446bab3f489653f80.jpeg","background":"rgb(240,230,140)"}],
				cardCur: 0,
				swiperList: [{
						id: 0,
						type: 'image',
						url: 'https://1img.hitv.com/preview/cms_icon/2020/1/4/01/20200104155613840.jpg_2048x550.jpg'
					}, {
						id: 1,
						type: 'image',
						url: 'https://3img.hitv.com/preview/cms_icon/2020/1/26/01/20200126154228745.jpg_2048x550.jpg',
					}, {
						id: 2,
						type: 'image',
						url: 'https://3img.hitv.com/preview/cms_icon/2020/1/26/01/20200126154228745.jpg_2048x550.jpg'
					}
				],
				videoList:[],
				imageList:[]
			}
		},
		
		mounted(){
			getHotImageWork().then((res)=>{
				this.imageList = res
			})
			getHotVideoWork().then((res)=>{
				this.videoList = res
			})
		},
		
		methods: {
			
			cardSwiper(e) {
				this.cardCur = e.detail.current
			},
			goDetail(){
				uni.navigateTo({
					url: '../detail/detail'
				})
			},
			goCategory(){
				uni.switchTab({
					url: '../category/category'
				})
			},
			gotoVideo(id){
				uni.navigateTo({
					url: '/pages/works/videoPlay?id=' + id
				});
			},
			gotoImage(id){
				uni.navigateTo({
					url: '/pages/works/imageContext?id=' + id
				});
			}
		}
	}
</script>

<style>
	@import url("./index.css");
</style>
