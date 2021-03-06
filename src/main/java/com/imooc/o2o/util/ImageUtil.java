package com.imooc.o2o.util;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.websuperadmin.AreaController;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 用来封装 thumbnailator 的
 * 改变图片的大小，同时打上水印
 * 并且将图片压缩输出
 * *@param position水印的位置。
 * <p>
 * *@param image水印的图像。
 * <p>
 * *@param opacity水印的不透明度。
 *
 * @author lixw
 * @date created in 20:49 2019/1/2
 */
@Slf4j
public class ImageUtil {
    /**
     * 处理缩略图
     * 1.处理门面照片
     * 2.处理商品的小图
     * CommonsMultipartFile：spring自带的文件处理方法 String targetAddr :文件储存的地方
     *
     * @param
     */
    /**
     * 获取当前文件的路径
     */
    //private static String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    private static String basePath = "";
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random random = new Random();
    private static Logger logger = LoggerFactory.getLogger(AreaController.class);

    /**
     * 将CommonsMultipartFile文件转化为File文件
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile) {
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            logger.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }


    /**
     * //处理用户上传过来的文件流  File 转CommonsMultipartFile 困难
     * public static String generateThumbnail(CommonsMultipartFile thumbnail, String targetAddr) {
     *
     * @param
     * @param targetAddr
     * @return
     */

    public static String generateThumbnail(ImageHolder thumbnailInputStream,  String targetAddr) {
        /**
         * 不使用用户上传图片的文件名 而是使用随机的名字
         * 获取图片的扩展名:参数就是文件传输过来的文件流
         */
        String realFileName = getRandomFileName();
        String extension = getFileExtension(thumbnailInputStream.getImageName());
        //创建目录
        makeDirPath(targetAddr);
        //获取相对路径
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        System.out.println("存儲文件絕對地址："+dest.getAbsolutePath());

        try {
            Thumbnails.of(thumbnailInputStream.getImage()).size(200, 200).
                    watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File("G:\\ssmpicture\\youxiang.jpg")),
                            0.25f).outputQuality(0.8f).toFile(dest);

        } catch (IOException e) {
            throw new RuntimeException("文件创建失败");

        }


        return relativeAddr;
    }

    /**
     * 创建目录路径所涉及的目录 即/home/work/ssmpeoject/xxx.jpg,
     * 那么/home/work/ssmpeoject/需要自动创建出来
     *
     * @param targetAddr
     */
    public static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        //将输入流的路径传入 判断是不是存在这个文件夹的
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取输入流 的扩展名 ：文件格式
     *
     * @param
     * @return
     */
    public static String getFileExtension(String fileName) {

        //substring:来获取.号后面的字符
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名  当前的年月日小时分钟秒 + 五位数的随机数
     *
     * @param
     */
    public static String getRandomFileName() {
        int rannum = random.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        //整型自动转成字符串
        return nowTimeStr + rannum;
    }


    public static void main(String[] args) {
        String basePath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

        System.out.println(basePath);
        try {
            Thumbnails.of(new File("G:/ssmpicture/picture.jpg")).size(1000, 1000).watermark(Positions.BOTTOM_RIGHT,
                    //压缩成为80%  不透明度为0.25f  ImageIO.read:读取水印流
                    ImageIO.read(new File(basePath + "/youxiang.jpg")), 0.25f).
                    outputQuality(0.8f).toFile("G:/ssmpicture/picture2.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 如果需要更新店铺中的图片，那么需要删除原来的图片
     * 根据原来图片的路径删除原来的图片信息
     * 如果storePath是文件的路径就删除该文件
     * 如果storePath是目录路径就删除目录下的所有文件
     * getImgBasePath()：获取全路径
     * @param storePath
     */
    public static void deletFileOrPath(String storePath){

        File fileOrPath = new File(PathUtil.getImgBasePath()+storePath);
        if (fileOrPath.exists()){
            //1.fileOrPath 是一个目录下面的文件全部删除
            if(fileOrPath.isDirectory()){
                File files[] = fileOrPath.listFiles();
                for(int i = 0;i < files.length;i++){
                    //delete()方法是已经封装好的直接调用
                    files[i].delete();
                }
            }
            //2.如果是文件的话直接删除  如果是目录 文件删完还是要删除最后的目录的
            fileOrPath.delete();
        }

    }


    /**
     * 处理详情图，并返回新生成图片的相对值路径
     *
     * @param thumbnail
     * @param targetAddr
     * @return
     */
    public static String generateNormalImg(ImageHolder thumbnail, String targetAddr) {
        // 获取不重复的随机值
        String realFileName = getRandomFileName();
        // 获取文件的扩展名
        String extension = getFileExtension(thumbnail.getImageName());
        // 如果目标路径不存在，则自动创建
        makeDirPath(targetAddr);
        // 获取文件存储的相对路径(带文件名)
        String relativeAddr = targetAddr + realFileName + extension;
        log.debug("current relativeAddr is : " + relativeAddr);
        // 获取文件要保存的目标路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        log.debug("current complete addr is : " + PathUtil.getImgBasePath() + relativeAddr);
        // 调用Thumbnails生成带有水印的图片
        try {
            Thumbnails.of(thumbnail.getImage()).size(337, 640)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePath + "G:\\ssmpicture\\youxiang.jpg")), 0.25f)
                    .outputQuality(0.9F).toFile(dest);
        } catch (IOException e) {
            log.error(e.toString());
            throw new RuntimeException("创建缩略图片失败" + e.toString());
        }
        // 返回图片相对路径地址
        return relativeAddr;
    }

}
