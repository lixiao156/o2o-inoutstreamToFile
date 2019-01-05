package com.imooc.o2o.service;

import com.imooc.o2o.entity.ShopCategory;

import java.util.List;

/**
 * 需要返回ShopCategory列表
 * @author lixw
 * @date created in 9:59 2019/1/5
 */
public interface ShopCategoryService {
    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);
}
