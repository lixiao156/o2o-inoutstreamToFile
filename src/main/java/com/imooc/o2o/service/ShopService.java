package com.imooc.o2o.service;

import com.imooc.o2o.dto.ShopExecution;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.exceptions.ShopOperationException;

import java.io.InputStream;

/**
 * @author lixw
 * @date created in 14:10 2019/1/3
 */
public interface ShopService {
    /**
     * 插入店铺
     * inputStream 中无法获得包的名字的 所以参数需要传入一个名字来获取文件的名字
     * @param shop
     * @param
     * @return
     */
    ShopExecution addShop(Shop shop, InputStream shopImgInputStream,String fileName) throws ShopOperationException;

    /**
     * 根据店铺的Id获取店铺的信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     *ShopExecution封装了店铺的信息以及店铺的状态
     * 修改店铺的信息
     * @param shop
     * @param shopImgInputStream
     * @param fileName
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop(Shop shop, InputStream shopImgInputStream,String fileName) throws ShopOperationException;


    /**
     * 根据shopCondition筛选条件分页查询店铺
     *
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize);

}
