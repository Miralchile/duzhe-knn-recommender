<template>
	<view class="contact page-pagination" :class="size=='large'?'size-large':size=='small'?'size-small':''">
		<view class="page-con" :class="!showBorder&&'no-border'">
			<view class="page-scroll">
				<!-- #ifdef APP-NVUE -->
				<scroll-view scroll-x="true">
					<!-- #endif -->
					<view class="page-scroll-child">
						<view v-for="(item, index) in setLayout" :key="index">
							<template v-if="item == 'total'">
								<!-- 总条数 -->
								<text class="page-total pc-page">共 {{total}} 条</text>
							</template>
							<template v-if="item == 'first'">
								<!-- 首页 -->
								<view class="pag-btn start" v-if="!numAround" :style="{color}"
									:class="[nowPage==1&&'btn-ban',btnText&&'btn-text']" @click="clickStart">
									<!-- #ifdef APP-NVUE -->
									<text>首页</text>
									<!-- #endif -->
									<!-- #ifndef APP-NVUE -->
									<text v-if="btnText">首页</text>
									<text v-else class="icon icon-left1"></text>
									<!-- #endif -->
								</view>
							</template>
							<template v-if="item == 'prev'">
								<!-- 上页 -->
								<view class="pag-btn prev" :style="{color}" :class="[nowPage==1&&'btn-ban',btnText&&'btn-text']"
									@click="clickPrev">
									<!-- #ifdef APP-NVUE -->
									<text>上页</text>
									<!-- #endif -->
									<!-- #ifndef APP-NVUE -->
									<text v-if="btnText">上页</text>
									<text v-else class="icon icon-left"></text>
									<!-- #endif -->
								</view>
							</template>
							<template v-if="item == 'page'">
								<!-- 简单模式 -->
								<view v-if="mode=='simple'" class="page-num simple">
									<text :style="{color}" style="margin-right: 5px;">{{nowPage}}</text>/ {{pageNum}}
								</view>
								<!-- 复杂模式 -->
								<view class="page-num" v-else>
									<template v-if="pageNum <= showPageSize">
										<view v-for="page in pageNumArr" :key="page" class="pag-btn" :class="page==nowPage&&'active'"
											:style="{color, backgroundColor:(page==nowPage?color:''), borderColor:(page==nowPage?color:'')}"
											@click="clickPage(page)">{{page}}</view>
									</template>
									<template v-else>
										<!-- 第一页页码 -->
										<view class="pag-btn" :class="nowPage==1&&'active'"
											:style="{color, backgroundColor:(nowPage==1?color:''), borderColor:(nowPage==1?color:'')}"
											v-if="numAround" @click="clickPage(1)">1</view>
										<!-- 左侧省略号 -->
										<view class="pag-btn ellipsis-btn"
											v-if="(forceEllipses && getFirstPage != 1) || (numAround && getFirstPage != 2)">
											<text class="icon icon-more"></text>
										</view>
										<template v-for="(p, i) in showPageSize">
											<template v-if="i<showPageSize-2">
												<!-- 中间页码 -->
												<view :key="i" class="pag-btn" :class="(getFirstPage+i)==nowPage&&'active'"
													:style="{color, backgroundColor:((getFirstPage+i)==nowPage?color:''),borderColor:((getFirstPage+i)==nowPage?color:'')}"
													@click="clickPage(getFirstPage+i)">
													{{getFirstPage+i}}
												</view>
											</template>
											<template v-else>
												<!-- 若显示省略号，则页码只显示 showPageSize-2 页 -->
												<view :key="i" v-if="!forceEllipses && !numAround" class="pag-btn"
													:class="(getFirstPage+i)==nowPage&&'active'"
													:style="{color, backgroundColor:((getFirstPage+i)==nowPage?color:''), borderColor:((getFirstPage+i)==nowPage?color:'')}"
													@click="clickPage(getFirstPage+i)">
													{{getFirstPage+i}}
												</view>
											</template>
										</template>
										<!-- 右侧省略号 -->
										<view class="pag-btn ellipsis-btn"
											v-if="(forceEllipses && getFirstPage<pageNum-(showPageSize-3)) || (numAround && getFirstPage < pageNum-(showPageSize-2))">
											<text class="icon icon-more"></text>
										</view>
										<!-- 最后一页页码 -->
										<view class="pag-btn last-page" :class="nowPage==pageNum&&'active'"
											:style="{color,backgroundColor:(nowPage==pageNum?color:''),borderColor:(nowPage==pageNum?color:'')}"
											v-if="numAround && getFirstPage < pageNum-(showPageSize-3)" @click="clickPage(pageNum)">{{pageNum}}</view>
									</template>
								</view>
							</template>
							<template v-if="item == 'next'">
								<!-- 下页 -->
								<view class="pag-btn next" :style="{color}" :class="[pageNum<=nowPage&&'btn-ban',btnText&&'btn-text']"
									@click="clickNext">
									<!-- #ifdef APP-NVUE -->
									<text>下页</text>
									<!-- #endif -->
									<!-- #ifndef APP-NVUE -->
									<text v-if="btnText">下页</text>
									<text v-else class="icon icon-right"></text>
									<!-- #endif -->
								</view>
							</template>
							<template v-if="item == 'last'">
								<!-- 尾页 -->
								<view class="pag-btn end" v-if="!numAround" :style="{color}"
									:class="[pageNum<=nowPage&&'btn-ban',btnText&&'btn-text']" @click="clickEnd">
									<!-- #ifdef APP-NVUE -->
									<text>尾页</text>
									<!-- #endif -->
									<!-- #ifndef APP-NVUE -->
									<text v-if="btnText">尾页</text>
									<text v-else class="icon icon-right1"></text>
									<!-- #endif -->
								</view>
							</template>
							<template v-if="item == 'limit'">
								<!-- 选择每页条数 -->
								<view class="page-limit pc-page">
									<picker v-model="pageSizeIndex" :range="newPageSizes" @change="changePageSize">
										<text class="page-input">{{newPageSizes[pageSizeIndex]}}</text>
										<text class="icon icon-left"></text>
									</picker>
								</view>
							</template>
							<template v-if="item == 'jumper'">
								<!-- 前往页数 -->
								<view class="page-go pc-page">前往第
									<template v-if="trigger=='blur'">
										<input class="page-input" v-model="inputPage" @blur="goPage" @input="onInput" />页
									</template>
									<template v-else>
										<input class="page-input" v-model="inputPage" @input="onInput" />页
										<text class="page-input-btn" @click="goPage">跳转</text>
									</template>
								</view>
							</template>
						</view>
						<!-- #ifndef MP-WEIXIN -->
						<view class="custom-info pc-page">
							<slot />
						</view>
						<!-- #endif -->
					</view>
					<!-- #ifdef APP-NVUE -->
				</scroll-view>
				<!-- #endif -->
			</view>
		</view>
		<view class="mobile-page">
			<view class="custom-info">
				<slot />
			</view>
			<view v-for="(item, index) in mLayout" :key="index">
				<template v-if="item == 'total'">
					<!-- 总条数 -->
					<view class="page-total">共 {{total}} 条</view>
				</template>
				<template v-if="item == 'limit'">
					<!-- 选择每页条数 -->
					<view class="page-limit">
						<picker v-model="pageSizeIndex" :range="newPageSizes" @change="changePageSize">
							<text class="page-input">{{newPageSizes[pageSizeIndex]}}</text>
							<text class="icon icon-left"></text>
						</picker>
					</view>
				</template>
				<template v-if="item == 'jumper'">
					<!-- 前往页数 -->
					<view class="page-go">前往第
						<template v-if="trigger=='blur'">
							<input class="page-input" v-model="inputPage" @blur="goPage" @input="onInput" />页
						</template>
						<template v-else>
							<input class="page-input" v-model="inputPage" @input="onInput" />页
							<text class="page-input-btn" @click="goPage">跳转</text>
						</template>
					</view>
				</template>
			</view>
		</view>
	</view>
</template>
<script>
	/**
	 * @property {Number} total 数据总条数，默认0
	 * @property {Number} pageSize 每页显示条数，默认10
	 * @property {Number} currentPage 当前页，默认1
	 * @property {String} mode = [multi|simple] 显示模式，默认multi
	 *  @value multi 标准模式(默认)
	 *  @value simple 简单模式，只显示当前页/总页数
	 * @property {Boolean} btnText = [true|false] 上页、下页、首页和尾页按钮是否显示汉字，默认false使用箭头表示
	 * @property {Boolean} numAround = [true|false] 是否用页码中的第一页和最后一页表示首尾页，将不在显示首尾页，默认false
	 * @property {Boolean} forceEllipses = [true|false] 是否显示省略号，默认false
	 * @property {Number} showPageSize 中间页码显示的个数，默认5
	 * @property {String} size = [large|normal|small] 按钮大小，默认normal
	 *  @value large 大号按钮
	 *  @value normal 普通按钮(默认)
	 *  @value small 小型按钮
	 * @property {String} color 自定义页码颜色，默认#1989FA
	 * @property {Boolean} showBorder = [true|false] 是否显示页码边框，默认true
	 * @property {String} layout 自定义分页排版，及是否显示 总条数(total)、首页(first)、上页(prev)、页码(page)、下页(next)、尾页(last)、每页条数选项(limit)和页码跳转(jumper)，默认顺序和排版为：first,prev,page,next,last
	 * @property {String} trigger = [blur|click] 页码跳转触发方式，默认blur
	 *  @value blur 失去焦点时触发(默认)
	 *  @value click 点击跳转按钮触发
	 * @property {Array} pageSizes 每页条数选项预设值，默认[10, 20, 50]
	 * @event {Function} change 当页码改变时触发事件，返回参数e={currentPage,type}，详情参数见文档
	 * @event {Function} sizeChange 每页条数(pageSize)改变时会触发，返回参数e={pageSize}
	 */
	export default {
		name: 'PagePagination',
		props: {
			total: { // 总条数
				type: Number,
				default: 0
			},
			pageSize: { // 每页条数
				type: Number,
				default: 10
			},
			currentPage: { // 当前页
				type: Number,
				default: 1
			},
			showPageSize: { // 显示的页码个数
				type: Number,
				default: 5
			},
			mode: { //显示模式
				type: String,
				default: "multi"
			},
			forceEllipses: { // 是否显示省略号
				type: Boolean,
				default: false
			},
			btnText: { // 是否显示汉字
				type: Boolean,
				default: false
			},
			numAround: { // 是否用页码表示首尾页
				type: Boolean,
				default: false
			},
			size: { // 按钮大小
				type: String,
				default: "normal"
			},
			color: { // 页码颜色
				type: String,
				default: "#1989FA"
			},
			trigger: { // 跳转页码触发方式
				type: String,
				default: "blur"
			},
			layout: { // 自定义布局
				type: String,
				default: "first,prev,page,next,last"
			},
			showBorder: { // 是否显示页码边框
				type: Boolean,
				default: true
			},
			pageSizes: { // 每页条数选项预设值
				type: Array,
				default () {
					return [10, 20, 50]
				}
			}
		},
		data() {
			return {
				nowPage: this.currentPage, //当前页
				inputPage: this.currentPage, //input输入框绑定值
				mLayout: [],
				nowPageSize: this.pageSize, // 当前每页条数
				pageSizeIndex: this.pageSizes.indexOf(this.pageSize),
				newPageSizes: this.pageSizes.map(item => item + "条/页"),
			}
		},
		watch: {
			currentPage(val) {
				this.nowPage = val > this.pageNum ? this.pageNum : val;
				this.inputPage = val > this.pageNum ? this.pageNum : val;
				if (val == 1) {
					this.nowPage = 1;
					this.inputPage = 1;
				}
			},
			pageSize(val) {
				this.nowPageSize = val;
				this.pageSizeIndex = this.pageSizes.indexOf(val);
				this.pageSizeEvent();
			}
		},
		computed: {
			// 总页数
			pageNum() {
				return Math.ceil(this.total / this.nowPageSize)
			},
			pageNumArr() { // 解决uni-app某些版本中，存在v-for中循环数字时从0开始
				var pageNumArr = [];
				for (let i = 0; i < this.pageNum; i++) {
					pageNumArr.push(i + 1);
				}
				return pageNumArr;
			},
			// 计算显示的第一个页码
			getFirstPage() {
				let firstPage = 0;
				let a = Math.floor((this.showPageSize - 1) / 2);
				let b = Math.floor(this.showPageSize / 2) - 1;
				if (this.nowPage <= a) {
					if (!this.numAround) firstPage = 1;
					else firstPage = 2;
				} else if (this.nowPage >= this.pageNum - b) {
					if (this.forceEllipses || this.numAround) firstPage = this.pageNum - this.showPageSize + 3;
					else firstPage = this.pageNum - this.showPageSize + 1;
				} else {
					if (this.forceEllipses || this.numAround) firstPage = this.nowPage - a + 1;
					else firstPage = this.nowPage - a;
				}
				return firstPage;
			},
			// 设置布局
			setLayout() {
				let layoutArr = this.layout.replace(/\s/g, "").split(",");
				let arr = ["total", "limit", "jumper"];
				this.mLayout = layoutArr.filter(item => arr.includes(item));
				return layoutArr;
			}
		},
		methods: {
			//选择页码
			clickPage(page) {
				if (this.nowPage != page) {
					this.nowPage = page;
					this.change(page, 'page');
				}
			},
			//点击上页
			clickPrev() {
				if (this.nowPage > 1) {
					let nowPage = this.nowPage - 1;
					this.nowPage = nowPage;
					this.change(nowPage, 'prev');
				}
			},
			//点击下页
			clickNext() {
				if (this.nowPage < this.pageNum) {
					let nowPage = this.nowPage + 1;
					this.nowPage = nowPage;
					this.change(nowPage, 'next');
				}
			},
			//点击首页
			clickStart() {
				if (this.nowPage != 1) {
					let nowPage = 1;
					this.nowPage = nowPage;
					this.change(nowPage, 'homePage');
				}
			},
			//点击尾页
			clickEnd() {
				if (this.nowPage != this.pageNum) {
					let nowPage = this.pageNum;
					this.nowPage = nowPage;
					this.change(nowPage, 'endPage');
				}
			},
			//页码改变时触发
			change(nowPage, type) {
				this.inputPage = nowPage;
				this.$emit('change', nowPage, type);
			},
			onInput(e) {
				let val = parseInt(e.target.value.replace(/[^\d]/g, ''));
				setTimeout(_ => {
					this.inputPage = val ? (val > this.pageNum ? this.pageNum : val) : '';
				}, 10)
			},
			goPage() {
				this.nowPage = parseInt(this.inputPage ? this.inputPage : '1');
				this.inputPage = parseInt(this.inputPage ? this.inputPage : '1');
				this.change(parseInt(this.inputPage), 'goPage');
			},
			changePageSize(e) { // 更改每页条数
				this.pageSizeIndex = e.detail.value;
				this.nowPageSize = this.pageSizes[e.detail.value];
				this.pageSizeEvent();
			},
			pageSizeEvent() {
				if (this.nowPage > this.pageNum) {
					this.nowPage = this.pageNum;
					this.inputPage = this.pageNum;
				}
				this.$emit('sizeChange', this.nowPageSize);
			}
		}
	}
</script>

<style lang="scss">
	@import 'icon/iconfont.css';

	$font-size: 14px;
	$font-color: #606266;

	@mixin page-input {
		font-size: $font-size;
		color: $font-color;
		height: 28px;
		line-height: 28px;
		border: 1px solid #DCDFE6;
		background-color: #FFF;
		border-radius: 3px;
		text-align: center;
		/* #ifndef APP-NVUE */
		cursor: not-allowed;
		/* #endif */
	}

	.page-pagination {
		width: 100%;
		/* #ifdef APP-NVUE */
		width: 750rpx;
		/* #endif */

		.page-con {
			width: 100%;
			display: flex;
			justify-content: center;
			/* #ifdef APP-NVUE */
			width: 750rpx;
			/* #endif */

			.page-scroll {
				width: 100%;
				display: flex;
				/* #ifndef APP-NVUE */
				overflow-x: auto;
				overflow-y: hidden;
				/* #endif */
				/* #ifdef APP-NVUE */
				width: 750rpx;
				/* #endif */

				.page-scroll-child {
					display: flex;
					flex-direction: row;
					align-items: center;
					height: 32px;
					line-height: 30px;
					/* #ifndef APP-NVUE */
					margin: 0 auto;
					padding: 0 0 0 10px;

					/* #endif */

					.pag-btn {
						background-color: #FFF;
						color: #1989FA;
						font-size: 12px;
						border: 1px solid #EBEEF5;
						padding: 0 11px;
						/* #ifndef APP-NVUE */
						cursor: pointer;
						white-space: nowrap;
						/* #endif */
						margin-left: -1px;
						display: flex;
					}

					.page-num {
						display: flex;
						flex-direction: row;
					}

					.pag-btn.active {
						background-color: #1989FA;
						color: #FFFFFF !important;
					}

					.pag-btn.btn-ban {
						color: #C0C4CC !important;
						/* #ifndef APP-NVUE */
						cursor: not-allowed;
						/* #endif */
					}

					.ellipsis-btn {
						padding: 0 1px;
						color: #999999;
						/* #ifndef APP-NVUE */
						cursor: auto;
						/* #endif */
					}

					.pag-btn.start,
					.pag-btn.prev,
					.pag-btn.next,
					.pag-btn.end,
					.pag-btn .icon-more {
						padding: 0 7px;
					}

					.icon-left,
					.icon-right {
						font-size: 13px;
					}

					.icon-left1,
					.icon-right1 {
						font-size: 17px;
					}

					// 简单模式
					.page-num.simple {
						padding: 0 40px;
						line-height: 34px;
						color: #303133;
						font-size: 16px;
					}
				}
			}
		}

		// 无边框模式
		.no-border .page-scroll .page-scroll-child {
			.pag-btn {
				/* #ifndef APP-NVUE */
				border: none;
				background: none;
				/* #endif */
				/* #ifdef APP-NVUE */
				border: rgba(255, 255, 255, 0);
				background-color: rgba(255, 255, 255, 0);
				/* #endif */
			}

			.pag-btn.active {
				border-radius: 4px;
			}

			.pag-btn.start,
			.pag-btn.prev,
			.pag-btn.next,
			.pag-btn.end {
				background-color: #F0F0F0 !important;
				border-radius: 4px;
				margin: 0 3px;
			}

			.page-num {
				margin: 0 3px;
			}
		}

		// 移动端
		.mobile-page {
			display: flex;
			justify-content: center;
			flex-direction: row;
			align-items: center;
			line-height: 28px;
			margin-top: 5px;
		}

		/* 附加功能样式 */
		// 总条数
		.page-total {
			display: flex;
			color: $font-color;
			font-size: $font-size;
			margin: 0 5px;
			/* #ifndef APP-NVUE */
			white-space: nowrap;
			/* #endif */
		}

		// 每页条数
		.page-limit {
			margin: 0 5px;
			display: flex;
			align-items: center;
			position: relative;
			/* #ifndef APP-NVUE */
			white-space: nowrap;
			/* #endif */

			.page-input {
				@include page-input;
				/* #ifndef APP-NVUE */
				outline: none;
				/* #endif */
				padding: 0 18px 0 6px;
				display: flex;
			}

			.icon-left {
				position: absolute;
				right: 3px;
				top: 1px;
				color: #C0C4CC;
				font-size: 12px;
				font-weight: 100;
				transform: rotate(-90deg);
			}
		}

		// 跳转页码
		.page-go {
			color: $font-color;
			font-size: $font-size;
			display: flex;
			flex-direction: row;
			align-items: center;
			margin: 0 5px;
			/* #ifndef APP-NVUE */
			white-space: nowrap;
			/* #endif */

			.page-input {
				@include page-input;
				width: 46px;
				margin: 0 5px;
			}

			.page-input-btn {
				@include page-input;
				padding: 0 10px;
				margin-left: 5px;
			}
		}

		// 自定义页码信息
		.custom-info {
			margin: 0 10px;
			color: $font-color;
			font-size: $font-size;
		}
	}

	// 大号模式
	.page-pagination.size-large .page-con .page-scroll-child {
		height: 40px;
		line-height: 38px;

		.pag-btn {
			padding: 0 15px;
			font-size: 15px;
		}

		.pag-btn.start,
		.pag-btn.prev,
		.pag-btn.next,
		.pag-btn.end,
		.ellipsis-btn {
			padding: 0 12px;
		}

		.pag-btn.btn-text {
			padding: 0 6px;
		}

		.icon-left,
		.icon-right {
			font-size: 15px;
		}

		.icon-left1,
		.icon-right1 {
			font-size: 19px;
		}
	}

	// 小号按钮
	.page-pagination.size-small .page-con .page-scroll-child {
		height: 24px;
		line-height: 22px;

		.pag-btn {
			padding: 0 7px;
		}

		.pag-btn.start,
		.pag-btn.prev,
		.pag-btn.next,
		.pag-btn.end {
			padding: 0 5px;
		}

		.ellipsis-btn {
			padding: 0 5px;
		}

		.icon-left,
		.icon-right {
			font-size: 12px;
		}

		.icon-left1,
		.icon-right1 {
			font-size: 15px;
		}
	}

	/* #ifndef APP-NVUE */
	.page-pagination .pc-page {
		display: none;
	}

	.page-pagination .mobile-page {
		display: flex;
	}

	@media screen and (min-width: 450px) {
		.page-pagination .pc-page {
			display: block;
		}

		.page-pagination .mobile-page {
			display: none;
		}
	}

	/* #endif */
</style>