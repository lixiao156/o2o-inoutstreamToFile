package com.imooc.o2o.web.shopadmin;

import com.imooc.o2o.dto.Result;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductCategoryExecution;
import com.imooc.o2o.enums.ProductCategoryStateEnum;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.service.ProductCategoryService;
import com.imooc.o2o.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lixw
 * @date created in 11:59 2019/1/8
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;


    @RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
    @ResponseBody
    private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
//        Shop shop = new Shop();
//        request.getSession().setAttribute("currentShop", shop);
//        shop.setShopId(31L);
//        request.getAttribute("shopId");
//        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
//        List<ProductCategory> list = null;
//        if (currentShop != null && currentShop.getShopId() > 0) {
//            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
//            return new Result<List<ProductCategory>>(true, list);
//        } else {
//            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
//            return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
//        }
//        Long shopId = (Long) request.getAttribute("shopId");
//        List<ProductCategory> list = productCategoryService.getProductCategoryList(shopId);
//        if (shopId != null && shopId > 0) {
//            return new Result<List<ProductCategory>>(true, list);
//        } else {
//            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
//            return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
//        }
        /**
         * 用户从店铺进来进入店铺时候店铺逻辑中已经生产了shopId的session
         */
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if (currentShop != null && currentShop.getShopId() > 0) {
            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true, list);
        } else {
            ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
            return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
        }
    }


    @RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> addProductCategorys(
            List<ProductCategory> productCategoryList,
            HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        Shop currentShop = (Shop) request.getSession().getAttribute(
                "currentShop");
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(currentShop.getShopId());
        }
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                ProductCategoryExecution pe = productCategoryService
                        .batchAddProductCategory(productCategoryList);
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS
                        .getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少输入一个商品类别");
        }
        return modelMap;
    }
    @RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
    @ResponseBody
    private Map<String, Object> removeProductCategory(Long productCategoryId,
                                                      HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        if (productCategoryId != null && productCategoryId > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute(
                        "currentShop");
                ProductCategoryExecution pe = productCategoryService
                        .deleteProductCategory(productCategoryId,
                                currentShop.getShopId());
                if (pe.getState() == ProductCategoryStateEnum.SUCCESS
                        .getState()) {
                    modelMap.put("success", true);
                } else {
                    modelMap.put("success", false);
                    modelMap.put("errMsg", pe.getStateInfo());
                }
            } catch (RuntimeException e) {
                modelMap.put("success", false);
                modelMap.put("errMsg", e.toString());
                return modelMap;
            }

        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "请至少选择一个商品类别");
        }
        return modelMap;
    }
//
//    @RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> getProductById(@RequestParam Long productId) {
//        Map<String, Object> modelMap = new HashMap<>();
//        // 非空判断
//        if (productId != null && productId > 0) {
//            // 获取商品信息
//            Product product = productService.getProductById(productId);
//            // 获取该店铺下商品类别列表
//            List<ProductCategory> productCategoryList = productCategoryService
//                    .getProductCategoryList(product.getShop().getShopId());
//            //将product.productCategoryList放到不同的key里面
//            modelMap.put("product", product);
//            modelMap.put("productCategoryList", productCategoryList);
//            modelMap.put("success", true);
//        } else {
//            modelMap.put("success", false);
//            modelMap.put("errMsg", "empty productId");
//        }
//        return modelMap;
//    }
}
