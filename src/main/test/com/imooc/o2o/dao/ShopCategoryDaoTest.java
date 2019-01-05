package com.imooc.o2o.dao;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class ShopCategoryDaoTest extends BaseTest {
    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void queryShopCategory() {


        List<ShopCategory> shopCategoryList = shopCategoryDao.queryShopCategory(new ShopCategory());
        assertEquals(2, shopCategoryList.size());
        ShopCategory childShopCategory = new ShopCategory();
        ShopCategory parentShopCategory = new ShopCategory();
        parentShopCategory.setShopCategoryId(1L);
        //将parentShopCategory对象使用set方法传入到childShopCategory对象中
        childShopCategory.setParent(parentShopCategory);
        System.out.println(childShopCategory);
        System.out.println(parentShopCategory == childShopCategory);

        List<ShopCategory> shopCategoryList2 = shopCategoryDao.queryShopCategory(childShopCategory);
        assertEquals(1, shopCategoryList2.size());

        System.out.println(shopCategoryList2.get(0).getShopCategoryName());
//
//        for (int i = 0; i < shopCategoryList.size(); i++) {
//            ShopCategory shopCategory1 = shopCategoryList.get(i);
//            System.out.println(shopCategory1.getShopCategoryName());
//        }

    }
}