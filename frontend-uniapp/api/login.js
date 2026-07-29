import request from '@/utils/request'
/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function login(data) {
	return request({
		method: 'post',
		url: '/duzhe/login',
		data
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function register(data) {
	return request({
		method: 'post',
		url: '/duzhe/register',
		data
	});
}
