import request from '@/utils/request'

// export function search(keywords, pageNo, pageSize, type, sortField) {
// 	var url = `/duzhe/search?keywords=` + keywords + "&pageNo=" + pageNo + "&pageSize=" + pageSize + "&type=" + type +
// 		"&sortField=" + sortField
// 	return request({
// 		url: url,
// 		method: 'get'
// 	});
// }

// export function searchTotal(keywords, type) {
// 	return request({
// 		url: `/duzhe/searchTotal?keywords=` + keywords + "&type=" + type,
// 		method: 'get'
// 	});
// }

// export function suggestSearch(prefix) {
// 	return request({
// 		url: `/duzhe/suggestSearch?prefix=` + prefix,
// 		method: 'get'
// 	});
// }

export function search(keywords, pageNo, type) {
	var url = `/duzhe/work/search?keywords=` + keywords + "&pageNo=" + pageNo + "&type=" + type
	return request({
		url: url,
		method: 'get'
	});
}

export function searchByCategory(pageNo, categoryName,channelName) {
	var url = `/duzhe/work/searchByCategory?pageNo=` + pageNo + "&categoryName=" + categoryName+ "&channelName=" + channelName;
	return request({
		url: url,
		method: 'get'
	});
}
