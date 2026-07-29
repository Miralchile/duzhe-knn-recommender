<template>
	<view>
		<view class="headertop">
			<view class="square-title" style="color: #1ed76d;font-size: 30rpx;">
				<text>推荐你来参与......</text>
			</view>
			<view class="change-another" style="color: #1ed76d;font-size: 30rpx;">
				<text>换一批看看</text>
			</view>
		</view>
		<view class="square-content">
			<view class="square-content-item" v-for="item in contentList">
				<view class="content-item">
					<view class="content-item-title">
						{{item.title}}
					</view>
					<view class="content-item-new-topic">
						<text>新话题</text>
					</view>
					<view class="content-item-join-topic">
						<text>参与话题</text>
					</view>
				</view>
			</view>
		</view>

		<view class="topic">
			<view class="topic-checked" @click="classificateTopic()">
				<text> > 分类查看话题</text>
			</view>
			<view class="topic-my" @click="myTopic()">
				<text>> 我的话题</text>
			</view>
			<view class="topic-new" @click="joinTopic()">
				<text>> 发起新话题</text>
			</view>
		</view>

		<view>
			<view style="color: #1ed76d;font-size: 40rpx;margin-top: 30rpx;">
				<text>24小时话题趋势.......</text>
			</view>
			
			<block v-for="(item,index) in hottopic" :key="index">
				<view class="content-hottopic">
						<text class="content-hottopic-topic">{{item.topic}}</text>
						<text class='content-hottopic-number'>{{item.number}}</text>
				</view>
			</block>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				contentList: [{
						title: '深圳老字号美食'
					},
					{
						title: '你一个人时，生活怎么样？'
					},
					{
						title: '长大后妈妈慢慢消失的天赋？'
					},
					{
						title: '做什么'
					},
					{
						title: '我的故事'
					},
					{
						title: '隐入尘烟'
					},
					{
						title: '人生大事'
					},
					{
						title: '独行月球'
					},
					{
						title: '笨小孩'
					}
				],
				hottopic:[
					{
					topic:'1.疫情前的最后一次旅行',
					number:'55.1万次浏览'
				    },
					{
					topic:'2.那些你甘愿“浪费的时间”',
					number:'29.7万次浏览'
					},
					{
					topic:'3.独行月球---金刚鼠',
					number:'20.1万次浏览'
					}
				]
			}
			
		},
		methods: {
			joinTopic() {
				uni.navigateTo({
					url: '/pages/social/components/square/publishTopic'
				})
			},
			classificateTopic() {
				uni.navigateTo({
					url: '/pages/social/components/square/classificationTopic'
				})
			},
			myTopic() {
				uni.navigateTo({
					url: '/pages/social/components/square/myTopic'
				})
			}
		},
	}
</script>

<style scoped>
	.headertop {
		/* margin-top: 10rpx; */
		padding-left: 20rpx;
		padding-right: 20rpx;
		display: flex;
		justify-content: space-between;
	}

	.square-content {
		display: flex;
		flex-direction: row;
		justify-content: space-around;
		flex-wrap: wrap;
	}

	.square-content-item {
		width: 200rpx;
		height: 240rpx;
		color: #4fa3f9;
		border: 1px solid #f9f9f9;
	}

	.content-item {
		display: flex;
		padding-top: 30rpx;
		flex-direction: column;
		align-items: center;
	}

	.content-item-title {
		text-align: center;
		font-size: 30rpx;
	}

	.content-item-new-topic {
		color: #6b644d;
		margin-top: 10rpx;
		font-size: 30rpx;

	}

	.content-item-join-topic {
		width: 140rpx;
		height: 40rpx;
		margin-top: 10rpx;
		text-align: center;
		color: #1ed76d;
		border: 1px solid #1ed76d;
		font-size: 30rpx;
	}

/* 	.topic {
		margin-top: 10rpx;
	} */

	.topic-my {
		margin-top: 40rpx;
		font-size: 30rpx;
	}

	.topic-new {
		margin-top: 40rpx;
		font-size: 30rpx;
	}

	.topic-checked {
		/* margin-top: 40rpx; */
		font-size: 30rpx;
	}

	/* .twelve-hour {
		margin-top: 90rpx;
	}
 */
.content-hottopic{
	margin-top: 40rpx;
	width: 100%;
    padding-left: 10upx;
	/* font-size: 40upx; */
	color: black;
}
.content-hottopic-topic{
	font-size: 30rpx;
}
	.content-hottopic-number{
		color: #bdbdbd;
	}
</style>
