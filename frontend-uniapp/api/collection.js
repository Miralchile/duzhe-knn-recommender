import request from '@/utils/request'

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function deleteCollectionByUserIdAndWorkId(workId) {
	return request({
	  url: `/duzhe/deleteCollectionByUserIdAndWorkId?workId=`+workId,
	  method: 'get'
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function checkIsCollection(workId) {
	return request({
	  url: `/duzhe/checkIsCollection?workId=`+workId,
	  method: 'get'
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getCollectionListByFolderId(folderId) {
	return request({
	  url: `/duzhe/getCollectionListByFolderId?folderId=`+folderId,
	  method: 'get'
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getFolderListByCurrentUser() {
	return request({
	  url: `/duzhe/getFolderListByCurrentUser`,
	  method: 'get'
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function addColletionToFolder(data) {
	return request({
	  url: `/duzhe/addColletionToFolder`,
	  method: 'post',
	  data
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function deleteFolderById(id) {
	return request({
	  url: `/duzhe/deleteFolderById?id=` + id,
	  method: 'get'
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function addFolder(data) {
	return request({
		url: `/duzhe/addFolder`,
		method: 'post',
		data,
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function uploadFolderCoverImage(temp) {
	let token = uni.getStorageSync('Authorization')
	return uni.uploadFile({
		url: `/duzhe/uploadFolderCoverImage`,
		method: 'post',
		filePath: temp,
		name: "file",
		header: {
			"Authorization": token
		}
	});
}