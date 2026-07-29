<template>
	<view>
		<view style="position: relative;height: 35%;height: 600rpx;">
			<image mode="aspectFill" style="width: 100%;height: 80%;" src="/static/social/img/reader.jpg"></image>
			<view style="position: absolute;right: 25rpx;display: flex;bottom: 10%;">
				<view style="text-align: center;line-height: 80rpx;color: #fff;margin-right: 10rpx;font-weight: 600;">{{personInfo.nickName}}</view>
				<image :src="personInfo.avatar" style="border-radius: 50%;width: 120rpx;height: 120rpx;"></image>
			</view>
		</view>
		
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
		    @clickChat="clickChat(item.id,item.userId)"
			>
		</Dynamic>
	</view>
</template>

<script>
	import {mapGetters} from 'vuex';
	import Dynamic from '@/components/social/qizai-dynamic/Dynamic.vue';
	import worldRequest from '@/api/social/world.js';
	import $store from '@/store/modules/social/test.js';
	import userRequest from '@/api/social/user.js';
	export default{
		components:{
			Dynamic: Dynamic
		},
		computed:{
			...mapGetters(['loginUserInfo','friendList'])
		},
		data(){
			return{
				indexId:0,
				articleList:[],
				hasNextPage:false,
				personId:0,
				personInfo:{}
			}
		},
		onLoad(options) {
			this.personId = options.id;
			//console.log(this.personId)
			this.getPersonInfo();
			this.init()
		},
		onReachBottom() {
			this.getHistory()
		},
		onPullDownRefresh() {
			this.init()
			setTimeout(()=>{
				uni.showToast({
					icon:'success',
					title:'刷新成功！'
				})
				uni.stopPullDownRefresh()
			},600)
		},
		methods:{
			async getPersonInfo(){
				let res = await userRequest.info({userId:this.personId});
				//console.log(res)
				this.personInfo = res.data;
				uni.setNavigationBarTitle({
					title:res.data.nickName+'的世界'
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
				let res = await worldRequest.personArticles({
					indexId: this.indexId,
					personId: this.personId
				})
				//console.log(res.data)
				this.articleList = this.articleList.concat(res.data)
				if(res.data.length<10) this.hasNextPage = false
				else{
					this.hasNextPage = true
					this.indexId = res.data[9].id
				}
			},
			async init(){
				this.indexId = 0
				let res = await worldRequest.personArticles({
					personId: this.personId,
					indexId: this.indexId
				})
				//console.log(res.data)
				this.articleList = res.data
				if(res.data.length<10) this.hasNextPage = false
				else{
					this.hasNextPage = true
					this.indexId = res.data[9].id
				}
			},
			clickDynamic(articleId,userId){
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
			    //console.log(id,isLike);
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
			clickChat(articleId,userId){
				uni.navigateTo({
					url:'/pages/social/components/moment/detail?id='+articleId+'&userId='+userId
				})
			}
		}
	}
</script>

<style>
</style>