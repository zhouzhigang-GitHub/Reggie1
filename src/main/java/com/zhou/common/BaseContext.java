package com.zhou.common;

/**
 * @Author 周志刚
 * @Date 2022/8/14 12:08
 * @PackageName: com.zhou.common
 * @ClassName: BaseContext
 * @Description: 基于ThreadLocal封装的工具类，获取当前用的id
 */
public class BaseContext {

    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setId(Long id){
        threadLocal.set(id);
    }

    public static Long getId(){
        return threadLocal.get();
    }
}

