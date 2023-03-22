package com.example.backstage.util.qiniu;

import com.example.backstage.entity.Food;
import com.example.backstage.entity.FoodType;
import com.example.backstage.entity.Goods;
import com.example.backstage.mapper.FoodMapper;
import com.example.backstage.mapper.FoodTypeMapper;
import com.example.backstage.mapper.GoodsMapper;
import com.example.backstage.util.QiNiuUtils;
import com.example.backstage.util.mini.GenerateUser;
import com.github.xiaoymin.knife4j.core.util.StrUtil;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author HuaRunSheng
 * @date 2022/11/16 22:37
 * @description :
 */
@SpringBootTest
class SavePictureTest {
    @Resource
    private FoodMapper foodMapper;

    @Resource
    private FoodTypeMapper foodTypeMapper;

    @Resource
    private GoodsMapper goodsMapper;

    @Value("${qiniu.accessKey}")
    private String accessKey;
    @Value("${qiniu.secretKey}")
    private String secretKey;
    @Value("${qiniu.bucket}")
    private String bucket;
    //// 桶的名称不能乱改
    //private static final String bucket = "foreign20221106";

    private Region region=Region.xinjiapo();
    @Test
    void main() {
        //Food food = foodMapper.findById("1");
        String imgPath="G:\\Picture\\头像\\foodType";
        //String imgPathFoodType="G:\\Picture\\头像\\foodType";
        String uploadParentPath="user_icon/";
        List<File> imageFiles = getAllFile(imgPath);
        for (File imageFile : imageFiles) {
            String fileName = imageFile.getName();
            String filePath = imageFile.getPath();

            System.out.println(fileName);
            System.out.println(filePath);

            String firstName = fileName.substring(0, fileName.lastIndexOf("."));

            //Food food = foodMapper.findById(firstName);
            FoodType food = foodTypeMapper.findByIdFoodType(Long.valueOf(firstName));
            //Goods food = goodsMapper.findById(Integer.valueOf(firstName));

            String uploadFileName = generateFileName(fileName, uploadParentPath, food.getTitle());
            System.out.println(uploadFileName);
            InputStream inputStream=null;
            try {
                inputStream= new FileInputStream(imageFile);
                upload(inputStream, uploadFileName, Region.region2());
                //food.setImageUrls(uploadFileName);
                //foodMapper.updateImage(food);

                food.setIcon(uploadFileName);
                foodTypeMapper.update(food);

                //food.setImageUrl(uploadFileName);
                //goodsMapper.update(food);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                if (inputStream !=null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
    @Test
    void foodTypeImage() {
        String imgPath="G:\\Picture\\头像\\good";
        String uploadParentPath="user_icon/";
        List<File> imageFiles = getAllFile(imgPath);
        for (File imageFile : imageFiles) {
            String fileName = imageFile.getName();
            String filePath = imageFile.getPath();

            System.out.println(fileName);
            System.out.println(filePath);

            Long firstName = Long.valueOf(fileName.substring(0, fileName.lastIndexOf(".")));

            FoodType food = foodTypeMapper.findByIdFoodType(firstName);
            String uploadFileName = generateFileName(fileName, uploadParentPath, food.getTitle());
            System.out.println(uploadFileName);
            InputStream inputStream=null;
            try {
                inputStream= new FileInputStream(imageFile);
                upload(inputStream, uploadFileName, Region.region2());
                food.setIcon(uploadFileName);
                foodTypeMapper.update(food);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }finally {
                if (inputStream !=null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }
    public UploadManager getUploadManage(Region region){
        // 构造一个指定地区的配置类,地区是自己选的服务器,也就是华南对应的是Region.region2()
        return new UploadManager(new com.qiniu.storage.Configuration(region));
    }
    // 上传
    public void upload(InputStream stream, String fileName, Region region) {
        // 东南亚Region.regionAs0()
        UploadManager uploadManager = getUploadManage(region);
        // 鉴权
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(stream, fileName, upToken, null, null);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        } catch (QiniuException e) {
            //log.error("文件上传失败");
            Response r = e.response;
        }
    }
    /**
     *
     * @param fileName
     * @param parentPath: "food_image/"
     * @return
     */
    public String generateFileName(String fileName, String parentPath, String title){

        return parentPath+title+"_"+ GenerateUser.getTimeStr()+"_"+fileName;
    }

    /**
     * 获取指定文件夹下所有文件，不含文件夹里的文件
     *
     * @param dirFilePath 文件夹路径
     * @return
     */
    public static List<File> getAllFile(String dirFilePath) {
        if (StrUtil.isBlank(dirFilePath))
            return null;
        return getAllFile(new File(dirFilePath));
    }


    /**
     * 获取指定文件夹下所有文件，不含文件夹里的文件
     *
     * @param dirFile 文件夹
     * @return
     */
    public static List<File> getAllFile(File dirFile) {
        // 如果文件夹不存在或着不是文件夹，则返回 null
        if (Objects.isNull(dirFile) || !dirFile.exists() || dirFile.isFile())
            return null;

        File[] childrenFiles = dirFile.listFiles();
        if (Objects.isNull(childrenFiles) || childrenFiles.length == 0)
            return null;

        List<File> files = new ArrayList<>();
        for (File childFile : childrenFiles) {
            // 如果是文件，直接添加到结果集合
            if (childFile.isFile()) {
                files.add(childFile);
            }
            //以下几行代码取消注释后可以将所有子文件夹里的文件也获取到列表里
//            else {
//                // 如果是文件夹，则将其内部文件添加进结果集合
//                List<File> cFiles = getAllFile(childFile);
//                if (Objects.isNull(cFiles) || cFiles.isEmpty()) continue;
//                files.addAll(cFiles);
//            }
        }
        return files;
    }

}