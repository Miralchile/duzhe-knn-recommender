<template>
	<view class="u-page">
		<view class="u-demo-block">
			<view class="u-demo-block__content">
				<!-- 注意，如果需要兼容微信小程序，最好通过setRules方法设置rules规则 -->
				<u--form
					labelPosition="left"
					:model="model1"
					ref="form1"
				>
					<u-form-item
						label="昵称"
						prop="userInfo.nickName"
						borderBottom
						ref="item1"
					>
						<u--input
							v-model="model1.userInfo.nickName"
							border="none"
							placeholder="昵称,不允许包含空格"
						></u--input>
					</u-form-item>
					<u-form-item
						label="邮箱"
						prop="userInfo.email"
						borderBottom
						ref="item1"
					>
						<u--input
							v-model="model1.userInfo.email"
							border="none"
							:disabled="true"
						></u--input>
					</u-form-item>
					<u-form-item
						label="性别"
						prop="userInfo.sex"
						borderBottom
						@click="showSex = true; hideKeyboard()"
						ref="item1"
					>
						<u--input
							v-model="model1.userInfo.sex"
							disabled
							disabledColor="#ffffff"
							placeholder="请选择性别"
							border="none"
						></u--input>
						<u-icon
							slot="right"
							name="arrow-right"
						></u-icon>
					</u-form-item>
					<u-form-item
						label="简介"
						prop="userInfo.remark"
						borderBottom
						ref="item3"
					>
						<u--textarea
							placeholder="不超过140个字"
							v-model="model1.userInfo.remark"
							count
						></u--textarea>
					</u-form-item>
					
					
					<!-- <u-form-item
						label="生日"
						prop="userInfo.birthday"
						borderBottom
						@click="showBirthday = true; hideKeyboard()"
						ref="item1"
					>
						<u--input
							v-model="model1.userInfo.birthday"
							disabled
							disabledColor="#ffffff"
							placeholder="请选择生日"
							border="none"
						></u--input>
						<u-icon
							slot="right"
							name="arrow-right"
						></u-icon>
					</u-form-item> -->
				</u--form>
				<u-button
					class="submit-btn"
					text="提交"
					customStyle="margin-top: 50px"
					@click="submit"
				></u-button>
		
				<u-action-sheet
					:show="showSex"
					:actions="actions"
					title="请选择性别"
					@close="showSex = false"
					@select="sexSelect"
				>
				</u-action-sheet>
			
				<!-- <u-datetime-picker
					:show="showBirthday"
					:value="birthday"
					mode="date"
					closeOnClickOverlay
					@confirm="birthdayConfirm"
					@cancel="birthdayClose"
					@close="birthdayClose"
				></u-datetime-picker> -->
			</view>
		</view>
	</view>
</template>

<script>
	import {mapGetters} from 'vuex';
	import timeUtil from '@/utils/social/timeUtil.js';
	import userRequest from '@/api/social/user.js';
	import $store from '@/store/modules/social/test.js';
	export default {
		computed:{
			...mapGetters(['loginUserInfo'])
		},
		data() {
			return {
				value: '',
				//showBirthday: false,
				model1: {
					userInfo: {
						id: -1,
						nickName: '',
						sex: '',
						// birthday: '',
						email: '',
						remark: ''
					}
				},
				showSex: false,
				// birthday: Number(new Date()),
				actions: [{
						name: '男',
					},
					{
						name: '女',
					}
				],
				rules: {
					'userInfo.nickName': [{
						type: 'string',
						required: true,
						message: '请填写姓名',
						trigger: ['blur', 'change']
					}, {
						// 此为同步验证，可以直接返回true或者false，如果是异步验证，稍微不同，见下方说明
						validator: (rule, value, callback) => {
							// 调用uView自带的js验证规则，详见：https://www.uviewui.com/js/test.html
							return !uni.$u.test.contains(value, " ");
						},
						message: "不允许包含空格",
						// 触发器可以同时用blur和change，二者之间用英文逗号隔开
						trigger: ["change", "blur"],
					}],
					'userInfo.sex': {
						type: 'string',
						max: 1,
						required: true,
						message: '请选择男或女',
						trigger: ['blur', 'change']
					},
					'userInfo.remark': {
						type: 'string',
						max: 140,
						required: false,
						message: '不超过140个字',
						trigger: ['change']
					},
					// 'userInfo.birthday': {
					// 	type: 'string',
					// 	required: true,
					// 	message: '请选择生日',
					// 	trigger: ['change']
					// },
				},
		
			
			}
		},
		onReady() {
			// 如果需要兼容微信小程序，并且校验规则中含有方法等，只能通过setRules方法设置规则
			this.$refs.form1.setRules(this.rules);
		},
		created() {
			this.initForm();
		},
		methods: {
			async updatePost(){
				this.model1.userInfo.sex = this.model1.userInfo.sex == '女'?0:1;
				let res = await userRequest.update(this.model1.userInfo);
				$store.state.loginUserInfo = res.data;
				this.model1.userInfo.sex = this.model1.userInfo.sex == 0?'女':'男';
				//console.log(res.data)
				uni.setStorageSync('LOGIN_USER_INFO', res.data);
				uni.showToast({
					icon:'success',
					title:'修改成功！'
				})
				this.initForm();
			},
			initForm(){
				this.model1.userInfo.id = this.loginUserInfo.userId;
				this.model1.userInfo.nickName = this.loginUserInfo.nickName;
				this.model1.userInfo.email = this.loginUserInfo.email;
				this.model1.userInfo.remark = this.loginUserInfo.remark;
				this.model1.userInfo.sex = this.loginUserInfo.sex===1?'男':'女';
				//this.model1.userInfo.birthday = this.loginUserInfo.birthday.substring(0,11)
			},
			groupChange(n) {
				// console.log('groupChange', n);
			},
			radioChange(n) {
				// console.log('radioChange', n);
			},
			navigateBack() {
				uni.navigateBack()
			},
			sexSelect(e) {
				this.model1.userInfo.sex = e.name
				this.$refs.form1.validateField('userInfo.sex')
			},
			change(e) {
				// console.log(e);
			},
		
			// birthdayClose() {
			// 	this.showBirthday = false
			// 	this.$refs.form1.validateField('userInfo.birthday')
			// },
			// birthdayConfirm(e) {
			// 	this.showBirthday = false
			// 	this.model1.userInfo.birthday = uni.$u.timeFormat(e.value, 'yyyy-mm-dd')
			// 	this.$refs.form1.validateField('userInfo.birthday')
			// },
			submit() {
				// 如果有错误，会在catch中返回报错信息数组，校验通过则在then中返回true
				this.$refs.form1.validate().then(res => {
					this.updatePost();
				}).catch(errors => {
					uni.$u.toast('校验失败')
				})
			},
			hideKeyboard() {
				uni.hideKeyboard()
			}
		},
	}
</script>
<style lang="scss">
	page {
	  background-color: #fff;
	}
</style>
<style lang="scss" scoped>
.u-page{
	padding: 25rpx;
}
.submit-btn{
	background-color: royalblue;
	color: #fff;
}
</style>
