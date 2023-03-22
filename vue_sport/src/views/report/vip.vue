<!--<template>-->
<!--  <div>-->
<!--    &lt;!&ndash; 面包屑导航 &ndash;&gt;-->
<!--    <el-select-->
<!--      v-model="type"-->
<!--      @change="getByYear"-->
<!--      placeholder="可以选择查询的年份"-->
<!--    >-->
<!--      <el-option-->
<!--        v-for="item in options"-->
<!--        :key="item.type"-->
<!--        :label="item.label"-->
<!--        :value="item.type"-->
<!--      />-->
<!--    </el-select>-->
<!--    <div id="echart" :style="{ width: '1450px', height: '650px' }"></div>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--export default {-->
<!--  data() {-->
<!--    return {-->
<!--      type: 1,-->
<!--      //下拉列表-->
<!--      options: [-->
<!--        { label: "半年前至今", type: 1 },-->
<!--        { label: "一年前至今", type: 2 },-->
<!--        { label: "三年前至今", type: 3 },-->
<!--      ],-->
<!--      //统计图作为属性 后面需要侦测变化-->
<!--      echarts_option: {-->
<!--        //标题，每个图表最多仅有一个标题控件，每个标题控件可设主副标题。-->
<!--        title: {-->
<!--          show: true,-->
<!--          text: "Vip用户统计", //标题文本-->
<!--          subtext: "横坐标代表日期，纵坐标代表用户注册数量", //副标题-->
<!--          x: "center", //标题居中-->
<!--          textStyle: {-->
<!--            //标题样式-->
<!--            fontSize: 45,-->
<!--            fontWeight: "bolder",-->
<!--            color: "#333",-->
<!--          },-->
<!--        },-->
<!--        //提示框，鼠标悬浮交互时的信息提示。-->
<!--        tooltip: {-->
<!--          show: true, //显示策略 显示-->
<!--          trigger: "axis", //触发类型-->
<!--        },-->
<!--        //横坐标-->
<!--        xAxis: {-->
<!--          type: "category", //类目类型-->
<!--          name: "月份",-->
<!--          data: [], //时间从后台取-->
<!--        },-->
<!--        //纵坐标-->
<!--        yAxis: {-->
<!--          type: "value", //数值类型-->
<!--          name: "会员数",-->
<!--          axisTick: {-->
<!--            show: false,-->
<!--          },-->
<!--        },-->
<!--        //驱动图表生成的数据内容数组，数组中每一项为一个系列的选项及数据，其中个别选项仅在部分图表类型中有效-->
<!--        series: [-->
<!--          {-->
<!--            //line折线图 bar柱状图 scatter散点图 k K线图 pie饼状图 radar雷达图 chord和弦图 force力导向布局图 map地图-->
<!--            type: "line",-->
<!--            clickable: false, //是否可以点击 默认不可以点击-->
<!--            name: "月增长数",-->
<!--            stack: "总量",-->
<!--            data: [], //折线图的数据从后台取-->
<!--          },-->
<!--        ],-->
<!--      },-->
<!--      echart: null,-->
<!--    };-->
<!--  },-->
<!--  //钩子函数 页面加载完后显示-->
<!--  mounted() {-->
<!--    this.init();-->
<!--  },-->
<!--  methods: {-->
<!--    //绘制折线图-->
<!--    init() {-->
<!--      // 基于准备好的dom，初始化echarts实例-->
<!--      this.echart = this.$echarts.init(document.getElementById("echart"));-->
<!--      //初始化默认查询半年前的数据-->
<!--      this.drawLine(this.echart, 1);-->
<!--    },-->
<!--    drawLine(echart, type) {-->
<!--      //初始化取近半年的数据-->
<!--      this.$ajax.post("/report/vip?type=" + type).then((res) => {-->
<!--        if (!res.data.flag) return this.$message.warning(res.data.message);-->
<!--        //迭代取值-->
<!--        for (let i in res.data.data) {-->
<!--          this.echarts_option.xAxis.data[i] = res.data.data[i].timeData;-->
<!--          this.echarts_option.series[0].data[i] = res.data.data[i].valueData;-->
<!--        }-->
<!--        echart.setOption(this.echarts_option, true);-->
<!--      });-->
<!--    },-->
<!--    getByYear(type) {-->
<!--      this.echarts_option.xAxis.data = [];-->
<!--      this.echarts_option.series[0].data = [];-->
<!--      this.drawLine(this.echart, type);-->
<!--    },-->
<!--  },-->
<!--};-->
<!--</script>-->

<!--<style lang="less" scoped>-->
<!--#echart {-->
<!--  color: white;-->
<!--}-->
<!--.el-select {-->
<!--  margin-top: -10px;-->
<!--  border: 1px solid #409eff;-->
<!--}-->
<!--.el-select-dropdown__list {-->
<!--  background-color: transparent !important;-->
<!--}-->

<!--.el-input__inner {-->
<!--  background-color: transparent !important;-->
<!--}-->

<!--.el-select-dropdown {-->
<!--  background-color: transparent !important;-->
<!--}-->
<!--</style>-->
