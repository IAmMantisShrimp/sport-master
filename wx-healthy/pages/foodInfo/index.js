import * as echarts from '../../ec-canvas/echarts';
let data = [];
function initChart(canvas, width, height, dpr) {
    const chart = echarts.init(canvas, null, {
        width: width,
        height: height,
        devicePixelRatio: dpr // new
    });
    canvas.setChart(chart);
    let option = {
        legend: {
            orient: 'vertical',
            //图标设置样式
            icon: "circle",
            //位置
            bottom: 'bottom',
            //格式化名字
            formatter: function (name) {
                let arr = [];
                data.forEach(item => {
                    if (item.name == name) {
                        arr.push(name + item.value + 'g');
                    }
                });
                return arr.join('');
			}
        },
        series: [
            {
                type: 'pie',
                radius: '50%',
                data: data,
                label: {
                    normal: {
                        show: true,
                        formatter: '{b}{d}%',//模板变量有 {a}、{b}、{c}、{d}，分别表示系列名，数据名，数据值，百分比。{d}数据会根据value值计算百分比
                    }
                }
            }
        ]
    };

    chart.setOption(option);
    return chart;
}


Page({

    /**
     * 页面的初始数据
     */
    data: {
        food: {},
        //轮播图列表
        imageList: [],
        //饼图
        ec: {
            onInit: initChart
        },

    },

    /**
     * 查看全部营养元素
     * @param {*} event 
     */
    getFoodDetail() {
        wx.navigateTo({
            url: '/pages/foodInfo/foodDetail/index?food=' + JSON.stringify(this.data.food),
        });
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let food = JSON.parse(options.food);
        this.setData({
            food: food
        });
        data = [
            {value: food.fat, name: '脂肪'},
            {value: food.protein, name: '蛋白质'},
            {value: food.carbonWater, name: '碳水化合物'}
        ];
        if (food.imageUrls !== '' && food.imageUrls !== null) {
            let arr = [];
            let images = food.imageUrls.split(',');
            images.forEach(item => {
                arr.push('http://ajie.jierxm.com/' + item);
            });
            this.setData({
                imageList: arr,
            });
        } else {
            this.setData({
                imageList: ['http://ajie.jierxm.com/5b5eeb76d1864b7ca761da4770781a18.jpg'],
            });
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