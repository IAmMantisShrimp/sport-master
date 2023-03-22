package com.example.backstage.config;

import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author HuaRunSheng
 * @date 2022/11/8 19:45
 * @description :
 */
@Configuration
public class BeanConfig {
    // new 一个对象,就会在堆里面生成一个对象信息,为了节约资源,注入到springBoot容器里,只需new一次
    @Bean
    public UploadManager uploadManager(){
        // 构造一个指定地区的配置类,地区是自己选的服务器,也就是华南对应的是Region.region2()
        com.qiniu.storage.Configuration configuration = new com.qiniu.storage.Configuration(Region.region2());
        return new UploadManager(configuration);
    }
}
