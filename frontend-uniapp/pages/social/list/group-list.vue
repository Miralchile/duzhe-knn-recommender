<template>
	<view>
		<view style="padding: 12px;">
			<view style="color: darkgrey;font-family: PingFangHK-Regular;font-size: 12px;font-weight: normal;line-height: 18px;letter-spacing: 0em;margin-top:2%;margin-bottom: 4%;">{{'群聊 （共'+groupList.length+'个群聊）'}}</view>
			<view class="item-container" v-for="(item,index) in groupList" :key="index">
				<u-row @tap="gotoPage('group', item.group_id)" customStyle="margin-bottom: auto;margin-top:4%;">
				    <u-col span="2">
				         <image class="group-avatar" :src="item.avatar"></image>
				    </u-col>
				    <u-col span="10">
				        <view style="font-family: PingFangHK-Regular;font-size: 14px;font-weight: normal;line-height: 18px;letter-spacing: 0px;color: #372C2C;">{{item.name}}</view>
				    </u-col>
				</u-row>
				<u-line></u-line>
			</view>
<!-- 			<view class="item-container">
				<u-row customStyle="margin-bottom: 10px">
				    <u-col span="10">
				         <view style="color: darkgrey;font-size: 25rpx;">{{'共'+groupList.length+'个群聊'}}</view>
				    </u-col>
				</u-row>
				<u-line></u-line>
			</view> -->
		</view>
	</view>
</template>

<script>
	import {mapGetters} from 'vuex'
 	export default{
		computed:{
			...mapGetters(['groupList'])
		},
		data(){
			return{
				
			}
		},
		onNavigationBarButtonTap(e) {
			if(e.index === 0){
				uni.navigateTo({
					url:'/pages/social/info/group-edit?action=new'
				})
			}
		},
		methods:{
			gotoPage(where, id){
				uni.navigateTo({
					url:'/pages/social/info/group-info?id='+id
				})
			}
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
		background-color: #ffffff;
	}
	.item-container{
		text-align: center;
		margin-top: 8rpx;
	}
	.group-avatar{
		width: 90rpx;
		height: 90rpx;
		border-radius: 50%;
		box-sizing: border-box;
		border: 1px  ;
	}
		.demo-layout {
	        height: 25px;
	        border-radius: 4px;
	    }
	
	    .bg-purple {
	        background: #FFFFFF;
	    }
	
	    .bg-purple-light {
	        background: #FFFFFF;
	    }
	
	    .bg-purple-dark {
	        background: #FFFFFF;
	    }
</style>