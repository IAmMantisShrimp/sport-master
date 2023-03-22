<template>
  <div>
    <h1>测试</h1>
    <el-button type="info" v-hasRole="['ADMIN']">findById</el-button>
    <el-button type="info" v-hasRole="['ADMIN']">findAll</el-button>
    <el-button type="info" v-hasRole="['ADMIN']">findPage</el-button>
    <el-button type="info" v-hasRole="['ADMIN']">findByInfo</el-button><br/>
    <el-button type="info" v-hasRole="['ADMIN']">update</el-button>
    <el-button type="info" v-hasRole="['ADMIN']">delete</el-button>
    <el-button type="info" v-hasRole="['ADMIN']">insert</el-button>
  </div>
</template>

<script>
export default {
  name: "add"
}
</script>

<style scoped>

.el-button--info {
  color: #FFF;
  background-color: #3cc1e6;
  border-color: #909399;
}
</style>