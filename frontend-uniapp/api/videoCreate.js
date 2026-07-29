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

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function addVideoWork(data) {
	return request({
		url: `/duzhe/addVideoWork`,
		method: 'post',
		data,
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function generateCoverImage(videoMD5) {
	return request({
		url: `/duzhe/generateCoverImage?videoMD5=` + videoMD5,
		method: 'get',
	});
}
