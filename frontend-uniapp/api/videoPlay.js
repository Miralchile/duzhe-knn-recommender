import request from '@/utils/request'
/**
 * 获取 接口管理列表
 * @param {Object} params 检索参数
 */
export function getVideoWorkCommentById(id) {
  return request({
    url: `/duzhe/getVideoWorkCommentById?id=` + id,
    method: 'get',
  })
}

export function addVideoWorkDanmuById(data) {
  return request({
    url: `/duzhe/addVideoWorkDanmuById`,
    method: 'post',
    data,
  })
}

export function getVideoWorkDanmuById(id) {
  return request({
    url: `/duzhe/getVideoWorkDanmuById?id=` + id,
    method: 'get',
  })
}

export function getVideoWorkCommentNumById(id) {
  return request({
    url: `/duzhe/getVideoWorkCommentNumById?id=` + id,
    method: 'get',
  })
}

export function getVideoWorkThumbNumById(id) {
  return request({
    url: `/duzhe/getVideoWorkThumbNumById?id=` + id,
    method: 'get',
  })
}

export function addOrDeleteVideoWorkThumbById(data) {
  return request({
    url: `/duzhe/addOrDeleteVideoWorkThumbById`,
    method: 'post',
    data,
  })
}
export function addUserMovieInfo(data) {
  return request({
    url: `/duzhe/system/userMovieInfo/add`,
    method: 'post',
    data,
  })
}
export function readScore(params) {
  return request({
    url: `/duzhe/system/userMovieInfo/readScore`,
    method: 'get',
    params,
  })
}

export function addVideoWorkCommentById(data) {
  return request({
    url: `/duzhe/addVideoWorkCommentById`,
    method: 'post',
    data,
  })
}

export function getVideoWorkById(id) {
  return request({
    url: `/duzhe/getVideoWorkById?id=` + id,
    method: 'get',
  })
}
export function getNewVideoWorkById(id) {
  return request({
    url: `/duzhe/system/movieInfo/getRecommendation?movieId=` + id,
    method: 'get',
  })
}

export function checkIsVideoWorkThumb(id) {
  return request({
    url: `/duzhe/checkIsVideoWorkThumb?id=` + id,
    method: 'get',
  })
}

export function getRelation(category) {
  return request({
    url: `/duzhe/getRelation?category=` + category,
    method: 'get',
  })
}

// export function videoPage(pageNo) {
//   return request({
//     url: `/duzhe/video/page?pageNo=`+pageNo,
//     method: "get"
//   });
// }
export function videoPage(pageNo) {
  return request({
    url: `/duzhe/video/page?pageNo=` + pageNo,
    method: 'get',
  })
}
// export function videoPageNew(pageNo) {
//   return request({
//     url: `/duzhe/video/newPage?pageNo=`+pageNo,
//     method: "get"
//   });
// }
