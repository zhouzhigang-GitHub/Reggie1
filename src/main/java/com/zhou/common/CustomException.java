package com.zhou.common;

/**
 * @Author 周志刚
 * @Date 2022/8/14 16:12
 * @PackageName: com.zhou.common
 * @ClassName: CustomExcption
 * @Description: TODO
 */
public class CustomException extends RuntimeException {
    public CustomException(String message){
        super(message);
    }
}
