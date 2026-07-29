<script>
	import { getToken } from '@/utils/auth'
	import $store from '@/store/modules/social';
	import $storeState from '@/store/modules/social/test.js';
	import websocket from '@/api/social/websocket.js';
	export default {
	
		globalData: {
			uid: '',
			name: '',
			baseUrl: 'http://localhost:59091',
		},
		onLaunch: function() {
		  this.initApp()
		  //连接websocket
		    	websocket.initConnect();
				$store.replaceState($storeState.state)
				console.log($store)
				
		    	//获取好友列表
		    	$store.dispatch('getFriendList');
		    	///获取通知消息
		    	$store.dispatch('getNoticeList');
		},
		onShow: function() {
			console.log('App Show')
		},
		onHide: function() {
			console.log('App Hide')
		},
		methods: {
		  // 初始化应用
		  initApp() {
		    // 初始化应用配置
		    // 检查用户登录状态
		    //#ifdef H5
		    this.checkLogin()
		    //#endif
		  },
		  checkLogin() {
		    if (!getToken()) {
		      this.$tab.reLaunch('/pages/index/index') 
		    }
		  }
		}
	}
</script>

<style lang="scss">
	/*每个页面公共css */
	// @import url("./icon/iconfont.css");
	// @import url("./common/common.css");
	// @import url("./static/scss/index.scss");
	
	
	html,body {
		width:100%;
		height:100%;
		background:rgba(255, 255, 255, 1.0);
	}
</style>
