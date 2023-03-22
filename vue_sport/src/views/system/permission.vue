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
        <el-col :span="4">
          <el-button
            type="primary"
            plain
            @click="
              addPerBox = true;
              boxHandleIndex = 1;
            "
            >增加权限</el-button
          >
        </el-col>
      </el-row>
      <!--  分割线  -->
      <el-divider STYLE="margin: 4px 0;"></el-divider>
      <!--  data为需要绑定的内容
      :header-row-style="{ height: '30px', 'line-height': '30px' }" 设置头行的汉高和line-height
      -->
      <el-table
        :data="tableData"
        style="width: 100%; margin-top: 1px; padding: 0"
        :row-class-name="tableRowClassName"
        :header-row-style="{ height: '30px', 'line-height': '30px' }"
      >
        <el-table-column prop="id" label="编号" width="180"> </el-table-column>
        <el-table-column prop="label" label="标签" width="180">
        </el-table-column>
        <el-table-column prop="code" label="代码"> </el-table-column>
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
      :visible.sync="addPerBox"
      width="300px"
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
          >增加权限</span
        >
        <el-row style="margin-top: 10px">
          <el-col :span="5" style="margin-top: 10px; font-family: 悠哉字体">
            <span>label:</span>
          </el-col>
          <el-col :span="16">
            <el-input
              v-model="addPermission.label"
              placeholder="请输入标签"
            ></el-input>
          </el-col>
        </el-row>
        <el-row style="margin: 5px 0">
          <el-col :span="5" style="margin-top: 10px; font-family: 悠哉字体">
            <span>code:</span>
          </el-col>
          <el-col :span="16">
            <el-input
              v-model="addPermission.code"
              placeholder="请输入代码"
            ></el-input>
          </el-col>
        </el-row>
        <span slot="footer" class="dialog-footer">
          <el-button
            @click="
              addPerBox = false;
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
      :visible.sync="deletePerBox"
      width="300px"
      style="line-height: 10px"
    >
      <span style="font-family: 悠哉字体; font-size: medium; color: red"
        >确定删除{{ addPermission.code }}权限嘛?</span
      >
      <span slot="footer" class="dialog-footer">
        <el-button @click="deletePerBox = false">取 消</el-button>
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
</style>

<script>
export default {
  created() {
    //获取权限信息
    this.$ajax({
      method: "get",
      url: "/permission/getInfo",
    }).then((res) => {
      console.log(res);
      this.totalC = parseInt(res.data.data.count);
      this.tableData = res.data.data.permissionList;
    });
  },
  methods: {
    // el-table 操作按钮方法
    handleClick(row) {
      // console.log(row);
      this.addPermission.id = row.id;
      this.addPermission.label = row.label;
      this.addPermission.code = row.code;
      this.addPerBox = true;
      this.boxHandleIndex = 2;
    },
    // 删除权限方法
    deleteClick(row) {
      this.deletePerBox = true;
      this.addPermission.id = row.id;
      this.addPermission.label = row.label;
      this.addPermission.code = row.code;
    },
    deletePerMed() {
      this.$ajax({
        method: "delete",
        url: `/permission/delete/${this.addPermission.id}`,
        data: this.addPermission,
      }).then((res) => {
        console.log("deletePerMed:" + res);
        this.totalC = parseInt(res.data);
        this.deletePerBox = false;
        this.addPermission.id = "";
        this.addPermission.label = "";
        this.addPermission.code = "";
        console.log("this.currentPage: ", this.currentPage);
        this.handleCurrentChange(this.currentPage);
      });

    },
    // 根据情况选择插入还是修改
    addPerMed() {
      console.log("boxHandleIndex: ", this.boxHandleIndex);
      const pathV = this.boxHandleIndex == 1 ? "insert" : "update";
      // console.log("pathV: ", pathV);
      this.$ajax({
        method: "post",
        url: `/permission/${pathV}`,
        data: this.addPermission,
      }).then((res) => {
        console.log("handle:" + res);
        if (this.boxHandleIndex == 2) {
          this.totalC = parseInt(res.data);
          console.log("this.currentPage: ", this.currentPage);
          this.handleCurrentChange(this.currentPage);
        }
        this.addPerBox = false;
        this.boxHandleIndex = 0;
        this.addPermission.id = "";
        this.addPermission.label = "";
        this.addPermission.code = "";

      });

    },
    // 根据信息查找
    searchPer() {
      this.$ajax({
        method: "post",
        url: `/permission/findByInfo?info=${this.searchStr}`,
      }).then((res) => {
        console.log(res);
        this.tableData = res.data.data;
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
          "/permission/findPage?" +
          "pageNo=" +
          (this.currentPage - 1) * val +
          "&pageSize=" +
          val,
      }).then((res) => {
        // console.log(res);
        this.tableData = res.data.data;
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
          "/permission/findPage?" +
          "pageNo=" +
          (val - 1) * this.pageSize +
          "&pageSize=" +
          this.pageSize,
      }).then((res) => {
        // console.log(res);
        this.tableData = res.data.data;
      });
    },
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
      addPerBox: false,
      // 为0,不显示, 为1,增加, 为2,修改
      boxHandleIndex: 0,
      deletePerBox: false,
      // 增加权限label
      addPermission: {
        id: "",
        label: "",
        code: "",
      },
      tableData: [],
    };
  },
};
</script>
