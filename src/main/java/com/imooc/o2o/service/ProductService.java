package com.imooc.o2o.service;

import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.exceptions.ProductOperationException;

import java.io.InputStream;
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
}
