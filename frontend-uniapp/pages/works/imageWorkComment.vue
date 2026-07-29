<template>
	<view class="overlay">
		<!-- 是评论的大部分呀 -->
		<view class="hb-comment">
			<!-- 阅读数-end -->
			<!-- 阅读数下边那条线-start -->
			<view class="seg_line_box">
				<view class="seg_line"></view>
				<view class="seg_dot"></view>
				<view class="seg_line"></view>
			</view>
			<!-- 阅读数下边那条线-end -->
			<!-- 评论主体-start -->
			<view class="comment-list" v-if="items.length != 0">
				<!-- 评论主体-顶部数量及发表评论按钮-start -->
				<view class="comment-num">
					<view>共 {{items.length}} 条评论</view>
					<view class="add-btn">
						<button type="primary" size="mini" @click="commentInput">发表评论</button>
					</view>
				</view>
				<!-- 评论主体-顶部数量及发表评论按钮-end -->
				<!-- 评论列表-start -->
				<view class="comment-box" v-for="(item, index) in items" :key="index">
					<view class="comment-box-item">
						<view>
							<image src="http://first-bucket20201002.oss-cn-hangzhou.aliyuncs.com/img/img/avatar/me.jpg"
							 mode="aspectFill" class="avatar"></image>
						</view>
						<view class="comment-main">
							<!-- 父评论体-start -->
							<view class="comment-main-top">
								<view class="nick-name-box">
									<view class="comLogo com1" v-if="index == 0">沙发</view>
									<view class="comLogo com2" v-if="index == 1">板凳</view>
									<view class="comLogo com3" v-if="index == 2">地板</view>
									<view class="comLogo com4" v-if="index > 2">{{index + 1}}楼</view>
									<view class="nick-name">{{item.commentator}}</view>

								</view>
							</view>
							<view class="comment-main-content">
								{{item.content.length > 60 ? item.content.slice(0, 59) : item.content}}
								<span v-if="item.content.length > 60">
									{{item.hasShowMore ? item.content.slice(59) : '...'}}
									<span class="foot-btn" @click="showMore(item.id)">
										{{item.hasShowMore ? '收起' : '展开'}}
									</span>
								</span>
							</view>
							<view class="comment-main-foot">
								<u--text mode="date" :text="item.createdBy" size="13" type="信息"></u--text>
								<view class="foot-btn" @click="reply(item.commentator)">回复</view>
								<view class="foot-btn" @click="confirmDelete(item._id)">删除</view>
							</view>
							<!-- 父评论体-end -->
						</view>
					</view>
				</view>
				<!-- 评论列表-end -->
			</view>
			<!-- 评论主体-end -->
			<!-- 无评论-start -->
			<view class="comment-none" v-else>
				暂无评论，<span @click="commentInput" style="color: #007AFF;">抢沙发</span>
			</view>
			<!-- 无评论-end -->
			<!-- 新增评论-start -->
			<view class="comment-submit-box" v-if="submit" @click="closeInput">
				<!-- 下边的click.stop.prevent用于让上边的click不传下去，以防点到下边的空白处触发closeInput方法 -->
				<view class="comment-add" @click.stop.prevent="stopPrevent" :style="'bottom:' + KeyboardHeight + 'px'">
					<view class="comment-submit">
						<view class="btn-click cancel" @click="closeInput">取消</view>
						<view>
							<view class="replayTag" v-show="showTag">
								<view>回复在 {{commentForm.content}} 的评论下</view>
								<view @click="tagClose" class="replyTagClose">×</view>
							</view>
						</view>
						<view>
							<view class="btn-click" @click="submitComment(message)">发布</view>
						</view>
					</view>
					<textarea class="textarea" v-model="commentForm.content" placeholder="请输入内容"
						:adjust-position="false" :show-confirm-bar="false" 
						maxlength="800"></textarea>
				</view>
			</view>
			<!-- 新增评论-end -->
		</view>
	</view>

</template>

<script>
	import {
		getImageWorkCommentById,
		addImageWorkCommentById
	} from '@/api/imageContext.js'
	export default {
		props: ['message'],
		data() {
			return{
				items: [],
				commentForm: {
					"id": undefined,
					"content": undefined,
					"commentator": undefined
				},
				commentNum : 0,
				submit: false,
				showTag:false
			}
		},
		mounted(){
			getImageWorkCommentById(this.message).then((res) => {
				console.log(res.commentList)
				this.items = res.commentList;
				this.commentForm.id=this.message;
				this.commentNum = "共"+this.items.length+"条评论"
			});
		},
		methods:{
			// 输入评论
			commentInput(e) {
				// TODO 调起键盘方法
				this.submit = true;
			},
			closeInput() {
				this.submit = false;
				this.commentForm.content = '';
				this.commentForm.commentator = null;
				this.showTag = false
			},
			reply(commentator) {
				this.commentForm.content = '@' + commentator + ' ';
				this.showTag = true;
				this.commentInput();
			},
			tagClose() {
				this.showTag = false;
				this.commentForm.content = '';
			},
			submitComment(id) {
				addImageWorkCommentById(this.commentForm).finally(() => {
					getImageWorkCommentById(id).then((res) => {
						console.log(res)
						this.items = res.commentList;
						this.commentNum = "共"+this.items.length+"条评论"
					}).finally(()=>{
						this.submit = false
						this.commentForm.content = '';
					})
			    });
			}
		}
	}
</script>

<style scoped>
	.overlay {
		position: relative;
		width: 100%;
		height: 50%;
		background-color: rgba(255, 255, 255, 1);
		overflow: auto;
		z-index: 20;
	},
	.title {
		margin: auto;
		text-align: center;
		font-size: 30px;
	}
	
	.author {
		font-size: 15px;
		margin-left: 200px;
	}
	
	.content {
		font-size: 20px;
		font-family: "宋体";
		line-height: 1.5;
	}
	
	.seg_line_box {
		display: flex;
		height: 5rpx;
		justify-content: space-between;
		margin: 5rpx 0;
	}
	
	.seg_line {
		width: 45%;
		border-bottom: 10rpx solid #e1e1e1;
	}
	
	.seg_dot {
		width: 8%;
		border-bottom: 10rpx dotted #dbdbdb;
	}
	
	.hb-comment {
		padding: 10rpx;
	}
	
	.top-read {
		font-size: 28rpx;
		padding-left: 10rpx;
		color: #999999;
	}
	
	.seg_line_box {
		display: flex;
		height: 5rpx;
		justify-content: space-between;
		margin: 5rpx 0;
	}
	
	.seg_line {
		width: 45%;
		border-bottom: 1rpx solid #e1e1e1;
	}
	
	.seg_dot {
		width: 8%;
		border-bottom: 5rpx dotted #dbdbdb;
	}
	
	.comment-num {
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding: 20rpx 0;
	}
	
	.comment-box {
		padding: 10rpx 0;
	}
	
	.comment-box-item {
		display: flex;
	}
	
	.comment-main {
		padding-left: 20rpx;
	}
	
	.comment-main-top {
		width: 600rpx;
		padding-top: 6rpx;
		display: flex;
		justify-content: space-between;
	}
	
	.sub-comment-main-top {
		width: 510rpx;
		padding-top: 6rpx;
		display: flex;
		justify-content: space-between;
	}
	
	.avatar {
		width: 70rpx;
		height: 70rpx;
		border-radius: 50%;
	}
	
	.nick-name-box {
		display: flex;
		align-items: center;
	}
	
	.comLogo {
		margin-right: 18rpx;
		font-size: 22rpx;
		border-radius: 10rpx;
		padding: 5rpx 15rpx;
		color: #FFFFFF;
	}
	
	.com1 {
		background-color: #d218b1;
	}
	
	.com2 {
		background-color: #f19c0b;
	}
	
	.com3 {
		background-color: #c8da85;
	}
	
	.com4 {
		background-color: #bfd0da;
	}
	
	.nick-name {
		color: #2d8cf0;
	}
	
	.isLike {
		font-size: 28rpx;
		padding-right: 10rpx;
		color: #2d8cf0;
	}
	
	.notLike {
		font-size: 28rpx;
		padding-right: 10rpx;
		color: #999999;
	}
	
	.comment-main-content {
		padding: 10rpx 10rpx 10rpx 0;
	}
	
	.comment-main-foot {
		display: flex;
		font-size: 22rpx;
	}
	
	.replayTag {
		color: #909399;
		margin-bottom: 5px;
		border: 1px solid #c8c9cc;
		background-color: #f4f4f5;
		border-radius: 3px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		font-size: 16rpx;
		padding: 5px 10px;
	}
	
	.replyTagClose {
		font-size: 20px;
		line-height: 12px;
		padding: 0 0 2px 5px;
	}
	
	.foot-btn {
		padding-left: 10rpx;
		color: #007AFF;
	}
	
	.comment-sub-box {
		padding: 20rpx 0;
	}
	
	.comment-sub-item {
		display: flex;
	}
	
	.comment-none {
		padding: 20rpx;
		width: 100%;
		text-align: center;
		color: #999999;
	}
	
	.comment-submit-box {
		position: fixed;
		display: flex;
		align-items: flex-end;
		z-index: 9900;
		left: 0;
		top: var(--window-top);
		bottom: 0;
		background-color: rgba($color: #000000, $alpha: 0.5);
		width: 100%;
	}
	
	.comment-add {
		position: fixed;
		background-color: #FFFFFF;
		width: 100%;
		padding: 5rpx;
		border: 1px solid #ddd;
		transition: .3s;
		-webkit-transition: .3s;
	}
	
	.btn-click {
		color: #007AFF;
		font-size: 28rpx;
		padding: 10rpx;
	}
	
	.cancel {
		color: #606266;
	}
	
	.textarea {
		height: 100px;
		padding: 16rpx;
		width: 100%;
	}
	
	.comment-submit {
		padding: 5rpx 20rpx 0 20rpx;
		border-bottom: 1px dashed #ddd;
		width: calc(100% - 40rpx);
		display: flex;
		justify-content: space-between;
		align-items: center;
	}
</style>