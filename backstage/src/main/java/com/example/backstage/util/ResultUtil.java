package com.example.backstage.util;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: HuaRunSheng
 * @date: 2022/5/16 11:55
 */
@Data
@ApiModel(value = "相应参数")
public class ResultUtil implements Serializable {
    /**
     * 响应前端是否成功的标识
     */
    @ApiModelProperty(value = "是否成功标志",dataType = "boolean")
    private boolean flag;
    /**
     * 响应信息
     */
    @ApiModelProperty(value = "响应信息",dataType = "String")
    private String message;
    @ApiModelProperty(value = "响应成功信息",dataType = "String")
    private static String defaultSuccessMessage="响应成功";
    @ApiModelProperty(value = "响应失败信息",dataType = "String")
    private static String defaultFailMessage="响应失败";
    /**
     * 响应数据
     */
    @ApiModelProperty(value = "响应数据",dataType = "Object")
    private Object data;

    public ResultUtil() {
    }
    public ResultUtil(boolean flag, String message) {
        this.flag = flag;
        this.message = message;
    }
    public ResultUtil(boolean flag, String message, Object data) {
        this.flag = flag;
        this.message = message;
        this.data = data;
    }

    // 相应成功
    public static ResultUtil success(Object data){
        return new ResultUtil(true,defaultSuccessMessage,data);
    }

    // 响应失败
    public static ResultUtil fail(){
        return new ResultUtil(false, defaultFailMessage);
    }

    // 相应成功
    public static ResultUtil success(String message,Object data){
        return new ResultUtil(true,message,data);
    }

    // 响应失败
    public static ResultUtil fail(String message){
        return new ResultUtil(false,message);
    }
}
