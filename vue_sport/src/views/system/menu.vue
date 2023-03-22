<template>
  <div>
    <h1>文件上传测试</h1>
    <!--  action: 上传地址,
    show-file-list: 是否展示列表
    list-type="picture"如果不是图片,file只有uid没有url
    :limit="1" 只上传一个文件
    :auto-upload="false" 自动上传
    :on-change="addFile" 文件状态改变时的钩子，添加文件、上传成功和上传失败时都会被调用
    on-preview	点击文件列表中已上传的文件时的钩子	function(file)
    -->
    <div class="avatar-upload">
      <el-row style="height: 100%">
        <el-col :span="5">
          <el-upload
            class="upload-demo"
            style="height: 80px; line-height: 0; margin-top: 20px"
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
            <el-button slot="trigger" size="big" type="primary"
              >选取文件</el-button
            >
          </el-upload>
        </el-col>
        <el-col :span="5" style="line-height: 0">
          <el-image
            v-show="uploadImage != null"
            style="width: 100px; height: 80px"
            :src="uploadImage != null ? uploadImage.url : ''"
            fit="contain"
          ></el-image>
        </el-col>
      </el-row>
    </div>
    <el-button
      style="margin-left: 10px"
      size="big"
      type="success"
      @click="submitUpload"
      >上传到服务器</el-button
    >
  </div>
</template>

<script>
export default {
  name: "menu",
  data() {
    return {
      action: "/tool/upload",
      headers: {
        Authorization: sessionStorage.getItem("token"),
      },
      dialogImageUrl: "",
      dialogVisible: false,
      fileList: [],
      uploadImage: null,
    };
  },
  methods: {
    // 上传图片
    submitUpload() {
      this.$refs.upload.submit();
    },

    uploadSuccess(response, file, fileList) {
      // this.imageUrl = this.domainName + response.data;
      // this.avatarShow = true;
      // console.log("imageurl: ", this.domainName + response.data);
      // console.log(response);
      this.$refs.upload.clearFiles();
    },
    addFile(file, fileList) {
      // fileList 为上传的文件,因为只需要一个头像,所以fileList的length始终为1
      // https://www.php.cn/vuejs/483459.html :this.$delete(fileList, 1);这样删能把长度也删掉,而不是简单的赋值为null
      this.uploadImage = file;
      // console.log("file: ", file);
      // console.log("fileList: ", fileList);
      if (fileList.length > 1) {
        fileList[0] = fileList[1];
        this.$delete(fileList, 1);
      }
    },
  },
};
</script>

<style scoped>
.avatar-upload {
  height: 80px;
}
</style>
