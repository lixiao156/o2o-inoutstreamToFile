package com.imooc.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * 验证码比对 前端验证码与后端进行比对
 * @author lixw
 * @date created in 18:01 2019/1/5
 */
public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request) {
        /**
         * 获取前端传递过来的会话的里面的信息Attribute ：属性的意思
         */
        String verifyCodeExpected = (String) request.getSession().getAttribute(
                Constants.KAPTCHA_SESSION_KEY
        );
        //工具类 将实际获取的值转化成为String 类型
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");

        //看实际代码是不是为空 或者二者不相等
        if ( verifyCodeActual.toUpperCase() == null || ! verifyCodeActual.toUpperCase().equals(verifyCodeExpected)){
                return false;
        }
        return true;
        //将工具类引入到店铺注册里面去
    }
}
