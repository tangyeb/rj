package com.itheima.common;

/**
 * 自定义业务异常类
 * @author 汤
 */
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}
