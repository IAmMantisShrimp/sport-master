package com.example.backstage.util;

import com.example.backstage.config.BeanConfig;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author HuaRunSheng
 * @date 2022/11/6 14:31
 * @description : 七牛云文件上传工具类
 */
@Component
@Slf4j
public class QiNiuUtils {
    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    // 桶的名称不能乱改
    @Value("${qiniu.bucket}")
    private String bucket;
    private Region region=Region.xinjiapo();
    public UploadManager getUploadManage(Region region){
        // 构造一个指定地区的配置类,地区是自己选的服务器,也就是华南对应的是Region.region2()
        return new UploadManager(new com.qiniu.storage.Configuration(region));
    }
    /**
     * 传本地文件路径下载
     * @param filePath
     * @param fileName
     */
    public void uploadQiniu(String filePath,String fileName, Region region) {
        //构造一个带指定 Region 对象的配置类
        UploadManager uploadManager = getUploadManage(region);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(filePath, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            Response r = ex.response;
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }

    /**
     * 传字节下载文件
     * @param bytes
     * @param fileName
     */
    public void uploadQiniu(byte[] bytes,String fileName) {
        //构造一个带指定 Region 对象的配置类
        UploadManager uploadManager = getUploadManage(region);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(bytes, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException ex) {
            Response r = ex.response;
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }


    /**
     * "user_icon/"
     * @param stream: 文件流
     * @param fileName: 文件名
     * @param parentPath: "user_icon/" 父路径
     * @return
     */
    public String upload(InputStream stream, String fileName, String parentPath, Region region) {
        UploadManager uploadManager = getUploadManage(region);
        // 鉴权
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        String name = parentPath + this.getName(fileName);

        try {
            Response response = uploadManager.put(stream, name, upToken, null, null);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            log.info("文件上传成功", putRet);
            return name;
        } catch (QiniuException e) {
            //log.error("文件上传失败");
            Response r = e.response;
            try {
                log.error("文件上传失败==>{}", r.bodyString());
            } catch (QiniuException qiniuException) {
                qiniuException.printStackTrace();
            }
            e.printStackTrace();
            return null;
        }
    }
    public String upload(InputStream stream, String fileName, String parentPath) {
        return upload(stream, fileName, parentPath, region);
    }
    public String upload(InputStream stream, String fileName) {
        return upload(stream, fileName, "user_icon/", region);
    }
    /**
     * 用户头像
     * @param stream
     * @param fileName
     * @return
     */
    public String uploadUserIcon(InputStream stream, String fileName) {
        return upload(stream, fileName, "user_icon/");
    }

    // 删除
    public void delete(String fileName) {
        // 构造一个指定地区的配置类,地区是自己选的服务器,也就是华南对应的是Region.region2()
        Configuration configuration = new Configuration(Region.region2());
        // 鉴权
        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, configuration);

        try {
            bucketManager.delete(bucket, fileName);
        } catch (QiniuException e) {
            // 遇到异常,删除失败
            log.error("删除失败==>{}", e.code());
            log.error(e.response.toString());
            //e.printStackTrace();
        }
    }

    /**
     * 根据时间生成文件名
     *
     * @param fileName
     * @return
     */
    public String getName(String fileName) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date()) + fileName;
    }



}
