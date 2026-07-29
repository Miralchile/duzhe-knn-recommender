<template>
	<view>
		<view class="tab-container">
			<tabControl style="top:-80px;" :current="current" :values="tabs" bgc="#fff" :fixed="true" :scrollFlag="true" :isEqually="false"
				@clickItem="onClickItem"></tabControl>
			<swiper class="swiper" style="height: 100%;" @change="scollSwiper" :current="current">
				<swiper-item v-for="(item, index) in tabs" :key="index">
					<scroll-view scroll-y="true" style="height: 100%;">{{ item }}</scroll-view>
				</swiper-item>
			</swiper>
		</view>
		<view class="show">
			<view v-show="currentIndex === 0" style="background-color: #F5F2F0;">
				<youScroll ref="scroll" @onPullDown="onPullDown">
					<view class="top2">
						<view class="card2" v-for="(item,index) in items" :key="index">
							<view v-if="item.type == 1" style="height: 100%;" @click="gotoVideo(item._id)">
								<view style="width: 100%;height: 65%;">
									<view v-if="!loadStatus[index]" class="loading-container">
										<i class="loading-icon"></i>
									</view>
									<image mode="aspectFit" v-show="loadStatus[index]" :src="item.coverImageUrlNew" @load="loadImage(index)" class="card2-img"></image>
									<u--text v-show="loadStatus[index]" color="white" :text="item.durationString" style="position: relative;left:240rpx;bottom: 85rpx;
							z-index: 5;height: 15%;width: 25%;background-color: black;opacity: 0.5;" align="center"></u--text>
								</view>
								<view>
									<u--text :text="item.title" style="position: relative; top:13rpx;left: 15rpx;"
										size="13"></u--text>
									<view style="position: relative;top: 28rpx;z-index: 5;">
										<uni-tag :circle="true" :text="item.category" size="small"
											style="color:#000000;border:0rpx;font-size: 10px;background-color: #ebebeb;margin-left: 15rpx;" />
										<uni-tag :circle="true" :text="item.channel" size="small"
											style="color:#000000;border:0rpx;font-size: 10px;background-color: #ebebeb;margin-left: 15rpx;" />
									</view>
								</view>
							</view>

							<view v-if="item.type == 2" style="height: 100%;" @click="gotoImage(item._id)">
								<view style="width: 100%;height: 65%;">
									<view v-if="!loadStatus[index]" class="loading-container">
										<i class="loading-icon"></i>
									</view>
									<image v-show="loadStatus[index]" :src="item.coverImageUrlNew" @load="loadImage(index)" class="card2-img"></image>
									<uni-tag v-show="loadStatus[index]" :circle="true" text="图文" type="primary" style="position: relative;left:255rpx;bottom: 88rpx;
							        z-index: 5;height: 15%;width: 25%;text-align: center;" size="small" />
								</view>
								<view>
									<u--text :text="item.title" style="position: relative; top:13rpx;left: 15rpx;"
										size="13"></u--text>
									<view style="position: relative;top: 28rpx;z-index: 5;">
										<uni-tag :circle="true" :text="item.category" size="small"
											style="color:#000000;border:0rpx;font-size: 10px;background-color: #ebebeb;margin-left: 15rpx;" />
										<uni-tag :circle="true" :text="item.channel" size="small"
											style="color:#000000;border:0rpx;font-size: 10px;background-color: #ebebeb;margin-left: 15rpx;" />
									</view>

								</view>
							</view>
						</view>
					</view>
				</youScroll>
			</view>
			
			
			<view v-show="currentIndex > 0" style="background-color: #F5F2F0;">
				<youScroll ref="scroll" @onPullDown="onPullDownOther">
					<view class="top2">
						<view class="card2" v-for="(item,index) in other" :key="index">
							<view v-if="item.type == 1" style="height: 100%;" @click="gotoVideo(item._id)">
								<view style="width: 100%;height: 65%;">
									<view v-if="!loadStatus[index]" class="loading-container">
										<i class="loading-icon"></i>
									</view>
									<image mode="aspectFit" v-show="loadStatus[index]" :src="item.coverImageUrlNew" @load="loadImage(index)" class="card2-img"></image>
									<u--text v-show="loadStatus[index]" color="white" :text="item.durationString" style="position: relative;left:240rpx;bottom: 85rpx;
							z-index: 5;height: 15%;width: 25%;background-color: black;opacity: 0.5;" align="center"></u--text>
								</view>
								<view>
									<u--text :text="item.title" style="position: relative; top:13rpx;left: 15rpx;"
										size="13"></u--text>
									<view style="position: relative;top: 28rpx;z-index: 5;">
										<uni-tag :circle="true" :text="item.category" size="small"
											style="color:#000000;border:0rpx;font-size: 10px;background-color: #ebebeb;margin-left: 15rpx;" />
										<uni-tag :circle="true" :text="item.channel" size="small"
											style="color:#000000;border:0rpx;font-size: 10px;background-color: #ebebeb;margin-left: 15rpx;" />
									</view>
								</view>
							</view>
			
							<view v-if="item.type == 2" style="height: 100%;" @click="gotoImage(item._id)">
								<view style="width: 100%;height: 65%;">
									<view v-if="!loadStatus[index]" class="loading-container">
										<i class="loading-icon"></i>
									</view>
									<image v-show="loadStatus[index]" :src="item.coverImageUrlNew" @load="loadImage(index)" class="card2-img"></image>
									<uni-tag v-show="loadStatus[index]" :circle="true" text="图文" type="primary" style="position: relative;left:255rpx;bottom: 88rpx;
							        z-index: 5;height: 15%;width: 25%;text-align: center;" size="small" />
								</view>
								<view>
									<u--text :text="item.title" style="position: relative; top:13rpx;left: 15rpx;"
										size="13"></u--text>
									<view style="position: relative;top: 28rpx;z-index: 5;">
										<uni-tag :circle="true" :text="item.category" size="small"
											style="color:#000000;border:0rpx;font-size: 10px;background-color: #ebebeb;margin-left: 15rpx;" />
										<uni-tag :circle="true" :text="item.channel" size="small"
											style="color:#000000;border:0rpx;font-size: 10px;background-color: #ebebeb;margin-left: 15rpx;" />
									</view>
			
								</view>
							</view>
						</view>
					</view>
				</youScroll>
			</view>
		</view>
		<view v-if="currentIndex!=0" class="add" @click="gotoChannelDiscussionIndex">讨论</view>
	</view>
</template>

<script>
	import tabControl from '@/components/tabControl/tabControl.vue';
	import youScroll from '@/components/you-scroll.vue'
	import {
		searchByCategory
	} from '@/api/search.js'
	import {
		getChannelsByCategoryName
	} from '@/api/channel.js'
	export default {
		components: {
			tabControl,
			youScroll
		},
		data() {
			return {
				categoryName: '',
				tabs: ['全部'],
				current: 0,
				currentIndex: 0,
				items: [],
				other: [],
				pageNo: 1,
				loadStatus: [false, false, false, false, false, false, false, false],
			}
		},
		onLoad(option) {
			uni.setNavigationBarTitle({
				title:'分类-'+option.name
			})
			this.categoryName = option.name;
			getChannelsByCategoryName(option.name).then((res) => {
				for(let i of res){
					this.tabs.push(i.channelName);
				}
				console.log("tabs",this.tabs)
				searchByCategory(this.pageNo,this.categoryName,'').then((res) => {
					this.items = res;
					for (var i = 0; i < this.items.length; i++) {
						var duration = this.items[i].duration
						var seconds = Math.floor(duration / 1000);
						var hours = Math.floor(seconds / 3600); // 计算小时数
						seconds %= 3600; // 去掉小时数后剩余的部分转换为分钟
						var minutes = Math.floor(seconds / 60); // 计算分钟数
						var seconds = seconds % 60; // 去掉分钟数后剩余的秒数
						hours = ("0" + hours).slice(-2);
						minutes = ("0" + minutes).slice(-2);
						seconds = ("0" + seconds).slice(-2);
						this.items[i].durationString = minutes + ':' + seconds
						this.items[i].coverImageUrlNew = this.items[i].coverImage + "?t=" + (new Date().getTime())
					}
				});
			});

		},
		methods: {
			loadImage(index) {
				this.$set(this.loadStatus, index, true);
			},
			onPullDown(done) {
				setTimeout(() => {
					this.pageNo = this.pageNo + 1
					searchByCategory(this.pageNo,this.categoryName,'').then((res) => {
						for (var i = 0; i < 7; i++) {
							this.$set(this.loadStatus, i, false);
						}
						this.items = res;
						for (var i = 0; i < this.items.length; i++) {
							var duration = this.items[i].duration
							var seconds = Math.floor(duration / 1000);
							var hours = Math.floor(seconds / 3600); // 计算小时数
							seconds %= 3600; // 去掉小时数后剩余的部分转换为分钟
							var minutes = Math.floor(seconds / 60); // 计算分钟数
							var seconds = seconds % 60; // 去掉分钟数后剩余的秒数
							hours = ("0" + hours).slice(-2);
							minutes = ("0" + minutes).slice(-2);
							seconds = ("0" + seconds).slice(-2);
							this.items[i].durationString = minutes + ':' + seconds
							this.items[i].coverImageUrlNew = this.items[i].coverImage + "?t=" + (new Date().getTime())
						}
					});
					done();
				}, 1000)
			},
			loadDate(){
				searchByCategory(this.pageNo,this.categoryName,this.tabs[this.current]).then((res) => {
					for (var i = 0; i < 7; i++) {
						this.$set(this.loadStatus, i, false);
					}
					this.other = res;
					for (var i = 0; i < this.other.length; i++) {
						var duration = this.other[i].duration
						var seconds = Math.floor(duration / 1000);
						var hours = Math.floor(seconds / 3600); // 计算小时数
						seconds %= 3600; // 去掉小时数后剩余的部分转换为分钟
						var minutes = Math.floor(seconds / 60); // 计算分钟数
						var seconds = seconds % 60; // 去掉分钟数后剩余的秒数
						hours = ("0" + hours).slice(-2);
						minutes = ("0" + minutes).slice(-2);
						seconds = ("0" + seconds).slice(-2);
						this.other[i].durationString = minutes + ':' + seconds
						this.other[i].coverImageUrlNew = this.other[i].coverImage + "?t=" + (new Date().getTime())
					}
				});
			},
			onPullDownOther(done) {
				setTimeout(() => {
					this.pageNo = this.pageNo + 1
					searchByCategory(this.pageNo,this.categoryName,this.tabs[this.current]).then((res) => {
						for (var i = 0; i < 7; i++) {
							this.$set(this.loadStatus, i, false);
						}
						this.other = res;
						for (var i = 0; i < this.other.length; i++) {
							var duration = this.other[i].duration
							var seconds = Math.floor(duration / 1000);
							var hours = Math.floor(seconds / 3600); // 计算小时数
							seconds %= 3600; // 去掉小时数后剩余的部分转换为分钟
							var minutes = Math.floor(seconds / 60); // 计算分钟数
							var seconds = seconds % 60; // 去掉分钟数后剩余的秒数
							hours = ("0" + hours).slice(-2);
							minutes = ("0" + minutes).slice(-2);
							seconds = ("0" + seconds).slice(-2);
							this.other[i].durationString = minutes + ':' + seconds
							this.other[i].coverImageUrlNew = this.other[i].coverImage + "?t=" + (new Date().getTime())
						}
					});
					done();
				}, 1000)
			},
			gotoVideo(id) {
				uni.navigateTo({
					url: '/pages/works/videoPlay?id=' + id
				});
			},
			gotoImage(id) {
				uni.navigateTo({
					url: '/pages/works/imageContext?id=' + id
				});
			},
			onClickItem(val) {
				this.current = val.currentIndex;
				if(val.currentIndex>0){
					this.pageNo = 1;
					this.loadDate()
				}
			},
			scollSwiper(e) {
				this.current = e.target.current;
				console.log(this.current)
				this.currentIndex = e.target.current;
			},
			gotoSearch() {
				uni.navigateTo({
					url: '/pages/index/search',
					success() {
					},
					fail(err) {
						console.log(err)
					}
				});

			},
			gotoSetting() {
				uni.navigateTo({
					url: '../my/setting/setting'
				})
			},
			gotoChannelDiscussionIndex() {
				console.log(this.categoryName)
				console.log(this.tabs[this.current])
				uni.navigateTo({
					url: '/pages/discussion/channelDiscussionIndex?channel=' + this.tabs[this.current]
				})
			}

		},
	};
</script>
<style scoped>
	page {
		background-color: #F5F2F0;
	}
	
	.loading-container {
		width: 100%;
		height: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
		background-color: #e8e8e8;
	}

	.loading-icon {
		border-radius: 50%;
		width: 40px;
		height: 40px;
		border: 4px solid #ccc;
		border-top-color: #2196f3;
		animation: spin 1s ease-in-out infinite;
	}

	@keyframes spin {
		to {
			transform: rotate(360deg);
		}
	}

	.nav {
		position: sticky;
		top: 0;
		z-index: 3;
	}

	.avatar {
		border-radius: 50%;
		margin: 40rpx 20rpx 0rpx 20rpx;
		width: 90rpx;
		height: 90rpx;
	}

	.title {
		font-size: 22px;
		margin-top: 5rpx;
		margin-left: 30rpx;
	}

	.right-icon {
		font-size: 22px;
	}

	.input {
		float: left;
		height: 30px;
		margin-top: 10px;
		width: 500rpx;
		border-radius: 30px;
		background-color: #f6f6f6;
	}

	.border {
		width: 100%;
		position: absolute;
		z-index: 3;
		bottom: 0;
		left: 0;
		right: 0;
		height: 1px;
		content: '';
		-webkit-transform: scaleY(.5);
		transform: scaleY(.5);
		background-color: #e5e5e5
	}

	.show {
		margin-top: 3px;
	}

	.tab-container {
		width: 100%;
		margin-top: 65rpx;
	}

	.top2 {
		background-color: #F5F2F0;
		height: 100%;
		display: flex;
		flex-direction: row;
		flex-wrap: wrap;
		width: 100%;
		align-items: center;
		/* margin-top: 20rpx; */
		border-radius: 5px;
	}

	.img {
		background-color: #FFFFFF;
		width: 100rpx;
		height: 100rpx;
	}

	.card2 {
		background-color: #FFFFFF;
		margin-left: 3%;
		margin-top: 20rpx;
		display: flex;
		flex-direction: column;
		width: 45.5%;
		height: 300rpx;
		border-radius: 5px;
	}

	.card-img {
		margin-left: 20rpx;
		margin-right: 30rpx;
		width: 95%;
		height: 90%;
		border-radius: 5px;
	}

	.card2-img {
		border-radius: 5px 5px 0 0;
		margin-bottom: 30rpx;
		width: 100%;
		height: 100%;
		background-color: #e8e8e8;
	}

	.card-text {
		color: #BBBBBB;
		font-size: 26rpx;
		width: 95%;

	}

	.card-text-img {

		background-color: #FFFFFF;
		width: 30rpx;
		height: 30rpx;
	}

	.card-lable {
		height: 30rpx;
		margin-bottom: 8rpx;

	}

	.card-lable-text {
		font-size: 26rpx;
		color: #FF502E;
		margin-bottom: 20rpx;
	}

	.swiper {
		margin-left: 15rpx;
		border-radius: 10rpx;
		margin-top: 40rpx;
		height: 300rpx;
		width: 95%;

	}

	.img-swiper {
		width: 100%;
		height: 300rpx;
		align-items: center;
		border-radius: 10rpx
	}
	
	.add {
		padding-left: 10rpx;
		padding-top: 20rpx;
		position: fixed;
		background-color: #1296db;
		color: white;
		right: 20px;
		width: 80rpx;
		height: 80rpx;
		border-radius: 50%;
		overflow: hidden;
		bottom: 200rpx;
	}
</style>
