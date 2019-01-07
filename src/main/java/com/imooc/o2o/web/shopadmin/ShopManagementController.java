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
import com.imooc.o2o.util.CodeUtil;
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

    @RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopById(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //shopId 是前端传过来的  是用来获取店铺信息的
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId > -1) {
            //获取店铺的信息
            try {
                Shop shop = shopService.getByShopId(shopId);
                List<Area> areaList = areaService.getAreaList();
                modelMap.put("shop", shop);
                modelMap.put("areaList", areaList);
                modelMap.put("success", true);
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                e.printStackTrace();

            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }

        return modelMap;

    }

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
        //引入验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入正确验证码");
            return modelMap;
        }

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
            //将前端传入过来的shopStr对象转化为一个shop实体类
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
            /**
             * 服务器和每个浏览器之间可以创建不同的会话Session对象
             * 服务器中的信息可以保存在浏览器中独有的session 中
             * 浏览器访问服务器会携带session的Id
             * Session用来保存用户信息和店铺的信息  Session默认是30min不做任何操作失效
             */
            /**
             * getAttribute("user")中的user是从哪里来的呢
             * 在我们登录的时候可以将用户的信息写入Session里面
             * 他的key是user  登录后可以从session中取出来
             */
            PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");

            //可以获取session中用户的登录信息就不用再自己添加默认的1L了
            //owner.setUserId(1L);
            shop.setOwner(owner);
            //转化文件的格式
            File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());


            //前端传递过来的信息存储到shop里面去  addShop(shop,shopImg);需要将CommonsMultipartFile转化为file类型，看源码中的转化方法
            ShopExecution se = null;
            try {
                se = shopService.addShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                //状态和
                if (se.getState() == ShopSateEnum.CHECK.getState()) {
                    modelMap.put("success", true);
                    /**
                     * 因为一个用户可以创建多个店铺 这里需要一个店铺列表
                     * 一旦店铺创建成功就将其保存在session中
                     * 下面表示该用户可以操作的用户列表
                     */
                    List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
                    /**
                     * 取出来了就将其添加进去
                     */
                    //如果第一次创建店铺
                    if (shopList == null || shopList.size() == 0) {
                        shopList = new ArrayList<>();
                    }
                    //不是第一次创建
                    shopList.add(se.getShop());
                    request.getSession().setAttribute("shopList", shopList);


                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", se.getStateInfo());

                }

            } catch (IOException e) {
                e.printStackTrace();
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }
            return modelMap;

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "清输入店铺信息");
            return modelMap;
        }
    }


    @RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        //引入验证码
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入正确验证码");
            return modelMap;
        }

        /**
         * 1.接收并转化接收相应的参数，包括店铺信息以及图片信息
         * 需要新建一个工具类去解析HttpServletRequest的信息
         * 2.修改店铺
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
            //实体类转json对象
            //将前端传入过来的shopStr对象转化为一个shop实体类
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
        //2.修改店铺  判断实例是不是为空
        //在修改店铺的部分  图片是可传可不传输 但是需要保证 更新对象的shopId 不为空
        if (shop != null && shop.getShopId() != null) {
            //由于前段传递过来的信息可能是不可靠的，需要竟可能的减少使用前段传递的信息 使用后端获取
            //使用session获取店主的信息

            PersonInfo owner = new PersonInfo();
            owner.setUserId(1L);
            shop.setOwner(owner);
            //转化文件的格式
            File shopImgFile = new File(PathUtil.getImgBasePath() + ImageUtil.getRandomFileName());


            //前端传递过来的信息存储到shop里面去  addShop(shop,shopImg);需要将CommonsMultipartFile转化为file类型，看源码中的转化方法
            ShopExecution se = null;
            try {
                if (shopImg == null) {
                    se = shopService.modifyShop(shop, null, null);
                } else {
                    se = shopService.modifyShop(shop, shopImg.getInputStream(), shopImg.getOriginalFilename());
                    //状态和注册店铺不一样 因为 注册的时候需要审核 而修改一般信息是不需要审核的 默认是SUCCESS
                    if (se.getState() == ShopSateEnum.SUCCESS.getState()) {
                        modelMap.put("success", true);
                    } else {
                        modelMap.put("success", false);
                        modelMap.put("errMsg", se.getStateInfo());

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                modelMap.put("success", false);
                modelMap.put("errMsg", e.getMessage());
            }


            return modelMap;

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入店铺的Id");
            return modelMap;
        }
    }


    @RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopList(HttpServletRequest request) {
        //Map用来存用户的信息
        Map<String, Object> modelMap = new HashMap<>();

        PersonInfo user = new PersonInfo();
        user.setUserId(1L);
        user.setName("test");
        request.getSession().setAttribute("user", user);

        user = (PersonInfo) request.getSession().getAttribute("user");
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(user);
            //一个用户有一百个商铺已经很不错了
            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
            //信息返回给前台
            modelMap.put("shopList", se.getShopList());
            modelMap.put("user", user);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }

        return modelMap;
    }

    @RequestMapping(value = "/getShopmanagementinfo", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();

        long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        //如果前端网页没有传一个shopId
        if (shopId < 0) {
            //试图从浏览器的session中获取
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                //如果还是为空那就重定向  redirect重定向设置为true
                result.put("redirect", true);
                result.put("url", "/o2o/shopadmin/shoplist");
            } else {
                Shop currentShop = (Shop) currentShopObj;
                result.put("redirect", false);
                result.put("shopId", currentShop.getShopId());
            }
        } else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            result.put("redirect", false);
        }

        return result;
    }


    /**
     * 可以将图片的输入流直接传给Spring框架下的CommonsMultipartFile处理
     * 将inputStream转化位File文件
     * 需要每次生成File空白文件 还需将输入的文件流写入到新创建的文件中
     * 可能产生垃圾文件，还可可能写入失败，增加系统的不稳定性
     * @param ins
     * @param file
     */
//    private static void inputStreamToFile(InputStream ins, File file) {
//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream(file);
//            int bytesRead = 0;
//            //需要用这个buffer读入输入流的数据  1024个字节
//            byte[] buffer = new byte[1024];
//            //需要读buffer里面的数据
//            while ((bytesRead = ins.read(buffer)) != -1) {
//                //只要buffer不为空需要将buffer里面的数据写入输出流 每满1024字节输出一次直到读完为止
//                os.write(buffer, 0, bytesRead);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//            throw new RuntimeException("调用inputStreamToFile 产生异常" + e.getMessage());
//        } finally {
//            //防止溢出需要将输入流与输出流关闭
//            try {
//                if (os != null) {
//                    os.close();
//
//                }
//                if (ins != null) {
//                    ins.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//                throw new RuntimeException("关闭io 产生异常" + e.getMessage());
//
//            }
//        }
//    }

}
