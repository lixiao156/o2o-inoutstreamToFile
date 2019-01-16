package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lixw
 * @date created in 22:10 2019/1/7
 */
public interface ProductCategoryDao {
    /**
     * 参数中的注解  @Param的作用是给参数命名,
     * 参数命名后就能根据名字得到参数值,正确的将参数传入sql语句中
     * 属于mybatis
     *
     * @param
     * @return
     */
    List<ProductCategory> queryProductCategory(
            @Param("productCategoryCondition")
            ProductCategory productCategory
            );

    /**
     * 通过商品的shopId获取店铺的商品类别
     *
     * @param shopId
     * @return List<ProductCategory>
     */
    List<ProductCategory> queryProductCategoryList(long shopId);


    /**
     * 批量插入
     * 新增商品类别  int  表示影响的行数
     *
     * @param productCategoryList
     *            productCategory
     * @return effectedNum
     */
    int batchInsertProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 删除商品类别（初版，即只支持删除尚且没有发布商品的商品类别）
     *
     * @param productCategoryId
     * @param shopId
     * @return effectedNum
     */
    int deleteProductCategory(
            @Param("productCategoryId") long productCategoryId,
            @Param("shopId") long shopId);
}
