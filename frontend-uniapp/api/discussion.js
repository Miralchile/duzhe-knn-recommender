import request from '@/utils/request'
/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function uploadImage(temp) {
	let token = uni.getStorageSync('Authorization')
	return uni.uploadFile({
		url: `/duzhe/uploadImage`,
		method: 'post',
		filePath: temp,
		name: "file",
		header: {
			"Authorization": token
		}
	});
}

export function addDiscussion(data){
	return request({
		url: `/duzhe/addDiscussion`,
		method: 'post',
		data,
	});
}

export function getDiscussionRecommendList(pageNo) {
	return request({
	  url: `/duzhe/getDiscussionRecommendList?pageNo=`+pageNo,
	  method: "get",
	});
}

export function getDiscussionByChannel(pageNo, channel) {
	return request({
	  url: `/duzhe/getDiscussionByChannel?pageNo=` + pageNo + '&channel=' + channel,
	  method: "get",
	});
}

export function getAnswerById(id) {
	return request({
	  url: `/duzhe/getAnswerById?id=` + id,
	  method: "get",
	});
}

export function getDiscussionById(id) {
	return request({
	  url: `/duzhe/getDiscussionById?id=` + id,
	  method: "get",
	});
}

export function addAnswerById(data){
	return request({
		url: `/duzhe/addAnswerById`,
		method: 'post',
		data,
	});
}

export function getBestAnswerById(id) {
	return request({
		url: `/duzhe/getBestAnswerById?id=`+id,
		method: 'get'
	});
}

export function addDiscussionBestAnswer(data){
	return request({
		url: `/duzhe/addDiscussionBestAnswer`,
		method: 'post',
		data,
	});
}

export function checkIsQuestioner(id) {
	return request({
		url: `/duzhe/checkIsQuestioner?id=`+id,
		method: 'get'
	});
}

export function cancelBestAnswerById(id) {
	return request({
		url: `/duzhe/cancelBestAnswerById?id=`+id,
		method: 'get'
	});
}


