import Dialog from '@vant/weapp/dialog/dialog';
const util = require('../../utils/util');
const app = getApp();
import * as echarts from '../../ec-canvas/echarts';

Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		//运动数据
		ec: {
			// 延迟加载
			lazyLoad: true 
		},
		data1: [],
		data2: [],
		data3: [],
		data4: [],
		//今日步数
		step: 0,
	},

	/**
	 * 生命周期函数--监听页面加载
	 */
	onLoad: function (options) {
		wx.login({
			success: (res) => {
				console.log(res);
				wx.request({
					url: app.globalData.url + '/user/runLogin?code=' + res.code,
					method: 'GET',
					header: {
						"Authorization": wx.getStorageSync('token')
					},
					success: (response) => {
						//调用运动接口
						wx.getWeRunData({
							success: (runRes) => {
								wx.request({
									url: app.globalData.url + '/calories/decrypt',
									method: "POST",
									header: {
										"Authorization": wx.getStorageSync('token')
									},
									data: {
										encryptedData: runRes.encryptedData,
										iv: runRes.iv,
										sessionKey: response.data.data
									},
									success: (res) => {
										console.log(res);
										let week1 = res.data.data.week1;
										let week2 = res.data.data.week2;
										let week3 = res.data.data.week3;
										let week4 = res.data.data.week4;
										//迭代运动信息
										for (let i=0; i<7; i++) {
											//装本周数据
											if (week1[i] === null || week1[i] === undefined) {
												this.data.data1.push(null);
											} else {
												this.data.data1.push(week1[i].runStep);
											}
											this.data.data2.push(week2[i].runStep);
											this.data.data3.push(week3[i].runStep);
											this.data.data4.push(week4[i].runStep);
										}
										this.setData({
											step: week1[week1.length-1].runStep
										});
										this.lineComponent = this.selectComponent('#mychart-dom-line');
										this.initChart();
									},
									fail: (err) => {
										console.log(err);
									}
								});
							},
							fail: (err) => {
								Dialog.alert({
									title: '标题',
									message: '开发者未开通微信运动，请关注“微信运动”公众号后重试',
									confirmButtonText: '知道了'
								});
							}
						});
					}
				});
			}
		});
	},

	initChart: function() {
		this.lineComponent.init((canvas, width, height, dpr) => {
			const chart = echarts.init(canvas, null, {
				width: width,
				height: height,
				devicePixelRatio: dpr // new
			});
			canvas.setChart(chart);
			chart.setOption(this.getOption());
			return chart;
		});
	},

	/**
	 * 折线图数据
	 */
	getOption() {
		return {
			//点击时显示信息
			tooltip: {
				trigger: 'axis'
			},
			legend: {
				data: ['本周', '第二周', '第三周', '第四周']
			},
			//格子数据 刻度
			grid: {
				left: '3%',
				right: '4%',
				bottom: '3%',
				containLabel: true
			},
			//X轴显示日期
			xAxis: {
				type: 'category',
				boundaryGap: false,
				data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日'],
			},
			//Y轴数据
			yAxis: {
				type: 'value'
			},
			series: [
				{
					name: '本周',
					type: 'line',
					data: this.data.data1
				},
				{
					name: '第二周',
					type: 'line',
					data: this.data.data2
				},
				{
					name: '第三周',
					type: 'line',
					data: this.data.data3
				},
				{
					name: '第四周',
					type: 'line',
					data: this.data.data4
				}
			]
		};
	},

	/**
	 * 生命周期函数--监听页面初次渲染完成
	 */
	onReady: function () {
		//setTimeout(this.getOption(), 500);
	},

	/**
	 * 生命周期函数--监听页面显示
	 */
	onShow: function () {
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