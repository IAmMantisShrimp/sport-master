import Vue from "vue";

/**
 * directive自定义指令的方法
 * 第一个参数是指令名称
 * 第二个实现方法
 */
Vue.directive("hasRole", {
  /**
   *
   * @param el：用于直接操作 DOM，表示指令绑定到的元素
   * @param binding对象：包含以下6个属性
   *                    instance:使用指令的组件实例
   *                    value: 传递给指令的值
   *                    oldValue:先前的值
   *                    arg:传递给指令的参数
   *                    modifiers: 传递给指令的修饰符
   *                    dir: 一个对象,其实就是注册指令时传递的配置对象.
   */
  inserted(el, binding) {
    //    获取指令传递进来的参数
    const { value } = binding;
    // console.log("value: ",value);
    // 获取vue中的角色信息
    const roles = JSON.parse(sessionStorage.getItem("roles"));
    // console.log("roles:", roles);
    //    定义超级管理员可以查看所有
    const admin="SUPER_ADMIN";
    // 判断指令是否传值,传递的值是否是一个数组,数组是否大于0
    if (value && value instanceof Array && value.length > 0){
      /**
       * some用于检测数组中的元素是否满足指定的条件,并不会改变原来的数组,返回true/false
       * 因为这里roles只是一个对象,不是数组,所以不能用some方法
       */
      // const hasRole=roles.some(item=>{
      //   /**
      //    * includes: 用于判断字符串是否包含指定的字符串
      //    */
      //   return item.code === admin || value.includes(item.code);
      // });

      // 如果没有该权限,比如roles是个普通用户,且value没有设置SAMPLE,则标签会删除
      if (!(roles.code === admin || value.includes(roles.code))){
        // 把对应的元素删除,但是el元素不能删除自己,所以要先定位到父元素,再删除当前元素
        el.parentNode.removeChild(el);
      }
    }
  },
});
