<template>
<view>
	<view class="search-cont">
		<view class="search">
			<image src="/static/social/img/search.png" mode="widthFix" class="search-img"></image>
				<input type="text" placeholder-class="inputclass" confirm-type="search"
				focus="true"
				placeholder="请输入搜索内容"
				v-model="keyword"/>
		</view>
		<view class="search-code" @click="search()">
			搜索
		</view>
	</view>
		<!-- 搜索结果 -->
		<view>
			<!-- 用户 -->
			<view>
				<view style="margin-left: 25rpx;color: darkgrey;">搜索到的用户</view>
				<u-empty
					v-show="keyword!==''&&hasResult&&personList.length===0"
					mode="search"
					icon="/static/social/img/search-empty.png"
				></u-empty>
				<view v-show="hasResult" style="width: 90%;margin-left: 5%;margin-top: 20rpx;">
					<u-row @tap="gotoPage('friend', item.userId)" class="row" customStyle="margin-bottom: 10px" v-for="(item,index) in personList" :key="index">
					    <u-col span="2">
							<u-avatar :src="'http://localhost:8080'+item.avatar"shape="square"></u-avatar>
					        <u-avatar v-if="item.type==='group'" :src="item.avatar"  shape="square"></u-avatar>
					    </u-col>
					    <u-col span="10">
					        <view>{{item.nickName}}</view>
					    </u-col>
					</u-row>
					<u-line></u-line>
				</view>
			</view>
			<!-- 群聊 -->
			<view>
				<view style="margin-top: 10rpx;margin-left: 25rpx;color: darkgrey;">搜索到的群组</view>
				<u-empty
					v-show="keyword!==''&&hasResult&&groupList.length===0"
					mode="search"
					icon="/static/social/img/search-empty.png"
				></u-empty>
				<view v-show="hasResult" style="width: 90%;margin-left: 5%;margin-top: 20rpx;">
					<u-row @tap="gotoPage('group', item.id)" class="row" customStyle="margin-bottom: 10px" v-for="(item,index) in groupList" :key="index">
					    <u-col span="2">
					        <u-avatar :src="item.avatar" shape="square"></u-avatar>
					    </u-col>
					    <u-col span="10">
					        <view>{{item.name}}</view>
					    </u-col>
					</u-row>
					<u-line></u-line>
				</view>
			</view>
		</view>
	</view>
</template>

<script>
	import userRequest from '@/api/social/user.js';
    import {mapGetters} from 'vuex';
	import $store from '@/store/modules/social';
	export default {
		data() {
			return {
				hasResult: false,
				keyword: '',
				groupList: [],
				personList: []
			}
		},
		computed:{
			...mapGetters(['loginUserInfo','sessionList'])
		},
		methods: {
			async search(){
				this.keyword = this.keyword.trim();
				if(this.keyword===''){
					this.showTip('请输入有效内容');
					return;
				}
				let res = await userRequest.search({
					keyword:this.keyword
				})
				console.log(res)
				this.personList = res.data.sysUserList
				this.groupList = res.data.groupList
				this.hasResult = true;
			},
			gotoPage(where,id){
				switch(where){
					case 'friend':
					uni.navigateTo({
						url:'/pages/social/info/friend-info?id='+id
					})
					break;
					case 'group':
					uni.navigateTo({
						url:'/pages/social/info/group-info?id='+id
					})
					break;
				}
			},
			showTip(tip){
				uni.showToast({
					icon:"none",
					title:tip
				})
			}
		}
	}
</script>

<style scoped>
	@import url("@/common/social/friend.css");
    .content-left{margin: 0 20upx;}
	.search{
		height: 55upx;
		line-height: 70upx;
		width: 100%;
		display: flex;
		flex-direction: row;
		background: #f8f8f8;
		border-radius: 20rpx;
	}
	.search-img{
		margin: auto 0 auto 20upx;
		width: 40upx;
		height: 40upx;
	}
	.search-input{
		height: 70upx;
		line-height: 70upx;
		width: 100%;
		font-size: 35upx;
		color: #666666;
		margin: 40upx !important;
		padding-left: 50upx;
	}
	.search-cont{
		display: flex;
		justify-content: space-between;
		/* height: 70upx; */
		align-items: center;
	}
	.search-code{
		width: 150upx;
		height: 50upx;
		text-align: center;
		font-size: 30upx;
		
	}
</style>
