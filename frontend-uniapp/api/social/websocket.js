import requestUrl from '@/api/social/url.js';
import $store from '@/store/modules/social';
import $storeState from '@/store/modules/social/test.js';
import noticeUtil from '@/utils/social/noticeUtil.js';
import timeUtil from '@/utils/social/timeUtil.js';
import userRequest from '@/api/social/user.js';
export default{
	//连接websocket
	initConnect(){
		uni.closeSocket()  //创建新的socket连接前确保旧的已关闭
		//创建一个socket连接
		uni.connectSocket({
		    url:requestUrl.getWebsokcetUrl()+$storeState.state.loginUserInfo.userId,
		    success: res=>{
			},
			fail(res){
				console.log(res)
			}
		})
		//监听socket打开
		uni.onSocketOpen(()=>{
			$store.state.isSocketOpen=true
		    // uni.showToast({
		    // 	icon:'success',
		    // 	title: '成功连接websocket！'
		    // })
			//连接之后才开始获取数据
			// $store.dispatch('getMessage');
		})
		
		//监听socket关闭
		uni.onSocketClose(()=>{
		   $store.state.isSocketOpen=false
		    console.log('WebSocket连接已关闭！');
			//connectSocketInit(); //尝试重连
		})
		//监听socket错误
		uni.onSocketError(()=>{
		    $store.state.isSocketOpen=false
		    console.log('WebSocket连接打开失败');
			// 用户下线
			$store.state.isSocketOpen = false;
			console.log('socket连接错误')
			if (!$store.state.isSocketOpen) {
				setTimeout(function() {
					//reconnect();
				}, 3000);
			}
		})
		//监听socket消息，浏览器端接收消息，获得从服务端发送过来的消息
		uni.onSocketMessage((res)=>{
		    let data=JSON.parse(res.data)  //socket信息是字符串，需要先转成json形式再去解析内容
					
			switch(data.type){
				////撤回消息
				case 'person-withdraw':
					for(let i=0;i<$store.state.personMessage.length;i++){
						if($store.state.personMessage[i].sessionId==data.data.sessionId){
							for(let j=$store.state.personMessage[i].list.length-1;j>=0;j--){
								if($store.state.personMessage[i].list[j].id==data.data.messageId){
									$store.state.personMessage[i].list[j].isWithdrawn=1
									break
								}
							}
							break;
						}
					}
				break;
				case 'group-withdraw':
					//console.log(data)
					for(let i=0;i<$store.state.groupMessage.length;i++){
						if($store.state.groupMessage[i].groupId==data.data.groupId){
							for(let j=$store.state.groupMessage[i].list.length-1;j>=0;j--){
								if($store.state.groupMessage[i].list[j].id==data.data.messageId){
									$store.state.groupMessage[i].list[j].isWithdrawn=1
									break
								}
							}
							break;
						}
					}
				break;
				case 'token-failed':
					uni.showToast({
						title:'身份验证已过期',
						icon:'none'
					})
					setTimeout(()=>{
						uni.redirectTo({
							url:'pages/login/login'
						})
					},1000)
				break;
				case 'person-apply':
				uni.showToast({
					icon:'none',
					title:'你收到了一个好友请求'
				})
				$store.dispatch('getNoticeList');
				if($store.state.systemConfig.bell){
					noticeUtil.playSystemAudio();
				}
				//#ifdef APP
				if($store.state.systemConfig.vibrate){
					uni.vibrateShort()
				}
				//#endif
				break;
				
				case 'count':
					$store.state.onlineArray = data.data
					//console.log(data)
					// uni.showToast({
					// 	title:'共'+data.data.length+'人在线',
					// 	icon:'none'
					// })
					break;
				case 'person-apply-agree':
					//刷新一遍好友列表
					$store.dispatch('getFriendList')
					let friend = data.data
					if(friend.myId==$store.state.loginUserInfo.userId){
						$store.dispatch('getNoticeList')
						console.log('这里需要向好友发送一个消息')
						let m = {
							senderId : friend.myId,
							receiverId : friend.friendId,
							sendTime: timeUtil.getFormatTime(new Date()),
							content: '我同意了你的好友申请~',
							messageType: 'text',
							sessionId: friend.sessionId
						}
						let msg = {
							type: 'person-message',
							data: m
						}
						uni.sendSocketMessage({
							data: JSON.stringify(msg),
							fail() {
								uni.showToast({
									title:'发送失败',
									icon:'error'
								})
							}
						})
						
						
						uni.showToast({
							title:'添加成功！',
							icon:'success'
						})
					}
					break;
				case 'notice-refresh':
					$store.dispatch('getNoticeList')
					uni.showToast({
						title:data.data,
						icon:'none'
					})
					break;
				case 'person-message':
					let message = data.data 
					/////消息加入消息历史列表
					for(let i=0;i<$store.state.personMessage.length;i++){
						if($store.state.personMessage[i].sessionId==message.sessionId){
							$store.state.personMessage[i].list.push(message)
							break
						}
					}
					///更新消息记录
					$store.state.lastUpdateSession = {
						id:message.sessionId,
						count:$store.state.lastUpdateSession.count+1
					}
					///如果消息不是我发的 则响铃
					if(message.senderId!=$store.state.loginUserInfo.userId){
						if($store.state.systemConfig.bell){
							noticeUtil.playMessageAudio();
						}
						//#ifdef APP
						if($store.state.systemConfig.vibrate){
							uni.vibrateShort()
						}
						//#endif
					}
					/////更新绘画列表状态
					for(let i=0;i<$store.state.sessionList.length;i++){
						if($store.state.sessionList[i].sessionId==message.sessionId){
							$store.state.sessionList[i].updateTime = message.updateTime
							switch(message.messageType){
								case 'image':
								$store.state.sessionList[i].lastMessage = '【图片】';break;
								case 'file':
								$store.state.sessionList[i].lastMessage = '【文件】';break;
								case 'audio':
								$store.state.sessionList[i].lastMessage = '【语音】';break;
								case 'video':
								$store.state.sessionList[i].lastMessage = '【视频】';break;
								default:
								$store.state.sessionList[i].lastMessage = message.content;
							}
							//console.log()
							////没有在这个对话 未读++
							if($store.state.chattingUserInfo==null||$store.state.chattingUserInfo.sessionId!=$store.state.sessionList[i].sessionId){
								if(message.senderId!=$store.state.loginUserInfo.userId)
									$store.state.sessionList[i].unread++;
							}else{///否则已读
								$store.dispatch('clearUnread',{
									myId: $store.state.loginUserInfo.userId,
									friendId: $store.state.chattingUserInfo.chattingUserId
								})
							}
														///需要把对话移到第一位
							let session = $store.state.sessionList.splice(i,1);
							$store.state.sessionList = session.concat($store.state.sessionList)
													
							break;
						}
					}
					$store.dispatch('countUnreadMessage')
					break;
					case 'join-success':
					let member = data.data 
						uni.showToast({
							title:'加入成功！',
							icon:'success'
						})
						$store.dispatch('getFriendList');
						console.log(member)
						let m = {
							senderId : $store.state.loginUserInfo.userId,
							senderName : $store.state.loginUserInfo.nickName,
							avatar: $store.state.loginUserInfo.avatar,
							sendTime: timeUtil.getFormatTime(new Date()),
							content: '我已加入群聊',
							messageType: 'text',
							groupId: member.groupId//this.chattingGroupInfo.sessionId
						}
						let msg = {
							 type: 'group-message',
							 data: m
						}
						uni.sendSocketMessage({
							data: JSON.stringify(msg),
							fail(res) {
								uni.showToast({
									icon:'error',
									title:'发送失败'
								})
							}
						})
						
					break;
					case 'group-message':
						let group_message = data.data
						/////消息加入消息历史列表
						console.log($store)
						for(let i=0;i<$store.state.groupMessage.length;i++){
							if($store.state.groupMessage[i].groupId==group_message.groupId){
								$store.state.groupMessage[i].list.push(group_message)
								break
							}
						}
						///更新消息记录
						$store.state.lastUpdateSession = {
							id:group_message.groupId,
							count:$store.state.lastUpdateSession.count+1
						}
						///如果消息不是我发的 则响铃
						if(group_message.senderId!=$store.state.loginUserInfo.userId){
							if($store.state.systemConfig.bell){
								noticeUtil.playMessageAudio();
							}
							//#ifdef APP
							if($store.state.systemConfig.vibrate){
								uni.vibrateShort()
							}
							//#endif
						}
						/////更新绘画列表状态
						for(let i=0;i<$store.state.sessionList.length;i++){
							if($store.state.sessionList[i].sessionId==group_message.groupId){
								$store.state.sessionList[i].updateTime = group_message.updateTime
								switch(group_message.messageType){
									case 'image':
									$store.state.sessionList[i].lastMessage =group_message.senderName +':【图片】';break;
									case 'file':
									$store.state.sessionList[i].lastMessage = group_message.senderName +':【文件】';break;
									case 'audio':
									$store.state.sessionList[i].lastMessage = group_message.senderName +':【语音】';break;
									case 'video':
									$store.state.sessionList[i].lastMessage = group_message.senderName +':【视频】';break;
									default:
									$store.state.sessionList[i].lastMessage = group_message.senderName +':'+group_message.content;
								}
								//console.log()
								////没有在这个对话 未读++
								if($store.state.chattingGroupInfo==null||$store.state.chattingGroupInfo.sessionId!=$store.state.sessionList[i].sessionId){
									if(group_message.senderId!=$store.state.loginUserInfo.userId)
										$store.state.sessionList[i].unread++;
								}else{///否则已读
									$store.dispatch('clearGroupUnread',{
										userId: $store.state.loginUserInfo.userId,
										groupId: $store.state.chattingGroupInfo.sessionId
									})
								}
															///需要把对话移到第一位
								let session = $store.state.sessionList.splice(i,1);
								$store.state.sessionList = session.concat($store.state.sessionList)
														
								break;
							}
						}
						$store.dispatch('countUnreadMessage')
					break;
			}
		})
	}
}