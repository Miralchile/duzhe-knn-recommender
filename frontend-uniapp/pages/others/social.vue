<template>
	<view>
		<!-- <Topsearch></Topsearch> -->
		<view>
			<u-tabs-swiper ref="uTabs" :list="list" :current="current" @change="tabsChange" :is-scroll="false"
			 swiperWidth="750" style="background-color:whitesmoke;" name="name"></u-tabs-swiper>
		</view>
		<swiper :current="swiperCurrent" @transition="transition" @animationfinish="animationfinish" style="width:100% ;height:3000rpx">
			<swiper-item class="swiper-item"  :key="0">
				<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="onreachBottom">
					<!-- <ChatList></ChatList> -->
				</scroll-view>
			</swiper-item>
			<swiper-item class="swiper-item"  :key="1">
				<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="onreachBottom">
					<Friend></Friend>
				</scroll-view>
			</swiper-item>
			<swiper-item class="swiper-item" :key="2">
				<scroll-view scroll-y style="height: 100%;width: 100%;" @scrolltolower="onreachBottom">
					<!-- <moment></moment> -->
				</scroll-view>
			</swiper-item>
		</swiper>
	</view>
</template>

<script>
	// import Topsearch from './components/topsearch/topsearch.vue'
	// import Title from './components/title/title.vue'
	// import ChatList from './components/chat/chatlist.vue'
	import Friend from './components/friend/friend.vue'
	// import Moment from './components/moment/moment.vue'
	// // import Find from './components/find/find.vue'
	// import { getUserProfile } from "@/api/system/user" 
	// import $store from '@/store/modules/social';
	export default {
		components:{
			// Topsearch,
			// Title,
			// ChatList,
			Friend
			// Moment
		},
		data() {
			return {
				list: [
					{
					name: '消息'
				},
				{
					name: '好友'
				}, 
				{
					name: '动态'
				}],
				current: 0, // tabs组件的current值，表示当前活动的tab选项
				swiperCurrent: 0, // swiper组件的current值，表示当前那个swiper-item是活动的
				
			};
		},
		onShow() {
			// this.getUser()
		},
		// 下拉刷新
		onPullDownRefresh(){
			///重新获取连接websocket
			if(!$store.state.isSocketOpen){
				websocket.initConnect()
			}
		///从新获取消息列表
		$store.dispatch('getFriendList')
		setTimeout(()=>{
			if($store.state.isSocketOpen){
				uni.showToast({
					icon:'success',
					title:'刷新成功！'
				})
			     $store.dispatch('initSessionList')
			}
			else{
				uni.showToast({
					icon:'error',
					title:'刷新失败！'
				})
			}
			uni.stopPullDownRefresh()
		},100)
		},
		methods: {
			// tabs通知swiper切换
			tabsChange(index) {
				this.swiperCurrent = index;
			},
			// swiper-item左右移动，通知tabs的滑块跟随移动
			transition(e) {
				let dx = e.detail.dx;
				this.$refs.uTabs.setDx(dx);
			},
			// 由于swiper的内部机制问题，快速切换swiper不会触发dx的连续变化，需要在结束时重置状态
			// swiper滑动结束，分别设置tabs和swiper的状态
			animationfinish(e) {
				let current = e.detail.current;
				this.$refs.uTabs.setFinishCurrent(current);
				this.swiperCurrent = current;
				this.current = current;
			},
			// getUser() {
			// 			  getUserProfile().then(response => {
			// 			    this.userId = response.data.userId;
			// 				console.log(response);
			// 			  })
			// 			}, 
			// scroll-view到底部加载更多
			onreachBottom() {
				
			}
		}
	};
</script>
<style>
	.swiper-item{
		/* padding: 10 15upx; */
		justify-content: space-between;
		align-items: center;
		/* background-color: rgba(231, 231, 231,.7); */
		background-color: whitesmoke;
		font-size: 40upx;
		display: flex;
	}

</style>