module.exports = {
  // 是否取消代码检查
  lintOnSave: false,
  //  配置反向代理
  devServer: {
    // 设置端口信息
    port: 9091,
    // 设置是否启动时打开浏览器
    // open: true,
    // 代理
    proxy: {
      // port: 8080, //前端服务启动的端口号
      // host: "localhost", //前端服务启动后的访问ip，默认为localhost, host和port组成了前端服务启动后的访问入口。
      // https: false,
      // open: true,
      //凡是/service开头的请求,代理到target域名地址下
      //方式二:可以将开头部分去除掉
      "/back": {
        //win服务器
        // target: "http://43.143.196.105:9090",
        //Linux服务器
        // target: "http://1.15.155.85:9090",
        //服务器域名
        // target: "http://bigtruck.love:9090",
        //本地后端
        target: "http://localhost:9090",
        changeOrigin: true, //是否跨域
        pathRewrite: {
          //要替换数据(支持正则表达式) : 替换为数据
          "^/back": "",
          //  ^ 为以什么开头,替换成空字符串
        },
      },
    },

  },
  // vue打包配置

  // 配置公共路径(必须的)
  // publicPath: "./",
  // publicPath: "/",
  // 打包到哪个文件夹
  // outputDir: "dist",
  // //将静态资源打包
  // assetsDir: "static",
  // // 去除打包后js的map文件
  // productionSourceMap: false,
  // 去除console
  // configureWebpack: (config)=>{
  //   // 判断是在什么环境,用户环境就不打印
  //   const option = this.arg.option.terserOptions.compress;
  //   // 打印开关
  //   option.drop_console = true;
  //   return this.arg;
  // }

  // chainWebpack(config) {
  //   config.optimization.minimizer('terser').tap((args) => {
  //     args[0].terserOptions.compress.drop_console = true
  //     return args
  //   })
  // }
};
