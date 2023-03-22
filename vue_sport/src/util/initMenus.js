/**
 * 格式化菜单,将菜单转化为组件
 * @param menus
 */
import router from "@/router";
import Home from "@/views/Home";


export const formatMenu = (menus, route) => {
  // 迭代菜单,并对菜单进行改造,并返回格式化后的菜单
  menus.forEach((item) => {
    if (item.component !== "/") {
      // 处理父级menu
      const subRoute = {
        path: item.component === "/" ? "/" : item.component,
        name: item.path,
        component: item.component === "/" ? Home : loadView(item.component),
      };
      // routes[0].children.push(subRoute);
      route.push(subRoute);
    }
    // 处理子菜单
    // router.addRoute(item.component);
    if (item.menus && item.menus.length > 0) {
      formatMenu(item.menus, route);
    }
  });
  return route;
};
export const generateRoute = (menus) => {
  const childRoute = [];
  return formatMenu(menus, childRoute);


};
/**
 * 路由懒加载
 * @param view: 组件路由
 * @returns {*}: 组件
 */
const loadView = (view) => {

  return require(`@/views${view}.vue`);
};
// 返回一个文件
// export const test = require("@/views/system/user.vue");
