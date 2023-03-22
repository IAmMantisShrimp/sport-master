<template>
  <div>
    <el-card class="box-card">
      <!--  Layout布局控件，并且分成24分栏。
       gutter: 每一栏之间的距离-->
      <el-row :gutter="10" style="line-height: 0">
        <el-col :span="15">
          <!--  input3:-->
          <el-input
            placeholder="请输入要查找的内容"
            v-model="searchStr"
            class="input-with-select"
          >
            <!-- select与value对应 -->
            <el-select v-model="searchType" slot="prepend" placeholder="请选择">
              <el-option label="未知" value="1"></el-option>
              <el-option label="未知" value="2"></el-option>
              <el-option label="未知" value="3"></el-option>
            </el-select>
            <el-button
              slot="append"
              icon="el-icon-search"
              @click="searchPer"
            ></el-button>
          </el-input>
        </el-col>
        <el-col :span="3">
          <el-button
            type="primary"
            plain
            @click="
              addUserBox = true;
              boxHandleIndex = 1;
            "
            style="float: left"
            >增加用户</el-button
          >
        </el-col>
        <el-col :span="3">
          <el-button
            type="primary"
            plain
            @click="allShow = !allShow"
            style="float: left"
            >{{ allShow ? "部分显示" : "显示所有" }}</el-button
          >
        </el-col>
      </el-row>
      <!--  分割线  -->
      <el-divider STYLE="margin: 4px 0;"></el-divider>
      <!--  data为需要绑定的内容
      :header-row-style="{ height: '30px', 'line-height': '30px' }" 设置头行的汉高和line-height
      :cell-style="{padding:'0px'}" 设置行内的padding
      行内可以加入图片或其他,但加入后需要设置好图片的格式,不然行高会随着图片的增大而增大,就算设置了汉高也会
      -->
      <el-table
        :data="tableData"
        style="width: 100%; margin-top: 1px; padding: 0"
        :row-class-name="tableRowClassName"
        :cell-style="{padding:'0px'}"
        :header-row-style="{ height: '30px', 'line-height': '30px' }"
      >
        <el-table-column prop="id" label="编号" width="50"> </el-table-column>
        <el-table-column prop="username" label="用户名" width="80">
        </el-table-column>
        <el-table-column prop="nickName" label="昵称"> </el-table-column>
        <el-table-column prop="sex" label="性别"> </el-table-column>
        <!--  表格中加入图片,需要用到template, 把prop值传入到template里面-->
        <el-table-column v-if="allShow" prop="avatar" label="用户头像">
          <template slot-scope="scope">
            <el-image
                style="max-height: 90px"
              v-show="scope.row.avatar != ''"
              :src="scope.row.avatar != '' ? scope.row.avatar : ''"
              fit="contain"
            ></el-image>
          </template>
        </el-table-column>
        <el-table-column v-if="allShow" prop="address" label="地址">
        </el-table-column>
        <el-table-column prop="openId" label="微信Id"> </el-table-column>
        <el-table-column prop="status" label="禁用"> </el-table-column>
        <el-table-column prop="admin" label="管理员"> </el-table-column>
        <el-table-column prop="phoneNumber" label="电话"> </el-table-column>
        <el-table-column fixed="right" label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="handleClick(scope.row)" type="text" size="small"
              >修改</el-button
            >

            <el-button @click="deleteClick(scope.row)" type="text" size="small"
              >删除</el-button
            >
          </template>
        </el-table-column>
      </el-table>
      <!--  分页 Pagination  -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[5, 6, 7, 8, 9, 10]"
        :page-size="pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="totalC"
        style="margin-top: 10px"
      >
      </el-pagination>
    </el-card>
    <el-dialog
      :visible.sync="addUserBox"
      width="380px"
      style="line-height: 10px"
    >
      <!--  设置对话框的样式  -->
      <div class="dialogS">
        <span
          style="
            margin-bottom: 10px;
            font-family: 悠哉字体;
            font-size: medium;
            text-align: center;
          "
          >增加用户</span
        >
        <el-row style="margin-top: 10px">
          <el-col :span="5" style="margin-top: 10px; font-family: 悠哉字体">
            <span>用户名:</span>
          </el-col>
          <el-col :span="16">
            <el-input
              v-model="addUser.username"
              placeholder="请输入用户名"
            ></el-input>
          </el-col>
        </el-row>
        <el-row style="margin-top: 10px">
          <el-col :span="5" style="margin-top: 10px; font-family: 悠哉字体">
            <span>性别:</span>
          </el-col>
          <el-col :span="16">
            <el-select v-model="dexValue" placeholder="请选择性别">
              <el-option
                v-for="(item, index) in sexes"
                :key="index"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
            <!--<el-input-->
            <!--    v-model="addUser.sex"-->
            <!--    placeholder="请输入性别"-->
            <!--&gt;</el-input>-->
          </el-col>
        </el-row>
        <el-row style="margin-top: 10px">
          <el-col :span="5" style="margin-top: 10px; font-family: 悠哉字体">
            <span>昵称:</span>
          </el-col>
          <el-col :span="16">
            <el-input
              v-model="addUser.nickName"
              placeholder="请输入昵称"
            ></el-input>
          </el-col>
        </el-row>
        <el-row style="margin-top: 10px">
          <el-col :span="6" style="margin-top: 42px; font-family: 悠哉字体">
            <span>用户头像:</span>
          </el-col>
          <div class="avatar-upload">
            <el-col :span="5">
              <el-upload
                class="upload-demo"
                style="height: 90px; line-height: 0; margin-top: 30px"
                ref="upload"
                :action="action"
                :headers="headers"
                :auto-upload="false"
                :multiple="false"
                :show-file-list="false"
                list-type="picture"
                :on-change="addFile"
                :on-success="uploadSuccess"
              >
                <el-button slot="trigger" size="small" type="primary"
                  >选择头像</el-button
                >
              </el-upload>
            </el-col>
            <el-col :span="11" style="line-height: 0; margin-top: 10px">
              <el-image
                v-show="addUser.avatar != ''"
                style="width: 100px; height: 80px"
                :src="addUser.avatar != '' ? addUser.avatar : ''"
                fit="contain"
              ></el-image>
            </el-col>
          </div>
        </el-row>
        <el-row style="margin-top: 10px">
          <el-col :span="5" style="margin-top: 10px; font-family: 悠哉字体">
            <span>地址:</span>
          </el-col>
          <el-col :span="16">
            <el-input
              v-model="addUser.address"
              placeholder="请输入地址"
            ></el-input>
          </el-col>
        </el-row>
        <el-row style="margin-top: 10px">
          <el-col :span="5" style="margin-top: 10px; font-family: 悠哉字体">
            <span>微信id:</span>
          </el-col>
          <el-col :span="16">
            <el-input
              v-model="addUser.openId"
              placeholder="请输入微信id"
            ></el-input>
          </el-col>
        </el-row>
        <el-row style="margin-top: 10px">
          <el-col :span="5" style="margin-top: 10px; font-family: 悠哉字体">
            <span>管理员:</span>
          </el-col>
          <el-col :span="16">
            <el-select v-model="adminValue" placeholder="是否未管理员">
              <el-option
                v-for="(item, index) in admins"
                :key="index"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </el-col>
        </el-row>
        <el-row style="margin-top: 10px">
          <el-col :span="5" style="margin-top: 10px; font-family: 悠哉字体">
            <span>状态:</span>
          </el-col>
          <el-col :span="16">
            <el-select v-model="userStatusValue" placeholder="是否未管理员">
              <el-option
                v-for="(item, index) in userStatus"
                :key="index"
                :label="item"
                :value="item"
              >
              </el-option>
            </el-select>
          </el-col>
          <!--<el-col :span="16">-->
          <!--  <el-input-->
          <!--      v-model="addUser.status"-->
          <!--      placeholder="请输入状态"-->
          <!--  ></el-input>-->
          <!--</el-col>-->
        </el-row>
        <el-row style="margin: 5px 0">
          <el-col :span="5" style="margin-top: 10px; font-family: 悠哉字体">
            <span>电话:</span>
          </el-col>
          <el-col :span="16">
            <el-input
              v-model="addUser.phoneNumber"
              placeholder="请输入电话"
            ></el-input>
          </el-col>
        </el-row>
        <span slot="footer" class="dialog-footer">
          <el-button
            @click="
              addUserBox = false;
              boxHandleIndex = 0;
            "
            >取 消</el-button
          >
          <el-button type="primary" @click="addPerMed">确 定</el-button>
        </span>
      </div>
    </el-dialog>
    <!--  删除弹出框  -->
    <el-dialog
      title="提示"
      :visible.sync="deleteUserBox"
      width="300px"
      style="line-height: 10px"
    >
      <span style="font-family: 悠哉字体; font-size: medium; color: red"
        >确定删除{{ addUser.code }}用户嘛?</span
      >
      <span slot="footer" class="dialog-footer">
        <el-button @click="deleteUserBox = false">取 消</el-button>
        <el-button type="primary" @click="deletePerMed">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<style>
.el-card__body {
  padding: 0 10px;
}
.box-card {
  width: 100%;
  margin-top: 10px;
  padding: 0;
  line-height: 0;
}
.avatar-upload {
  height: 90px;
}
.el-table__body tr,
.el-table__body td {
  height: 90px;
}
</style>

<script>
export default {
  created() {
    // 初始化,获取权限信息
    this.$ajax({
      method: "get",
      url: "/user/initial",
    }).then((res) => {
      console.log("res: ", res);
      this.totalC = parseInt(res.data.data.count);
      // this.tableData = res.data.data.userList;
      res.data.data.userList.forEach((item) => {
        // console.log(`item: ` , (item))
        // console.log("addUser: ", this.addUser);
        this.tableData.push(this.assignUser(item));
      });
      console.log("this.tableData: ", this.tableData);
    });
  },
  data() {
    return {
      searchStr: "",
      searchType: "",
      // 现在的页数
      currentPage: 1,
      // 每页的数目
      pageSize: 5,
      // 所有的数目
      totalC: 100,
      // 增加权限信息框
      addUserBox: false,
      // 为0,不显示, 为1,增加, 为2,修改
      boxHandleIndex: 0,
      deleteUserBox: false,
      // 是否全部显示
      allShow: false,
      // 增加权限label
      sexes: ["男", "女"],
      dexValue: "",
      admins: ["管理员", "普通用户"],
      adminValue: "",
      userStatus: ["禁用", "未禁用"],
      userStatusValue: "",

      action: "/tool/upload",
      headers: {
        Authorization: sessionStorage.getItem("token"),
      },
      dialogImageUrl: "",
      dialogVisible: false,
      fileList: [],
      uploadUrl: "",
      addUser: {
        id: 0,
        username: "",
        password: "",
        nickName: "",
        sex: "",
        avatar: "",
        address: "",
        openId: "",
        status: "",
        admin: "",
        phoneNumber: "",
      },
      tableData: [],
    };
  },
  methods: {
    // 上传图片
    submitUpload() {
      this.$refs.upload.submit();
    },
    // 上传图片成功
    uploadSuccess(response, file, fileList) {
      // this.imageUrl = this.domainName + response.data;
      // this.avatarShow = true;
      console.log(response);
      // 接收服务器返回的图片链接,不需要加域名
      this.addUser.avatar = response.data;
      // console.log("imageurl: ", this.domainName + this.addUser.avatar);
      this.insertOrUpdateUser();

      this.$refs.upload.clearFiles();
      this.uploadImage = null;
    },
    // 添加图片,修改图片
    addFile(file, fileList) {
      // fileList 为上传的文件,因为只需要一个头像,所以fileList的length始终为1
      // https://www.php.cn/vuejs/483459.html :this.$delete(fileList, 1);这样删能把长度也删掉,而不是简单的赋值为null
      this.uploadImage = file;
      // 先将前端的url给avatar
      this.addUser.avatar = file.url;
      // console.log("file: ", file);
      // console.log("fileList: ", fileList);
      if (fileList.length > 1) {
        fileList[0] = fileList[1];
        this.$delete(fileList, 1);
      }
    },
    // 赋值操作
    assignUser(user) {
      // console.log("user: ", user);
      return {
        id: parseInt(user.id),
        username: user.username,
        password: user.password,
        //avatar: this.domainName + user.avatar,
        avatar: user.avatar,
        nickName: user.nickName,
        sex: user.sex == 1 ? "男" : "女",
        status: user.status ? "禁用" : "未禁用",
        admin: user.admin ? "管理员" : "普通用户",
        phoneNumber: user.phoneNumber,
        openId: user.openId,
        address: user.address,
      };
    },
    // 修改或添加时, 将选中的列数据添加到对话框中.
    setAddUser(user) {
      this.addUser.id = parseInt(user.id);
      this.addUser.username = user.username;
      this.addUser.password = user.password;
      this.addUser.nickName = user.nickName;
      this.dexValue = user.sex;
      this.addUser.avatar = user.avatar;
      this.userStatusValue = user.status;
      this.adminValue = user.admin;
      this.addUser.phoneNumber = user.phoneNumber;
      this.addUser.openId = user.openId;
      this.addUser.address = user.address;
    },
    // 复位操作
    resetUser() {
      // 对话框不显示
      this.deleteUserBox = false;
      this.addUserBox = false;
      // 模式设置为0
      this.boxHandleIndex = 0;
      this.addUser.id = 0;
      this.addUser.username = "";
      this.addUser.password = "";
      this.addUser.nickName = "";
      this.addUser.sex = 0;
      this.addUser.status = 0;
      this.addUser.admin = 0;
      this.addUser.phoneNumber = "";
      this.addUser.openId = "";
      this.addUser.address = "";
      this.addUser.avatar = "";
      // 备份的头像设为null
      this.uploadUrl = "";
    },
    // el-table 操作按钮方法,即修改用户按钮触发
    handleClick(row) {
      // 修改头像时,如果头像为改变则不修改,备份一下
      this.uploadUrl = row.avatar;
      // 更新数据
      this.setAddUser(row);
      // 显示对话框
      this.addUserBox = true;
      // 对话框模式为2,即增加用户
      this.boxHandleIndex = 2;
    },
    // 删除权限方法
    deleteClick(row) {
      // 显示对话框
      this.deleteUserBox = true;
      // 更新数据
      this.setAddUser(row);
    },
    deletePerMed() {
      this.$ajax({
        method: "delete",
        url: `/user/delete/${this.addUser.id}`,
        data: this.addUser,
      }).then((res) => {
        console.log("deletePerMed:" + res);
        this.totalC = parseInt(res.data.data);
        // 处理完复位
        this.resetUser();
        console.log("this.currentPage: ", this.currentPage);
        this.handleCurrentChange(this.currentPage);
      });
    },
    // 将对话框中的数据,转换成后端能接收的数据
    userSendBackstage(user) {
      return {
        id: parseInt(user.id),
        username: user.username,
        password: user.password,
        nickName: user.nickName,
        sex: this.dexValue === "男" ? 1 : 0,
        status: this.userStatusValue === "禁用",
        admin: this.adminValue === "管理员",
        phoneNumber: user.phoneNumber,
        avatar: user.avatar,
        openId: user.openId,
        address: user.address,
      };
    },
    // 根据情况选择插入还是修改, 点击确定事件
    addPerMed() {
      // 头像不为空
      if (this.addUser.avatar !== "") {
        // 增加模式,直接上传, 如果为修改模式,且头像被修改了才上传
        if (
          this.boxHandleIndex == 1 ||
          (this.boxHandleIndex == 2 && this.addUser.avatar !== this.uploadUrl)
        ) {
          console.log(
            `头像被修改了, 原来的头像: ${this.uploadUrl} ==> 现在的头像:${this.addUser.avatar}`
          );
          this.submitUpload();
          return null;
        } else {
          this.insertOrUpdateUser();
        }
      } else {
        this.insertOrUpdateUser();
      }
    },
    // 向后端发送请求,增加或删除用户
    insertOrUpdateUser() {
      console.log("userSendBackstage: ", this.userSendBackstage(this.addUser));
      const pathV = this.boxHandleIndex == 1 ? "insert" : "update";
      // console.log("pathV: ", pathV);
      this.$ajax({
        method: "post",
        url: `/user/${pathV}`,
        data: this.userSendBackstage(this.addUser),
      }).then((res) => {
        console.log("handle:", res);
        if (this.boxHandleIndex == 1) {
          console.log("parseInt(res.data): ", parseInt(res.data));
          this.totalC = parseInt(res.data.data);
        }
        // console.log("this.currentPage: ", this.currentPage);
        this.handleCurrentChange(this.currentPage);
        // 处理完毕 复位
        this.resetUser();
      });
    },
    // 根据信息查找
    searchPer() {
      this.$ajax({
        method: "post",
        url: `/user/findByNameVo?name=${this.searchStr}`,
      }).then((res) => {
        console.log(res);
        // 先清空列表
        this.tableData = [];
        this.totalC = res.data.data.length;
        res.data.data.forEach((item) => {
          this.tableData.push(this.assignUser(item));
        });
      });
    },
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return "warning-row";
      } else if (rowIndex === 3) {
        return "success-row";
      }
      return "";
    },
    // 条数大小改变处理
    handleSizeChange(val) {
      // console.log(`每页 ${val} 条`);
      this.pageSize = val;
      this.$ajax({
        method: "post",
        url:
          "/user/findPage?" +
          "pageNo=" +
          this.currentPage +
          "&pageSize=" +
          val,
      }).then((res) => {
        // console.log(res);
        // this.tableData = res.data.data;
        // 先清空列表
        this.tableData = [];
        res.data.data.forEach((item) => {
          this.tableData.push(this.assignUser(item));
        });
      });
    },
    // 页数改变处理
    handleCurrentChange(val) {
      // console.log(`当前页: ${val}`);
      // console.log("this.currentPage: ",this.currentPage);
      this.currentPage = val;
      this.$ajax({
        method: "post",
        url:
          "/user/findPage?" +
          "pageNo=" +
          val +
          "&pageSize=" +
          this.pageSize,
      }).then((res) => {
        // console.log(res);
        // this.tableData = res.data.data;
        // 先清空列表
        this.tableData = [];
        res.data.data.forEach((item) => {
          this.tableData.push(this.assignUser(item));
        });
      });
    },
  },
};
</script>

<!--<template>-->
<!--  <div>-->
<!--    <h1>测试</h1>-->
<!--    <el-button type="info" v-hasRole="['ADMIN']" @click="findById(1)"-->
<!--      >findById</el-button-->
<!--    >-->
<!--    <el-button type="info" v-hasRole="['ADMIN']" @click="findAll()"-->
<!--      >findAll</el-button-->
<!--    >-->
<!--    <el-button type="info" v-hasRole="['ADMIN']" @click="findPage(0, 5)"-->
<!--      >findPage</el-button-->
<!--    >-->
<!--    <el-button type="info" v-hasRole="['ADMIN']" @click="findInfo('User')"-->
<!--      >findByInfo</el-button-->
<!--    ><br />-->
<!--    <el-button type="info" v-hasRole="['ADMIN']" @click="insertPermission()"-->
<!--      >insert</el-button-->
<!--    >-->
<!--    <el-button type="info" v-hasRole="['ADMIN']" @click="updatePermission()"-->
<!--      >update</el-button-->
<!--    >-->
<!--    <el-button type="info" v-hasRole="['ADMIN']" @click="deletePermission(10)"-->
<!--      >delete</el-button-->
<!--    >-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import "@/util/user";-->
<!--export default {-->
<!--  name: "user",-->
<!--  methods: {-->
<!--    findById(id) {-->
<!--      this.$ajax({-->
<!--        method: "get",-->
<!--        url: "/permission/findById/" + id,-->
<!--      }).then((res) => {-->
<!--        console.log("findById: ", res);-->
<!--      });-->
<!--    },-->
<!--    findAll() {-->
<!--      this.$ajax({-->
<!--        method: "get",-->
<!--        url: "/permission/findAll",-->
<!--      }).then((res) => {-->
<!--        console.log("findAll: ", res);-->
<!--      });-->
<!--    },-->
<!--    findPage(pageNo, pageSize) {-->
<!--      this.$ajax({-->
<!--        method: "post",-->
<!--        url:-->
<!--          "/permission/findPage?pageNo=" +-->
<!--          pageNo +-->
<!--          "&pageSize=" +-->
<!--          pageSize,-->
<!--      }).then((res) => {-->
<!--        console.log("findPage: ", res);-->
<!--      });-->
<!--    },-->
<!--    findInfo(info) {-->
<!--      this.$ajax({-->
<!--        method: "post",-->
<!--        url: "/permission/findByInfo?info=" + info,-->
<!--      }).then((res) => {-->
<!--        console.log("findByInfo: ", res);-->
<!--      });-->
<!--    },-->
<!--    updatePermission() {-->
<!--      const permission = {-->
<!--        id: 10,-->
<!--        label: "更新",-->
<!--        code: "Update",-->
<!--      };-->
<!--      this.$ajax({-->
<!--        method: "post",-->
<!--        url: "/permission/update",-->
<!--        data: permission,-->
<!--      }).then((res) => {-->
<!--        console.log("updatePermission: ", res);-->
<!--      });-->
<!--    },-->
<!--    insertPermission() {-->
<!--      const permission = {-->

<!--        label: "更新菜单",-->
<!--        code: "UpdateMenu",-->
<!--      };-->
<!--      this.$ajax({-->
<!--        method: "post",-->
<!--        url: "/permission/insert",-->
<!--        data: permission,-->
<!--      }).then((res) => {-->
<!--        console.log("insertPermission: ", res);-->
<!--      });-->
<!--    },-->
<!--    deletePermission(id) {-->
<!--      this.$ajax({-->
<!--        method: "delete",-->
<!--        url: "/permission/delete/" + id,-->
<!--      }).then((res) => {-->
<!--        console.log("deletePermission: ", res);-->
<!--      });-->
<!--    },-->
<!--  },-->
<!--};-->
<!--</script>-->

<!--<style scoped></style>-->
