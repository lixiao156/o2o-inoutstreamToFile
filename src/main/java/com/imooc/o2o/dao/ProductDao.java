package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Product;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lixw
 * @date created in 22:06 2019/1/8
 */


public interface ProductDao {
    /**
     * 插入商品
     *
     * @param product
     * @return
     */
    int insertProduct(Product product);

    /**
     * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id,商品类别
     *
     * @param productCondition
     * @param rowIndex
     * @param pageSize
     * @return
     */
    List<Product> queryProductList(
            @Param("productCondition") Product productCondition,
            @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    /**
     * 删除商品
     *
     * @param productId
     * @return
     */
    int deleteProduct(@Param("productId") long productId,
                      @Param("shopId") long shopId);

    /**
     * 通过商品的Id获取唯一的Id的商品的信息
     * @param productId
     * @return
     */
    Product queryProductByProductId(Long productId);

    /**
     * 查询对应商品的总数
     * @param productCondition
     * @return
     */
    int queryProductCount(@Param("productCondition") Product productCondition);

    /**
     * 更新商品的行数
     * @param product
     * @return
     */
    int updateProduct(Product product);

    /**
     * 删除商品之前，需要将商品的id置为空
     * @param productCategoryId
     * @return
     */
    int updateProductCategoryToNull(long productCategoryId);


}
