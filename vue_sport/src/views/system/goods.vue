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
            >添加商品</el-button
          >
        </el-col>
        <el-col :span="2">
          <!-- action上传路径 multiple是否支持多选 beforeUpload上传之前 handleSuccess上传成功 -->
          <el-upload
            class="upload-demo"
            :action="uploadUrl"
            :headers="headers"
            name="goods"
            :multiple="false"
            :show-file-list="false"
            :on-success="handleSuccess"
            :before-upload="beforeUpload"
          >
            <el-button type="warning">批量导入</el-button>
          </el-upload>
        </el-col>
        <el-col :span="2">
          <el-button type="success" @click="batchExport">批量导出</el-button>
        </el-col>
      </el-row>
      <el-table :data="tableData" style="width: 100%">
        <el-table-column label="商品名称" prop="name" />
        <el-table-column label="商品价格" prop="price" />
        <el-table-column label="库存数量" prop="number" />
        <el-table-column label="商品图片">
          <template slot-scope="props">
            <div class="demo-image__preview">
              <el-image
                style="width: 100px; height: 100px"
                :src="props.row.imageUrl"
                :preview-src-list="props.row.imageUrl"
              />
            </div>
          </template>
        </el-table-column>
        <el-table-column label="特色功能" prop="characteristic" />
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
              @click="deleteById(scope.row.id)"
            />
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页 -->
      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="queryInfo.pageNumber"
        :page-sizes="[3, 10, 20, 50]"
        :page-size="queryInfo.pageSize"
        layout="total, sizes, prev, pager, next, jumper"
        :total="total"
      />
    </el-card>
  </div>
</template>

<script>
export default {
  name: "SysGoods",
  data() {
    return {
      queryInfo: {
        pageNumber: 1,
        pageSize: 3,
        queryString: "",
      },
      uploadUrl: "/goods/batchImport",
      headers: {
        Authorization: sessionStorage.getItem("token"),
      },
      tableData: [],
      total: 0,
    };
  },
  created() {
    this.findPage();
  },
  methods: {
    async findPage() {
      console.log(this.queryInfo);
      await this.$ajax.post("/goods/findPage", this.queryInfo).then((res) => {
        if (!res.data.flag) return this.$message.error(res.data.message);
        this.tableData = res.data.data.rows;
        this.total = res.data.data.total;
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
    beforeUpload(file) {
      let suffix = file.name.substring(file.name.lastIndexOf(".") + 1);
      if (suffix !== "xlsx" && suffix !== "xls") {
        this.$message.warning("请选择excel文件");
        return false;
      }
    },
    handleSuccess(res) {
      if (!res.flag) return this.$message.error(res.message);
      this.$message.success(res.data.message);
      this.findPage();
    },
    batchExport() {
      window.location.href = "http://localhost:9000/vue/goods/batchExport";
    },
    showEditDialog(row) {},
    deleteById(id) {},
  },
};
</script>

<style lang="less" scoped></style>
