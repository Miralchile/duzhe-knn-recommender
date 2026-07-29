import request from '@/utils/request'
/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getOthersById(userId,pageNo,pageSize) {
	return request({
	  url: `/duzhe/getOthersById?userId=` + userId + '&pageNo=' + pageNo + '&pageSize=' + pageSize,
	  method: 'get'
	});
}

export function getOthersWorkListTotal(userId) {
  return request({
    url: `/duzhe/getOthersWorkListTotal?userId=` + userId,
    method: 'get'
  });
}

export function isFollowThisUser(userId) {
  return request({
    url: `/duzhe/isFollowThisUser?userId=` + userId,
    method: 'get'
  });
}

export function followThisUser(userId) {
  return request({
    url: `/duzhe/followThisUser?userId=` + userId,
    method: 'get'
  });
}

export function getFollowList() {
  return request({
    url: `/duzhe/getFollowList`,
    method: 'get'
  });
}

export function cancelFollowThisUser(userId) {
  return request({
    url: `/duzhe/cancelFollowThisUser?userId=` + userId,
    method: 'get'
  });
}