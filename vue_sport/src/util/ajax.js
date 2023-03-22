import Vue from "vue";
import axios from "axios";
import { Message } from "element-ui";
import store from "@/store/index";
import router from "@/router/index";
/**
 * 创建ajax实例,并设置请求超时时间
 * @type {AxiosInstance}
 */
const ajax = axios.create({
  timeout: 100000,
  //
  baseURL: "/back",
});
// 白名单
//请求拦截器
ajax.interceptors.request.use((res) => {
  console.log("请求信息: ", res.url);
  // 方式一:这里判断localStorage里面是否存在token，如果有则在请求头里面设置
  if (store.state.token != null && res.url !== "/back/user/login") {
    // res.headers.Authorization = getLocalStorage("jwtToken");
    // console.log(store.state.token);
    // 设置token认证
    res.headers.Authorization = store.state.token;
  }

  // 方式二,用sessionStorage
  // const token = sessionStorage.getItem("token");
  // if (token) {
  //   //  给请求头加上token
  //   res.headers["Authorization"] = token;
  // }
  // 放行,不让下面的方法拿不到参数
  return res;
});

// ajax响应拦截器,响应
ajax.interceptors.response.use(
  (res) => {
    console.log("响应信息:", res.data);
    if (res.data.flag) {
      Message({
        message: res.data.message,
        type: "success",
      });
    } else {
      Message.error(res.data.message);
    }
    // 放行,不让下面的方法拿不到参数
    return res;
  },
  (err) => {
    if (err.response.status === 400) {
      Message.error(err.response.data.message);
    } else if (err.response.status === 401) {
      Message.error("你尚未登录,请登录后操作.");
      // 清空缓存,重新登录
      sessionStorage.clear();
      router.push("/login");
    } else if (err.response.status === 403) {
      Message.error(err.response.data.message);
    } else if (err.response.status === 404) {
      Message.error("后端接口未找到!");
    } else if (err.response.status === 500) {
      Message.error("后端异常-->" + err.response.data.message);
    } else {
      Message.error("未知错误!");
    }
  }
);
// 挂载后,全局可以用$ajax对象
Vue.prototype.$ajax = ajax;

// 暴露ajax,让其他的js文件使用
export default ajax;
