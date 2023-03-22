import Vue from "vue";
import "./plugins/axios";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "./plugins/element.js";
import "@/util/ajax";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";
// 引入全局样式
import "@/assets/global.css";
// 引入自定义指令
import "@/util/user";
// 引入iconfont的css文件
import "@/assets/iconfont/iconfont.css";
// 引入echarts
import * as echarts from "echarts";
Vue.prototype.$echarts = echarts;

Vue.config.productionTip = false;
// 全局属性
Vue.prototype.domainName = "http://rkww6usm1.hn-bkt.clouddn.com/";
// 全局方法
// Vue.prototype.updateUserInfo =
// 引入全局样式
new Vue({
  ElementUI,
  router,
  store,
  render: (h) => h(App),
}).$mount("#app");
