//package com.example.backstage.util.qiniu;
//
//import com.example.backstage.entity.Food;
//import com.example.backstage.mapper.FoodMapper;
//import com.example.backstage.util.mini.GenerateUser;
//import com.github.xiaoymin.knife4j.core.util.StrUtil;
//import com.google.gson.Gson;
//import com.qiniu.common.QiniuException;
//import com.qiniu.http.Response;
//import com.qiniu.storage.Region;
//import com.qiniu.storage.UploadManager;
//import com.qiniu.storage.model.DefaultPutRet;
//import com.qiniu.util.Auth;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
///**
// * @author HuaRunSheng
// * @date 2022/11/16 21:30
// * @description :
// */
//@Service
//public class SavePicture {
//
//    @Value("${qiniu.accessKey}")
//    private String accessKey;
//    @Value("${qiniu.secretKey}")
//    private String secretKey;
//    // 桶的名称不能乱改
//    private static final String bucket = "foreign20221106";
//    public void main() {
//
//    }
//    public UploadManager getUploadManage(Region region){
//        // 构造一个指定地区的配置类,地区是自己选的服务器,也就是华南对应的是Region.region2()
//        return new UploadManager(new com.qiniu.storage.Configuration(region));
//    }
//
//    /**
//     * 传本地文件路径下载
//     * @param filePath: 本地文件
//     * @param fileName: 上传文件名
//     *
//     */
//    public void uploadQiniu(String filePath,String fileName) {
//        //构造一个带指定 Region 对象的配置类
//        UploadManager uploadManage = getUploadManage(Region.regionAs0());
//        Auth auth = Auth.create(accessKey, secretKey);
//        String upToken = auth.uploadToken(bucket);
//
//        try {
//            Response response = uploadManage.put(filePath, fileName, upToken);
//            //解析上传成功的结果
//            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//        } catch (QiniuException ex) {
//            Response r = ex.response;
//            try {
//                System.err.println(r.bodyString());
//            } catch (QiniuException ex2) {
//                //ignore
//            }
//        }
//    }
//
//    /**
//     *
//     * @param fileName
//     * @param parentPath: "food_image/"
//     * @return
//     */
//    public String generateFileName(String fileName, String parentPath){
//        String firstName = fileName.substring(0, fileName.lastIndexOf("."));
//        Food food = foodMapper.findById("1");
//        return parentPath+food.getTitle()+GenerateUser.getTimeStr()+"_"+fileName;
//    }
//
//    /**
//     * 获取指定文件夹下所有文件，不含文件夹里的文件
//     *
//     * @param dirFilePath 文件夹路径
//     * @return
//     */
//    public static List<File> getAllFile(String dirFilePath) {
//        if (StrUtil.isBlank(dirFilePath))
//            return null;
//        return getAllFile(new File(dirFilePath));
//    }
//
//
//    /**
//     * 获取指定文件夹下所有文件，不含文件夹里的文件
//     *
//     * @param dirFile 文件夹
//     * @return
//     */
//    public static List<File> getAllFile(File dirFile) {
//        // 如果文件夹不存在或着不是文件夹，则返回 null
//        if (Objects.isNull(dirFile) || !dirFile.exists() || dirFile.isFile())
//            return null;
//
//        File[] childrenFiles = dirFile.listFiles();
//        if (Objects.isNull(childrenFiles) || childrenFiles.length == 0)
//            return null;
//
//        List<File> files = new ArrayList<>();
//        for (File childFile : childrenFiles) {
//            // 如果是文件，直接添加到结果集合
//            if (childFile.isFile()) {
//                files.add(childFile);
//            }
//            //以下几行代码取消注释后可以将所有子文件夹里的文件也获取到列表里
////            else {
////                // 如果是文件夹，则将其内部文件添加进结果集合
////                List<File> cFiles = getAllFile(childFile);
////                if (Objects.isNull(cFiles) || cFiles.isEmpty()) continue;
////                files.addAll(cFiles);
////            }
//        }
//        return files;
//    }
//
//
//}
