package com.imooc.o2o.service;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductCategory;
import com.imooc.o2o.entity.Shop;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductOperationException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class ProductServiceTest extends BaseTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductDao productDao;

    @Test
    public void addProduct() {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(31l);

        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(24l);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试商品1");
        product.setProductDesc("测试商品1描述");
        product.setPriority(20);
        product.setCreateTime(new Date());
        product.setEnableStatus(ProductStateEnum.SUCCESS.getState());

        File thumbnailFile = new File("G:\\ssmpicture\\picture.jpg");
        InputStream in = null;
        try {
            in = new FileInputStream(thumbnailFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ImageHolder thumbnail = new ImageHolder(in, thumbnailFile.getName());

        File productImg1 = new File("G:\\ssmpicture\\picture2.jpg");
        InputStream in1 = null;
        try {
            in1 = new FileInputStream(productImg1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        File productImg2 = new File("G:\\ssmpicture\\youxiang.jpg");
        InputStream in2 = null;
        try {
            in2 = new FileInputStream(productImg2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<ImageHolder> productImgList = new ArrayList<>();
        productImgList.add(new ImageHolder(in1, productImg1.getName()));
        productImgList.add(new ImageHolder(in2, productImg2.getName()));

        ProductExecution pe = null;
        try {
            pe = productService.addProduct(product, thumbnail, productImgList);
        } catch (ProductOperationException e) {
            e.printStackTrace();
        }

        assertEquals(ProductStateEnum.SUCCESS.getState(), pe.getState());
    }

    @Test
    public void getProductList() {
        Product product = new Product();
        product.setProductId(18L);
        product = productDao.queryProductByProductId(20L);
        System.out.println(product.toString());
        Shop shop = new Shop();
        shop.setShopId(31L);
        product.setShop(shop);
    }

    @Test
    public void getProductList1() {
    }

}