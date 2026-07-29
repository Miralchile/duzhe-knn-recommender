//铃声通知
export default{
	//播放系统提示音乐
	playSystemAudio(){
	    let music = null;
	    music = uni.createInnerAudioContext();
	    music.src = '/static/audio/system-notice.mp3';
	    music.play();
	    music.onEnded(()=>{music = null;})
	},
	//播放提示音
	playMessageAudio(){
	    let music = null;
	    music = uni.createInnerAudioContext();
	    music.src = '/static/audio/message-notice.mp3';
	    music.play();
	    music.onEnded(()=>{music = null;})
	}
}