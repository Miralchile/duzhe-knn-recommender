import request from '@/utils/request'
/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getImageWorkCommentById(id) {
	return request({
	  url: `/duzhe/getImageWorkCommentById?id=`+id,
	  method: "get",
	});
}

export function getImageWorkCommentNumById(id) {
	return request({
	  url: `/duzhe/getImageWorkCommentNumById?id=`+id,
	  method: "get",
	});
}

export function getImageWorkThumbNumById(id) {
	return request({
	  url: `/duzhe/getImageWorkThumbNumById?id=`+id,
	  method: "get",
	});
}

export function addOrDeleteImageWorkThumbById(data){
	return request({
	  url:`/duzhe/addOrDeleteImageWorkThumbById`,
	  method:"post",
	  data,
	});
}

export function addImageWorkCommentById(data) {
	return request({
	  url: `/duzhe/addImageWorkCommentById`,
	  method: "post",
	  data,
	});
}

export function getImageWorkById(id) {
	return request({
	  url: `/duzhe/getImageWorkById?id=`+id,
	  method: "get",
	});
}

export function checkIsImageWorkThumb(id) {
	return request({
	  url: `/duzhe/checkIsImageWorkThumb?id=`+id,
	  method: "get",
	});
}