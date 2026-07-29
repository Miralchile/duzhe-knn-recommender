<template>
	<view>
		<!-- 顶部导航 -->
		<view class="search-view">
			<view class="navigate-bar-left">
				<!-- <view class="navigate-bar-left-avatar" @tap="showDrawer()"> -->
				<view class="navigate-bar-left-avatar">
					<image class="navigate-bar-left-avatar-image"></image>
					<!-- <image class="navigate-bar-left-avatar-image" :src="loginUserInfo.avatar"></image> -->
					<view v-if="!isSocketOpen" class="navigate-bar-left-avatar-red-dot"></view>
					<view v-if="isSocketOpen" class="navigate-bar-left-avatar-green-dot"></view>
				</view>
			</view>
			<!--搜索框-->
			<view class="search-cont">
				<view class="search" @click="search()">
					<image src="/static/social/img/search.png" mode="widthFix" class="search-img"></image>
					<input type="text" placeholder="搜索" disabled=""/>
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
					url:"../social/search/search"
				})
			},
			addfriend(){
				uni.navigateTo({
					url:"../social/addfriend/addfriend"
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
		height: 80upx;
	}
	.search-text{
		padding: 0 40upx;
	}
	/*搜索*/
	.search-img{
		margin: auto 0 auto 20upx;
		width: 40upx;
		height: 40upx;
	}
	.search{
		height: 50upx;
		line-height: 70upx;
		width: 100%;
		display: flex;
		flex-direction: flex;
		background: #f8f8f8;
		border-radius: 30rpx;
	}
	.search-input{
		height: 70upx;
		line-height: 70upx;
		width: 100%;
		font-size: 70upx;
		align-items: center;
	}
	.search-cont{
		/* background-color: azure; */
		display: flex;
		justify-content: space-between;
		height: 50upx;
		width: 280px;
		align-items: center;
		border-radius: 40%;
	}
	.navigate-bar-left-avatar-image{
		width: 30px;height: 30px;border-radius: 50%;
		margin-left: 10rpx;
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
	}
</style>
