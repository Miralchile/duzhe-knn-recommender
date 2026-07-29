import request from '@/utils/request'
/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getHotVideoWork() {
  return request({
    url: `/duzhe/getHotVideoWork`,
    method: "get"
  });
}

/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getHotImageWork() {
  return request({
    url: `/duzhe/getHotImageWork`,
    method: "get"
  });
}