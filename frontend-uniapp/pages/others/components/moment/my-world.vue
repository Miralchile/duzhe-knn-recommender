<template>
	<view>
		<view style="position: relative;height: 35%;height: 600rpx;">
			<image mode="aspectFill" style="width: 100%;height: 80%;" src="/static/social/img/reader.jpg"></image>
			<view style="position: absolute;right: 25rpx;display: flex;bottom: 10%;">
				<view style="text-align: center;line-height: 80rpx;color: #333;margin-right: 10rpx;font-weight: 600;">{{loginUserInfo.nickName}}</view>
				<image :src="loginUserInfo.avatar" style="border-radius: 50%;width: 120rpx;height: 120rpx;"></image>
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
			:authority="item.authority"
			:isMySelf="item.userId==loginUserInfo.userId"
		    @clickDynamic="clickDynamic(item.id,item.userId)"
		    @clickUser="clickUser(item.userId)"
		    @clickFocus="clickFocus(item.userId)"
		    @clickThumbsup="clickThumbsup(item.id,item.isLike,item.userId)"
		    @clickChat="clickChat(item.id,item.userId)"
			@changeAuthority="changeAuthority(index,item.id,item.authority,item.userId)"
			@deleteArticle="deleteArticle(index,item.id,item.userId)">
		</Dynamic>
	</view>
</template>

<script>
	import {mapGetters} from 'vuex';
	import Dynamic from '@/components/social/qizai-dynamic/Dynamic.vue';
	import worldRequest from '@/api/social/world.js';
	import $store from '@/store/modules/social/test.js';
	import myDynamic from '@/components/social/qizai-dynamic/myDynamic.vue'
	export default{
		components:{
			Dynamic: myDynamic
		},
		computed:{
			...mapGetters(['loginUserInfo','friendList'])
		},
		data(){
			return{
				indexId:0,
				articleList:[],
				hasNextPage:false
			}
		},
		onLoad() {
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
			async changeAuthority(index,id,userId){
				let res = await worldRequest.changeArticle({
					articleId: id
				})
				this.articleList[index].authority = res.data
				
			},
			deleteArticle(index,id,userId){
				console.log(index,id,userId)
				let that = this
				uni.showModal({
					cancelText:'取消',
					confirmText:'删除',
					title:'确认删除',
					success(res) {
						if(res.confirm){
							that.postDelete(index,id,userId)
						}
					}
				})
			},
			async postDelete(index,id,userId){
				let res = await worldRequest.deleteArticle({
					articleId:id
				})
				uni.showToast({
					icon:'success',
					title:res.data
				})
				this.articleList.splice(index,1)
				$store.state.publishSuccessFlag = true
				// console.log(res)
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
					personId: this.loginUserInfo.userId,
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
					personId: this.loginUserInfo.userId,
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
			    //console.log(e);
			    //console.log('clickChat');
				uni.navigateTo({
					url:'/pages/social/components/moment/detail?id='+articleId+'&userId='+userId
				})
			}
		}
	}
</script>

<style>
</style>