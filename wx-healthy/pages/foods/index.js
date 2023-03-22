const app = getApp();

let id = 0;

Page({

    /**
     * 页面的初始数据
     */
    data: {
        queryInfo: {
			//当前请求数据是第几页
			pageNumber: 1,
			//每页数据条数                          
			pageSize: 10,
			//食物分类
			queryString: '',
        },
        dataList: [],
        //上拉时是否继续请求数据，即是否还有更多数据
        hasMoreData: true,
        //展示搜索框
        searchShow: false,
        //是否展示食物列表
        foodsShow: false,
    },

    /**
	 * 请求分页数据
	 * @param {*} e 
	 */
	findPage: function(message, url) {
        console.log(this.data.queryInfo);
		//在当前页面显示导航条加载动画
		wx.showNavigationBarLoading();
		//显示 loading 提示框
		wx.showLoading({
		  	title: message,
        });
        wx.request({
            url: app.globalData.url + '/food/' + url,
            data: JSON.stringify(this.data.queryInfo),
            method: 'POST',
            header: {
                'Authorization': wx.getStorageSync('token'),
            },
            success: (res) => {
                let temp = this.data.dataList;
                let result = res.data.data.rows;
                console.log(result);
                if (result.length > 0) {
                    wx.hideNavigationBarLoading();
                    wx.hideLoading();
                    if (this.data.queryInfo.pageNumber == 1) {
                        temp = [];
                    }
                    if (result.length < this.data.queryInfo.pageSize) {
                        this.setData({
                            dataList: temp.concat(result),
                            hasMoreData: false
                        });
                    } else {
                        this.setData({
                            dataList: temp.concat(result),
                            hasMoreData: true,
                            queryInfo: {
                                pageNumber: this.data.queryInfo.pageNumber + 1,
                                pageSize: 10,
                                queryString: id
                            }
                        });
                    }
                } else {
                    wx.hideNavigationBarLoading();
                    wx.hideLoading();
                    this.setData({
                        hasMoreData: false
                    });
                    //在当前页面显示导航条加载动画
                    wx.showNavigationBarLoading();
                    //显示 loading 提示框
                    wx.showLoading({
                        title: '到底了！',
                    });
                    setTimeout(() => {
                        wx.hideNavigationBarLoading();
                        wx.hideLoading();
                    }, 1000);
                    
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
     * 点击食物卡片
     * 跳转食物统计页面
     */
    getFoodInfo(event) {
        let food = event.target.dataset.food;
        //将对象序列化做参数传递
        wx.navigateTo({
            url: '/pages/foodInfo/index?food=' + JSON.stringify(food),
        });
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        if (options.id) {
            id = options.id;
            this.setData({
                queryInfo: {
                    pageNumber: 1,
                    pageSize: 10,
                    queryString: id
                },
                foodsShow: true
            });
            this.findPage('正在加载数据...', 'typeId');
        } else {
            //显示搜索框
            this.setData({
                searchShow: true
            });
        }
    },

    onChange(e) {
        this.setData({
            queryInfo: {
                pageNumber: 1,
                pageSize: 5,
                queryString: e.detail
            },
            foodsShow: true
        });
        this.findPage('搜索中...', 'findPage');
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
		this.findPage('正在刷新数据', 'typeId');
    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom: function () {
        if (this.data.hasMoreData) {
			this.findPage('加载更多数据', 'typeId');
		}
    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function () {

    }
})