package com.example.backstage.exception;

import com.example.backstage.util.ResultUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author HuaRunSheng
 * @date 2022/5/18 14:52
 * @description :全局异常
 */
@RestControllerAdvice
public class GlobalException {
    // 500错误,代码错误
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = RuntimeException.class)
    public ResultUtil exception(RuntimeException e){
        System.out.println("系统运行时异常-->" + e.getMessage());
        return ResultUtil.fail(e.getMessage());
    }
    // 403错误,权限不足
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value= AccessDeniedException.class)
    public ResultUtil exception(AccessDeniedException e){
        System.out.println("权限不足-->" + e.getMessage());
        return ResultUtil.fail("权限不足,请联系管理员!");
    }
}
