package com.imooc.o2o.util;

import java.io.File;

/**
 * 用于封住处理路径的方法
 * 该工具类的作用：
 * 1.返回项目图片的根路径
 * 2.依据不同的需求返回项目图片的子路径
 *
 * @author lixw
 * @date created in 22:42 2019/1/2
 */
public class PathUtil {
    /**
     * 在程序刚运行时候就知道系统的执行属性
     * 获取文件的分格符号file.seperator
     */

    private static String seperator = File.separator;

    /**
     * 用工具类直接大家都能用静态的直接类名点获取
     *
     * @return
     */
    public static String getImgBasePath() {
        /**
         * 获取操作系统的名称
         */
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "D:/ssmprojectdev/image/";
        } else {
            basePath = "/home/ssmproject/image/";
        }
        /**
         * 将/替换成 系统的分隔符在 所在的系统就是有效的
         */
        basePath = basePath.replace("/", seperator);
        return basePath;
    }

    /**
     * 获取店铺图片的存储路径
     * 分别存储在各自店铺的路径之下  店铺的 Id是 惟一的
     */
    public static String getShopImagePath(long shopId) {
        /**
         * 不使用根目录 容易重启服务丢失文件
         */
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/", seperator);
    }

    public static void main(String[] args) {
        System.out.println(File.separator);
    }
}
