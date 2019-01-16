package com.imooc.o2o.service;

import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.dto.ProductCategoryExecution;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;

import java.util.List;

/**
 * @author lixw
 * @date created in 11:40 2019/1/8
 */
public interface ProductCategoryService {
    /**
     * 查询指定的某个店铺下的所有商品类别信息
     *
     * @param shopId
     * @return
     */

    List<ProductCategory> getProductCategoryList(long shopId);


    /**
     * 批量增加商品分类
     * RuntimeException:事务管理
     * @param productCategoryList
     * @return
     * @throws RuntimeException
     */
    ProductCategoryExecution batchAddProductCategory(
            List<ProductCategory> productCategoryList) throws RuntimeException;

    /**
     * 将此类别下的商品里的类别id置位空，再删除掉该商品类别
     *
     * @param productCategoryId
     * @param shopId
     * @return
     * @throws ProductCategoryOperationException
     */
    ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId)
            throws ProductCategoryOperationException;

}
