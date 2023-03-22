import Vue from "vue";
import VueRouter from "vue-router";
import Home from "../views/Home.vue";
import Login from "@/views/login";
import store from "@/store/index";
import { Message } from "element-ui";
import ajax from "@/util/ajax";
import Welcome from "@/views/Welcome";
import User from "@/views/system/user";
import SysMenu from "@/views/system/menu";
import Roles from "@/views/system/roles";
import Permission from "@/views/system/permission";
import addUser from "@/views/system/user/add";
import deleteUser from "@/views/system/user/delete";
import testChart from "@/views/chart/test";
import food from "@/views/sport/food";
import counsel from "@/views/system/sport";
// 引入menu格式化方法
// import {formatMenu, generateRoute} from "@/util/initMenus";

Vue.use(VueRouter);

export const routes = [
  {
    path: "/",
    component: Home,
    children: [
      {
        path: "/home/login",
        component: Login,
      },
      {
        path: "/home/welcome",
        component: Welcome,
      },
      {
        path: "/system/user",
        component: User,
      },
      {
        path: "/system/role",
        component: Roles,
      },
      {
        path: "/system/permission",
        component: Permission,
      },
      {
        path: "/system/user/add",
        component: addUser,
      },
      {
        path: "/system/user/delete",
        component: deleteUser,
      },
      {
        path: "/system/menu",
        component: SysMenu,
      },
      {
        path: "/food/food",
        component: food,
      },
      {
        path: "/sport/counsel",
        component: counsel,
      },
    ],
  },
  {
    path: "/login",
    component: Login,
  },
  {
    path: "/chart",
    component: testChart,
  },
];

const router = new VueRouter({
  routes,
});
// to:跳转到的页面, from: 本页面
router.beforeEach((to, from, next) => {
  console.log("页面跳转拦截: router.js");
  // to:请求路径信息
  // console.log('to: ',to)
  // from,原来的路径信息
  // console.log('from: ',from);
  //
  // next:放行,如果登录了有token则放行,取缓存里的数据
  if (store.state.token != null) {
    // 判断vuex中是否存在用户基本信息
    if (!store.state.roles || store.state.roles.length < 1) {
      // 向后端发送请求,获取用户基本信息
      ajax.get("/user/getInfo").then((res) => {
        console.log(res);
        if (res.data.data != null && res.data.flag) {
          // this.updateUserInfo(res.data);
          const user = res.data.data;
          store.commit("setName", user.username);
          store.commit("setAvatar", user.avatar);
          if (user.roles && user.roles.length > 0) {
            store.commit("setRoles", user.roles[0]);
            if (user.roles[0].menus.length > 0) {
              // 格式化菜单,放到缓存里
              // const route=generateRoute(user.roles[0].menus);
              // console.log("routeList: ", route);
              // // // 放入到路由中
              // router.addRoutes(route);
              // store.commit("setMenus", user.roles[0].menus);
            }
          }
        } else {
          Message.error("未登录,请先登录!");
          this.$router.push("/login");
        }
      });
    }
    next();
  } else if (to.path === "/login") {
    next();
  } else {
    console.log("info: don't login...");
    // 也可以指向某一个url,否则到登录页面
    next("/login");
    // 也可以重定向到login,带着跳转页面信息
    // next(`/login?redirect=${to.fullPath}`);
  }
});

export default router;
