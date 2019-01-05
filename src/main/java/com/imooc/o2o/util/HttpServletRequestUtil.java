package com.imooc.o2o.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理HttpServletRequest信息的工具类
 *
 * @author lixw
 * @date created in 23:08 2019/1/3
 */
public class HttpServletRequestUtil {
    /**
     *HttpServletRequest request 会有个各种类型的key  将其转化成我们需要的类型
     */
    /**
     * 将key转化为整型
     *
     * @param request
     * @param key
     * @return
     */
    public static int getInt(HttpServletRequest request, String key) {
        try {
            return Integer.decode(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 将key转化为长整型
     *
     * @param request
     * @param key
     * @return
     */
    public static long getLong(HttpServletRequest request, String key) {
        try {
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    /**
     * 试着将请求里面的键key值转化为Double类型
     *
     * @param request
     * @param key
     * @return
     */
    public static Double getDouble(HttpServletRequest request, String key) {
        try {
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1d;
        }
    }

    /**
     * 将request中的键位 转化为Boolean 如果转化失败那么就是返回一个false
     *
     * @param request
     * @param key
     * @return
     */
    public static boolean getBoolean(HttpServletRequest request, String key) {
        try {
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return false;
        }
    }


    public static String getString(HttpServletRequest request, String key) {
        try {
            String result = request.getParameter(key);
            if (result != null) {
                //如果result不为空将result两端的空格去掉
                result = result.trim();
            }
            if ("".equals(result)) {
                result = null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }
}
