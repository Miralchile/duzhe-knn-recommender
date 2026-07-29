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
export function addImageWork(data) {
	return request({
		url: `/duzhe/addImageWork`,
		method: 'post',
		data,
	});
}
