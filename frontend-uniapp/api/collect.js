import request from '@/utils/request'
/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function collect(wid) {
  return request({
    // url: `http://localhost/duzhe/collect?wid=`+wid,
	url: `/duzhe/collect?wid=`+wid,
    method: "get"
  });
}

export function checkIsCollect(wid) {
  return request({
    // url: `http://localhost/duzhe/checkIsCollect?wid=`+wid,
	url: `/duzhe/checkIsCollect?wid=`+wid,
    method: "get"
  });
}
export function getCollectNumById(wid) {
  return request({
    // url: `http://localhost/duzhe/getCollectNumById?wid=`+wid,
	url: `/duzhe/getCollectNumById?wid=`+wid,
    method: "get"
  });
}
export function collectPage(pageNo) {
  return request({
    // url: `http://localhost/duzhe/collect/page?pageNo=`+pageNo,
	url: `/duzhe/collect/page?pageNo=`+pageNo,
    method: "get"
  });
}