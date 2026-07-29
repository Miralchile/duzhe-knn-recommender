import request from '@/utils/request'
/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getRecommendList(pageNo) {
	return request({
	  url: `/duzhe/getRecommendList?pageNo=`+pageNo,
	  method: "get",
	});
}

export function getRecommendListByCategory(pageNo,category) {
	return request({
	  url: `/duzhe/getRecommendListByCategory?pageNo=`+pageNo +`&category=`+category ,
	  method: "get",
	});
}