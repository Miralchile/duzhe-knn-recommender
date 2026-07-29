
const url = 'localhost:8080'
// const url = 'http://localhost:59091'
// const url = 'localhost:59091'
function getRequestUrl() {
  return 'http://'+url+'/';
}

function getWebsokcetUrl() {
  return 'ws://'+'localhost:8080'+'/socket/';
  // return 'ws://'+url+'/socket/';
}

module.exports = {
	getUrl : getRequestUrl,
	getWebsokcetUrl:getWebsokcetUrl,
}