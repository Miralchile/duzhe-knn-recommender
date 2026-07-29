<template>
	<view>
		<view style="padding: 20rpx;">
			<view style="color: darkgrey;font-family: PingFangHK-Regular;font-size: 12px;font-weight: normal;line-height: 18px;letter-spacing: 0em;margin-top:2%;margin-bottom: 4%;">{{'群成员（共'+memberList.length+'个用户）'}}</view>
			<view class="item-container" v-for="(item,index) in memberList" :key="index">
				<u-row @tap="gotoPage('group', item.user_id)" customStyle="margin-bottom: auto;margin-top:4%;">
				    <u-col span="2">
				         <image v-if="item.avatar" class="group-avatar" :src="'http://localhost:8080'+item.avatar"></image>
						 <image v-if="!item.avatar" class="group-avatar" :src="base64Avatar"></image>
				    </u-col>
				    <u-col span="10">
				        <view style="font-family: PingFangHK-Regular;font-size: 14px;font-weight: normal;line-height: 18px;letter-spacing: 0px;color: #372C2C;">{{item.nick_name}}</view>
						<view v-if="loginUserInfo.userId==memberList[0].user_id" class="btn" @click="exitGroup(item.user_id)" style="position:absolute;right: 20px;color: #00C777;font-family: PingFangHK-Regular;font-size: 13px;font-weight: normal;">移除</view>
				    </u-col>
				</u-row>
				<u-line></u-line>
			</view>
<!-- 			<view class="item-container">
=======
			<view class="item-container">
				<u-row>
					<u-icon name="plus" label="邀请好友" labelSize="20upx" label-pos="bottom" color="#333" size="28" class="group-avatar" @click="addFriend()"></u-icon>
				</u-row>
				<u-line></u-line>
			</view>
			<view class="item-container">
>>>>>>> 4d0a02ff7a16e754b14e8b40fce3f3a467cd96e8
				<u-row customStyle="margin-bottom: 10px">
				    <u-col span="10">
				         <view style="color: darkgrey;font-size: 25rpx;">{{'共'+memberList.length+'个用户'}}</view>
				    </u-col>
				</u-row>
				<u-line></u-line>
			</view> -->
		
		</view>
	</view>
</template>

<script>
	import userRequest from '@/api/social/user.js';
	import {mapGetters} from 'vuex';

	import $store from '@/store/modules/social';
 	export default{
		computed:{
			...mapGetters(['loginUserInfo'])
		},
		data(){
			return{
				
				fileList1: [],
				
				groupInfo:{},
				memberList:[],
				groupId:null,
				base64Avatar:"data:image/jpg;base64,/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAAA8AAD/4QMraHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjMtYzAxMSA2Ni4xNDU2NjEsIDIwMTIvMDIvMDYtMTQ6NTY6MjcgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcD0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLyIgeG1sbnM6eG1wTU09Imh0dHA6Ly9ucy5hZG9iZS5jb20veGFwLzEuMC9tbS8iIHhtbG5zOnN0UmVmPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvc1R5cGUvUmVzb3VyY2VSZWYjIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDUzYgKFdpbmRvd3MpIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjREMEQwRkY0RjgwNDExRUE5OTY2RDgxODY3NkJFODMxIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjREMEQwRkY1RjgwNDExRUE5OTY2RDgxODY3NkJFODMxIj4gPHhtcE1NOkRlcml2ZWRGcm9tIHN0UmVmOmluc3RhbmNlSUQ9InhtcC5paWQ6NEQwRDBGRjJGODA0MTFFQTk5NjZEODE4Njc2QkU4MzEiIHN0UmVmOmRvY3VtZW50SUQ9InhtcC5kaWQ6NEQwRDBGRjNGODA0MTFFQTk5NjZEODE4Njc2QkU4MzEiLz4gPC9yZGY6RGVzY3JpcHRpb24+IDwvcmRmOlJERj4gPC94OnhtcG1ldGE+IDw/eHBhY2tldCBlbmQ9InIiPz7/7gAOQWRvYmUAZMAAAAAB/9sAhAAGBAQEBQQGBQUGCQYFBgkLCAYG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"
				
			}
		},
		onLoad(options) {
			this.groupId = options.id
			if(this.groupId==null||this.groupId==undefined){
				uni.switchTab({
					url:'/pages/social/social'
				})
			}
			this.getMemberList()
		},
		methods:{
			addFriend(){
				uni.navigateTo({
					url:'/pages/social/list/friend-list?id='+this.groupInfo.id
				})
			},
			async getMemberList(){
				let res = await userRequest.groupMember({id:this.groupId})
				
				this.memberList = res.data
				
				let res1 = await userRequest.groupInfo({id:this.groupId})
				this.groupInfo = res1.data
			},
			gotoPage(where, id){
				uni.navigateTo({
					url:'/pages/social/info/friend-info1?id='+id
				})
			},
			exitGroup(id){
							let groupId  =  this.groupInfo.id
							let userId = id
							//console.log(groupId,userId)
							uni.request({
								url: 'http://localhost:8080/ry-vue/group/exitGroup',
								method:'POST',
								data: {
									"groupId" : groupId,
									"userId" : id
								},
								success: (res) => {
							        uni.navigateBack();
									console.log(res.data);
								},
							})
						},
		}
	}
</script>
<style lang="scss">
	page {
	  background-color: #fff;
	}
</style>
<style lang="scss" scoped>
	.item-container:active{
		background-color: #FFFFFF;
	}
	.item-container{
		text-align: center;
		margin-top: 10rpx;
	}
	.group-avatar{
		width: 90rpx;
		height: 90rpx;
		border-radius: 50%;
	}
		.demo-layout {
	        height: 25px;
	        border-radius: 4px;
	    }
	
	    .bg-purple {
	        background: #CED7E1;
	    }
	
	    .bg-purple-light {
	        background: #e5e9f2;
	    }
	
	    .bg-purple-dark {
	        background: #99a9bf;
	    }
</style>