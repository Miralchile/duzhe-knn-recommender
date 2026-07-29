<template>
	<view>
		
		  <!-- <view style="position: relative;height: 35%;height: 100rpx;"> -->
			  <!-- <view class="moment-top" @click='toPublishMoment'>
			  	<text>来这里分享你的日常吧~</text>
			  	<image src="/static/icon/video-collect.png" width="80upx" height="80upx" @click='toPublishMoment'> </image>
			  </view> -->
<!-- 			<image mode="aspectFill" style="width: 100%;height: 80%;" src="/static/img/duzhe.jpg"></image> -->
		    <!-- <view style="position: absolute;right: 25rpx;display: flex;bottom: 10%;" @tap="gotoMyWorld()">
				<view style="text-align: center;line-height: 80rpx;color: #000;margin-right: 10rpx;font-weight: 600;">{{loginUserInfo.nickName}}</view>
				<image src="/static/social/default.jpg" style="color: #000;border-radius: 50%;width: 80rpx;height: 80rpx;"></image>
 			</view> -->
		<!-- </view> -->
		<Dynamic v-for="(item,index) in articleList" :key="index" 
			:articleId="item.id"
		    :imgList="toArray(item.imgList)" 
		    :avatar="item.avatar"
		    :name="item.name"
		    :publishTime="toNumber(item.publishTime)"
		    :content="item.content"
		    :isLike="item.isLike"
		    :likeNumber="item.likeNum"
		    :viewNumber="item.viewNum"
		    :chatNumber="item.commentNum"
			:isFocusOn="item.isFriend"
			:isMySelf="item.userId==loginUserInfo.userId"
		    @clickDynamic="clickDynamic(item.id,item.userId)"
		    @clickUser="clickUser(item.userId)"
		    @clickFocus="clickFocus(item.userId)"
		    @clickThumbsup="clickThumbsup(item.id,item.isLike,item.userId)"
		    @clickChat="clickChat(item.id,item.userId)">
		</Dynamic>
		<uni-fab
			:pattern="pattern"
			:content="content"
			:horizontal="'right'"
			:vertical="'top'"
			@trigger="trigger"
		></uni-fab>
	</view>
</template>

<script>
	import {mapGetters} from 'vuex';
	import Dynamic from '@/components/social/qizai-dynamic/Dynamic.vue';
	let showSelect = uni.getStorageSync('SHOW_SELECT')|0;
	import worldRequest from '@/api/social/world.js';
	import $store from '@/store/modules/social';
	export default {
	    components: {
	        Dynamic
	    },
		computed:{
			...mapGetters(['loginUserInfo','friendList'])
		},
		onShow() {
			if($store.state.publishSuccessFlag){
				uni.startPullDownRefresh()
				$store.state.publishSuccessFlag = false
			}
		},
		onPullDownRefresh() {
			this.initArticleList()
			setTimeout(()=>{
				uni.showToast({
					icon:'success',
					title:'刷新成功！'
				})
				uni.stopPullDownRefresh()
			},600)
		},
	    data() {
	        return {
				pattern:{
					buttonColor: '#42B983'
				},
				content:[{
					iconPath: '/static/social/img/round_add.png',
					text: '编辑'
				},{
					iconPath: '/static/social/img/earth.png',
					selectedIconPath: '/static/social/img/earth-selected.png',
					text: '所有人',
					active: showSelect===0
				},{
					iconPath: '/static/social/img/friend.png',
					selectedIconPath: '/static/social/img/friend-selected.png',
					text: '仅好友',
					active: showSelect===1
				}],
				indexId:0,
				articleList:[],
				hasNextPage: false,
	        }
	    },
		created() {
			
			this.initArticleList();
		},
		onLoad() {
			// console.log('onload.....')
			// this.initArticleList();
		},
		onReachBottom() {
			this.getHistory();
		},
	    methods:{
			gotoMyWorld(){
				uni.navigateTo({
					url:'/pages/social/components/moment/my-world'
				})
			},
			toNumber(n){
				return Number(n)/1000
			},
			toArray(s){
				if(s==null||s=='') return [];
				return s.split(',')
			},
			async getHistory(){
				if(!this.hasNextPage){
					uni.showToast({
						icon:'none',
						title:'已加载所有记录~'
					})
					return
				}
				let res = await worldRequest.articles({
					indexId: this.indexId,
					authority: showSelect
				})
				this.articleList = this.articleList.concat(res.data)
				if(res.data.length<10) this.hasNextPage = false
				else{
					this.hasNextPage = true
					this.indexId = res.data[9].id
				}
			},
			async initArticleList(){
				this.indexId = 0
				let res = await worldRequest.articles({
					indexId: this.indexId,
					authority: showSelect,
					userId: this.loginUserInfo.userId
				})
				//console.log(this.friendList)
				this.articleList = res.data
				console.log(this.articleList)
				if(res.data.length<10) this.hasNextPage = false
				else{
					this.hasNextPage = true
					this.indexId = res.data[9].id
				}
			},
			trigger(e){
				if(e.index==0){
					uni.navigateTo({
						url:'../social/components/moment/publishMoment'
					})
				}else if(e.index==1){
					this.content[1].active = true
					this.content[2].active = false
					showSelect = 0
					uni.setStorageSync('SHOW_SELECT',0)
					uni.startPullDownRefresh()
				}else{
					showSelect = 1
					this.content[1].active = false
					this.content[2].active = true
					uni.setStorageSync('SHOW_SELECT',1)
					uni.startPullDownRefresh()
				}
			},
	        clickDynamic(articleId, userId){
	            //console.log('childDynamic');
				uni.navigateTo({
					url:'/pages/social/components/moment/detail?id='+articleId+'&userId='+userId
				})
	        },
	        // 点击用户信息
	        clickUser(id){
	            if(id==this.loginUserInfo.userId){
					uni.navigateTo({
						url:'/pages/social/info/person-info'
					})
				}else{
					uni.navigateTo({
						url:'/pages/social/info/friend-info?id='+id
					})
				}
	        },
	        // 点击关注
	        clickFocus(id){
	            uni.navigateTo({
	            	url:'/pages/social/info/friend-info?id='+id
	            })
	        },
	        // 点赞
	        async clickThumbsup(id,isLike,userId){
	            console.log(id,isLike,userId);
				let res = await worldRequest.likeArticle({
					type: isLike?'cancel':'like',
					articleId:id,
					userId:userId
				})
				uni.showToast({
					icon:'none',
					title:res.data
				})
				//console.log(res)
				for(let i=this.articleList.length-1;i>=0;i--){
					if(this.articleList[i].id==id){
						this.articleList[i].isLike = !isLike;
						if(isLike) this.articleList[i].likeNum--;
						else this.articleList[i].likeNum++;
						break;
					}
				}
	        },
	        // 点击聊天
	        clickChat(articleId, userId){
	            //console.log(userId);
	            //console.log('clickChat');
				uni.navigateTo({
					url:'/pages/social/components/moment/detail?id='+articleId+'&userId='+userId
				})
	        },
			toPublishMoment(){
				uni.navigateTo({
			    url: '../social/components/moment/publishMoment'
				})
			},
	    }
	}
</script>

<style scoped>
.moment-top{
		display: flex;
		padding: 15 15upx;
		/* justify-content: space-between; */
		align-items: center;
		background-color: white;
	}
	.moment-top text{
		display: flex;
		height: 80upx;
		line-height: 80upx;
		font-size: 40upx;
		color: #666666;
	}
	.moment-top image{
		height: 80upx;
		width: 80upx;
	}
</style>
