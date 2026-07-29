<template>
		<view>
			<view class="p-2">
				<!-- 头像昵称   |   关注按钮 -->
				<view class="flex align-center justify-between">
					<!-- 左边 -->
					<view class="flex align-center">
						<!-- 头像 -->
						<image class="rounded-circle mr-2" :src="item.userpic" @click="openSpace" style="width: 65rpx;height: 65rpx;" lazy-load>
						</image>
						<!-- 昵称  发布时间 -->
						<view>
							<view class="font" style="line-height: 1.5;">{{item.username}}</view>
							<text class="font-sm text-light-muted" style="line-height: 1.5;">{{item.newstime}}</text>
						</view>
					</view>
					<!-- 右边 -->
					<!-- 按钮 -->
					<view @click="follow" v-if="!item.isFollow"
					    class="flex align-center justify-center rounded bg-main text-white animated fast"
						style="width: 90rpx;height: 50rpx;font-size: 30rpx;"
						hover-class="jello">
						关注
					</view>
				</view>
				<!-- 标题 -->
				<view class="font-md my-1" @click="openDetail">
					{{item.title}}
				</view>
				<!-- 图片 -->
				<image v-if="item.titlepic" :src="item.titlepic" style="height: 350rpx;" class="rounded w-100" @click="openDetail"></image>
				<!-- 图标按钮 -->
				<view class="flex align-center">
					<view class="flex align-center justify-center flex-1 mr-2 animated fast" hover-class="jello text-main" @click="zan('support')"
					:class="item.support.type === 'support' ? 'supportactive':''">
						<text class="iconfont icon-dianzan2"></text>
						<text>{{item.support.support_count > 0 ? item.support.support_count : '支持'}}</text>
					</view>
					<view class="flex align-center justify-center flex-1 mr-2 animated fast" hover-class="jello text-main" @click="zan('unsupport')"
					:class="item.support.type === 'unsupport' ? 'supportactive':''">
						<text class="iconfont icon-cai"></text>
						<text>{{item.support.unsupport_count > 0 ? item.support.unsupport_count : '不喜欢'}}</text>
					</view>
					<view class="flex align-center justify-center flex-1 mr-2 animated fast" hover-class="jello text-main" @click="openDetail">
						<text class="iconfont icon-pinglun2"></text>
						<text>{{item.comment_count > 0 ? item.comment_count : '评论'}}</text>
					</view>
					<view class="flex align-center justify-center flex-1 mr-2 animated fast" hover-class="jello text-main" @click="openDetail">
						<text class="iconfont icon-fenxiang"></text>
						<text>{{item.share_num > 0 ? item.share_num : '分享'}}</text>
					</view>
				</view>
			</view>
		</view>
</template>

<script>
	export default {
		props:{
			item:Object,
			index:Number
		},
		methods:{
			//打开个人空间
			openSpace(){
				console.log('打开个人空间');
			},
			//关注
			follow(){
				//通知父组件
				this.$emit('follow',this.index)
			},
			//进入详情页
			openDetail(){
				console.log('进入详情页');
			},
			//顶踩操作
			zan(type){
				this.$emit('zan',{
					type:type,
					index:this.index
				})
			}
		}
	}
	
</script>

<style>
	.supportactive{
		color: #429955;
	}
</style>
