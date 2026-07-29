<template>
  <view style="display: flex; justify-content: center; margin-top: 50px;">
  <view class="qrimg">
  	<tki-qrcode ref="qrcode" :cid="cid" :val="val" :size="size" :unit="unit" :background="background"
  		:foreground="foreground" :pdground="pdground" :icon="icon" :iconSize="iconsize" :lv="lv"
  		:onval="onval" :loadMake="loadMake" :showLoading="showLoading" :loadingText="loadingText"
  		@result="qrR" />
  </view>
  </view>
</template>

<script>
//导入组件
import tkiQrcode from "@/components/tki-qrcode/tki-qrcode.vue"
import userRequest from '@/api/social/user.js';
import requestUrl from '@/api/social/url.js';
import {mapGetters} from 'vuex';
import timeUtil from '@/utils/social/timeUtil.js';
import $store from '@/store/modules/social';
export default{
	computed:{
		...mapGetters(['loginUserInfo'])
	},
  name: 'App',
  data() {
    return {
      value: 'https://www.baidu.com', //二维码内容
	  // 生成二维码的信息
	  cid: '',
	  size: 300, //二维码大小
	  val: "{}",
	  unit: 'rpx',
	  background: '#b4e9e2', // 背景色
	  foreground: '#309286', // 前景色
	  pdground: '#32dbc6', // 角标色
	  icon: '', // 二维码图标
	  iconsize: 40, // 二维码图标大小
	  lv: 3, // 二维码容错级别 ， 一般不用设置，默认就行
	  onval: true, // val值变化时自动重新生成二维码
	  loadMake: true, // 组件加载完成后自动生成二维码
	  showLoading: true,
	  loadingText: '二维码生成中...',
	  // 生成二维码
    }
  },
  onShow() {
  
  },
  
  mounted() {
  	// 每秒更新时间
  	setInterval(() => {
  		const now = new Date()
  		const options = {
  			hour12: false,
  			timeZone: 'Asia/Shanghai'
  		}
  		this.currentTime = "当前时间: " + now.toLocaleTimeString('en-US', options)
  	}, 1000)
  },
  
  onLoad: function(e) {
  
  	//let orderdatadetail = JSON.parse(decodeURIComponent(e.data));
  	// console.log(orderdatadetail);
  
  	console.log("=======================");
  
  	let temp = {
  		"type": "二维码",
  		"userid": this.loginUserInfo.userId,
  	};
  
  	// console.log(temp);
  
  	this.val = JSON.stringify(temp);
  
  	console.log("=========");
  	console.log(this.val);
  
  },
  
  methods: {
  
  	qrR(res) {
  		console.log(res);
  	},
  
  }
}
</script>

