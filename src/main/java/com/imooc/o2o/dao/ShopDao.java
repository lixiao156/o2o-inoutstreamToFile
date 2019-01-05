package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Shop;

/**
 * @author lixw
 * @date created in 17:51 2019/1/2
 */
public interface ShopDao {
    /**
     * 新增店铺
     * 返回改变的行数  如果返回：1插入一行 如果返回：-1 插入失败
     * 由mybatis 实现这个类 mapper中配置
     * @param shop
     * @return
     */
    int insertShop(Shop shop);

    /**
     * 更新店铺
     * @param shop
     * @return
     */
    int updateShop(Shop shop);

}
