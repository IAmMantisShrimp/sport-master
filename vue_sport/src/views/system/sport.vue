<template>
  <div>
    <!-- 卡片视图区 -->
    <el-card>
      <el-row :gutter="25">
        <el-col :span="10">
          <!-- 搜索添加 -->
          <el-input
            placeholder="请输入搜索内容"
            v-model="queryInfo.queryString"
            clearable
            @clear="findPage"
          >
            <el-button slot="append" icon="el-icon-search" @click="findPage" />
          </el-input>
        </el-col>
        <el-col :span="2">
          <el-button type="primary" @click="addDialogVisible = true"
            >添加运动</el-button
          >
        </el-col>
      </el-row>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column type="expand">
          <template slot-scope="props">
            <el-form label-position="left" inline class="demo-table-expand">
              <el-form-item label="运动概述">
                <div
                  class="markdown-body"
                  v-highlight
                  v-html="props.row.introduction"
                ></div>
              </el-form-item>
            </el-form>
          </template>
        </el-table-column>
        <el-table-column label="运动名称" prop="name" />
        <el-table-column label="适用年龄" prop="applicableAge" />
        <el-table-column label="受益部位" prop="beneficialPosition" />
        <el-table-column label="操作">
          <template slot-scope="scope">
            <!-- 修改 -->
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="medium"
              @click="showEditDialog(scope.row)"
            />
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="medium"
              @click="deleteSport(scope.row.id)"
            />
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNumber"
        :page-sizes="[5, 10, 20, 50]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      />
    </el-card>
    <!-- 添加运动项目对话框 -->
    <el-dialog
      title="添加运动"
      :visible.sync="addDialogVisible"
      width="75%"
      @close="addDialogClosed"
    >
      <!-- 内容主体区域 -->
      <el-form
        :model="addForm"
        :rules="addFormRules"
        ref="addFormRef"
        label-width="80px"
      >
        <!-- 运动名称 -->
        <el-row>
          <el-col :span="12">
            <el-form-item label="运动名称" prop="name">
              <el-input v-model="addForm.name" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="适用年龄" prop="applicableAge">
              <el-input v-model="addForm.applicableAge" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="受益部位" prop="beneficialPosition">
          <el-input v-model="addForm.beneficialPosition" />
        </el-form-item>
        <el-form-item label="运动简介">
          <!-- 该属性可设置主题 theme="light" ishljs开启代码高亮 -->
          <mavon-editor
            ref="md"
            v-model="addForm.introduction"
            class="pc-md"
            :ishljs="true"
            @imgAdd="uploadImg"
          />
          <mavon-editor
            ref="md"
            v-model="addForm.introduction"
            :toolbars="toolbars"
            :subfield="false"
            class="phone-md"
            @imgAdd="uploadImg"
          />
        </el-form-item>
      </el-form>
      <!-- 内容底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addBtn">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="项目修改"
      :visible.sync="editDialogVisible"
      width="75%"
      @close="editDialogClosed"
    >
      <!-- 内容主体区域 -->
      <el-form
        :model="editForm"
        :rules="editFormRules"
        ref="editFormRef"
        label-width="80px"
      >
        <!-- 运动名称 -->
        <el-row>
          <el-col :span="12">
            <el-form-item label="运动名称" prop="name">
              <el-input v-model="editForm.name" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="适用年龄" prop="applicableAge">
              <el-input v-model="editForm.applicableAge" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="受益部位" prop="beneficialPosition">
          <el-input v-model="editForm.beneficialPosition" />
        </el-form-item>
        <el-form-item label="运动简介">
          <!-- 该属性可设置主题 theme="light" ishljs开启代码高亮 -->
          <mavon-editor
            ref="md"
            v-model="editForm.introduction"
            class="pc-md"
            :ishljs="true"
            @imgAdd="uploadImg"
          />
          <mavon-editor
            ref="md"
            v-model="editForm.introduction"
            :toolbars="toolbars"
            :subfield="false"
            class="phone-md"
            @imgAdd="uploadImg"
          />
        </el-form-item>
      </el-form>
      <!-- 内容底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editBtn">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {marked} from "marked";
export default {
  name: "SysSport",
  data() {
    return {
      queryInfo: {
        pageNumber: 1,
        pageSize: 5,
        queryString: "",
      },
      tableData: [], //数据列表
      total: 0, //数据总记录数
      addDialogVisible: false, //默认屏蔽添加对话框
      addForm: {},
      //添加运动信息的校验规则
      addFormRules: {
        name: [
          { required: true, message: "请输入运动项目名称", trigger: "blur" },
          {
            min: 1,
            max: 12,
            message: "长度在 1 到 12 个字符",
            trigger: "blur",
          },
        ],
        beneficialPosition: [
          {
            required: true,
            message: "请输入受益部位，以逗号分割",
            trigger: "blur",
          },
        ],
        applicableAge: [
          {
            required: true,
            message: "请输入该运动项目适用的年龄",
            trigger: "blur",
          },
        ],
      },
      //编辑器工具栏
      toolbars: {
        bold: false, // 粗体
        italic: false, // 斜体
        header: true, // 标题
        underline: false, // 下划线
        strikethrough: false, // 中划线
        mark: false, // 标记
        superscript: false, // 上角标
        subscript: false, // 下角标
        quote: false, // 引用
        ol: false, // 有序列表
        ul: true, // 无序列表
        link: false, // 链接
        imagelink: true, // 图片链接
        code: false, // code
        table: false, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: false, // 沉浸式阅读
        htmlcode: false, // 展示html源码
        help: false, // 帮助
        /* 1.3.5 */
        undo: false, // 上一步
        redo: false, // 下一步
        trash: true, // 清空
        save: false, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: false, // 导航目录
        /* 2.1.8 */
        alignleft: false, // 左对齐
        aligncenter: false, // 居中
        alignright: false, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: false, // 预览
      },
      editDialogVisible: false, //默认屏蔽修改对话框
      editForm: {},
      editFormRules: {
        name: [
          { required: true, message: "请输入运动项目名称", trigger: "blur" },
          {
            min: 1,
            max: 12,
            message: "长度在 1 到 12 个字符",
            trigger: "blur",
          },
        ],
        beneficialPosition: [
          {
            required: true,
            message: "请输入受益部位，以逗号分割",
            trigger: "blur",
          },
        ],
        applicableAge: [
          {
            required: true,
            message: "请输入该运动项目适用的年龄",
            trigger: "blur",
          },
        ],
      },
    };
  },
  mounted() {
    this.findPage();
  },
  methods: {
    //异步加载
    async findPage() {
      this.$ajax
        .post("/sport/findPage", this.queryInfo)
        .then((res) => {
          if (!res.data.flag) return this.$message.error(res.data.message);
          //给列表赋值
          this.tableData = res.data.data.rows;
          this.total = res.data.data.total;
          //console.log(this.tableData);
          for (let i = 0; i < this.tableData.length; i++) {
            if (
              this.tableData[i].introduction != "" &&
              this.tableData[i].introduction != null
            ) {
              this.tableData[i].introduction = marked(
                this.tableData[i].introduction
              );
            }
          }
          console.log(this.tableData);
        })
        .catch((err) => {
          console.log(err.message);
          if (err.request.status == 400)
            return this.$message.warning("请求错误！");
          if (err.request.status == 500)
            return this.$message.warning("数据请求失败，请稍后再试！");
        });
    },
    //页数发生编码
    handleSizeChange(newPageSize) {
      this.queryInfo.pageSize = newPageSize;
      this.findPage();
    },
    //页码发生变化
    handleCurrentChange(newPageNumber) {
      this.queryInfo.pageNumber = newPageNumber;
      this.findPage();
    },
    addDialogClosed() {
      //清空数据
      this.addForm = {};
      this.$refs.addFormRef.resetFields(); // 重置表单
    },
    //图片选择校验
    imageValidate(file) {
      if (file.type != "image/jpeg" && file.type != "image/png") {
        this.$message.warning("文件仅支持PNG、JP格式！");
        return false;
      }
      if (file.size / 1024 / 1024 > 3) {
        this.$message.warning("图片大小不能超过 3MB！");
        return false;
      }
      return true;
    },
    //编辑器图片上传
    uploadImg(pos, file) {
      // console.log(this.imageFiles);
      if (this.imageValidate(file)) {
        let formData = new FormData();
        formData.append("image", file);
        //图片上传 url data headers

        // this.$ajax
        //   .post("/upload/markdown", formData, {
        //     "Content-Type": "multipart/form-data",
        //   })
        this.$ajax({
          method: "post",
          url: "/upload/markdown",
          data: formData,
          headers: {
            "Content-Type": "multipart/form-data",
          },
        }).then((res) => {
          if (!res.data.flag) return this.$message.error(res.data.message);
          //回显
          // / pos,
          // this.domainName + res.data.data
          this.$refs.md.$img2Url(
            pos,
            res.data.data
          );
        });
      } else {
        console.log(this.$refs.md);
      }
    },
    //添加运动信息
    addBtn() {
      console.log(this.addForm);
      this.$refs.addFormRef.validate((valid) => {
        if (!valid) return this.$message.error("数据校验失败！");
        //调用ajax请求
        this.$ajax.post("/sport/add", this.addForm).then((res) => {
          if (!res.data.flag) return this.$message.error(res.data.message);
          this.addDialogVisible = false;
          this.findPage();
        });
      });
    },
    deleteSport(id) {
      this.$confirm("此操作将永久删除该项目, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          this.$ajax.delete(`/sport/delete?id=${id}`).then((res) => {
            if (!res.data.flag) return this.$message.error(res.data.message);
            //删除成功刷新页面
            this.findPage();
            //提示
            this.$message.success(res.data.message);
          });
        })
        .catch(() => {
          this.$message.info("已取消删除");
        });
    },
    editDialogClosed() {
      this.$refs.editFormRef.resetFields();
    },
    showEditDialog(row) {
      console.log(row);
      this.editDialogVisible = true;
      this.editForm = row;
    },
    editBtn() {
      this.$refs.editFormRef.validate((valid) => {
        if (!valid) return this.$message.warning("数据校验失败！");
        //发送ajax请求
        this.$ajax.post("/sport/edit", this.editForm).then((res) => {
          if (res.data.flag) {
            this.editDialogVisible = false;
            this.findPage();
            this.$message.success(res.data.message);
          } else {
            this.$message.error(res.data.message);
          }
        });
      });
    },
  },
};
</script>
<style scoped>
/*<style lang="less" scoped>*/
.demo-table-expand {
  font-size: 0;
}
.demo-table-expand label {
  width: 90px;
  color: #99a9bf;
}
.demo-table-expand .el-form-item {
  margin-right: 0;
  margin-bottom: 0;
  width: 90%;
}
</style>
