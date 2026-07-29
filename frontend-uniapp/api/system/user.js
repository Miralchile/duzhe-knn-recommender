import request from '@/utils/request'

// 用户密码重置
export function updateUserPwd(oldPassword, newPassword) {
  const data = {
    oldPassword,
    newPassword
  }
  return request({
    url: '/duzhe/system/user/profile/updatePwd',
    method: 'put',
    params: data
  })
}

// 查询用户个人信息
export function getUserProfile() {
  return request({
    url: '/duzhe/system/user/profile',
    method: 'get'
  })
}

// 修改用户个人信息
export function updateUserProfile(data) {
  return request({
    url: '/duzhe/system/user/profile',
    method: 'put',
    data: data
  })
}

// 查询会员个人信息
export function getMemberProfile() {
  return request({
    url: '/duzhe/system/member/memberprofile',
    method: 'get'
  })
}

// // 查询会员个人信息
// export function getMemberProfile() {
//   return request({
//     url: '/system/member',
//     method: 'get'
//   })
// }
// 查询用户详细
// export function getUser(userId) {
//   return request({
//     url: '/system/user/' + parseStrEmpty(userId),
//     method: 'get'
//   })
// }
export function getUser(userId) {
  return request({
    url: '/duzhe/system/user/'+userId,
    method: 'get'
  })
}
