<template>
  <div id="app">
    <router-link to="/login"></router-link>
    <keep-alive>
      <router-view></router-view>
    </keep-alive>
  </div>
</template>

<script>

export default {
  name: "app",
  components: {
  },
};
</script>

<style>
#app {
  height: 100%;
}
</style>
