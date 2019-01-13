package com.imooc.o2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.CodeUtil;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品操作controller层
 * @author lixw
 * @date created in 18:08 2019/1/9
 */

@Controller
@RequestMapping(value = "/shopadmin")
public class ProductManagementController {

    @Autowired
    private ProductService productService;
    @Autowired
    private ProductCategoryService productCategoryService;


    /**
     * 支持上传商品详情图的最大数量
     */
    private static final int IMAGE_MAX_COUNT = 6;

    /**
     * request中的返回值得形式是一个map的形式
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProduct(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<>();
        /**
         * 验证码是否与自己填写的相符
         */
        if (!CodeUtil.checkVerifyCode(request)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "输入了错误的验证码");
            return modelMap;
        }
        // 接受前端参数的变量的初始化，包装商品、缩略图、详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        //商品的信息是由前端由json转化为String的形式
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        ImageHolder thumbnail = null;
        //保存图片文件流以及他的名字列表
        List<ImageHolder> productImgList = new ArrayList<>();
        //multipartResolver：是从requestSession中获取文件流的
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        try {
            // 若请求中存在文件流，则取出相关的文件
            if (multipartResolver.isMultipart(request)) {
                //将文件流处理并保存到ImageHolder对象之中
                thumbnail = handleImage(request, productImgList);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "上传图片不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        try {
            // 尝试获取前端传过来的表单string流，并将其转换成Product实体类
            //readValue 属于json 依赖的jar中的方式方法
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.toString());
            return modelMap;
        }
        // 若Product信息、缩略图以及详情图里诶包为空，则开始进行商品添加操作
        if (product != null && thumbnail != null && productImgList.size() > 0) {
            try {
                // 从session中获取当前店铺的id并赋值给product，减少对前端数据的依赖
                Shop shopId = (Shop) request.getSession().getAttribute("shopId");
                product.setShop(shopId);
                // 执行添加操作
                ProductExecution pe = productService.addProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请输入商品信息");
        }
        return modelMap;
    }

    private ImageHolder handleImage(HttpServletRequest request, List<ImageHolder> productImgList) throws IOException {
        MultipartHttpServletRequest multipartRequest;
        ImageHolder thumbnail;
        multipartRequest = (MultipartHttpServletRequest) request;
        // 取出缩略图并构建ImageHolder对象
        CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
        //获取文件以及文件流的文件名将其保存到ImageHolder对象中
        thumbnail = new ImageHolder(thumbnailFile.getInputStream(), thumbnailFile.getOriginalFilename());
        // 取出详情图列表并构建List<ImageHolder>列表对象，最多支持6张图片上传
        for (int i = 0; i < IMAGE_MAX_COUNT; i++) {
            CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
            if (productImgFile != null) {
                // 若取出的详情图片文件流不为空，则将其加入详情图列表
                ImageHolder productImg = new ImageHolder(productImgFile.getInputStream(),
                        productImgFile.getOriginalFilename());
                productImgList.add(productImg);
            } else {
                // 若取出的详情图片文件流为空，则终止循环
                break;
            }
        }
        return thumbnail;
    }

    /**
     * 出现默认的shopId = 31打印情况
     * 实现根据产品所在店铺的id查出来他的产品分类列表信息
     * @param productId
     * @return
     */
    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductById(@RequestParam Long productId) {
        Map<String, Object> result = new HashMap<>();
        if (productId > 0) {
            Product product = productService.getProductById(productId);
            System.out.println("------------------------------");
            System.out.println(product.toString());
            System.out.println("------------------------------");

            if (product != null) {
                System.out.println(product.getShop().getShopId());
                //根据产品所在店铺的id查出来他的产品分类列表信息
                List<ProductCategory> productCategoryList = productCategoryService
                        .getProductCategoryList(product.getShop().getShopId());
                result.put("product", product);
                result.put("productCategoryList", productCategoryList);
                result.put("success", true);
            } else {
                result.put("product", null);
                result.put("productCategoryList", null);
                result.put("success", true);
            }
        } else {
            result.put("success", false);
            result.put("errMsg", "empty productId");
        }
        return result;
    }

    @RequestMapping(value = "/modifyproduct", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> modifyProduct(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        // 区分是商品编辑还是上下架商品
        boolean statusChange = HttpServletRequestUtil.getBoolean(request, "statusChange");
        // 验证码
        if (!statusChange && !CodeUtil.checkVerifyCode(request)) {
            result.put("success", false);
            result.put("errMsg", "输入了错误的验证码");
            return result;
        }
        // 接受前端参数变量初始化，包括商品、缩略图、详情图列表
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        ImageHolder thumbnail = null;
        List<ImageHolder> productImgList = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext());
        // 若请求中存在文件流，则去除相关的文件

        try {
            if (multipartResolver.isMultipart(request)) {
                thumbnail = handleImage(request, productImgList);
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("errMsg", e.toString());
            return result;
        }
        try {
            String productStr = HttpServletRequestUtil.getString(request, "productStr");
            // 尝试获取前端传过来的表单string流，并将其转换成Product实体类
            product = mapper.readValue(productStr, Product.class);
        } catch (Exception e) {
            result.put("success", false);
            result.put("errMsg", e.toString());
            return result;
        }
        // 若Product信息、缩略图以及详情图里诶包为空，则开始进行商品添加操作
        if (product != null) {
            try {
                // 从session中获取当前店铺的id并赋值给product，减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                product.setShop(currentShop);
                // 进行商品更新
                ProductExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
                if (pe.getState() == ProductStateEnum.SUCCESS.getState()) {
                    result.put("success", true);
                } else {
                    result.put("success", false);
                    result.put("errMsg", pe.getStateInfo());
                }
            } catch (ProductOperationException e) {
                result.put("success", false);
                result.put("errMsg", e.toString());
                return result;
            }
        } else {
            result.put("success", false);
            result.put("errMsg", "请输入商品信息");
        }
        return result;
    }

    @RequestMapping(value = "/getproductlistbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> getProductListByShop(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        // 获取前台传过来的页码
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        // 获取前台传过来的每页要求返回的商品数
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        // 从session中获取店铺信息
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
            // 获取传入的检索条件 知道店铺id需要根据商品分类列出商品
            long productCategoryId = HttpServletRequestUtil.getLong(request, "productCategoryId");
            //获取前端出传入的商品名称？？？？？？前台就知道商品名称了？？？  debug明显session里面是没有productCategoryId这个属性的
            String productName = HttpServletRequestUtil.getString(request, "productName");
            //根据商品的所谓店铺 商品的分类 商品名称输出
            Product productCondition = compactProductCondition4Search(currentShop.getShopId(), productCategoryId,
                    productName);
            // 传入查询条件以及分页信息查询，返回响应商品列表及总数
            ProductExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
            result.put("productList", pe.getProductList());
            result.put("count", pe.getCount());
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return result;
    }

    /**
     * 组合查询条件
     *
     * @param shopId
     * @param productCategoryId
     * @param productName
     * @return
     */
    private Product compactProductCondition4Search(long shopId, long productCategoryId, String productName) {
        Product productCondition = new Product();
        Shop shop = new Shop();
        shop.setShopId(shopId);
        productCondition.setShop(shop);
        if (productCategoryId != -1L) {
            ProductCategory productCategory = new ProductCategory();
            productCategory.setProductCategoryId(productCategoryId);
            productCondition.setProductCategory(productCategory);
        }
        if (productName != null && !productName.equalsIgnoreCase("null")) {
            productCondition.setProductName(productName);
        }
        return productCondition;
    }

}
