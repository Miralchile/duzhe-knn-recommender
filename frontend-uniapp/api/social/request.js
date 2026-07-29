// const BASE_URL = 'https://api.imooc-blog.lgdsunday.club/api';
import $store from '@/store/modules/social/test.js';
import URL from '@/api/social/url.js';
const BASE_URL = URL.getUrl();

function request({ url, data, method, header }) {
  return new Promise((resolve, reject) => {
    // 发起网络请求
    uni.request({
      url: BASE_URL + url,
      data,
      method,
	  header,
      success: ({ data }) => {
        // 响应成功，获取数据，解析数据
        if (data.success) {
          resolve(data);
        } else {
		   // 响应失败，给用户提示
		   uni.showToast({
		     title: data.message,
		     icon: 'none',
		     mask: false,
		     duration: 3000,
		   });
			if(data.code=='E1'){
				uni.closeSocket();
				setTimeout(()=>{
					uni.clearStorage('TOKEN')
					uni.clearStorage('LOGIN_USER_INFO')
					uni.reLaunch({
						url:'/pages/login/login',
						fail(res){
							console.log(res)
						}
					})
				}, 800)
			}
		   reject(data.message);
        }
      },
      fail: (error) => {
        reject(error);
      },
      complete: () => {
        // 关闭加载
        uni.hideLoading();
      },
    });
  });
}

export default request;
