<template>
  <div class="video-wrap" :style="{ width: `${width}`, height: `${height}` }">
    <!-- video player -->
    <view @click="handleControls" >
      <video
        class="video-player"
        :id="videoId"
        :style="{ width: `${width}`, height: `${height}` }"
        webkit-playsinline="false"
        playsinline="false"
        x-webkit-airplay="allow"
        x5-video-player-type="h5-page"
        x5-video-orientation="portrait"
        :src="src"
        :initial-time="initialTime"
        :controls="isFullScreen"
        :show-center-play-btn="false"
        :autoplay="autoplay"
        :muted="isMute"
        :poster="poster"
        @play="videoPlay"
        @pause="videoPause"
        @ended="videoEnded"
        @timeupdate="videoTimeUp"
        @loadedmetadata="videoLoaded"
        @waiting="videoWaiting"
        @error="videoError"
        @fullscreenchange="onFullScreen"
      ></video>
    </view>
    <view class="abs-center">
      <!-- 中心播放按钮 -->
      <image
        src="../../static/play-btn.png"
        mode=""
        class="play-btn"
        v-if="!isVideoPlay && !showLoading"
        @click="videoPlayCenter"
      ></image>
      <!--  加载中 -->
      <div class="video-loading" v-if="showLoading">
        <image src="../../static/loading.png" mode="" class="loading-btn"></image>
      </div>
    </view>

    <!-- 控制条 -->
    <cover-view class="controls-bar">
      <!-- 播放 -->
      <view class="play-box" @click="videoPlayClick">
        <image src="../../static/pause.png" mode="" class="play-icon" v-if="isVideoPlay"></image>
        <image src="../../static/play.png" mode="" class="play-icon" v-else></image>
      </view>
      <!-- 声音 -->
      <cover-view class="mute-box" @click="videoMuteClick">
        <cover-image src="../../static/sound.png" mode="" class="mute-icon" v-if="!isMute"></cover-image>
        <cover-image src="../../static/mute.png" mode="" class="mute-icon" v-else></cover-image>
      </cover-view>
      <!-- 进度 -->
      <view class="progress">
        <view class="currtime">{{ currentTimeStr }}</view>
        <view class="slider-container">
          <slider
            @change="sliderChange"
            @changing="sliderChanging"
            :step="step"
            :value="sliderValue"
            backgroundColor="#9f9587"
            activeColor="#d6d2cc"
            block-color="#ff5500"
            block-size="12"
          />
        </view>
        <view class="druationTime">{{ druationTimeStr }}</view>
      </view>
      <!-- 倍速 -->
      <view class="play-rate" @click="videoPlayRate" v-if="showRate">{{ playbackRate }}x</view>
      <!-- 全屏 -->
      <view class="play-full" @click="videoFull">
        <image
          src="../../static/fullscreen.png"
          mode=""
          class="play-icon"
          @click="videoFull"
        ></image>
      </view>
      <!-- 倍速菜单 -->
      <!-- <ul class="play-rate-menu" :style="{ height: height }" v-if="showRateMenu">
        <li
          v-for="item in playbackRates"
          :key="item"
          :class="[{ activeRate: playbackRate === item }, 'play-rate-item']"
          @click="changePlayRate(item)"
        >
          {{ item }}x
        </li>
      </ul> -->
    </cover-view>
  </div>
</template>

<script>
export default {
  name: 'XVideo',
  props: {
    // 视频地址
    videoId: {
      type: String,
      default: 'myVideo'
    },
    // 视频地址
    src: {
      type: String
    },
    // 自动播放
    autoplay: {
      type: Boolean,
      default: true
    },
    // 封面
    poster: {
      type: String
    },
    // 步长，表示占比，取值必须大于0且为整数
    step: {
      type: Number,
      default: 1
    },
    // 初始播放进度，表示占比
    progress: {
      type: Number
    },
    // 视频宽度
    width: {
      type: String,
      default: '100%'
    },
    // 视频高度
    height: {
      type: String,
      default: '484rpx'
    },
    // 播放错误提示
    errorTip: {
      type: String,
      default: '播放错误'
    },
    // 是否展示倍速
    showRate: {
      type: Boolean,
      default: true
    },
    // 播放速率
    playbackRates: {
      type: Array,
      default: () => [0.8, 1, 1.25, 1.5, 2]
    }
  },
  data() {
    return {
      controls: false, //显示播放控件
      isVideoPlay: false, // 是否正在播放
      isMute: false, // 是否静音
      isVideoEnd: false, // 是否播放结束
      showPoster: true, // 是否显示视屏封面
      showLoading: false, // 加载中
      durationTime: 0, //总播放时间 时间戳
      currentTime: 0, //当前播放时间 时间戳
      druationTimeStr: '00:00', //总播放时间 字符串 计算后
      currentTimeStr: '00:00', //当前播放时间 字符串 计算后
      sliderValue: 0, //进度条的值 百分比
      isSeeked: true, //防止进度条拖拽失效
      playbackRate: 1, // 初始播放速率
      showRateMenu: false, //显示播放速率
      initialTime: 0, //初始播放时间
      isFullScreen: false, //是否全屏
      windowWidth: 0 //屏幕宽度
    }
  },
  mounted() {
    this.videoPlayer = uni.createVideoContext(this.videoId, this)
    // #ifdef H5
    // 处理微信不能自动播放
    if (this.autoplay) {
      document.addEventListener(
        'WeixinJSBridgeReady',
        () => {
          this.videoPlayer.play()
          this.isVideoPlay = true
        },
        false
      )
    }
    // #endif
  },
  onHide() {
    clearTimeout(this.timer)
  },
  methods: {
    // 自动隐藏控制条
    hideControls() {
      this.timer = setTimeout(() => {
        this.controls = false
      }, 5000)
    },
    // 点击显示/隐藏控制条
    handleControls() {
      this.controls = !this.controls
    },
    // 根据秒获取时间
    formatSeconds(second) {
      second = Math.round(second)
      var hh = parseInt(second / 3600)
      var mm = parseInt((second - hh * 3600) / 60)
      if (mm < 10) mm = '0' + mm
      var ss = parseInt((second - hh * 3600) % 60)
      if (ss < 10) ss = '0' + ss
      if (hh < 10) hh = hh == 0 ? '' : `0${hh}:`
      var length = hh + mm + ':' + ss
      if (second > 0) {
        return length
      } else {
        return '00:00'
      }
    },
    // 缓冲
    videoWaiting(e) {
      // 没有缓冲结束事件，所以在不播放的情况触发loading
      if (!this.isVideoPlay) this.showLoading = true
    },
    // 视频信息加载完成
    videoLoaded(e) {
      this.durationTime = e.detail.duration
      this.druationTimeStr = this.formatSeconds(this.durationTime)
      this.initialTime = this.progress * this.durationTime
      this.sliderValue = this.progress * 100
      this.videoPlayer.seek(this.initialTime)
      this.currentTimeStr = this.formatSeconds(this.initialTime)
      this.controls = true
      this.showLoading = false
      this.$emit('loadeddata', e.detail.duration)
    },
    // 播放进度更新,触发频率 250ms 一次
    videoTimeUp(e) {
      let sliderValue = Math.round((e.detail.currentTime / this.durationTime) * 100)
      if (this.isSeeked) {
        //判断拖拽完成后才触发更新，避免拖拽失效
        if (sliderValue % this.step === 0)
          // 比例值能被步进值整除
          this.sliderValue = sliderValue
      }
      this.currentTimeStr = this.formatSeconds(e.detail.currentTime)
      this.$emit('timeupdate', e.detail.currentTime)
    },
    //正在拖动slider
    sliderChanging(e) {
      this.isSeeked = false // 拖拽过程中，不允许更新进度条
      this.showLoading = true
      this.videoPlayer.pause()
      this.$emit('seeking')
    },
    // 拖动slider完成后
    sliderChange(e) {
      this.sliderValue = e.detail.value
      let currentTime = (this.sliderValue / 100) * this.durationTime
      this.showLoading = false
      this.isSeeked = true // 完成拖动后允许更新滚动条
      this.videoPlayer.seek(currentTime)
      if (this.sliderValue < 100) {
        this.videoPlayer.play()
      } else {
        this.videoPlayer.pause()
        this.videoEnded()
      }
      this.hideControls()
      this.$emit('seeked', this.sliderValue)
    },

    // 点击中心播放
    videoPlayCenter() {
      this.videoPlayer.play()
      this.$emit('play')
    },
    // 点击左下角播放/暂停,会触发原始播放/暂停事件,分开写，防止重复触发
    videoPlayClick() {
      if (this.isVideoPlay) {
        this.videoPlayer.pause()
      } else {
        this.videoPlayer.play()
        this.$emit('play')
      }
    },
    // 原始播放
    videoPlay() {
      if (this.pauseTimer) {
        clearTimeout(this.pauseTimer)
      }
      this.isVideoPlay = true
      this.isVideoEnd = false
      this.showLoading = false
      this.hideControls()
    },
    // 原始暂停
    videoPause() {
      // 处理播放结束和拖动会先触发暂停的问题
      this.pauseTimer = setTimeout(() => {
        if (this.isVideoEnd) return
        if (!this.isSeeked) return
        this.isVideoPlay = false
        this.$emit('pause')
      }, 100)
    },
    // 静音
    videoMuteClick() {
      this.isMute = !this.isMute
    },
    // 播放结束
    videoEnded() {
      // 重置状态
      this.isVideoPlay = false
      this.showPoster = true
      this.isVideoEnd = true
      this.$emit('ended')
    },
    // 播放错误
    videoError(e) {
      uni.showToast({
        title: this.errorTip,
        icon: 'none'
      })
      this.$emit('error')
    },
    // 显示倍速
    videoPlayRate() {
      this.showRateMenu = true
    },
    // 点击倍速
    changePlayRate(rate) {
      this.playbackRate = rate
      this.videoPlayer.playbackRate(rate)
      this.showRateMenu = false
      this.hideControls()
    },
    // 添加倍速按钮
    createPlayRateDOM() {
      const playRateDom = document.createElement('div')
      playRateDom.className = 'full-play-rate'
      playRateDom.innerText = `${this.playbackRate}x`
      playRateDom.onclick = () => {
        const playRateMenuDom = document.querySelector('.full-play-rate-menu')
        playRateMenuDom.style.display = 'block'
      }
      return playRateDom
    },
    // 添加倍速菜单
    createPlayRateMenuDom() {
      const playRateMenuDom = document.createElement('ul')
      playRateMenuDom.className = `play-rate-menu full-play-rate-menu`
      playRateMenuDom.style.height = this.windowWidth + 'px'
      playRateMenuDom.style.display = 'none'
      let liStr = ''
      this.playbackRates.forEach((item) => {
        liStr += `
					<li class="${this.playbackRate === item ? 'activeRate' : ''} play-rate-item full-play-rate-item">
						${item}x
					</li> 
					`
      })
      playRateMenuDom.innerHTML = liStr
      return playRateMenuDom
    },
    // 点击全屏
    videoFull() {
      this.videoPlayer.requestFullScreen()
      if (this.showRate) {
        this.windowWidth = uni.getSystemInfoSync().windowWidth
        const fullScreen = document.querySelector('.uni-video-fullscreen')

        // 添加倍速菜单
        const playRateMenuDom = document.querySelector('.full-play-rate-menu')
        const videoContainer = document.querySelector('.uni-video-container')
        if (!playRateMenuDom) {
          videoContainer.appendChild(this.createPlayRateMenuDom())

          const lis = document.querySelectorAll('.full-play-rate-item')
          lis.forEach((item) => {
            item.style.lineHeight = this.windowWidth / this.playbackRates.length + 'px'
            item.onclick = () => {
              let rate = +item.innerText.slice(0, -1)
              this.playbackRate = rate
              this.videoPlayer.playbackRate(rate)
              lis.forEach((li) => {
                li.classList.remove('activeRate')
                let rate = +li.innerText.slice(0, -1)
                if (this.playbackRate === rate) {
                  li.classList.add('activeRate')
                }
              })
              const playRateMenuDom = document.querySelector('.full-play-rate-menu')
              playRateMenuDom.style.display = 'none'
              const playRateDom = document.querySelector('.full-play-rate')
              playRateDom.innerText = `${this.playbackRate}x`
            }
          })
        }
        // 添加倍速按钮
        const playRateDom = document.querySelector('.full-play-rate')
        if (!playRateDom) {
          fullScreen.parentNode.insertBefore(this.createPlayRateDOM(), fullScreen)
        }
      }
    },
    // 监听原生全屏事件
    onFullScreen({ detail }) {
      if (detail.fullScreen) {
        this.isFullScreen = true
      } else {
        this.isFullScreen = false
      }
    }
  }
}
</script>
<style lang="scss" scoped>
.show {
  opacity: 1 !important;
}

.hide {
  opacity: 0 !important;
  pointer-events: none;
}

.abs-center {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}

::v-deep .full-play-rate {
  color: #cbcbcb;
  font-size: 32rpx;
  margin-left: 24rpx;
  margin-right: 24rpx;
}

::v-deep .full-play-rate-menu {
  position: fixed !important;
}

::v-deep .play-rate-menu {
  list-style-type: none;
  background-color: rgba(0, 0, 0, 0.7);
  width: 24%; 
  position: absolute;
  right: 0;
  bottom: 0;
  padding-left: 0;
  box-sizing: border-box;
}

::v-deep .play-rate-item {
  line-height: 70rpx;
  font-size: 28rpx;
  text-align: center;
  color: #fff;
}

::v-deep .activeRate {
  color: #5785e3;
}

.video-wrap {
  position: relative;

  .play-btn {
    width: 120rpx;
    height: 120rpx;
  }

  @keyframes run {
    from {
      transform: rotate(0deg);
    }

    to {
      transform: rotate(360deg);
    }
  }

  .loading-btn {
    width: 120rpx;
    height: 120rpx;
    animation: run 0.8s linear 0s infinite;
  }

  .controls-bar {
    width: 100%;
    padding: 1% 1% 1% 0;
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    z-index: 99;
    display: flex;
    align-items: center;
    background: rgba(25, 24, 24, 0.7);
    color: #fff;
    opacity: 0.9;
    transition: opacity 1s;
    height: 84rpx;

    .play-box,
    .mute-box,
    .play-full {
      width: 84rpx;
      height: 100%;
      display: flex;
      align-items: center;
      justify-content: center;
    }

    .mute-icon {
      width: 40rpx;
      height: 40rpx;
    }

    .play-icon {
      width: 34rpx;
      height: 34rpx;
    }

    .progress {
      display: flex;
      align-items: center;
      flex: 1;
      font-size: 24rpx;
      margin-left: 16rpx;

      .slider-container {
        flex: 1;
        max-width: 58%;
      }

      .currtime {
        color: #ffffff;
        width: 11%;
        height: 100%;
        text-align: center;
        margin-right: 20rpx;
      }

      .druationTime {
        color: #ffffff;
        width: 12%;
        height: 100%;
        text-align: center;
      }
    }

    .play-rate {
      font-size: 32rpx;
      margin-right: 24rpx;
    }

    .play-rate-menu {
      padding-top: 26rpx;
    }
		
    .play-rate-item:first-child {
      margin-top: 30rpx;
    }
  }
}
</style>
