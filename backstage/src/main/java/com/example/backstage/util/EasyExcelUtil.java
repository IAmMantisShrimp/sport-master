package com.example.backstage.util;

//import com.ajie.constant.MessageConstant;
//import com.ajie.result.Result;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.metadata.WriteSheet;
import org.yaml.snakeyaml.util.UriEncoder;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 对excel操作的工具类
 * @Author 阿杰
 * @create 2021-03-31 15:50
 */
public class EasyExcelUtil {

    /**
     * 读取excel
     * @param file
     * @param model
     * @param <T>
     * @return
     */
    public static<T> List<T> readExcel(InputStream file, Class<T> model) {
        List<T> list = new ArrayList<>();
        EasyExcel
                //读取的文件
                .read(file)
                //反射获取类型
                .head(model)
                //excel类型
                .excelType(ExcelTypeEnum.XLSX)
                //读取的excel左下角的名字
                .sheet(0)
                //注册监听器,每次读取一行
                .registerReadListener(new AnalysisEventListener<T>() {
                    @Override
                    public void invoke(T t, AnalysisContext analysisContext) {
                        list.add(t);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
                        System.out.println("读取完毕" + model);
                    }
                }).doRead();
        return list;
    }
}
