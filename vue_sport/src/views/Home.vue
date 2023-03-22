<template>
  <!--主容器-->
  <el-container class="main-class">
    <!--头部信息-->
    <el-header>
      <el-row style="height: 100%">
        <!--:span="1" 占多少比例,一共24份-->
        <el-col :span="2" style="height: 100%">
          <!--头像-->
          <el-avatar :size="60" :src="this.userInfo_main.avatar"></el-avatar>
        </el-col>
        <!--项目名称-->
        <el-col :span="15" class="title">
          <b
            >&nbsp;个人运动管理平台<span>
              -- {{ this.userInfo_main.role + " -- " + this.userInfo_main.username }}</span
            ></b
          >
        </el-col>
        <!--退出按钮-->
        <el-col :span="7" class="logout-but">
          <el-button type="info" plain @click="test">测试</el-button>
          <el-button type="info" plain @click="logout">退出登录</el-button>
        </el-col>
      </el-row>
    </el-header>
    <!--主体-->
    <el-container>
      <!--侧边栏:asideIsActive为true值就是200px,否则为65px -->
      <el-aside :width="asideIsActive ? '200px' : '65px'">
        <!--  点击事件,asideIsActive取反  -->
        <div class="aside-show-div" @click="asideIsActive = !asideIsActive">
          <!--  根据asideIsActive值选择图标  -->
          <span
            :class="!asideIsActive ? 'el-icon-s-unfold' : 'el-icon-s-fold'"
          ></span>
        </div>
        <!--unique-opened: 只展开一个子菜单,
            router: 让菜单为路径模式,会让菜单中的index属性为path

            使用v-for便利导航栏,导航栏的信息都在menus信息里.
        -->
        <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          background-color="#545c64"
          text-color="#fff"
          :collapse="!asideIsActive"
          active-text-color="#ffd04b"
          unique-opened
          router
          v-for="(menu, menuIndex) in this.userInfo_main.menus"
          :key="menuIndex"
          @select="changeMenu"
        >
          <!--@open="handleOpen"-->
          <!--@close="handleClose"-->
          <!--  :index="menu.path": 是导航栏标识符,唯一,不能与其他栏目一样,不然会同时打开和关闭-->
          <el-submenu :index="menu.component">
            <template slot="title">
              <i :class="'iconfont ' + menu.icon" style="margin-right: 5px"></i>
              <span style="font-family: 悠哉字体; padding-top: 2px">{{
                menu.title
              }}</span>
            </template>
            <!--  子menu: -->
            <el-menu-item
              v-for="(childMenu, childMenuIndex) in menu.menus"
              :key="childMenuIndex"
              style="margin-left: 10px; width: 170px"
              :index="childMenu.component"
            >
              <i
                :class="'iconfont ' + childMenu.icon"
                style="margin-right: 5px"
              ></i>
              <span style="font-family: 悠哉字体">{{ childMenu.title }}</span>
            </el-menu-item>
            <!--<el-menu-item index="/home/welcome">欢迎页</el-menu-item>-->
          </el-submenu>
        </el-menu>
      </el-aside>

      <!--主体-->
      <el-container>
        <el-main>
          <div style="padding: 5px 0; margin: 0px 0">
            <!--  面包屑  -->
            <el-breadcrumb>
              <el-breadcrumb-item :to="{ path: '/' }" @click="changeMenu"
                >首页</el-breadcrumb-item
              >
              <el-breadcrumb-item
                v-for="(title, index) in breadTitle"
                :key="index"
              >
                > {{ title }}</el-breadcrumb-item
              >
            </el-breadcrumb>
            <span
              v-show="$router.currentRoute.path === '/'"
              class="welcome-title"
              >欢迎来到个人运动管理平台</span
            >
            <!--  作为主体子路由,  -->
            <router-view />
          </div>

        </el-main>
        <!--  页脚  -->
        <el-footer>Footer</el-footer>
      </el-container>
    </el-container>
  </el-container>
</template>

<script>
import store from "@/store/index";
import { Message } from "element-ui";
import { generateRoute } from "@/util/initMenus";
import router from "@/router";
import User from "@/views/system/user.vue";

export default {
  name: "Home",
  components: {},
  data() {
    return {
      asideWidth: 200,
      asideIsActive: true,
      breadTitle: [],
      userInfo_main: {
        id: 0,
        username: "未登录",
        admin: false,
        avatar:
          "https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png",
        role: "未知",
        menus: [],
      },
      timer: null,
    };
  },
  // 计算属性,可以从缓存中取出数据
  computed: {},
  activated(){
    //获取用户信息
    this.$ajax({
      method: "get",
      url: "/user/getInfo",
    }).then((res) => {
      console.log("getInfo_created: ", res);
      if (res.data.data != null && res.data.flag) {
        this.updateUserInfo(res.data.data);
        console.log("userInfo_main: ",this.userInfo_main)
      } else {
        Message.error("未登录,请先登录!");
        this.$router.push("/login");
      }
    });
  },
  methods: {
    // 根据说给参数更新用户
    updateUserInfo(user){
      console.log("user: ", user);
      this.userInfo_main.id = user.id;
      this.userInfo_main.username = user.username;
      this.userInfo_main.admin = user.admin;
      this.userInfo_main.avatar = user.avatar;
      this.$store.commit("setName", user.username);
      // roles是一个数组,数据库设计时有点问题,不应该是一个数组
      if (user.roles.length > 0) {
        this.$store.commit("setRoles", user.roles[0]);
        this.userInfo_main.role = user.roles[0].label;
        if (user.roles[0].menus.length > 0) {
          this.$store.commit("setMenus", user.roles[0].menus);
          this.userInfo_main.menus = user.roles[0].menus;
        }
      }
      this.$store.commit("setAvatar", user.avatar);
    },
    test() {
      console.log("this.info: ", this.userInfo_main)
      // this.isUpdatePassword = true;
    },
    logout() {
      this.$confirm("是否退出登录?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$ajax({
            method: "get",
            url: "/user/logout",
          }).then((res) => {
            console.log(res);
            // 清空本地缓存
            sessionStorage.clear();
            // 跳转到登录页面
            this.$router.push("/login");
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    changeMenu() {
      // 清空数组
      this.breadTitle = [];
      if (this.$router.currentRoute.path !== "/") {
        const titles = this.getBreadCrumbTitle(this.$router.currentRoute.path);
        // console.log("titles: ", titles);
        titles.forEach((title) => {
          this.breadTitle.push(title);
        });
      }
      return null;
    },
    getBreadCrumbTitle(title) {
      // console.log("被调用...");
      const getTitle = [];
      const titles = [];
      this.breadCrumbTitle(title, getTitle, this.userInfo_main.menus);
      for (let i = getTitle.length - 1; i >= 0; i--) {
        titles.push(getTitle[i]);
      }
      return titles;
    },
    breadCrumbTitle(title, getTitle, menus) {
      // console.log("匹配title: ", title);
      if (menus && menus.length > 0) {
        for (let i = 0; i < menus.length; i++) {
          // console.log("component: ", menus[i].component);
          if (menus[i].component === title) {
            // console.log("匹配成功.");
            getTitle.push(menus[i].title);
            return menus[i].title;
          } else if (menus[i].menus && menus[i].menus.length > 0) {
            // console.log("子menus");
            var childTitle = this.breadCrumbTitle(
              title,
              getTitle,
              menus[i].menus
            );
            // console.log("childTitle:", childTitle);
            if (childTitle != null) {
              getTitle.push(menus[i].title);
              return menus[i].title;
            }
          }
        }
      }
      return null;
    },
  },
};
</script>
<style scoped>
.el-header,
.el-footer {
  background-color: #b3c0d1;
  color: #333;
  text-align: center;
  line-height: 60px;
}

.el-aside {
  background-color: #d3dce6;
  color: #333;
}

.el-main {
  background-color: #e9eef3;
  color: #333;
  text-align: center;
  line-height: 160px;
}
/*主容器样式*/
.main-class {
  height: 100%;
}
.title {
  text-align: left;
  font-size: 25px;
  font-family: 悠哉字体;
}
.logout-but {
  text-align: right;
}
.aside-show-div {
  flex: 1;
  /*图标大小*/
  font-size: 30px;
  text-align: center;
  /*移动到上面有鼠标样式*/
  cursor: pointer;
}
/*  侧边栏样式  */
.el-submenu .el-menu-item {
  min-width: 100px;
  padding: 0;
  margin: 0;
}
.el-menu-item-group__title {
  padding: 0;
}
.welcome-title {
  text-align: center;
  font-size: 50px;
  font-family: 演示悠然小楷;
  color: black;
  padding: 10px;
}
</style>
