class YBBarrage {
    constructor({container, barrages = [], config = {}}) {
        this.container = container
		this.canvas = null
		this.paused = true
		this.barrages = barrages
		this.config = {}
		this._ctx = null
		this._barrages = []
		this._w = 0
		this._h = 0
		this._drawTimer = null
		this._drawAnima = null
		this.currentTime = 0
		this._count = 0
		this._startTime = 0
		this.setConfig(config)
		this._init()
    }
	_init () {
		this.canvas = document.createElement('CANVAS');
		this.canvas.setAttribute('width', this.container.offsetWidth);
		this.canvas.setAttribute('height', this.container.offsetHeight);
		this.container.appendChild(this.canvas);
		this._ctx = this.canvas.getContext('2d');
		let rect = this.canvas.getBoundingClientRect();
		this._w = rect.right - rect.left;
		this._h = rect.bottom - rect.top;
		this.currentTime = this.config.initialTime;
	}
	_destroy () {
		this.paused = true;
		this._clearTimer();
		this._cancelAnima();
		this._clear();
		this.barrages = [];
		this._barrages = [];
		this.container = null;
		this._ctx = null;
		if ( this.canvas ) {
			this.canvas.remove();
			this.canvas = null;
		}
	}
	//开始绘制
	_render() {
		this._cancelAnima();
		if ( this.paused ) return;
	    if (this._barrages.length) {
			this._clear();
	        for (let i = 0; i < this._barrages.length; i++) {
	            let b = this._barrages[i];
	            if (b.left + b.width <= 0) {
	                this._barrages.splice(i, 1);
	                i--;
	                continue;
	            }
				b.offset = this._detectionBump(b);
				let offset = b.offset * this.config.playbackRate;
				b.left -= offset;
	            this._drawText(b);
	        }
	    }
		this._drawAnima = window.requestAnimationFrame(this._render.bind(this));
	}
	_cancelAnima () {
		if ( this._drawAnima ) {
			window.cancelAnimationFrame(this._drawAnima)
			this._drawAnima = null
		}
	}
	//清空画布
	_clear () {
		this._ctx && this._ctx.clearRect(0, 0, this._w, this._h);
	}
	//绘制文字
	_drawText(barrage) {
		if ( this._ctx ) {
			this._ctx.beginPath();
			this._ctx.font = `${barrage.fontSize || this.config.fontSize}px ${this.config.fontFamily}`;
			this._ctx.strokeStyle = this._hex2rgba(this._getStrokeColor(barrage.color || this.config.defaultColor), this.config.opacity);
			this._ctx.strokeText(barrage.text, barrage.left, barrage.top);
			this._ctx.fillStyle = this._hex2rgba(barrage.color || this.config.defaultColor, this.config.opacity);
			this._ctx.fillText(barrage.text, barrage.left, barrage.top);
			this._ctx.closePath();
		}
	}
	_startTimer () {
		this._startTime = new Date().getTime();
		this._count = 0;
		this._drawTimer = window.setTimeout(this._fixed.bind(this), 1000);
	}
	_clearTimer () {
		if ( this._drawTimer ) {
			window.clearTimeout(this._drawTimer)
			this._drawTimer = null
		}
	}
	_fixed() {
		this._clearTimer();
		if ( this.paused ) return;
		if ( this.config.duration > -1 && this.currentTime >= this.config.duration ) {
			this.seek(0);
			return;
		} 
		this._count++;
		this.currentTime += (1 * this.config.playbackRate);
		let barrages = this.barrages.filter(barrage => parseInt(barrage.time) == parseInt(this.currentTime));
		for ( let i = 0; i < barrages.length; i++ ) {
			let bar = this._getBarrage(barrages[i]);
			bar && this._barrages.push(bar);
		}
		let offset = new Date().getTime() - (this._startTime + (this._count * 1000));
		let nextTime = 1000 - offset;
		if (nextTime < 0) nextTime = 0;
		this._drawTimer = window.setTimeout(this._fixed.bind(this), nextTime);
	}
	//测绘弹幕
	_getBarrage(barrage, isVisible = false) {
		let fontSize = barrage.fontSize || this.config.fontSize;
	    let offset = this._getOffset();
		this._ctx.font = `${barrage.fontSize || this.config.fontSize}px ${this.config.fontFamily}`;
	    let width = Math.ceil(this._ctx.measureText(barrage.text).width);
		let top = this._getTop(fontSize, width, offset, isVisible);
		if ( top > -1 ) {
			return {
			    text: barrage.text,
				time: barrage.time,
				fontSize: fontSize,
				color: barrage.color || this.config.defaultColor,
			    top: top,
			    left: this._w,
			    offset: offset,
			    width: width
			}
		}
	   return false;
	}
	_getStrokeColor (color) {
		let hex = color.length == 7 ? color : '#' + color.slice(1, 4) + color.slice(1, 4)
		const r = parseInt(hex.slice(1,3),16);
		const g = parseInt(hex.slice(3,5),16);
		const b = parseInt(hex.slice(5,7),16);
		let $grayLevel = (r * 0.299) + (g * 0.587) + (b * 0.144);
		if ($grayLevel >= 120) {
			return '#000000';
		} else {
		　	return '#ffffff';
		}
	}
	_hex2rgba (hex, opacity) {
		hex = hex.length == 7 ? hex : '#' + hex.slice(1, 4) + hex.slice(1, 4)
		let str="rgba("
		const r = parseInt(hex.slice(1,3),16).toString();
		const g = parseInt(hex.slice(3,5),16).toString();
		const b = parseInt(hex.slice(5,7),16).toString();
		str += r+","+g+","+b+","+opacity+")";
		return str;
	}
	//计算弹幕距离顶部位置
	_getTop(size, width, offset, isVisible = false) {
	    //canvas绘制文字x,y坐标是按文字左下角计算，预留30px
		let top = -1;
		let len = -1
		//根据弹幕高度分割轨道
		for ( let i = 0; i < Math.floor(this._h / (size + this.config.lineHeight)); i++ ) {
			//当前轨道的顶部位置
			let nowTop = ((i + 1) * size) + (i * this.config.lineHeight);
			//当前轨道上有多少条弹幕
			let barrages = this._barrages.filter(barrage => barrage.top < nowTop + size && barrage.fontSize + barrage.top > nowTop );
			if ( barrages.length > 0 ) {
				//当前轨道有弹幕运行
				let arr = barrages.map(barrage => barrage.left + barrage.width);
				if ( !this.config.overlap || isVisible ) {
					//开启防重叠会取消多余弹幕显示
					if ( arr.length < len || len < 0 ) {
						len = arr.length;
						top = nowTop;
					}
				}
				//获取当前轨道最右的弹幕
				let max = Math.max(...arr);
				//如果当前轨道还有空位则将弹幕放入当前轨道
				if ( max < this._w - 10 ) {
					top = nowTop;
					break;
				}
			} else {
				//当前轨道没有弹幕运行
				top = nowTop;
				break;
			}
		}
		return top;
	}
	//获取偏移量
	_getOffset() {
	    return parseFloat( ((this.config.speed / 70) + Math.random()).toFixed(1) );
	}
	//碰撞检测 速度计算
	_detectionBump (bar) {
		let nowTop = bar.top;
		let offset = bar.offset;
		//检测当前弹幕轨道有多少条弹幕
		let barrages =  this._barrages.filter(barrage => barrage.top < nowTop + bar.fontSize && barrage.fontSize + barrage.top > nowTop && bar.left != barrage.left);
		//检测当前弹幕轨道前方有多少条弹幕
		let headbarrages = barrages.filter(barrage => bar.left + bar.width >= barrage.left + barrage.width + 10 && barrage.left + barrage.width > 0);
		//检测当前弹幕轨道后方有多少条弹幕
		let footbarrages = barrages.filter(barrage => bar.left + bar.width + 10 < barrage.left && barrage.left + barrage.width > 0);
		if ( headbarrages.length > 0 ) {
			let arr = headbarrages.map(barrage => barrage.left + barrage.width);
			//取出离当前弹幕最近的那条弹幕
			let max = Math.max(...arr);
			let i = arr.indexOf(max);
			if ( bar.left > max + 20 ) {
				//如果离最近弹幕距离大于10，则加速
				offset = headbarrages[i].offset + 0.01;
			} else {
				//否则减速
				offset = headbarrages[i].offset - 0.01;
			}
		}
		if ( footbarrages.length > 0 ) {
			//前方一条弹幕都没有 且 未弹幕未完全显示 则加速
			if ( bar.left + bar.width > this._w && headbarrages.length == 0 ) {
				offset = bar.offset + 0.01;
			} else {
				let arr = footbarrages.map(barrage => barrage.left);
				//取出离当前弹幕最近的那条弹幕
				let min = Math.min(...arr);
				let i = arr.indexOf(min);
				//如果后方存在弹幕 且距离小于10则加速
				if ( min - (bar.left + bar.width) <= 20 ) {
					offset = bar.offset + 0.01;
				}
			}
		}
		if ( offset < 0.5 ) offset = 0.5;
		if ( offset > 3 ) offset = 3;
		return offset;
	}
	setBarrages (barrages) {
		this.barrages = barrages
	}
	add (barrage) {
		barrage.time = barrage.time || this.currentTime;
		this.barrages.push(barrage);
		if ( parseInt(barrage.time) == parseInt(this.currentTime) ) {
			let bar = this._getBarrage(barrage, true);
			bar.left = this._w - bar.width;
			this._barrages.push(bar);
		}
	}
	setConfig (config) {
		this.config = Object.assign({}, {
			duration: -1, // 弹幕动画的循环周期，-1 表示不循环播放
			speed: 150, // 弹幕的运动速度
			fontSize: 24, // 文字大小，单位：像素
			fontFamily: 'Microsoft Yahei', // 字体，默认值：微软雅黑
			opacity: 1, // 透明度，有效值 0-1
			defaultColor: '#fff', // 默认颜色，与 CSS 颜色属性一致
			lineHeight: 5,//行间距
			overlap: true,//开启防重叠 可能会导致部分弹幕不显示
			playbackRate: 1.0,//播放倍速
			initialTime: 0//初始时间
		}, this.config, config)
	}
	//刷新尺寸（当容器大小变化时调用此接口）
	refresh () {
		if ( this.canvas && this.container ) {
			this.canvas.width = this.container.offsetWidth;
			this.canvas.height = this.container.offsetHeight;
			this._w = this.container.offsetWidth;
			this._h = this.container.offsetHeight;
			let paused = this.paused;
			if ( !paused ) {
				this.play()
			}
		}
	}
	play () {
		if ( this.paused ) {
			this.paused = false;
			this._startTimer();
			this._render();
		}
	}
	pause () {
		if ( !this.paused ) {
			this.paused = true;
			this._clearTimer();
			this._cancelAnima();
		}
	}
	stop () {
		this._destroy();
	}
	seek(time) {
		this._barrages = [];
		this.currentTime = parseInt(time);
		this._clear();
		let paused = this.paused;
		if ( !paused ) {
			this.play()
		}
	}
}