import {
	getToken
} from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import {
	toast,
	showConfirm,
	tansParams
} from '@/utils/common'

let timeout = 1000000

const request = config => {
	// 是否需要设置 token
	config.header = {}
	config.header['Authorization'] = getToken()
	// get请求映射params参数
	if (config.params) {
		let url = config.url + '?' + tansParams(config.params)
		url = url.slice(0, -1)
		config.url = url
	}
	return new Promise((resolve, reject) => {
		uni.request({
				method: config.method || 'get',
				timeout: config.timeout || timeout,
				url: 'http://localhost:59091' + config.url,
				//以后修改别的接口修改这里就可以
				data: config.data,
				header: config.header,
				dataType: 'json'
			}).then(response => {
				let [error, res] = response
				if (error) {
					toast('后端接口连接异常')
					reject('后端接口连接异常')
					return
				}
				const code = res.statusCode || 200
				const msg = errorCode[code] || res.data.msg || errorCode['default']
				if (code === 500) {
					toast(msg)
					reject('500')
				} else if (code !== 200) {
					toast(msg)
					reject(code)
				}
				resolve(res.data)
			})
			.catch(error => {
				let {
					message
				} = error
				if (message === 'Network Error') {
					message = '后端接口连接异常'
				} else if (message.includes('timeout')) {
					message = '系统接口请求超时'
				} else if (message.includes('Request failed with status code')) {
					message = '系统接口' + message.substr(message.length - 3) + '异常'
				}
				toast(message)
				reject(error)
			})
	})
}

export default request
