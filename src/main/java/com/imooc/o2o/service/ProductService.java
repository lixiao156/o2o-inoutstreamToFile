package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exceptions.ProductOperationException;

import java.util.List;

/**
 * @author lixw
 * @date created in 12:17 2019/1/9
 */
public interface ProductService {
    /**
     * 1.处理缩略图
     * 2.处理商品详情图片
     * 3.添加商品的信息
     *
     * ImageHolder:可以减少参数
     *
     * @return
     * @throws ProductOperationException
     */

    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImageHolderList
    ) throws ProductOperationException;





    /**
     * 通过商品id查询唯一的商品信息
     *
     * @param productId
     * @return
     */
    Product getProductById(long productId);


    /**
     * 需要传入那些信息 参数 返回值 ？？？？？？
     * 更新商品的信息
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     */
    ProductExecution modifyProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImgHolderList)
    throws ProductOperationException;

    /**
     * 查询商品的列表并分页，可以输入的条件有，商品名称（模糊），店铺的id,商品的类别
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ProductExecution getProductList (Product productCondition , int pageIndex , int pageSize);

}
