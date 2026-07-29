<template>
	<view>
		<view class="content-new" @click="search()">
			<text>来这里搜索你加入的圈吧~</text>
			<image src="/static/social/img/search.png" @click="search()"></image>
		</view>
		<block v-for="(item,index) in circle" :key="index">
			<view class="content-view" @click="talk()">
				<view class="content-img">
					<image :src="item.pic" mode="aspectFill"></image>
				</view>
				<view class="content-title">
					<text class="content-text">{{item.friend}}</text>
				</view>
			</view>
		</block>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				circle:[
					{
					'pic':'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202107%2F09%2F20210709142454_dc8dc.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1663231589&t=aae94826c1b584c286b084bbd978b972',
					'friend':'读者唯美语言'
				    },
					{
					'pic':'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fc-ssl.duitang.com%2Fuploads%2Fblog%2F202107%2F09%2F20210709142454_dc8dc.thumb.1000_0.jpeg&refer=http%3A%2F%2Fc-ssl.duitang.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1663231589&t=aae94826c1b584c286b084bbd978b972',
					'friend':'读者心得分享'
					}
				]
			}
		},
		methods: {
			search(){
				uni.navigateTo({
					url:"/pages/social/search/search"
				})
			},
			talk(){
				uni.navigateTo({
					url:"/pages/social/components/friend/talk"
				})
			},
		}
	}
</script>

<style>
@import url("@/common/social/friend.css");
  .content-new image{
  	width: 40upx;
  	height: 40upx;
  	display: flex;
  	float: right;
	padding-right: 30upx;
  	padding-top: 10upx;
  	justify-content: space-between;
  	margin: 25upx 0;
  }
</style>
