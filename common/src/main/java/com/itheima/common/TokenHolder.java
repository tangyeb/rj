package com.itheima.common;

/**
 * 基于ThreadLocal封装工具类，用户保存和获取当前登录用户id
 *
 * @author tongdulong@itcas.cn
 */
public class TokenHolder {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    /**
     * 设置值
     *
     * @param id id
     */
    public static void setCurrentId(String id) {
        threadLocal.set(id);
    }

    /**
     * 获取值
     *
     * @return Long
     */
    public static String getCurrentId() {
        return threadLocal.get();
    }

    /**
     * 删除值
     */
    public static void removeCurrentId() {
        threadLocal.remove();
    }
}