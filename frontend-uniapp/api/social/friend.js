import request from '@/api/social/request.js';
import { getToken } from '@/utils/auth'

import $store from '@/store/modules/social/test.js';
export default{
	getList(data) {
	 //  return request({
	 //    url: '/duzhe/ry-vue/friend/getList',
	 //    method: 'POST',
	 //    data,
		// header:{
		// 	'content-type': 'application/json',
		// 	'Authorization': getToken()
		// }
	 //  });
	},
	edit(data) {
	 //  return request({
	 //    url: '/duzhe/ry-vue/friend/edit',
	 //    method: 'POST',
	 //    data,
		// header:{
		// 	'content-type': 'application/json',
		// 	'Authorization': getToken()
		// }
	 //  });
	},
	
	
	// 删除好友
	 deleteSession(data) {
	  return request({
	      url: '/duzhe/ry-vue/friend/delete',
	      method: 'POST',
	      data,
	  	header:{
	  		'content-type': 'application/json',
	  		'Authorization': getToken()
	  	}
	    });
	  }
	
}
