<template>
	<view>
		<u-index-list :indexList="indexList">
			<view slot="header" class="list">
				<view class="list__item" @tap="gotoPage(0)">
					<view style="position: relative;">
						<text class="list__item__name">通知</text>
						<view style="margin-top: 10px;">
						<u-avatar shape="circle" size="45" icon="bell-fill"  fontSize="26" randomBgColor colorIndex="6"></u-avatar>
						</view>
						<!-- <u-badge style="position: absolute;top: 10upx;right:5upx;" :type="'error'" max="99" :value="totalUnread.notice"></u-badge> -->
					</view>
				</view>
				
				<u-line></u-line>
				
				<view class="list__item" @tap="gotoPage(1)" >
					<text class="list__item__name" >读友圈</text>
					<view style="margin-top: 10px;">
					<u-avatar shape="circle" size="45" icon="chat" fontSize="26" randomBgColor colorIndex="5"></u-avatar>
					</view>
				</view>
				<u-line></u-line>
			</view>
			<view style="margin-left: 6%;margin-top:3%;margin-bottom:2%;font-family: PingFangHK-Regular;font-size: 10px;font-weight: normal;line-height: 16px;letter-spacing: 0em;color: #3D3D3D;">朋友 ( 共{{friendListShow.length}}位好友 )</view>
			
					<view class="list" v-for="(item1, index1) in friendListShow" :key="index1">
						<view class="list__item" @tap="gotoFriendInfo(item1.userId)">
							<image v-if="item1.avatarUrl" class="list__item__avatar" :src="item1.avatarUrl"></image>
							<image v-if="!item1.avatarUrl" class="list__item__avatar" :src="base64Avatar"></image>
							<text class="list__item__user-name">{{item1.userName}}</text>
						</view>
						<u-line></u-line>
					</view>
					
			<view slot="footer" class="u-safe-area-inset--bottom">
				<text class="list__footer">共{{friendListShow.length}}位好友</text>
			</view>
		</u-index-list>
	</view>
</template>

<script>
	const indexList = () => {
		const indexList = []
		//const charCodeOfA = 'A'.charCodeAt(0)
		//indexList.push("↑")
		indexList.push("")
		//indexList.push("☆")
		indexList.push("")
		/* for (let i = 0; i < 26; i++) {
			indexList.push(String.fromCharCode(charCodeOfA + i))
		} */
		//indexList.push('#')
		indexList.push('')
		return indexList
	}
	// import {mapGetters} from 'vuex';
	import stringUtil from '@/utils/social/stringUtil.js';
	//import friend from '@/api/social/friend.js';
	import $store from '@/store/modules/social';
	import { getList } from "@/api/social/friend.js"
	import { getFollowList } from '@/api/others.js'
	export default {
		data() {
			return {
				friendListShow:[],
				indexList: indexList(),
				list:[],
				base64Avatar:"data:image/jpg;base64,/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAAA8AAD/4QMraHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjMtYzAxMSA2Ni4xNDU2NjEsIDIwMTIvMDIvMDYtMTQ6NTY6MjcgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDUzYgKFdpbmRvd3MpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjREMEQwRkY0RjgwNDExRUE5OTY2RDgxODY3NkJFODMxIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjREMEQwRkY1RjgwNDExRUE5OTY2RDgxODY3NkJFODMxIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6NEQwRDBGRjJGODA0MTFFQTk5NjZEODE4Njc2QkU4MzEiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6NEQwRDBGRjNGODA0MTFFQTk5NjZEODE4Njc2QkU4MzEiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz7/7gAOQWRvYmUAZMAAAAAB/9sAhAAGBAQEBQQGBQUGCQYFBgkLCAYG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"
			}
		},
		computed: {
			// ...mapGetters(['loginUserInfo', 'isSocketOpen','friendListShow','friendList','totalUnread'])
		},
		
		onPullDownRefresh() {
			///重新获取连接websocket
			// if(!$store.state.isSocketOpen){
			// 	websocket.initConnect()
			// }
			// ///从新获取消息列表
			// $store.dispatch('getNoticeList')
			// setTimeout(()=>{
			// 	if($store.state.isSocketOpen){
			// 		uni.showToast({
			// 			icon:'success',
			// 			title:'刷新成功！'
			// 		})
			// 	}
			// 	else{
			// 		uni.showToast({
			// 			icon:'error',
			// 			title:'刷新失败！'
			// 		})
			// 	}
			// 	uni.stopPullDownRefresh()
			// },500)
		},
		onLoad() {
			uni.hideTabBar();
			this.list = getList(1);
		},
		created() {
			getFollowList().then((res)=>{
				this.friendListShow = res
				console.log(res)
			})
		},
		methods: {
			gotoFriendInfo(userId){
				// uni.navigateTo({
				// 	url:'/pages/social/info/friend-info1?id='+id
				// })
				uni.navigateTo({
					url:'/pages/others/index?userId=' + userId
				})
				
			},
			//去搜索页面
			gotoPage(i){
				switch(i){
					// case 3:
					// uni.navigateTo({
					// 	url:'/pages/social/search/search'
					// })
					// break;
					case 1:
					uni.navigateTo({
						url:'/pages/social/list/group-list'
					}) 
					break;
					case 0:
					uni.navigateTo({
						url:'/pages/social/list/notice-list'
					})
					break;
				}
				
			}
		}
	}
</script>

<style scoped lang="scss">
	page{
		background-color: #FFFFFF;
	}
	.list__item:active{
		background-color: #e8e8e8;
	}
	.list__item{
			padding: 6px 12px;
			align-items: center;
			justify-content: space-between;
		}
    .list__item__avatar{
			height: 90rpx;
			width: 90rpx;
			border-radius:50%;
			float: left;
		}
    .list__item__user-name{
		    // align-items: center;
			
			margin-left: 22upx;
			font-family: Source Han Sans CN;
			font-size: 16px;
			font-weight: normal;
			line-height: 45px;
			letter-spacing: 0em;
			
			color: #333333;
			
		}
		.list__item__name{
				margin-left: 2%;
			    font-family: PingFangHK-Regular;
			    font-size: 10px;
			    font-weight: normal;
			    line-height: 16px;
			    letter-spacing: 0em;
			    
			    color: #3D3D3D;
			}
    .list__footer{
			color: #B3DD4B;
			font-size: 14px;
			text-align: center;
			margin: 15px 0;
		}
	.u-safe-area-inset--bottom{
		height: 150rpx;
		margin-top: 7%;
		text-align: center;
	}
</style>
