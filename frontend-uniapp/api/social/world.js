import request from '@/api/social/request.js';

import $store from '@/store/modules/social/test.js';
export default{
	publish(data) {
	  return request({
	    url: '/duzhe/ry-vue/world/publish',
	    method: 'POST',
	    data,
		header:{
			'content-type': 'application/json',
			'Authorization': getToken()
		}
	  });
	},
	articles(data) {
		console.log(data)
	  return request({
	    url: '/duzhe/ry-vue/world/articles',
	    method: 'POST',
	    data,
		header:{
			'content-type': 'application/json',
			'Authorization': getToken()
		}
	  });
	},
	article(data) {
	  return request({
	    url: '/duzhe/ry-vue/world/article',
	    method: 'POST',
	    data,
		header:{
			'content-type': 'application/json',
			'Authorization': getToken()
		}
	  });
	},
	likeArticle(data) {
	  return request({
	    url: '/duzhe/ry-vue/world/like/article',
	    method: 'POST',
	    data,
		header:{
			'content-type': 'application/json',
			'Authorization': getToken()
		}
	  });
	},
	postComment(data) {
		console.log(data)
	  return request({
	    url: '/duzhe/ry-vue/world/comment/publish',
	    method: 'POST',
	    data,
		header:{
			'content-type': 'application/json',
			'Authorization': getToken()
		}
	  });
	},
	likeComment(data) {
	  return request({
	    url: '/duzhe/ry-vue/world/like/comment',
	    method: 'POST',
	    data,
		header:{
			'content-type': 'application/json',
			'Authorization': getToken()
		}
	  });
	},
	comments(data) {
	  return request({
	    url: '/duzhe/ry-vue/world/comments',
	    method: 'POST',
	    data,
		header:{
			'content-type': 'application/json',
			'Authorization': getToken()
		}
	  });
	}
	,
	deleteComment(data) {
	  return request({
	    url: '/duzhe/ry-vue/world/comment/delete',
	    method: 'POST',
	    data,
		header:{
			'content-type': 'application/json',
			'Authorization': getToken()
		}
	  });
	},
	personArticles(data) {
	  return request({
	    url: '/duzhe/ry-vue/world/person/articles',
	    method: 'POST',
	    data,
		header:{
			'content-type': 'application/json',
			'Authorization': getToken()
		}
	  });
	},
	deleteArticle(data) {
	  return request({
	    url: '/duzhe/ry-vue/world/article/delete',
	    method: 'POST',
	    data,
		header:{
			'content-type': 'application/json',
			'Authorization': getToken()
		}
	  });
	},
	changeArticle(data) {
	  return request({
	    url: '/duzhe/ry-vue/world/article/change',
	    method: 'POST',
	    data,
		header:{
			'content-type': 'application/json',
			'Authorization': getToken()
		}
	  });
	}
}