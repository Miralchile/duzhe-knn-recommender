import request from '@/utils/request'
/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getAllChannel() {
	return request({
	  url: `/duzhe/getAllChannel`,
	  method: 'get'
	});
}
// 如果在这里，就直接去掉http://localhost就可以了，其他的地方有两种方式，一个是全部整合成这种接口文件，另外一种是设置一个全局的参数，一个个去改
/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getAllCategory() {
	return request({
	  // url: `http://localhost/duzhe/getAllCategory`,
	  url: `/duzhe/getAllCategory`,
	  method: 'get'
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getAllChannelByCategoryId() {
	return request({
	  // url: `http://localhost/duzhe/getAllChannelByCategoryId`,
	  url: `http:/duzhe/getAllChannelByCategoryId`,
	  method: 'get'
	});
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getChannelsByCategoryName(name) {
	return request({
	  // url: `http://localhost/duzhe/getChannelsByCategoryName?name=`+name,
	  url: `/duzhe/getChannelsByCategoryName?name=`+name,
	  method: 'get'
	});
}