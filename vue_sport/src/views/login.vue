<template>
  <div class="form-class">
    <!--<img class="logo" src="../assets/logo.png"/>-->
    <!--el-card: 实现阴影效果-->
    <el-card>
      <el-form
          :model="form"
          status-icon
          :rules="rules"
          ref="form"
          label-width="60px"
      >
        <el-form-item style="margin-top: 20px" label="账号" prop="checkPass">
          <el-input
              style="max-width: 200px"
              type="primary"
              v-model="form.username"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input
              style="max-width: 200px"
              type="password"
              v-model="form.password"
              autocomplete="off"
          ></el-input>
        </el-form-item>

        <el-form-item>
          <el-row>
            <el-col :span="6">
              <el-button type="primary" size="small" @click="submitForm('form')"
              >提交
              </el-button
              >
            </el-col>
            <el-col :span="6">
              <el-button size="small" @click="resetForm('form')"
              >重置
              </el-button
              >
            </el-col>
            <el-col :span="9">
              <el-button size="small" type="danger" plain @click="forgetBut"
              >忘记密码
              </el-button
              >
            </el-col>
          </el-row>
        </el-form-item>
      </el-form>
    </el-card>

    <el-dialog
        :visible.sync="isUpdatePassword"
        width="380px"
        style="line-height: 10px"
    >
      <!--  设置对话框的样式  -->
      <div class="dialogS">
        <span
            style="
            margin-left: 130px;
            margin-bottom: 10px;
            font-family: 悠哉字体;
            font-size: medium;
            text-align: center;
          "
        >忘记密码</span
        >

        <el-row style="margin: 10px 0;">
          <el-col :span="5" style="margin-top: 10px; font-family: 悠哉字体">
            <span>用户名:</span>
          </el-col>
          <el-col style="margin-left: 14px" :span="12">
            <el-input v-model="form.username" :disabled="true"></el-input>
          </el-col>
        </el-row>
        <el-form
            :model="form"
            status-icon
            :rules="rules"
            ref="forgetForm"
            style="margin-top: 5px"
        >
          <el-form-item
              style="margin-top: 15px"
              label="输入密码:"
              prop="password"
          >
            <el-row>
              <!--<el-col :span="5" style="margin-top: 10px; font-family: 悠哉字体">-->
              <!--  <span>设置密码:</span>-->
              <!--</el-col>-->
              <el-col :span="12">
                <el-input
                    v-model="form.password"
                    placeholder="请输入密码"
                ></el-input>
              </el-col>
            </el-row>

          </el-form-item>
          <el-form-item
              style="margin-top: 15px"

              prop="verificationCode">
            <el-row style="margin: 0">
              <el-col
                  :span="5"
                  style="margin-right: 13px;  font-family: 悠哉字体"
              >
                <span>验证码:</span>
              </el-col>
              <el-col :span="10">
                <el-input
                    v-model="form.verificationCode"
                    placeholder="请输入验证码"
                ></el-input>
              </el-col>
              <el-col :span="5" style="margin-left: 10px; font-family: 悠哉字体">
                <el-button
                    type="primary"
                    @click="sendVerificationCode"
                    plain
                    :loading="!sendButState"
                >{{ sendButState ? "发送验证码" : "已发送" }}
                </el-button
                >
              </el-col>
            </el-row>
          </el-form-item>
        </el-form>


        <span slot="footer" class="dialog-footer">
          <el-button
              style="margin-left: 83px; margin-right: 32px;"
              @click="
              isUpdatePassword = false;
              password = '';
              sendButState = true;
            "
          >取 消</el-button
          >
          <el-button type="primary" @click="updatePassword">确 定</el-button>
        </span>
      </div>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      isUpdatePassword: false,
      sendButState: true,
      mailUrl: "1253088506@qq.com",
      // 账户数据
      form: {
        username: "",
        password: "",
        verificationCode: "",
      },
      // 表单验证
      rules: {
        //required:必填
        //trigger: 'blur' 触发条件失去焦点
        username: [
          {required: true, message: "请输入用户名", trigger: "blur"},
          {
            min: 2,
            max: 15,
            message: "长度在 2 到 15 个字符",
            trigger: "blur",
          },
        ],
        password: [
          {required: true, message: "请输入密码", trigger: "blur"},
          {
            min: 4,
            max: 20,
            message: "长度在 4 到 20 个字符",
            trigger: "blur",
          },
        ],
        verificationCode: [
          {required: true, message: "请输入验证码", trigger: "blur"},
          {
            min: 7,
            max: 7,
            message: "长度为7个个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },
  methods: {
    // 显示忘记密码对话框按钮
    forgetBut() {
      this.isUpdatePassword = true;
    },
    // 发送验证码按钮
    sendVerificationCode() {
      this.sendButState = false;
      this.$ajax({
        method: "get",
        url: `/user/code/${this.form.username}`,
      }).then(res => {
        console.log("发送验证码: ", res);
      });
    },
    generateUser(user) {
      return {
        username: user.username,
        password: user.password,
        verificationCode: user.verificationCode,
        mailUrl: this.mailUrl,
        id: "",
      };
    },
    // 忘记密码,确定修改按钮
    updatePassword() {
      this.$refs.forgetForm.validate(valid => {
        if (valid) {
          console.log("user: ", this.generateUser(this.form));
          // 自己配置的提交关键字
          this.$ajax({
            method: "post",
            url: "/user/forgot",
            data: this.generateUser(this.form),
          }).then((res) => {
            console.log("修改密码: ", res);
          });
        } else {
          this.$message.error("验证失败,请注意格式.");
          return false;
        }
      })
    },
    // 提交表单数据
    submitForm() {
      //validate:数据校验函数, valid为表单校验的结果,为true或false
      this.$refs.form.validate((valid) => {
        if (valid) {
          // 自己配置的提交关键字
          this.$ajax({
            method: "post",
            // http://43.143.196.105:9090/test/hello
            url: "/user/login",
            data: this.form,
          }).then((res) => {
            // 过500毫秒调用,1s=1000ms
            setTimeout(() => {
              // 方法区
              if (res.data.flag) {
                // res.data里面有一个返回名为data的对象
                let token = res.data.data.token;
                let tokenHead = res.data.data.tokenHead;
                //  存储token到浏览器
                this.$store.commit("setToken", tokenHead + token);
                // 登录成功,转到home界面,可以用replace或push
                this.$router.replace("/");
              } else {
                // 登录失败,打印失败信息
                console.log(res.data.message);
                this.$message.error(res.data.message);
              }
            }, 500);
            // 从这里开始res被拦截,详见util/ajax.js
            // res.data是返回的数据,data是应该object

          });
        } else {
          this.$message.error("验证失败,请注意格式.");
          return false;
        }
      });
    },
    resetForm() {
      this.$message({
        message: "重置成功",
        type: "success",
      });
      this.$refs.form.resetFields();
    },
  },
};
</script>

<style scoped>
.form-class {
  /* 边框线*/
  /*border: 1px solid red;*/
  min-width: 350px;
  width: 30%;
  /* 300px: 上边,200px: 左边*/
  margin: 100px 150px auto;
}

.logo {
  margin-left: 130px;
  height: 100px;
  width: 100px;
}
</style>
