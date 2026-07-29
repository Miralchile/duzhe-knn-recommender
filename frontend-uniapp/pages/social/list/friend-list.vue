<template>
	<view>
		<Topsearch></Topsearch>
		<view>
			<u-index-list :indexList="indexList">
			<template v-for="(item, index) in friendListShow">
				<!-- #ifdef APP-NVUE -->
				<u-index-anchor v-if="item.length!==0" :text="indexList[index]" :key="index"></u-index-anchor>
				<!-- #endif -->
				<u-index-item :key="index">
					<!-- #ifndef APP-NVUE -->
					<u-index-anchor v-if="item.length!==0" :text="indexList[index]"></u-index-anchor>
					<!-- #endif -->
					<view class="list" v-for="(item1, index1) in item" :key="index1">
						<view class="list__item" @tap="checkFriend(item1.id)">
							<image v-if="item1.url" class="list__item__avatar" :src="'http://localhost:8080'+item1.url"></image>
							<image v-if="!item1.url" class="list__item__avatar" :src="base64Avatar"></image>
							<text class="list__item__user-name">{{item1.name}}</text>
							<view @tap="addMember(item1.id)" class="btn">邀请</view>
							<u-line></u-line>
						</view>
					</view>
				</u-index-item>
			</template>
			</u-index-list>
		</view>
		
	</view>
</template>

<script>
	const indexList = () => {
		const indexList = []
		const charCodeOfA = 'A'.charCodeAt(0)
		indexList.push("↑")
		indexList.push("☆")
		for (let i = 0; i < 26; i++) {
			indexList.push(String.fromCharCode(charCodeOfA + i))
		}
		indexList.push('#')
		return indexList
	}
	import Topsearch from '@/pages/social/components/topsearch/topsearch.vue'
	import {mapGetters} from 'vuex';
	import stringUtil from '@/utils/social/stringUtil.js';
	import $store from '@/store/modules/social';
	import { getList } from "@/api/social/friend.js"
	import userRequest from '@/api/social/user.js';
	import timeUtil from '@/utils/social/timeUtil.js';
	
	export default{
		components:{
			Topsearch
		},
		data() {
			return {
				groupInfo:{},
				value: 1,
				indexList: indexList(),
				itemArr:[],
				list:[],
				groupId:null,
				pickerUserIndex:-1,
				isMember:false,
				base64Avatar:"data:image/jpg;base64,/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAAA8AAD/4QMraHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjMtYzAxMSA2Ni4xNDU2NjEsIDIwMTIvMDIvMDYtMTQ6NTY6MjcgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDUzYgKFdpbmRvd3MpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjREMEQwRkY0RjgwNDExRUE5OTY2RDgxODY3NkJFODMxIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjREMEQwRkY1RjgwNDExRUE5OTY2RDgxODY3NkJFODMxIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6NEQwRDBGRjJGODA0MTFFQTk5NjZEODE4Njc2QkU4MzEiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6NEQwRDBGRjNGODA0MTFFQTk5NjZEODE4Njc2QkU4MzEiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz7/7gAOQWRvYmUAZMAAAAAB/9sAhAAGBAQEBQQGBQUGCQYFBgkLCAYG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"
			}
		},
		computed: {
			...mapGetters(['loginUserInfo','sessionList', 'isSocketOpen','friendListShow','friendList','totalUnread','groupList'])
		},
		onLoad(options) {
			this. groupId = options.id;
			let groupId = this. groupId
			this.getGroupInfo(groupId)
		},
		methods:{
			async addMember(id){
				let groupId = this.groupId
				let res2 = await userRequest.groupMember({id:groupId})
				let users = res2.data
				for(let i=0;i<users.length;i++){
					if(users[i].user_id===id){
						uni.showToast({
							title:"已是圈内成员",
							icon:'error'
						})
					}else{
						let m = {
							 id : id,
							 groupId : this.groupId
						}
						let msg = {
							 type: 'join-group',
							 data: m
						}
						uni.sendSocketMessage({
							data: JSON.stringify(msg),
							fail() {
								uni.showToast({
									icon:'success',
									title:'邀请成功'
								})
							},
							success() {
								uni.navigateBack();
							}
						})
					}
				}
			},
			showTime(time){
				return timeUtil.getFormatTime(time)
			},
			async getGroupInfo(groupId){
				let res = await userRequest.groupInfo({id:groupId})
				let res1 = await userRequest.groupMember({id:groupId})
				this.groupInfo = res.data;
				let users = res1.data
				this.urls = []
				for(let i=0;i<users.length;i++){
					this.urls.push('http://localhost:8080'+users[i].avatar)
				}
				for(let i=0;i<this.groupList.length;i++){
					if(this.groupList[i].group_id===this.groupInfo.id){
						this.isMember = true
						break
					}
				}
			}
		}
	}
</script>

<style scoped lang="scss">
	.list__item:active{
		background-color: #e8e8e8;
	}
	.list__item{
			padding: 6px 30px;
			align-items: center;
			justify-content: space-between;
		}
	.list__item_checkbox{
		height: 85rpx;
		width: 80rpx;
		float: left;
	}
    .list__item__avatar{
			height: 85rpx;
			width: 80rpx;
			border-radius: 3px;
			float: left;
			margin-left: -20upx;
		}
    .list__item__user-name{
		    // align-items: center;
			font-size: 16px;
			margin-left: 22upx;
			line-height: 40px;
			color: black;
		}
		.list__item__name{
			    align-items: center;
				font-size: 16px;
				margin-left: 2upx;
				color: black;
			}
    .list__footer{
			color: royalblue;
			font-size: 14px;
			text-align: center;
			margin: 15px 0;
		}
	.u-safe-area-inset--bottom{
		height: 150rpx;
		text-align: center;
	}
	.btn{
		background-color: royalblue;
		width: 48px;
		height: 62rpx;
		color: #fff;
		// font-weight: 900;
		text-align: center;
		line-height: 65rpx;
		float: right;
		border-radius: 15rpx;
	}
	.footer{
	    justify-content: space-between;
	}
	.footer_button{
		background-color: #19be60 ;
		display: block;
		float: right;
		height: 30px;
		width: 53px;
		vertical-align: middle;
		line-height: 30px;
		text-align: center;
		font-size: 20upx;
	}
</style>
