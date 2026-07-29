import request from '@/api/social/request.js';
import { getToken } from '@/utils/auth'

import $store from '@/store/modules/social/test.js';
export default{
	register(data) {
	  return request({
	    url: 'ry-vue/index/register',
	    method: 'POST',
	    data
	  });
	},
	login(data){
		return request({
		  url: 'ry-vue/index/login',
		  method: 'POST',
		  data
		});
	},
	//更新用户信息
	update(data){
		return request({
		  url: 'ry-vue/user/update',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'token': $store.state.token
		  }
		});
	},
	//搜索用户和群聊
	search(data){
		return request({
		  url: 'ry-vue/user/search',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	//获取用户的基本信息
	info(data){
		return request({
		  url: '/duzhe/ry-vue/user/info',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	//创建群聊
	createGroup(data){
		return request({
		  url: '/duzhe/ry-vue/group/create',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	//创建群聊
	getGroup(data){
		// return request({
		//   url: '/duzhe/ry-vue/group/getList',
		//   method: 'POST',
		//   data,
		//   header:{
		// 	  'content-type': 'application/json',
		// 	  'Authorization': getToken()
		//   }
		// });
	},
	//获取群聊信息
	groupInfo(data){
		return request({
		  url: '/duzhe/ry-vue/group/info',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	//修改群聊信息
	updateGroup(data){
		return request({
		  url: '/duzhe/ry-vue/group/update',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	//获取群聊信息
	groupMember(data){
		return request({
		  url: '/duzhe/ry-vue/group/member',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	//清空已读群聊消息
	clearGroupUnread(data){
		return request({
		  url: '/duzhe/ry-vue/group/clearUnread',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	
	//解散群聊
	deleteGroup(data){
		return request({
		  url: '/duzhe/ry-vue/group/deleteGroup',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	
	//退出群聊
	exitGroup(data){
		return request({
		  url: '/duzhe/ry-vue/group/exitGroup',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	
	
	//获取通知信息
	getNotice(data){
		// return request({
		//   url: '/duzhe/ry-vue/notice/list',
		//   method: 'POST',
		//   data,
		//   header:{
		// 	  'content-type': 'application/json',
		// 	  'Authorization': getToken()
		//   }
		// });
	},
	//已读通知信息
	checkNotice(data){
		return request({
		  url: '/duzhe/ry-vue/notice/check',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	//拒绝通知信息
	rejectNotice(data){
		return request({
		  url: '/duzhe/ry-vue/notice/reject',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	//获取通知信息
	getNotice(data){
		// return request({
		//   url: '/duzhe/ry-vue/notice/list',
		//   method: 'POST',
		//   data,
		//   header:{
		// 	  'content-type': 'application/json',
		// 	  'Authorization': getToken()
		//   }
		// });
	},
	//删除通知信息
	deleteNotice(data){
		return request({
		  url: '/duzhe/ry-vue/notice/delete',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	//获取好友消息
	getPeronMessage(data){
		// return request({
		//   url: '/duzhe/ry-vue/message/person',
		//   method: 'POST',
		//   data,
		//   header:{
		// 	  'content-type': 'application/json',
		// 	  'Authorization': getToken()
		//   }
		// });
	},
	//获取群聊消息
	getGroupMessage(data){
		return request({
		  url: '/duzhe/ry-vue/message/group',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	//删除一条消息
	deleteMessage(data){
		return request({
		  url: '/duzhe/ry-vue/message/delete',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	//清空消息
	clearMessage(data){
		return request({
		  url: '/duzhe/ry-vue/message/clearMessage',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	//删除群聊消息
	deleteGroupMessage(data){
		return request({
		  url: '/duzhe/ry-vue/group/delete',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
	clearUnread(data){
		return request({
		  url: '/duzhe/ry-vue/friend/clearUnread',
		  method: 'POST',
		  data,
		  header:{
			  'content-type': 'application/json',
			  'Authorization': getToken()
		  }
		});
	},
}
