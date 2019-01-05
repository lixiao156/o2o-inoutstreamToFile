package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.PersonInfo;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.enums.ShopSateEnum;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现对店铺逻辑的管理
 *
 * @author lixw
 * @date created in 22:59 2019/1/3
 */
@Controller
//访问路径
@RequestMapping("/shopadmin")
public class ShopManagementController {

    @Autowired
    private ShopService shopService;
    /**
     * 获取区域及分类信息返回给前台
     */
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopInitInfo() {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        List<ShopCategory> shopCategoryList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        //传入一个空的对象 获取全部的分类信息
        try {
            shopCategoryList = shopCategoryService.getShopCategoryList(new ShopCategory());
            //获取全部的地域列表信息
            areaList = areaService.getAreaList();
            //如果查询成功
            modelMap.put("shopCategoryList", shopCategoryList);
            modelMap.put("areaList", areaList);
            //成功情况下 success 设置为true
            modelMap.put("success", true);

        } catch (Exception e) {
            //遇到异常 success为false
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            e.printStackTrace();
        }
        //将这个方法得到的值存储在modelMap中返回
        return modelMap;

    }
    /**
     * 表单数据比较重要需要Post
     * //ResponseBody： 需要将键值对转化为Json表单
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/registershop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> registerShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        /**
         * 1.接收并转化接收相应的参数，包括店铺信息以及图片信息
         * 需要新建一个工具类去解析HttpServletRequest的信息
         * 2.注册店铺
         * 3.返回结果
         */
        /**
         * 需要和前端约定好需要向前端传入一个shopStr字符串
         * 1.接收并转化接收相应的参数，包括店铺信息以及图片信息
         * 接收前端传过来的字符串的信息  将其转化成 实体类shop；类
         */
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        /**
         * 根据git中介绍  new 一个ObjectMapper
         */
        ObjectMapper mapper = new ObjectMapper();
        Shop shop = null;

        try {
            //将实体类转化成json对象
            shop = mapper.readValue(shopStr, Shop.class);
        } catch (IOException e) {
            e.printStackTrace();
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        /**
         * 将前端传过来的图片信息存到  shopImg = (CommonsMultipartFile) multipartH 中的shopImg中
         */
        //处理图片的相关信息
        CommonsMultipartFile shopImg = null;
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
                //需要从request的本次会话的上下文去获取相关文件上传的内容
                request.getSession().getServletContext()
        );
        //需要判断request有上传的文件没有
        if (commonsMultipartResolver.isMultipart(request)) {
            //有文件名需要将文件进行转换
            MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
            //shopImg；提取文件流  CommonsMultipartFile：是spring 能够处理的文件流 getFile("shopImg")：前端上传过来的
            shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "上传图片不能为空");
            return modelMap;
        }
        //2。注册店铺  判断实例是不是为空
        if (shop != null && shopImg != null) {
            //由于前段传递过来的信息可能是不可靠的，需要竟可能的减少使用前段传递的信息 使用后端获取
            //使用session获取店主的信息
            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwner(owner);
            //转化文件的格式
            File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());
            try {
                shopImgFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                //一旦失败 停止 返回相应的结果
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;

            }
            try {
                inputStreamToFile(shopImg.getInputStream(), shopImgFile);
            } catch (IOException e) {
                e.printStackTrace();
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
            //前端传递过来的信息存储到shop里面去  addShop(shop,shopImg);需要将CommonsMultipartFile转化为file类型，看源码中的转化方法
            ShopExecution se = shopService.addShop(shop, shopImgFile);
            //状态和
            if (se.getState() == ShopSateEnum.CHECK.getState()) {
                modelMap.put("success", true);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", se.getStateInfo());
                return modelMap;
            }
            return modelMap;
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "清输入店铺信息");
            return modelMap;
        }
    }

    private static void inputStreamToFile(InputStream ins, File file) {
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            int bytesRead = 0;
            //需要用这个buffer读入输入流的数据  1024个字节
            byte[] buffer = new byte[1024];
            //需要读buffer里面的数据
            while ((bytesRead = ins.read(buffer)) != -1) {
                //只要buffer不为空需要将buffer里面的数据写入输出流 每满1024字节输出一次直到读完为止
                os.write(buffer, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("调用inputStreamToFile 产生异常" + e.getMessage());
        } finally {
            //防止溢出需要将输入流与输出流关闭
            try {
                if (os != null) {
                    os.close();

                }
                if (ins != null) {
                    ins.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("关闭io 产生异常" + e.getMessage());

            }
        }
    }
}
