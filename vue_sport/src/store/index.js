import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    // 字段,相当与vue中的data
    token: sessionStorage.getItem("token") || "",
    name: sessionStorage.getItem("name") || "",
    avatar: sessionStorage.getItem("avatar") || "",
    // sessionStorage.getItem()取出来的是字符串,要用JSON.parse()转为对象
    roles: (sessionStorage.getItem("roles") || ""),
    menus: (sessionStorage.getItem("menus") || ""),
    // permissions: [],
  },
  mutations: {
    // 保存方法
    setToken(state, data) {
      state.token = data;
      // console.log("vuex中的token: ", data);
      // 永久存储
      sessionStorage.setItem("token", data);
    },
    setName(state, data) {
      state.name = data;
      sessionStorage.setItem("name", data);
    },
    setAvatar(state, data) {
      state.avatar = data;
      sessionStorage.setItem("avatar", data);
    },
    // Roles是一个List
    setRoles(state, data){
      state.roles = data;
      // sessionStorage只能保存字符串,需要用将Json数据转成字符串
      sessionStorage.setItem("roles", JSON.stringify(data));
      // console.log("setRoles,存储成功: ", data);
    },
    setMenus(state, data){
      state.roles = data;
      // sessionStorage只能保存字符串,需要用将Json数据转成字符串
      sessionStorage.setItem("menus", JSON.stringify(data));
      // console.log("setMenus存储成功: ", data);
    },
    // setPermissions(state, data){
    //   state.permissions = data;
    //   // sessionStorage只能保存字符串,需要用将Json转成字符串
    //   sessionStorage.setItem("token", JSON.stringify(data));
    // },
  },
  actions: {},
  modules: {},
});
