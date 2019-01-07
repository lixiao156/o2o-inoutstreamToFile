package com.imooc.o2o.util;

/**
 * 将前端需要按页显示与后端的按照返回行数数量格式显示转化的工具类
 * @author lixw
 * @date created in 17:13 2019/1/7
 */
public class PageCalculator {

    public static int calculateRowIndex(int pageIndex, int pageSize) {
        //意思就是页数大于0的情况下 就是从 pageIndex - 1 乘以 pageSize 下开始算到 加上pageSize位置
        //否则则是pageIndex = 0，就是从0开始算
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }

}
