import request from '@/utils/request'

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getCurrentUser() {
	return request({
	  url: `/duzhe/getCurrentUser`,
	  method: "get",
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getMyWorkList(pageNo,pageSize) {
	return request({
	  url: `/duzhe/getMyWorkList?pageNo=` + pageNo + '&pageSize=' + pageSize,
	  method: 'get'
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getMyWorkListTotal() {
	return request({
	  url: `/duzhe/getMyWorkListTotal`,
	  method: "get",
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function deleteWorkById(id) {
	return request({
	  url: `/duzhe/deleteWorkById?id=` + id,
	  method: "get",
	});
}

