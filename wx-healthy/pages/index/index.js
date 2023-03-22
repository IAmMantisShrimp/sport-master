const app = getApp();
import Toast from '@vant/weapp/toast/toast';
Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		userInfo: {
			nickName: '',
			sex: 0,
			address: '',
			avatarUrl: '',
		},
		phoneNumber: '',
		msgCode: '',
		smsText: '发送验证码',
		//是否可以点击
		disabled: false,
		token: null,
		//用户点击tabbar
		active: 0,
		//食物类型列表
		foodList: [],
	},

	getPhoneNumber: function (e) {
		this.setData({
			phoneNumber: e.detail
		});
	},

	getCode: function (e) {
		this.data.msgCode = e.detail;
	},

	/**
	 * 发送验证码
	 */
	sendSms: function (e) {
		//手机号正则验证
		let test = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
		if (this.data.phoneNumber == '') return Toast.fail('请填写手机号');
		if (!test.test(this.data.phoneNumber)) return Toast.fail('手机号不正确');
		wx.request({
			url: app.globalData.url + '/user/sendSms?phoneNumber=' + this.data.phoneNumber,
			method: "GET",
			success: (res) => {
				Toast.success(res.data.message);
				//定时器 60秒后重新发送
				let times = 60;
				let i = setInterval(() => {
					if (times == 0) {
						this.setData({
							disabled: false,
							smsText: "发送验证码",
						});
						clearInterval(i);
					} else {
						this.setData({
							smsText: "重新发送(" + times-- + ")",
							disabled: true,
						});
					}
				}, 1000);
			},
			fail: (err) => {
				console.log(err);
			}
		});
	},

	/**
	 * 登录
	 */
	login() {
		//手机号正则验证
		let test = /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/;
		if (this.data.phoneNumber == '') return Toast.fail('请填写手机号');
		if (this.data.msgCode == '') return Toast.fail('请填写验证码');
		if (!test.test(this.data.phoneNumber)) return Toast.fail('手机号不正确');
		wx.getUserProfile({
			desc: '用于完善会员资料',
			lang: 'zh_CN',
			success: (res) => {
				//拿到用户信息
				let user = res.userInfo;
				//昵称
				let nickName = user.nickName;
				//性别
				let sex = user.gender;
				//地址
				let address = user.country + user.province + user.city;
				//头像
				let avatarUrl = user.avatarUrl;
				//用户信息
				this.data.userInfo = {
					nickName: nickName,
					sex: sex,
					address: address,
					avatarUrl: avatarUrl
				};
				//获取到用户信息后登录
				wx.login({
					success: (res) => {
						if (res.code) {
							//使用请求码发送请求
							wx.request({
								url: app.globalData.url + '/user/wxlogin',
								data: {
									code: res.code,
									userInfo: this.data.userInfo,
									phoneNumber: this.data.phoneNumber,
									msgCode: this.data.msgCode
								},
								method: 'POST',
								success: (loginRes) => {
									if (!loginRes.data.flag) return Toast.fail(loginRes.data.message);
									this.setData({
										token: loginRes.data.data.tokenHead + loginRes.data.data.token
									});
									wx.setStorageSync('token', loginRes.data.data.tokenHead + loginRes.data.data.token);
									wx.setStorageSync('userInfo', this.data.userInfo);
									this.getFoodType();
									this.getTabBar().init();
								},
								fail: (err) => {
									console.log(err);
								}
							});

						} else {
							console.log('登录失败 --> ' + res.errMsg);
						}
					}
				});
			}
		});
	},

	/**
	 * 点击食物分类查询对应的食物
	 * @param {long} id 
	 */
	toFood(event) {
		let id = event.target.dataset.id;
		wx.navigateTo({
			url: '/pages/foods/index?id=' + id,
		});
	},

	/**
	 * 点击食物搜索
	 */
	onChange(e) {
		//跳转到食物列表界面
		wx.navigateTo({
			url: '/pages/foods/index',
		});
	},

	/**
	 * 获取食物类型
	 */
	getFoodType() {
		//页面加载获取食物类型数据
		wx.showNavigationBarLoading();
		//显示 loading 提示框
		wx.showLoading({
			title: '食物类型加载中...',
		});
		wx.request({
			url: app.globalData.url + '/food/typeAll',
			header: {
				'Authorization': wx.getStorageSync('token'),
			},
			method: 'GET',
			success: (res) => {
				if (res.statusCode == 401) {
					wx.clearStorageSync();
					this.getTabBar().init();
				} else {
					console.log(res);
					this.setData({
						foodList: res.data.data
					});
					wx.hideNavigationBarLoading();
					wx.hideLoading();
				}
			},
			fail: (err) => {
				wx.hideNavigationBarLoading();
				wx.hideLoading();
				console.log(err);
			}
		});
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function (options) {
		let token = wx.getStorageSync('token');
		if (token) {
			this.setData({
				token: token
			});
			this.getFoodType();
		}
	},

	/**
	 * 生命周期函数--监听页面初次渲染完成
	 */
	onReady: function () {

	},

	/**
	 * 生命周期函数--监听页面显示
	 */
	onShow: function () {
		let token = wx.getStorageSync('token');
		this.setData({
			token: token
		});
		this.getTabBar().init();
	},

	/**
	 * 生命周期函数--监听页面隐藏
	 */
	onHide: function () {

	},

	/**
	 * 生命周期函数--监听页面卸载
	 */
	onUnload: function () {

	},

	/**
	 * 页面相关事件处理函数--监听用户下拉动作
	 */
	onPullDownRefresh: function () {

	},

	/**
	 * 页面上拉触底事件的处理函数
	 */
	onReachBottom: function () {

	},

	/**
	 * 用户点击右上角分享
	 */
	onShareAppMessage: function () {

	}
})