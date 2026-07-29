<template>
	<view class="uni-navbar">
		<view :class="{'uni-navbar--fixed': fixed,'uni-navbar--shadow':border,'uni-navbar--border':border}" :style="{'background-color':backgroundColor}" class="uni-navbar__content">
			<uni-status-bar v-if="statusBar" />
			<view :style="{color:color}" class="uni-navbar__header uni-navbar__content_view">
				<view class="uni-navbar__header-btns uni-navbar__content_view" @tap="onClickLeft">
					<view v-if="leftIcon.length" class="uni-navbar__content_view">
						<uni-icons :type="leftIcon" :color="color" size="24" />
					</view>
					<view v-if="leftText.length" :class="{'uni-navbar-btn-icon-left':!leftIcon.length}" class="uni-navbar-btn-text uni-navbar__content_view">{{ leftText }}</view>
					<slot name="left" />
				</view>
				<view class="uni-navbar__header-container uni-navbar__content_view">
					<view v-if="title.length" class="uni-navbar__header-container-inner uni-navbar__content_view">{{ title }}</view>
					<!-- 标题插槽 -->
					<slot name="mod"/>
				</view>
				<view class="uni-navbar__header-container uni-navbar__content_view">
					<view class="uni-navbar__header-container-inner uni-navbar__content_view">
						<u-icon name="grid-fill" size="25px" style="position: relative;top:10rpx;" @click="changeShowCategory"></u-icon>
					</view>
				</view>
				
			</view>
		</view>
		<view v-if="fixed" class="uni-navbar__placeholder">
			<uni-status-bar v-if="statusBar" />
			<view class="uni-navbar__placeholder-view" />
		</view>
		<view v-if="this.showCategory" style="width: 100%;height: 60vh; background-color: #ffffff; overflow:auto;position: absolute;z-index: 5;">
			<view style="padding: 20rpx;">
				<text style="font-size: 28rpx;">
					我的频道
				</text>
				<text style="font-size: 20rpx; position: relative;left: 15rpx;color: #5a5a5a;">
					点击进入频道
				</text>
				<u-icon name="close" style="position: absolute; top: 15rpx;right: 30rpx;" @click="handleCloseCategory"></u-icon>
				<view style="margin-left: 20rpx;display: flex;flex-wrap: wrap;">
					<uni-tag text="自然百科" style="width: 112rpx;text-align: center;margin-right: 33rpx;
					margin-top: 20rpx;background-color: #dddddd;color: #000000;" />
					<uni-tag text="环保科普" style="width: 112rpx;text-align: center;margin-right: 33rpx;
					margin-top: 20rpx;background-color: #dddddd;color: #000000;" />
					<uni-tag text="科学原理" style="width: 112rpx;text-align: center;margin-right: 33rpx;
					margin-top: 20rpx;background-color: #dddddd;color: #000000;" />
				</view>
				
			</view>
			<view v-for="(category, categoryIndex) in categoryList" :key="categoryIndex" style="padding: 20rpx;">
				<text style="font-size: 28rpx;">
					{{category.category}}
				</text>
				<text style="font-size: 20rpx; position: relative;left: 15rpx;color: #5a5a5a;" @click="gotoCategory(category.category)">
					点击进入频道
				</text>
				<view style="margin-left: 20rpx;display: flex;flex-wrap: wrap;">
					<uni-tag v-for="(channel, channelIndex) in category.channelList" :key="channelIndex" :text="channel.channelName"
					 style="width: 112rpx;text-align: center;margin-right: 33rpx;margin-top: 20rpx;background-color: #fff;color: #000000;" />
					
				</view>
			</view>
			
		</view>
	</view>
</template>

<script>
	import uniStatusBar from '../uni-status-bar/uni-status-bar.vue'
	import uniIcons from '../uni-icons/uni-icons.vue'
	import { getAllChannel } from '@/api/channel.js'

	export default {
		name: 'UniNavBar',
		components: {
			uniStatusBar,
			uniIcons
		},
		data(){
			return{
				showCategory: false,
				categoryList:[]
			}
		},
		props: {
			title: {
				type: String,
				default: ''
			},
			leftText: {
				type: String,
				default: ''
			},
			rightText: {
				type: String,
				default: ''
			},
			leftIcon: {
				type: String,
				default: ''
			},
			rightIcon: {
				type: String,
				default: ''
			},
			fixed: {
				type: [Boolean, String],
				default: false
			},
			color: {
				type: String,
				default: '#000000'
			},
			backgroundColor: {
				type: String,
				default: '#FFFFFF'
			},
			statusBar: {
				type: [Boolean, String],
				default: false
			},
			shadow: {
				type: [String, Boolean],
				default: true
			},
			border: {
				type: [String, Boolean],
				default: true
			}
		},
		methods: {
			gotoCategory(category){		
			console.log("category",category);		
			    uni.navigateTo({		
			        url: '/pages/index/categoryIndex?name='+category		
			    })		
			},
			onClickLeft() {
				this.$emit('click-left')
			},
			onClickRight() {
				this.$emit('click-right')
			},
			changeShowCategory() {
				getAllChannel().then((res)=>{
					this.categoryList = res
					this.showCategory = !this.showCategory
				})
			},
			handleCloseCategory() {
				this.showCategory = false
			}
		}
	}
</script>

<style>

	.uni-navbar__content {
		display: block;
		position: relative;
		width: 100%;
		background-color: #fff;
		overflow: hidden
	}

	.uni-navbar__content .uni-navbar__content_view {
		display: flex;
		align-items: center
	}

	.uni-navbar__header {
		display: flex;
		flex-direction: row;
		width: 100%;
		height: 80px;
		margin-top: 10px;
		line-height: 40px;
		font-size: 22px
	}

	.uni-navbar__header-btns {
		display: inline-flex;
		flex-wrap: nowrap;
		flex-shrink: 0;
		width: 120upx;
		padding: 0 12upx
	}

	.uni-navbar__header-btns:first-child {
		padding-left: 0
	}

	.uni-navbar__header-btns:last-child {
		width: 60upx
	}

	.uni-navbar__header-btns-right:last-child {
		width: 120rpx;
		text-align: right;
		flex-direction: row-reverse
	}

	.uni-navbar__header-container {
		width: 100%;
		margin: 0 10upx
	}

	.uni-navbar__header-container-inner {
		width: 100%;
		display: flex;
		justify-content: center;
		font-size: 30upx
	}

	.uni-navbar__placeholder-view {
		height: 44px
	}

	.uni-navbar--fixed {
		position: fixed;
		z-index: 998
	}

	.uni-navbar--shadow {
		
	}

	.uni-navbar--border:after {
		/* position: absolute;
		z-index: 3;
		bottom: 0;
		left: 0;
		right: 0;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #e5e5e5 */
	}
</style>