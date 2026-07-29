<template>
	<view>
		<!-- 顶部导航 -->
		<view class="search-view">
			</view>
			<!--搜索框-->
			<view class="search-cont">
				<view class="search" @click="search()">
					<!-- <image  style="border-radius: 24px;"src="/static/social/img/search_1.png"  class="search-img"></image> -->
					<image  style="width: 54px;height: 20px;margin-top: 2%;margin-left: 42%;" src="/static/social/img/search.png"></image>
			    </view>
				
			</view>
		</view>
		
		
		<!-- 侧边栏 -->
		 <!-- <unidrawer ref="draw" :mask="true" :width="280" :mode="'left'">
			<sideview></sideview>
		</unidrawer> -->
	</view>
</template>

<script>
	import timeUtil from '@/utils/social/timeUtil.js';
	import stringUtil from '@/utils/social/stringUtil.js';
	import userRequest from '@/api/social/user.js';
	import {mapGetters} from 'vuex';
	import requestUrl from '@/api/social/url.js';
	import websocket from '@/api/social/websocket.js';
	import $store from '@/store/modules/social/test.js';
	import unidrawer from '@/uni_modules/uni-drawer/components/uni-drawer/uni-drawer.vue';
	//import sideview from '@/components/social/sideView/sideview.vue';
	export default {
		components:{
			unidrawer:unidrawer,
			//sideview:sideview
		},
		data() {
			return {
				selectedImage: '',
			}
		},
		computed:{
			...mapGetters(['loginUserInfo','isSocketOpen','sessionList','totalUnread'])
		},
		///刷新
		onPullDownRefresh() {
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
				}
				else{
					uni.showToast({
						icon:'error',
						title:'刷新失败！'
					})
				}
				uni.stopPullDownRefresh()
			},500)
		},
		
		methods: {
			close(){},
			open(){},
			// //显示抽屉
			// showDrawer(){
			// 	this.$refs.draw.open();
			// },
			// hideDrawer(){
			// 	this.$refs.draw.close();
			// },
			async updateAvater(data){
				let res = await userRequest.update({
					id: this.loginUserInfo.userId,
					avatar: requestUrl.getUrl()+data.data.compressUrl,
					background: requestUrl.getUrl()+data.data.url
				})
				uni.hideLoading();
				// uni.showToast({
				// 	icon:'none',
				// 	title:'上传成功！'
				// })
				$store.state.loginUserInfo = res.data;
				uni.setStorageSync('LOGIN_USER_INFO', res.data);
				this.uploadShow = false
				////加入公共聊天室
				
				///构造websocket消息
				let m = {
					 userId : this.loginUserInfo.userId,
					 groupId : '49911198753361920'
				}
				let msg = {
					 type: 'join-group',
					 data: m
				}
				//uni.sendSocketMessage({
				//	data: JSON.stringify(msg),
				//	fail(res) {
				//		uni.showToast({
				//			icon:'error',
				//			title:'发送失败'
				//		})
				//	}
				//})
				
				
				
			},
			//到跳转搜索页面
			search(){
				uni.navigateTo({
					url:"/pages/social/search/search"
				})
			},
			addfriend(){
				uni.navigateTo({
					url:"../social/addfriend/addfriend"
				})
			},
			intoPersonal(){
				uni.navigateTo({
						url:"/pages/social/info/person-info"
					})
				}
		}
	}
</script>

<style scoped>
/*定位*/
   /* .search-view image{
	width: 35upx;
	height: 35upx;
    } */
    .search-view{
		font-size: 40upx;
        display: flex;
		align-items: center;
		height: 20upx;
		justify-content: baseline;
		background-color: #FFFFFF;
	}
	.search-text{
		padding: 0 40upx;
	}
	/*搜索*/
	.search-img{
		
		/* margin: auto 0 auto 20upx; */
		width: 160px;
		height: 36px;
		border-radius: 24px;
	}
	.search{
		margin-left: 10px;
		height: 70upx;
		line-height: 70upx;
		width: 95%;
		display: flex;
		flex-direction: flex;
		background: #EFEFEF;
		border-radius: 24px;
	}
	.search-input{
		height: 70upx;
		line-height: 70upx;
		width: 100%;
		font-size: 70upx;
		align-items: center;
	}
	.search-cont{
		background-color: #FFFFFF;
		height: 40px;
	
		
		
	}
	.navigate-bar-left-avatar-image{
		width: 35px;height: 35px;border-radius: 50%;
		margin-left: 30rpx;
		
	}
	.navigate-bar-left-avatar{
		margin-bottom: 5rpx;
		position: relative;
	}
	.navigate-bar-left-avatar-red-dot{
		position: absolute;
		background-color: red;
		width: 10px;
		height: 10px;
		border-radius: 50%;
		left: 35px;
		bottom: 5px;
	}
	.navigate-bar-left-avatar-green-dot{
		position: absolute;
		background-color: lawngreen;
		width: 10px;
		height: 10px;
		border-radius: 50%;
		left: 35px;
		bottom: 5px;
	}
	.navigate-bar-left{
		width: 20%;
		height: 100%;
		padding: 4px;
		margin-top: -80px;
	}
</style>
