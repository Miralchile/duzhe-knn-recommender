<template>
	<view>
		
		<uni-list-wx>
			 <uni-list-wx :border="true">
				<view class="list">
					<view class="flex_col" @longpress="onLongPress" :class="{'active':pickerUserIndex==index}" @tap="listTap(item)" v-for="(item,index) in sessionList"
					 :key="index" :data-index="index">
						<view class="avatar-container">
							<image :src="item.avatar" mode="aspectFill" style="border-radius: 10%;"></image>
							<u-badge style="position: absolute;right: -10upx;top: -10upx;" :type="'error'" max="99" :value="item.unread"></u-badge>
						</view>
						<view class="flex_grow">
							<view class="flex_col">
								<view class="flex_grow">{{item.name}}</view>
								<view class="time">{{item.updateTime}}</view>
							</view>
							<view class="info">{{item.lastMessage}}</view>
						</view>
					</view>
				</view>
			</uni-list-wx>
		</uni-list-wx>
		<top-right-tool-wx ref="trtw"></top-right-tool-wx>
	</view>
</template>

<script>
	import timeUtil from '@/utils/social/timeUtil.js';
	import stringUtil from '@/utils/social/stringUtil.js';
	import userRequest from '@/api/social/user.js';
	import {mapGetters} from 'vuex';
	import requestUrl from '@/api/social/url.js';
	import websocket from '@/api/social/websocket.js';
	import $store from '@/store/modules/social';
	export default{
		data() {
			return {
				keyword:'',
				uploadShow: false,
				title: '读者小站',
				show: false,
				selectedAvartar: null,
				maxImageMB: 3,
				register: {
					nickName: '',
					password1: '',
					password2: ''
				},
				tip: '',
				loginShow: false,
				login:{
					nickName: '',
					password: ''
				},
				value: 0,
				/* 窗口尺寸 */
				winSize: {},
				/* 显示遮罩 */
				showShade: false,
				/* 显示操作弹窗 */
				showPop: false,
				/* 弹窗按钮列表 */
				popButton: ["tip"],
				/* 弹窗定位样式 */
				popStyle: "",
				/* 选择的用户下标 */
				pickerUserIndex: -1
			}
		},
		computed:{
			...mapGetters(['loginUserInfo','isSocketOpen','sessionList','totalUnread'])
		},
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
			timeShowFormat(time){
				return timeUtil.timeShowFormat(timeUtil.getFormatTime(Number(time)));
			},
		listTap(item) {
			//console.log(item)
			/* 因弹出遮罩问题，所以需要在遮罩弹出的情况下阻止列表事件的触发 */
			if (this.showShade) {
				return;
			}
			//
			//console.log("列表触摸事件触发")
			if(item.type=='person'){
				$store.state.chattingUserInfo = item;
				uni.navigateTo({
					url:'/pages/social/components/chat/person-chat'
				})
			}else{
				$store.state.chattingGroupInfo = item;
				//debugger
				uni.navigateTo({
					url:'/pages/social/components/chat/group-chat'
				})
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
		// onLongPress(e) {
		// 	let [touches, style, index] = [e.touches[0], "", e.currentTarget.dataset.index];
		
		// 	/* 因 非H5端不兼容 style 属性绑定 Object ，所以拼接字符 */
		// 	if (touches.clientY > (this.winSize.height / 2)) {
		// 		style = `bottom:${this.winSize.height-touches.clientY}px;`;
		// 	} else {
		// 		style = `top:${touches.clientY}px;`;
		// 	}
		// 	if (touches.clientX > (this.winSize.witdh / 2)) {
		// 		style += `right:${this.winSize.witdh-touches.clientX}px`;
		// 	} else {
		// 		style += `left:${touches.clientX}px`;
		// 	}
		
		// 	this.popStyle = style;
		// 	this.pickerUserIndex = Number(index);
		// 	this.showShade = true;
		// 	this.$nextTick(() => {
		// 		setTimeout(() => {
		// 			this.showPop = true;
		// 		}, 10);
		// 	});
		// },
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
			let index = Number(e.currentTarget.dataset.index);
			//console.log(`第${this.pickerUserIndex+1}个用户,第${index+1}个按钮`);
			// 在这里开启你的代码秀
		
			uni.showToast({
				title: '读者',
				icon: "none",
				mask: true,
				duration: 600
			});
		
			/* 
			 因为隐藏弹窗方法中会将当前选择的用户下标还原为-1,
			 如果行的菜单方法存在异步情况，请在隐藏之前将该值保存，或通过参数传入异步函数中
			 */
			this.hidePop();
		}
	}
	}
	
</script>

<style scoped lang="scss">
	.alertTips {
		line-height: 80rpx;
		background-color: #fcfcfc;
		color: #999999;
		text-align: center;
		font-size: 28rpx;
	}
	/* 列式弹性盒子 */
	.flex_col {
		display: flex;
		flex-direction: row;
		flex-wrap: nowrap;
		justify-content: flex-start;
		align-items: center;
		align-content: center;
	}
	
	/* 弹性盒子弹性容器 */
	.flex_col .flex_grow {
		width: 0;
		-webkit-box-flex: 1;
		-ms-flex-positive: 1;
		flex-grow: 1;
	}
	
	.flex_row .flex_grow {
		-webkit-box-flex: 1;
		-ms-flex-positive: 1;
		flex-grow: 1;
	}
	
	/* 弹性盒子允许换行 */
	.flex_col.flex_wrap {
		-ms-flex-wrap: wrap;
		flex-wrap: wrap;
	}
	
	/* 列表 */
	.list {
		background-color: #fff;
		font-size: 28upx;
		color: #333;
		user-select: none;
		touch-callout: none;
	
		&>view {
			padding: 24upx 30upx;
			position: relative;
	
			&:active,
			&.active {
				background-color: #f3f3f3;
			}
			
			.avatar-container{
				height: 90upx;
				width: 90upx;
				border-radius: 4px;
				margin-right: 20upx;
				position: relative;
			}
			image {
				height: 90upx;
				width: 90upx;
				// border-radius: 4px;
				// margin-right: 20upx;
			}
	
			&>view {
				line-height: 40upx;
	
				.time,
				.info {
					color: #999;
					font-size: 24upx;
				}
	
				.time {
					width: 150upx;
					text-align: right;
				}
	
				.info {
					overflow: hidden;
					text-overflow: ellipsis;
					white-space: nowrap;
				}
			}
		}
	
		&>view:not(:first-child) {
			margin-top: 1px;
	
			&::after {
				content: '';
				display: block;
				height: 0;
				border-top: #CCC solid 1px;
				width: 620upx;
				position: absolute;
				top: -1px;
				right: 0;
				transform:scaleY(0.5);	/* 1px像素 */
			}
		}
	}
</style>