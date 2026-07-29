<template>
	<view>
		<view style="margin-top: 20rpx;">
		 <input v-model="value" :type="type" :border="border" :height="height" :auto-height="autoHeight" clearable placeholder='新的话题'/>	
		</view>
		<view style='margin-top:200rpx;' >
				<u-upload ref="uUpload" :action="action" :auto-upload="false" ></u-upload>
		</view>
		<!--弹出公告-->
		  <uni-popup :show="show">
			   <view class="gonggao" :class="donghua">
				   <image src="/static/social/img/gonggao.png" mode="aspectFit"></image>
				   <view class="zhuyi">1.涉及黄色，政治，广告及骚扰信息，涉及黄色，政治，广告及骚扰信息</view>
				   <view class="zhuyi">2.涉及黄色，政治，广告及骚扰信息，涉及黄色，政治，广告及骚扰信息</view>
				   <view class="zhuyi">3.涉及黄色，政治，广告及骚扰信息，涉及黄色，政治，广告及骚扰信息</view>
				   <view class="zhuyi">一经核实将被封禁，情节严重者永久封禁！</view>
				   <button type="default" @tap="ttt">我知道了</button>
			   </view>
		</uni-popup>
		<view style="margin-top:40rpx;">
			<button type="primary"  class='bt'>发布话题</button>
		</view>
	</view>	
</template>
<script >
	import uniPopup from '@/pages/social/components/moment/uni-popup.vue'
	export default {
		components:{
			uniPopup
		},
	 	data() {
			return{
			donghua:'animated zoomInDown',
			show:true,
			value: '',
			type: 'textarea',
			border: true,
			height: 300,
			autoHeight: true
		}
		},
		methods:{
			ttt(){
				this.donghua='animated zoomInDown'
				this.show=false
			},
		}
		}
	
</script>
<style>
	.common{
		margin-top: 200rx;
	}
	.bt{
		margin-top: 200rpx;
	}
	.gonggao{
		width: 500upx;
		background-color: #FFFFFF;
		border-radius: 10upx;
		padding: 30upx 40upx;
	}
	.gonggao image{
		width: 90%;
		height: 200rpx;
	}
	.gonggao button{
		background-color: #FFE934;
		margin-top: 40upx;
		border-radius: 10upx;
	}
	.zhuyi{
		text-align: left;
		}
</style>