package com.imooc.o2o.web.shopadmin;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.entity.ShopCategory;
import com.imooc.o2o.service.AreaService;
import com.imooc.o2o.service.ShopCategoryService;
import com.imooc.o2o.service.ShopService;
import com.imooc.o2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 返回商品列表里面的ShopCategory列表（二级或者一级目录) 以及区域列表
 *
 * @author lixw
 * @date created in 15:19 2019/1/17
 */
@Controller
@RequestMapping("/frontend")
public class ShopListController {
    @Autowired
    private AreaService areaService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private ShopService shopService;
    /**
     * 获取组合查询条件（店铺类别、区域列表等）
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshoppageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listShopPageInfo(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 从前端请求中获取parentId
        long parentId = HttpServletRequestUtil.getLong(request,"parentId");
        List<ShopCategory> shopCategoryList = null;
        if(parentId!=-1){ // 如果用户选择了某个一级商铺类别
            try{
                ShopCategory shopCategoryCondition = new ShopCategory();
                ShopCategory parentCategory = new ShopCategory(); // 父类别
                parentCategory.setShopCategoryId(parentId);
                shopCategoryCondition.setParent(parentCategory);
                shopCategoryList = shopCategoryService.getShopCategoryList(shopCategoryCondition);



            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
        }else{ // 如果用户选择了全部商铺列表
            try{
                shopCategoryList = shopCategoryService.getShopCategoryList(null);
            }catch (Exception e){
                modelMap.put("success",false);
                modelMap.put("errMsg",e.getMessage());
                return modelMap;
            }
        }
        /**
         * 这里是要么获取某一级目录下的全部分类列表
         * 或者如果ParentId为空 获取的就是一级列表
         * 键值对就是
         */
        modelMap.put("shopCategoryList",shopCategoryList);
        // 获取区域列表
        List<Area> areaList = null;
        try {
            // 获取区域列表信息
            areaList = areaService.getAreaList();
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        return modelMap;
    }

    /**
     * 获取指定查询条件下的店铺列表
     * @param request
     * @return
     */
    @RequestMapping(value = "/listshops",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> listShops(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<>();
        // 获取页码
        int pageIndex = HttpServletRequestUtil.getInt(request,"pageIndex");
        // 获取一页允许显示记录条数
        int pageSize = HttpServletRequestUtil.getInt(request,"pageSize");
        if(pageIndex > -1 && pageSize > -1){
            // 获取一级类别
            long parentId = HttpServletRequestUtil.getLong(request,"parentId");
            // 获取二级类别
            long shopCategoryId = HttpServletRequestUtil.getLong(request,"shopCategoryId");
            // 获取区域Id
            int areaId = HttpServletRequestUtil.getInt(request,"areaId");
            // 获取店铺名称  名字进行模糊查询
            String shopName = HttpServletRequestUtil.getString(request,"shopName");
            // 获取组合之后的查询条件
            Shop shopCondition = compactShopCondition4Search(parentId,shopCategoryId,areaId,shopName);
            // 获取店铺列表 根据查询条件和分页信息获取店铺列表，并返回总数
            ShopExecution shopDto = shopService.getShopList(shopCondition,pageIndex,pageSize);
            modelMap.put("success",true);
            modelMap.put("shopList",shopDto.getShopList());
            modelMap.put("count",shopDto.getCount());
        }else{
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex");
        }
        return modelMap;
    }

    /**
     * 整合店铺查询条件，并封装在shopCondition中返回
     * @param parentId
     * @param shopCategoryId
     * @param areaId
     * @param shopName
     * @return
     */
    private Shop compactShopCondition4Search(long parentId, long shopCategoryId, int areaId, String shopName){
        Shop shopCondition = new Shop();
        /**
         * parentId 不为空
         */
        if(parentId!=-1L){
            // 查询某个一级ShopCategory下面的所有二级ShopCategory里面的店铺列表
            ShopCategory childCategory = new ShopCategory();
            ShopCategory parentCategory = new ShopCategory();
            parentCategory.setShopCategoryId(parentId);
            childCategory.setParent(parentCategory);
            shopCondition.setShopCategory(childCategory);
        }
        if (shopCategoryId != -1L) {
            // 查询某个二级ShopCategory下面的店铺列表
            ShopCategory shopCategory = new ShopCategory();
            shopCategory.setShopCategoryId(shopCategoryId);
            shopCondition.setShopCategory(shopCategory);
        }
        if (areaId != -1) {
            // 查询位于某个区域Id下的店铺列表
            Area area = new Area();
            area.setAreaId(areaId);
            shopCondition.setArea(area);
        }
        if (shopName != null) {
            // 查询名字里包含shopName的店铺列表
            shopCondition.setShopName(shopName);
        }
        // 前端展示的店铺都是审核成功的店铺
        shopCondition.setEnableStatus(1);
        return shopCondition;
    }
}
