class YBPlayer {
	constructor({container, src, title, poster, formats, barrages, barrageShow, barrageGap, barrageConfig, controls, progressShow = true, autoplay, mirror, pictureInPicture, nextBtnShow, prevBtnShow, muted, loop, preload, settings, initialTime, volume, playbackRate, objectFit, crossOrigin, segments, isLive, flvConfig, enableBlob }) {
		this.container = typeof container == 'string' ? document.getElementById(container) : container
		this.src = src || ''
		this.title = title || ''
		this.poster = poster || ''
		this.formats = formats || 'auto'
		this.barrages = barrages
		this.barrageShow = barrageShow
		this.barrageGap = barrageGap || 5
		this.barrageConfig = barrageConfig
		this.controls = controls
		this.progressShow = progressShow
		this.autoplay = autoplay
		this.mirror = mirror
		this.pictureInPicture = pictureInPicture
		this.nextBtnShow = nextBtnShow
		this.prevBtnShow = prevBtnShow
		this.muted = muted
		this.loop = loop
		this.preload = preload || 'auto'
		this.settings = settings || 'all'
		this.initialTime = initialTime || 0
		this.volume = volume || 1
		this.playbackRate = playbackRate || 1
		this.objectFit = objectFit || 'contain'
		this.crossOrigin = crossOrigin || ''
		this.segments = segments
		this.isLive = isLive
		this.flvConfig = flvConfig || {}
		this.enableBlob = enableBlob
		this.video = null
		this.hls = null
		this.flv = null
		this.barrage = null
		this._eventCallback = {}
		this._videoEl = null
		this._headersEl = null
		this._controlsEl = null
		this._barrageEl = null
		this._settingEl = null
		this._playbackRateEl = null
		this._isParsed = false
		this._isDrag = false
		this._controlsTimer = null
		this._init()
	}
	_init () {
		if ( this.container && typeof this.container != 'undefined' ) {
			try{
				this.container.style.position = 'relative';
				this.container.style.overflow = 'hidden';
				//监听全屏事件
				this.container.addEventListener('fullscreenerror', this._fullscreenerror.bind(this));
				this.container.addEventListener('mozfullscreenerror', this._fullscreenerror.bind(this));
				this.container.addEventListener('msfullscreenerror', this._fullscreenerror.bind(this));
				this.container.addEventListener('webkitfullscreenerror', this._fullscreenerror.bind(this));
				this.container.addEventListener('fullscreenchange', this._fullscreenchanged.bind(this));
				this.container.addEventListener('mozfullscreenchange', this._fullscreenchanged.bind(this));
				this.container.addEventListener('msfullscreenchange', this._fullscreenchanged.bind(this));
				this.container.addEventListener('webkitfullscreenchange', this._fullscreenchanged.bind(this));
				this._initVideo();
				this._setVideoUrl();
				if ( this.controls ) this._initControls();
			}catch(e){
				throw new Error(e.toString());
			}
		}
	}
	_destroy () {
		if ( this.hls ) {
			this.hls.destroy();
			this.hls = null;
		}
		if ( this.flv ) {
			this.flv.pause();
			this.flv.destroy();
			this.flv = null;
		}
		this._destroyControls();
		this._destroyHeaders();
		this._destroyBarrage();
		this._destroyVideo();
		if ( this.container ) {
			this.container.removeEventListener('fullscreenerror', this._fullscreenerror.bind(this));
			this.container.removeEventListener('mozfullscreenerror', this._fullscreenerror.bind(this));
			this.container.removeEventListener('msfullscreenerror', this._fullscreenerror.bind(this));
			this.container.removeEventListener('webkitfullscreenerror', this._fullscreenerror.bind(this));
			this.container.removeEventListener('fullscreenchange', this._fullscreenchanged.bind(this));
			this.container.removeEventListener('mozfullscreenchange', this._fullscreenchanged.bind(this));
			this.container.removeEventListener('msfullscreenchange', this._fullscreenchanged.bind(this));
			this.container.removeEventListener('webkitfullscreenchange', this._fullscreenchanged.bind(this));
			this.container = null;
		}
	}
	_initVideo () {
		this._videoEl = document.createElement('DIV');
		this._videoEl.setAttribute('class', 'ybplayer-video-content');
		this.container.appendChild(this._videoEl);
		this.video = document.createElement('VIDEO');
		this.video.setAttribute('style', 'width: 100%;height:100%;flex:1;object-fit:' + this.objectFit + ';');
		this.video.setAttribute('preload', this.preload);
		this.video.setAttribute('playbackRate', this.playbackRate);
		this.video.setAttribute('volume', this.volume);
		this.video.setAttribute('poster', this.poster);
		this.video.setAttribute('x-webkit-airplay', true);
		this.video.setAttribute('webkit-playsinline', true);
		this.video.setAttribute('playsinline', true);
		this.video.setAttribute('x5-video-player-type', 'h5');
		this.video.setAttribute('x5-video-play', true);
		this.crossOrigin && this.video.setAttribute('crossOrigin', this.crossOrigin);
		this.video.innerHTML = '您的浏览器不支持 video 标签。';
		this._videoEl.appendChild(this.video);
		this.video.muted = this.muted;
		this.video.autoplay = this.autoplay;
		this.video.loop = this.loop;
		this.video.oncanplay = () => {
			this._eventCallback.canplay && this._eventCallback.canplay({
				duration: this.video.duration,
				width: this.video.videoWidth,//视频宽度
				height: this.video.videoHeight//视频高度
			});
		}
		this.video.oncanplaythrough = () => {
			this._eventCallback.canplaythrough && this._eventCallback.canplaythrough({
				duration: this.video.duration,
				width: this.video.videoWidth,//视频宽度
				height: this.video.videoHeight//视频高度
			});
		}
		this.video.onloadeddata = () => {
			this._eventCallback.loadeddata && this._eventCallback.loadeddata({
				duration: this.video.duration,
				width: this.video.videoWidth,//视频宽度
				height: this.video.videoHeight//视频高度
			});
			this.video.currentTime = this.initialTime;
		}
		this.video.onloadedmetadata = () => {
			this._eventCallback.loadedmetadata && this._eventCallback.loadedmetadata({
				duration: this.video.duration,
				width: this.video.videoWidth,//视频宽度
				height: this.video.videoHeight//视频高度
			});
			if ( this.barrageShow ) this._initBarrage();
		}
		this.video.onloadstart = () => {
			this._eventCallback.loadstart && this._eventCallback.loadstart();
		}
		this.video.onplay = () => {
			this._eventCallback.play && this._eventCallback.play()
			this.barrage && this.barrage.play();
			this._setControlsView('play')
		}
		this.video.onpause = () => {
			this._eventCallback.pause && this._eventCallback.pause();
			this.barrage && this.barrage.pause();
			this._setControlsView('play')
		}
		this.video.onended = () => {
			this._eventCallback.ended && this._eventCallback.ended();
		}
		this.video.onseeking = () => {
			this._eventCallback.seeking && this._eventCallback.seeking({
				currentTime: this.video.currentTime
			});
		}
		this.video.onseeked = () => {
			this._eventCallback.seeked && this._eventCallback.seeked({
				currentTime: this.video.currentTime
			});
			this.barrage && this.barrage.seek(this.video.currentTime);
		}
		this.video.ontimeupdate = () => {
			this._eventCallback.timeupdate && this._eventCallback.timeupdate({
				currentTime: this.video.currentTime
			})
			this._setControlsView('timeUpdate')
		}
		this.video.ondurationchange = () => {
			this._eventCallback.durationchange && this._eventCallback.durationchange({
				duration: this.video.duration
			})
		}
		this.video.onwaiting = () => {
			this._eventCallback.waiting && this._eventCallback.waiting({
				currentTime: this.video.currentTime
			});
			this.barrage && this.barrage.pause();
		}
		this.video.onplaying = () => {
			this._eventCallback.playing && this._eventCallback.playing({
				currentTime: this.video.currentTime
			})
			this.barrage && this.barrage.play();
		}
		this.video.onprogress = () => {
			this._eventCallback.progress && this._eventCallback.progress({
				buffered: this.video.buffered
			})
		}
		this.video.onabort = () => {
			this._eventCallback.abort && this._eventCallback.abort();
		}
		this.video.onerror = (e) => {
			this._eventCallback.error && this._eventCallback.error(e);
		}
		this.video.onvolumechange = () => {
			this._eventCallback.volumeChange && this._eventCallback.volumeChange({
				volume: this.video.volume
			});
			this._setControlsView('volume')
		}
		this.video.onratechange = () => {
			this._eventCallback.rateChange && this._eventCallback.rateChange({
				playbackRate: this.video.playbackRate
			});
			if ( this._playbackRateEl && this._querySelector('ybplayer-setting') ) {
				for ( let i = 0; i < this._querySelectorAll('ybplayer-setting').length; i++ ) {
					this._querySelectorAll('ybplayer-setting')[i].getElementsByClassName('ybplayer-setting-text')[0].style.color = this.playbackRate == this._querySelectorAll('ybplayer-setting')[i].getAttribute('data-rate') ? '#2ca2f9' : '#333';
				}
			}
			this.barrage && this.barrage.setConfig({
				playbackRate: this.video.playbackRate
			});
		}
		this.video.onenterpictureinpicture = () => {
			this._eventCallback.enterpictureinpicture && this._eventCallback.enterpictureinpicture()
			this._setSettingView('pictureInPicture');
		}
		this.video.onleavepictureinpicture = () => {
			this._eventCallback.leavepictureinpicture && this._eventCallback.leavepictureinpicture()
			this._setSettingView('pictureInPicture');
		}
		this._barrageEl = document.createElement('DIV');
		this._barrageEl.setAttribute('class', 'ybplayer-video-barrage');
		this._barrageEl.setAttribute('style', 'margin:' + this.barrageGap + 'px 0;');
		this.container.appendChild(this._barrageEl);
		this._barrageEl.onclick = () => {
			if ( this._playbackRateEl ) {
				this._hidePlaybackRate()
			} else if ( this._settingEl ) {
				this._hideSetting()
			} else if ( this._controlsEl ) {
				if ( this._controlsEl.classList.value.indexOf('ybplayer-controls-show') > -1 ) {
					this._hideControls()
				} else {
					this._showControls()
				}
			}
		}
	}
	_destroyVideo () {
		if ( this.video ) {
			this.video.pause();
			this.video.src = '';
			this.video.oncanplay = null;
			this.video.onloadedmetadata = null;
			this.video.onplay = null;
			this.video.onpause = null;
			this.video.onended = null;
			this.video.onseeking = null;
			this.video.onseeked = null;
			this.video.ontimeupdate = null;
			this.video.ondurationchange = null;
			this.video.onwaiting = null;
			this.video.onplaying = null;
			this.video.onprogress = null;
			this.video.onabort = null;
			this.video.onerror = null;
			this.video.onvolumechange = null;
			this.video.onratechange = null;
			this.video.onenterpictureinpicture = null;
			this.video.onleavepictureinpicture = null;
			this.video.remove();
			this.video = null;
		}
		if ( this._videoEl ) {
			this._videoEl.remove();
			this._videoEl = null;
		}
		if ( this._barrageEl ) {
			this._barrageEl.onclick = null;
			this._barrageEl.remove();
			this._barrageEl = null;
		}
	}
	_initHeaders () {
		if ( this.container && this.title ) {
			this._headersEl = document.createElement('DIV');
			this._headersEl.setAttribute('class', 'ybplayer-headers');
			this._headersEl.innerHTML = `
				<div class="ybplayer-headers-shadow"></div>
				<i class="ybplayerIconfont icon-angle-arrow-left ybplayer-icon ${this._setClassName('ybplayer-icon-back')}"></i>
				<span class="${this._setClassName('ybplayer-headers-title')}">${this.title}</span>
			`;
			this.container.appendChild(this._headersEl);
			this._querySelector('ybplayer-icon-back').onclick = () => {
				this.exitFullscreen()
			}
		}
	}
	_destroyHeaders () {
		if ( this._headersEl ) {
			this._querySelector('ybplayer-icon-back').onclick = null
			this._headersEl.remove();
			this._headersEl = null;
		}
	}
	_initControls () {
		if ( this.container ) {
			this._controlsEl = document.createElement('DIV');
			this._controlsEl.setAttribute('class', 'ybplayer-controls');
			this._controlsEl.innerHTML = `
				<div class="ybplayer-controls-top">
					<div class="${this._setClassName('ybplayer-icon-play-prev')}" style="margin-right: ${this.prevBtnShow ? '10px' : 0};">
						${this.prevBtnShow ? `
						<i class="ybplayerIconfont icon-play-prev-fill ybplayer-icon"></i>
						` : ''}
					</div>
					<div class="${this._setClassName('ybplayer-icon-play')}">
						<i class="ybplayerIconfont icon-play ybplayer-icon"></i>
					</div>
					<div class="${this._setClassName('ybplayer-icon-play-next')}" style="margin-left: ${this.nextBtnShow ? '10px' : 0};">
						${this.nextBtnShow ? `
						<i class="ybplayerIconfont icon-play-next-fill ybplayer-icon"></i>
						` : ''}
					</div>
					<span class="${this._setClassName('ybplayer-controls-time')}">00:00 / 00:00</span>
					<div class="${this._setClassName('ybplayer-icon-volume')} ybplayer-controls-top-btn">
						<i class="ybplayerIconfont icon-volume-${this.muted ? 'muted' : 'medium'} ybplayer-icon"></i>
					</div>
					<div class="${this._setClassName('ybplayer-icon-setting')} ybplayer-controls-top-btn">
						<i class="ybplayerIconfont icon-setting ybplayer-icon"></i>
					</div>
					<div class="${this._setClassName('ybplayer-icon-fullscreen')} ybplayer-controls-top-btn">
						<i class="ybplayerIconfont icon-in-fullscreen ybplayer-icon"></i>
					</div>
				</div>
				<div class="${this._setClassName('ybplayer-controls-progress')}">
				</div>
				<div class="ybplayer-controls-shadow"></div>
			`;
			this.container.appendChild(this._controlsEl);
			this._controlsEl.onmousedown = () => {
				this._clear_controlsTimer()
			}
			this._controlsEl.onmouseleave = () => {
				this._start_controlsTimer()
			}
			this._querySelector('ybplayer-icon-play').onclick = () => {
				this.toggle()
			}
			this._querySelector('ybplayer-icon-volume').onclick = () => {
				this.setConfig('muted', !this.muted)
			}
			this._querySelector('ybplayer-icon-setting').onclick = () => {
				this._showSetting()
			}
			this._querySelector('ybplayer-icon-fullscreen').onclick = () => {
				this.switchFullscreen()
			}
			if ( this.prevBtnShow ) {
				this._querySelector('ybplayer-icon-play-prev').onclick = () => {
					this._eventCallback.prevBtnClick && this._eventCallback.prevBtnClick();
				}
			}
			if ( this.nextBtnShow ) {
				this._querySelector('ybplayer-icon-play-next').onclick = () => {
					this._eventCallback.nextBtnClick && this._eventCallback.nextBtnClick();
				}
			}
			this._initProgress()
			this._getSlots();
			this._showControls();
		}
	}
	_destroyControls () {
		if ( this._controlsEl ) {
			this._destroyProgress()
			this._controlsEl.onmousedown = null
			this._controlsEl.onmouseleave = null
			this._querySelector('ybplayer-icon-play').onclick = null
			this._querySelector('ybplayer-icon-volume').onclick = null
			this._querySelector('ybplayer-icon-setting').onclick = null
			this._querySelector('ybplayer-icon-fullscreen').onclick = null
			if ( this.prevBtnShow ) this._querySelector('ybplayer-icon-play-prev').onclick = null
			if ( this.nextBtnShow ) this._querySelector('ybplayer-icon-play-next').onclick = null
			this._controlsEl.remove();
			this._controlsEl = null
		}
		if ( this._settingEl ) {
			for ( let i = 0; i < this._querySelectorAll('ybplayer-setting').length; i++ ) {
				this._querySelectorAll('ybplayer-setting').onclick = null
			}
			this._settingEl.remove();
			this._settingEl = null;
		}
		if ( this._playbackRateEl ) {
			for ( let i = 0; i < this._querySelectorAll('ybplayer-setting').length; i++ ) {
				this._querySelectorAll('ybplayer-setting')[i].onclick = null
			}
			this._playbackRateEl.remove();
			this._playbackRateEl = null;
		}
		if ( this._slotsEl ) this._slotsEl = null;
	}
	_showControls () {
		this._clear_controlsTimer()
		if ( this._controlsEl ) {
			this._controlsEl.setAttribute('class', 'ybplayer-controls ybplayer-controls-show');
			if ( this._slotsEl ) this._slotsEl.style.visibility = 'visible';
			if ( this._headersEl ) this._headersEl.setAttribute('class', 'ybplayer-headers ybplayer-headers-show');
			this._eventCallback.controlsChange && this._eventCallback.controlsChange({
				controls: true
			});
			this._start_controlsTimer()
		}
	}
	_hideControls () {
		this._clear_controlsTimer()
		if ( this._controlsEl ) {
			this._controlsEl.setAttribute('class', 'ybplayer-controls ybplayer-controls-hide')
			if ( this._headersEl ) this._headersEl.setAttribute('class', 'ybplayer-headers ybplayer-headers-hide');
			if ( this._slotsEl ) this._slotsEl.style.visibility = 'hidden';
			this._eventCallback.controlsChange && this._eventCallback.controlsChange({
				controls: false
			});
		}
	}
	_setControlsView (value) {
		if ( value == 'play' ) {
			if ( this._querySelector('ybplayer-icon-play') ) {
				this._querySelector('ybplayer-icon-play').innerHTML = `
					<i class="ybplayerIconfont icon-${this.video.paused ? 'play' : 'pause'} ybplayer-icon"></i>
				`;
			}
		} else if ( value == 'timeUpdate' ) {
			if ( !this._isDrag ) {
				let currentTime = this.video.currentTime && this.video.currentTime != 'Infinity' ? this.video.currentTime : 0;
				let duration = this.video.duration && this.video.duration != 'Infinity' ? this.video.duration : 0;
				let timeEl = this._querySelector('ybplayer-controls-time')
				if ( this._querySelector('ybplayer-slider-focus') ) {
					this._querySelector('ybplayer-slider-focus').style.width = ((currentTime / duration) * 100) + '%'
				}
				if ( this._querySelector('ybplayer-slider') ) {
					this._querySelector('ybplayer-slider').value = (currentTime / duration) * 100
				}
				if ( this._querySelector('ybplayer-controls-time') ) {
					this._querySelector('ybplayer-controls-time').innerHTML = `${this._timesFormat(currentTime)} / ${this._timesFormat(duration)}`
				}
			}
		} else if ( value == 'volume' ) {
			if ( this._querySelector('ybplayer-icon-volume') ) {
				this._querySelector('ybplayer-icon-volume').innerHTML = '<i class="ybplayerIconfont icon-volume-' + (this.muted ? 'muted' : 'medium') + ' ybplayer-icon"></i>'
			}
		} else if ( value == 'fullscreen' ) {
			if ( this._querySelector('ybplayer-icon-fullscreen') ) {
				this._querySelector('ybplayer-icon-fullscreen').innerHTML = '<i class="ybplayerIconfont icon-' + (this.fullscreen ? 'out-fullscreen' : 'in-fullscreen') + ' ybplayer-icon"></i>';
			}
		} else if ( value == 'prevBtn' ) {
			if ( this.prevBtnShow ) {
				this._querySelector('ybplayer-icon-play-prev').style.marginRight = '10px';
				this._querySelector('ybplayer-icon-play-prev').innerHTML = '<i class="ybplayerIconfont icon-play-prev-fill ybplayer-icon"></i>';
				this._querySelector('ybplayer-icon-play-prev').onclick = () => {
					this._eventCallback.prevBtnClick && this._eventCallback.prevBtnClick()
				}
			} else {
				this._querySelector('ybplayer-icon-play-prev').style.marginRight = '';
				this._querySelector('ybplayer-icon-play-prev').innerHTML = '';
				this._querySelector('ybplayer-icon-play-prev').onclick = null;
			}
		} else if ( value == 'nextBtn' ) {
			if ( this.nextBtnShow ) {
				this._querySelector('ybplayer-icon-play-next').style.marginLeft = '10px';
				this._querySelector('ybplayer-icon-play-next').innerHTML = '<i class="ybplayerIconfont icon-play-next-fill ybplayer-icon"></i>';
				this._querySelector('ybplayer-icon-play-next').onclick = () => {
					this._eventCallback.nextBtnClick && this._eventCallback.nextBtnClick()
				}
			} else {
				this._querySelector('ybplayer-icon-play-next').style.marginLeft = '';
				this._querySelector('ybplayer-icon-play-next').innerHTML = '';
				this._querySelector('ybplayer-icon-play-next').onclick = null;
			}
		}
	}
	_initProgress () {
		if ( this._querySelector('ybplayer-controls-progress') && this.progressShow ) {
			this._querySelector('ybplayer-controls-progress').innerHTML = `
				<div class="ybplayer-slider-box">
					<div class="${this._setClassName('ybplayer-slider-track')}"></div>
					<div class="${this._setClassName('ybplayer-slider-focus')}"></div>
					<input class="${this._setClassName('ybplayer-slider')} ybplayer-controls-slider" value="0" type="range">
				</div>
			`
			this._querySelector('ybplayer-slider').onchange = (e) => {
				this._isDrag = false
				this.video && this.seek((this._querySelector('ybplayer-slider').value / 100) * this.video.duration);
				this._start_controlsTimer();
			}
			this._querySelector('ybplayer-slider').oninput = (e) => {
				this._isDrag = true
				if ( this._querySelector('ybplayer-slider-focus') ) this._querySelector('ybplayer-slider-focus').style.width = this._querySelector('ybplayer-slider').value + '%'
				this._clear_controlsTimer();
			}
		}
	}
	_destroyProgress () {
		if ( this._querySelector('ybplayer-controls-progress') ) {
			if ( this._querySelector('ybplayer-slider') ) this._querySelector('ybplayer-slider').onchange = null
			if ( this._querySelector('ybplayer-slider') ) this._querySelector('ybplayer-slider').oninput = null
			this._querySelector('ybplayer-controls-progress').innerHTML = ''
		}
	}
	_clear_controlsTimer () {
		if ( this.controls && this._controlsTimer ) {
			window.clearTimeout(this._controlsTimer)
			this._controlsTimer = null
		}
	}
	_start_controlsTimer () {
		if (  this.controls ) {
			this._controlsTimer = window.setTimeout(() => {
				this._hideControls()
			}, 5000)
		}
	}
	_getSlots () {
		let nodes = this.container.childNodes;
		for ( let i = 0; i < nodes.length; i++ ) {
			if ( [].concat(nodes[i].classList).toString().indexOf('ybplayer-slots') > -1 ) {
				this._slotsEl = nodes[i]
			}
		}
	}
	_showSetting () {
		if ( this.container ) {
			this._settingEl = document.createElement('DIV');
			this._settingEl.setAttribute('class', 'ybplayer-settings');
			let barrageHtml = '';
			let settings = this.settings.split(',').map(setting => setting.trim());
			try{
				barrageHtml = YBBarrage ? `
					<div class="${this._setClassName('ybplayer-setting')}" data-value="barrage">
						<i class="ybplayerIconfont icon-barrage-${this.barrageShow ? 'show' : 'hide'} ybplayer-setting-icon"></i>
						<p class="ybplayer-setting-text">${this.barrageShow ? '关闭弹幕' : '开启弹幕'}</p>
					</div>
				` : '';
			}catch(e){
			}
			this._settingEl.innerHTML = `
				${ this.settings == 'all' || settings.indexOf('barrage') > -1 ? barrageHtml : ''}
				${ this.settings == 'all' || settings.indexOf('playbackRate') > -1 ? `
					<div class="${this._setClassName('ybplayer-setting')}" data-value="playbackRate">
						<i class="ybplayerIconfont icon-play-rate-circle ybplayer-setting-icon"></i>
						<p class="ybplayer-setting-text">播放速度</p>
					</div>
				` : '' }
				${ this.settings == 'all' || settings.indexOf('mirror') > -1 ? `
					<div class="${this._setClassName('ybplayer-setting')}" data-value="mirror">
						<i class="ybplayerIconfont icon-mirror ybplayer-setting-icon"></i>
						<p class="ybplayer-setting-text">${this.mirror ? '关闭镜像' : '镜像画面'}</p>
					</div>
				` : '' }
				${ this.settings == 'all' || settings.indexOf('capture') > -1 ? `
					<div class="${this._setClassName('ybplayer-setting')}" data-value="capture">
						<i class="ybplayerIconfont icon-screenshot ybplayer-setting-icon"></i>
						<p class="ybplayer-setting-text">截取画面</p>
					</div>
				` : '' }
				${ (this.settings == 'all' || settings.indexOf('pictureInPicture') > -1) && document.pictureInPictureEnabled ? `
					<div class="${this._setClassName('ybplayer-setting')}" data-value="pictureInPicture">
						<i class="ybplayerIconfont icon-picture-in-picture-${this.pictureInPicture ? 'open' : 'exit'} ybplayer-setting-icon"></i>
						<p class="ybplayer-setting-text">${this.pictureInPicture ? '退出画中画' : '画中画'}</p>
					</div>
				` : '' }
			`;
			this.container.appendChild(this._settingEl);
			for ( let i = 0; i < this._querySelectorAll('ybplayer-setting').length; i++ ) {
				this._querySelectorAll('ybplayer-setting')[i].onclick = () => {
					let type = this._querySelectorAll('ybplayer-setting')[i].getAttribute('data-value');
					if ( type == 'barrage' ) {
						this.setConfig('barrageShow', !this.barrageShow);
					} else if ( type == 'playbackRate' ) {
						if ( this._playbackRateEl ) {
							this._hidePlaybackRate();
						} else {
							this._showPlaybackRate();
						}
					} else if ( type == 'mirror' ) {
						this.setConfig('mirror', !this.mirror);
					} else if ( type == 'capture' ) {
						this.capture();
						this._hideSetting();
					} else {
						this.setConfig('pictureInPicture', !this.pictureInPicture);
					}
				}
			}
		}
	}
	_hideSetting () {
		if ( this.container && this._settingEl ) {
			this._settingEl.style.animation = ' settingHide .3s both';
			for ( let i = 0; i < this._querySelectorAll('ybplayer-setting').length; i++ ) {
				this._querySelectorAll('ybplayer-setting')[i].onclick = null
			}
			window.setTimeout(() => {
				this.container.removeChild(this._settingEl);
				this._settingEl = null;
			}, 300);
		}
	}
	_setSettingView (value) {
		if ( this._querySelector('ybplayer-setting') ) {
			for ( let i = 0; i < this._querySelectorAll('ybplayer-setting').length; i++ ) {
				if ( this._querySelectorAll('ybplayer-setting')[i].getAttribute('data-value') == value ) {
					if ( value == 'barrage' ) {
						this._querySelectorAll('ybplayer-setting')[i].innerHTML = `
							<i class="ybplayerIconfont icon-barrage-${this.barrageShow ? 'show' : 'hide'} ybplayer-setting-icon"></i>
							<p class="ybplayer-setting-text">${this.barrageShow ? '关闭弹幕' : '开启弹幕'}</p>
						`;
					} else if ( value == 'mirror' ) {
						this._querySelectorAll('ybplayer-setting')[i].innerHTML = `
							<i class="ybplayerIconfont icon-mirror ybplayer-setting-icon"></i>
							<p class="ybplayer-setting-text">${this.mirror ? '关闭镜像' : '镜像画面'}</p>
						`;
					} else if ( value == 'pictureInPicture' ) {
						this._querySelectorAll('ybplayer-setting')[i].innerHTML = `
							<i class="ybplayerIconfont icon-picture-in-picture-${this.pictureInPicture ? 'open' : 'exit'} ybplayer-setting-icon"></i>
							<p class="ybplayer-setting-text">${this.pictureInPicture ? '退出画中画' : '画中画'}</p>
						`;
					}
				}
			}
		}
	}
	_showPlaybackRate () {
		if ( this.container ) {
			this._hideSetting();
			this._playbackRateEl = document.createElement('DIV');
			this._playbackRateEl.setAttribute('class', 'ybplayer-settings');
			let playbackRates = [{
				label: '0.25',
				value: 0.25
			},{
				label: '0.5',
				value: 0.5
			},{
				label: '0.75',
				value: 0.75
			},{
				label: '正常',
				value: 1.0
			},{
				label: '1.25',
				value: 1.25
			},{
				label: '1.5',
				value: 1.5
			},{
				label: '1.75',
				value: 1.75
			},{
				label: '2.0',
				value: 2.0
			}];
			for ( let rate of playbackRates ) {
				this._playbackRateEl.innerHTML += `
					<div class="${this._setClassName('ybplayer-setting')}" data-rate="${rate.value}">
						<p class="ybplayer-setting-text" style="color: ${rate.value == this.playbackRate ? '#2ca2f9' : '#333'};">${rate.label}</p>
					</div>
				`;
			}
			this.container.appendChild(this._playbackRateEl);
			let that = this
			for ( let i = 0; i < this._querySelectorAll('ybplayer-setting').length; i++ ) {
				this._querySelectorAll('ybplayer-setting')[i].onclick = function () {
					that.setConfig('playbackRate', this.getAttribute('data-rate'))
				}
			}
		}
	}
	_hidePlaybackRate () {
		if ( this.container && this._playbackRateEl ) {
			this._playbackRateEl.style.animation = ' settingHide .3s both';
			for ( let i = 0; i < this._querySelectorAll('ybplayer-setting').length; i++ ) {
				this._querySelectorAll('ybplayer-setting')[i].onclick = null
			}
			window.setTimeout(() => {
				this.container.removeChild(this._playbackRateEl);
				this._playbackRateEl = null;
				this._showSetting();
			}, 300);
		}
	}
	_initBarrage () {
		try{
			this.barrage = new YBBarrage({
				container: this._barrageEl,
				barrages: this.barrages,
				config: Object.assign({}, this.barrageConfig, {
					initialTime: this.video ? this.video.currentTime : 0
				})
			});
			if ( !this.video.paused ) this.barrage.play();
			this._setSettingView('barrage');
			this._eventCallback.barrageChange && this._eventCallback.barrageChange({
				show: true
			})
		}catch(e){
			//TODO handle the exception
		}
	}
	_destroyBarrage () {
		if ( this.barrage ) {
			this.barrage.stop();
			this.barrage = null;
			this._setSettingView('barrage');
			this._eventCallback.barrageChange && this._eventCallback.barrageChange({
				show: false
			})
		}
	}
	_setConfig (attribute, value) {
		let videoAttributes = ['poster', 'autoplay', 'loop', 'muted', 'preload', 'volume', 'playbackRate', 'crossOrigin'];
		if ( this.video && videoAttributes.indexOf(attribute) > -1 )
			this.video[attribute] = value;
		if ( this.video && attribute == 'objectFit' )
			this.video.style.objectFit = value;
		if ( this.video && attribute == 'mirror' ) {
			this.video.style.transform = 'rotateY(' + (value ? 180 : 0) + 'deg)';
			this._setSettingView('mirror');
		}
		if ( this.video && attribute == 'pictureInPicture' ) {
			if ( document.pictureInPictureEnabled ) {
				if ( value )
					this.video.requestPictureInPicture();
				else
					document.pictureInPictureElement && document.exitPictureInPicture();
			}
		}
		if ( attribute == 'controls' )  {
			if ( value )
				this._initControls()
			else
				this._destroyControls()
		}
		if ( attribute == 'progressShow' )  {
			if ( value )
				this._initProgress()
			else
				this._destroyProgress()
		}
		if ( attribute == 'barrageShow' )  {
			if ( value )
				this._initBarrage()
			else
				this._destroyBarrage()
		}
		if ( attribute == 'barrageConfig' )  {
			this.barrage && this.barrage.setConfig(value);
		}
		if ( attribute == 'barrageGap' )  {
			this.barrage && this.barrage.refresh();
		}
		if ( attribute == 'title' )  {
			if ( value ) {
				if ( this._querySelector('ybplayer-headers-title') ) {
					this._querySelector('ybplayer-headers-title').innerHTML = value;
				} else {
					this._initHeaders();
				}
			} else {
				this._destroyHeaders();
			}
		}
		if ( attribute == 'prevBtnShow' )  {
			this._setControlsView('prevBtn')
		}
		if ( attribute == 'nextBtnShow' )  {
			this._setControlsView('nextBtn')
		}
		if ( attribute == 'src' || attribute == 'segments' ) {
			this._destroyControls();
			this._destroyBarrage();
			this._destroyHeaders();
			if ( this.hls ) {
				this.hls.destroy();
				this.hls = null;
			}
			if ( this.flv ) {
				this.flv.pause();
				this.flv.destroy();
				this.flv = null;
			}
			this._destroyVideo();
			this._initVideo();
			this._setVideoUrl();
			if ( this.fullscreen ) this._initHeaders();
			if ( this.controls ) this._initControls();
		}
	}
	//获取后缀
	_suffix (name) {
	  	let fileName = name.lastIndexOf(".");
	  	let fileNameLength = name.length;
	  	let fileFormat = name.substring(fileName + 1, fileNameLength);
	  	return fileFormat;
	}
	//格式化时间
	_timesFormat (value) {
		let hours = Math.floor(value / 60 / 60 % 60) >= 10 ? Math.floor(value / 60 / 60 % 60) : '0' + Math.floor(value / 60 / 60 % 60);
		let minutes = Math.floor(value / 60 % 60) >= 10 ? Math.floor(value / 60 % 60) : '0' + Math.floor(value / 60 % 60);
		let seconds = Math.floor(value % 60) >= 10 ? Math.floor(value % 60) : '0' + Math.floor(value % 60);
		return hours == '00' ? (minutes + ':' + seconds) : (hours + ':' + minutes + ':' + seconds);
	}
	_setClassName (className) {
		return this.container.id + '_' + className + ' ' + className
	}
	_querySelector (className) {
		return this.container ? document.getElementsByClassName(this.container.id + '_' + className) ? document.getElementsByClassName(this.container.id + '_' + className)[0] : null : null
	}
	_querySelectorAll (className) {
		return this.container ? document.getElementsByClassName(this.container.id + '_' + className) || [] : []
	}
	_setVideoUrl () {
		this._isParsed = false;
		if ( this.src ) {
			let formats = '';
			if ( this.formats == 'auto'  )
				formats = this._suffix(this.src.split('?')[0]);
			else
				formats = this.formats;
			if ( formats == 'm3u8' )
				this._setM3u8();
			else if ( formats == 'flv' )
				this._setFlv();
			else
				this._setBlob();
		} else if ( this.segments ) {
			this._setFlv();
		}
	}
	_setBlob () {
		if ( this.enableBlob ) {
			let xhr = new XMLHttpRequest()
			xhr.open('GET', this.src, true)
			xhr.responseType = 'blob'
			xhr.onload = () => {
				if ( xhr.status == 200 ) {
					try {
						this.video.srcObject = xhr.response
						this.video.load()
					} catch (error) {
						const URL = window.URL || window.webkitURL
						this.video.src = URL.createObjectURL(xhr.response);
						this.video.load()
					}
				} else {
					this.video.src = this.src
					this.video.load()
				}
				xhr.abort()
				xhr = null
			}
			xhr.onerror = () => {
				this.video.src = this.src
				this.video.load()
				xhr = null
			}
			xhr.send()
		} else {
			this.video.src = this.src
			this.video.load()
		}
	}
	_setM3u8 () {
		try{
			if ( Hls.isSupported() ) {
				this.hls = new Hls();
				this.hls.loadSource(this.src);
				this.hls.attachMedia(this.video);
				this.hls.on(Hls.Events.MANIFEST_PARSED, (e) => {
					this._isParsed = true;
				});
				this.hls.on(Hls.Events.ERROR, (e, data) => {
					if ( !this._isParsed ) {
						this._eventCallback.error && this._eventCallback.error(data);
						this.hls.destroy();
						this.hls = null;
					}
				});
			} else {
				this.video.src = this.src;
				this.video.load();
			}
		}catch(e){
			this._eventCallback.error && this._eventCallback.error(e)
		}
	}
	_setFlv () {
		try{
			if (flvjs.isSupported()) {
				this.flv = flvjs.createPlayer(
					{
						type: "flv",
						isLive: this.isLive,
						url: this.src ? this.src : null,
						segments: this.segment && this.segments.length > 0 ? this.segment : null,
						cors: this.flvConfig.cors || false,
						withCredentials: this.flvConfig.withCredentials || false,
						hasAudio: this.flvConfig.hasAudio || false,
						hasVideo: this.flvConfig.hasVideo || false,
						duration: this.flvConfig.duration || null,
						filesize: this.flvConfig.filesize || null
					},
					Object.assign({}, {
						enableWorker: false, //不启用分离线程
						enableStashBuffer: false, //关闭IO隐藏缓冲区
						isLive: true,
						lazyLoad: false
					}, this.flvConfig)
				)
			} else {
				this.video.src = this.src;
				this.video.load();
			}
			this.flv.on('error', e => {
				this._eventCallback.error && this._eventCallback.error(e);
				this.flv.pause();
				this.flv.destroy();
				this.flv = null;
			});
			this.flv.attachMediaElement(this.video);
			this.flv.load();
		}catch(e){
			this._eventCallback.error && this._eventCallback.error(e);
		}
	}
	_fullscreenerror () {
		if ( this.container ) {
			this.container.style.position = 'fixed';
			this.container.style.left = 0;
			this.container.style.right = 0;
			this.container.style.bottom = 0;
			this.container.style.top = 0;
			this.container.style.width = '100vw';
			this.container.style.height = '100vh';
			this.container.style.zIndex = 999;
			this._cssfullscreenchange();
		}
	}
	_fullscreenchanged () {
		if ( document.fullscreenElement || document.mozFullScreenElement || document.msFullscreenElement || document.webkitFullscreenElement ) {
			this._eventCallback.fullscreenChange && this._eventCallback.fullscreenChange({
				fullscreen: true,
				type: 'requestfullscreen'
			});
			this.fullscreen = true;
			this._initHeaders();
		} else {
			this._eventCallback.fullscreenChange && this._eventCallback.fullscreenChange({
				fullscreen: false,
				type: 'requestfullscreen'
			});
			this.fullscreen = false;
			this._destroyHeaders();
		}
		this._setControlsView('fullscreen');
		this.refreshBarrage();
	}
	_cssfullscreenchange () {
		if ( this.container && this.container.style.position == 'fixed' ) {
			this._eventCallback.fullscreenChange && this._eventCallback.fullscreenChange({
				fullscreen: true,
				type: 'cssfullscreen'
			});
			this.fullscreen = true;
			this._initHeaders();
		} else {
			this._eventCallback.fullscreenChange && this._eventCallback.fullscreenChange({
				fullscreen: false,
				type: 'cssfullscreen'
			});
			this.fullscreen = false;
			this._destroyHeaders();
		}
		this._setControlsView('fullscreen');
		this.refreshBarrage();
	}
	//注册事件
	on (event, callback) {
		this._eventCallback[event] = callback;
	}
	//播放
	play () {
		this.video && this.video.play()
	}
	//暂停
	pause () {
		this.video && this.video.pause()
	}
	//播放、暂停
	toggle () {
		this.video && this.video.paused ? this.video.play() : this.video.pause()
	}
	//跳转
	seek (time) {
		if ( this.video ) this.video.currentTime = time
	}
	//停止
	stop () {
		this._destroy()
	}
	//截图
	capture () {
		const canvas = document.createElement('canvas');
		canvas.width = this.video.videoWidth;
		canvas.height = this.video.videoHeight;
		canvas.getContext('2d').drawImage(this.video, 0, 0, canvas.width, canvas.height);
		const fileName = this.video.currentTime + '.png';
		canvas.toBlob(blob => {
			this._eventCallback.captureFinish && this._eventCallback.captureFinish({
				blob: blob,
				base64: canvas.toDataURL('image/png')
			})
		}, 'image/png')
	}
	drawBarrage (barrage) {
		this.barrage && this.barrage.add(barrage);
	}
	refreshBarrage (barrage) {
		this.barrage && this.barrage.refresh();
	}
	setBarrages (barrages) {
		this.barrages = barrages;
		this.barrage && this.barrage.setBarrages(barrages);
	}
	setConfig (attribute, value) {
		this[attribute] = value;
		this._setConfig(attribute, value);
	}
	switchFullscreen () {
		if ( document.fullscreenElement || document.mozFullScreenElement || document.msFullscreenElement || document.webkitFullscreenElement || (this.container && this.container.style.position == 'fixed') ) {
			this.exitFullscreen()
		} else {
			this.lanuchFullscreen()
		}
	}
	lanuchFullscreen () {
		if ( document.fullscreenElement || document.mozFullScreenElement || document.msFullscreenElement || document.webkitFullscreenElement ) return
		const rfs = document.documentElement.requestFullscreen || document.documentElement.webkitRequestFullscreen || document.documentElement.mozRequestFullscreen || document.documentElement.requestFullScreen || document.documentElement.webkitRequestFullScreen || document.documentElement.mozRequestFullScreen
		if ( typeof rfs != 'undefined' && rfs && this.container ) {
			rfs.call(this.container)
		} else if (typeof window.ActiveXObject !== "undefined") {
			//IE浏览器，模拟按下F11全屏
			var wscript = new ActiveXObject("WScript.Shell");
			if (wscript != null) {
				wscript.SendKeys("{F11}");
			}
		} else {
			this._fullscreenerror()
		}
	}
	exitFullscreen () {
		if ( document.fullscreenElement || document.mozFullScreenElement || document.msFullscreenElement || document.webkitFullscreenElement ) {
			const cfs = document.exitFullscreen || document.mozCancelFullScreen || document.msExitFullscreen || document.webkitExitFullscreen;
			if ( typeof cfs != 'undefined' && cfs ) {
				cfs.call(document)
			}
		} else if (typeof window.ActiveXObject !== "undefined") {
			//IE浏览器，模拟按下F11键退出全屏
			var wscript = new ActiveXObject("WScript.Shell");
			if (wscript != null) {
				wscript.SendKeys("{F11}");
			}
		} else {
			if ( this.container && this.container.style.position == 'fixed' ) {
				this.container.style.position = 'relative';
				this.container.style.width = '100%';
				this.container.style.height = '100%';
				this.container.style.top = 'inherit';
				this.container.style.left = 'inherit';
				this.container.style.right = 'inherit';
				this.container.style.bottom = 'inherit';
				this.container.style.zIndex = 'inherit';
				this._cssfullscreenchange();
			}
		}
	}
}