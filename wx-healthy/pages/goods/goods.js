const app = getApp();
Page({

	/**
	 * 页面的初始数据
	 */
	data: {
		//获取的数据列表，以追加的形式添加进去
		goodsList: [],
		queryInfo: {
			//当前请求数据是第几页
			pageNumber: 1,
			//每页数据条数                          
			pageSize: 10
		},
		//上拉时是否继续请求数据，即是否还有更多数据
		hasMoreData: true,
	},

	/**
	 * 请求分页数据
	 * @param {*} e 
	 */
	findPage: function(message) {
		console.log(this.data.queryInfo);
		//在当前页面显示导航条加载动画
		wx.showNavigationBarLoading();
		//显示 loading 提示框
		wx.showLoading({
		  	title: message,
		});
		wx.request({
			url: app.globalData.url + '/goods/findPage',
			data: JSON.stringify(this.data.queryInfo),
			header: {
				'Authorization': wx.getStorageSync('token'),
			},
			method: 'POST',
			success: (res) => {
				console.log(res);
				let temp = this.data.goodsList;//res.data.data.rows
				let result = res.data.data.rows;
				if (result.length > 0) {
					wx.hideNavigationBarLoading();
					wx.hideLoading();
					if (this.data.queryInfo.pageNumber == 1) {
						temp = [];
					}
					if (result.length < this.data.queryInfo.pageSize) {
						this.setData({
							goodsList: temp.concat(result),
							hasMoreData: false
						});
					} else {
						this.setData({
							goodsList: temp.concat(result),
							hasMoreData: true,
							queryInfo: {
								pageNumber: this.data.queryInfo.pageNumber + 1,
								pageSize: 10
							}
						});
					}
				}
				console.log(res);
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
		this.findPage('正在加载数据...');
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
		this.data.queryInfo.pageNumber = 1;
		this.findPage('正在刷新数据');
	},

	/**
	 * 页面上拉触底事件的处理函数
	 */
	onReachBottom: function () {
		if (this.data.hasMoreData) {
			this.findPage('加载更多数据');
		}
	},

	/**
	 * 用户点击右上角分享
	 */
	onShareAppMessage: function () {

	}
})