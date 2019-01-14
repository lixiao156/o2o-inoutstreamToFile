package com.imooc.o2o.service.impl;

import com.imooc.o2o.dao.ProductDao;
import com.imooc.o2o.dao.ProductImgDao;
import com.imooc.o2o.dto.ImageHolder;
import com.imooc.o2o.dto.ProductExecution;
import com.imooc.o2o.entity.Product;
import com.imooc.o2o.entity.ProductImg;
import com.imooc.o2o.enums.ProductStateEnum;
import com.imooc.o2o.exceptions.ProductCategoryOperationException;
import com.imooc.o2o.exceptions.ProductOperationException;
import com.imooc.o2o.service.ProductService;
import com.imooc.o2o.util.ImageUtil;
import com.imooc.o2o.util.PageCalculator;
import com.imooc.o2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lixw
 * @date created in 14:37 2019/1/9
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    /**
     * 1.处理缩略图，获取缩略图相对路径你，并赋值给product
     * 2.往tb_product写入商品的信息 然后productId赋值给tb_product中
     * 3.结合product批量处理商品的详情图
     * 4.将商品详情图列表批量的插入到tb_product_img中
     *
     * @param product
     * @param thumbnail
     * @return
     * @throws ProductOperationException
     */
    @Override
    @Transactional
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) {
        //商品实例不为空 相关店铺不为空
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认为上架的状态
            product.setEnableStatus(1);

            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {
                //根据影响的行数判断是否添加成功
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品失败");
                }
            } catch (Exception e) {
                try {
                    throw new ProductOperationException("创建商品失败" + e.toString());
                } catch (ProductOperationException e1) {
                    e1.printStackTrace();
                }
            }

            // 若商品详情图不为空则添加
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                try {
                    addProductImgList(product, productImgHolderList);
                } catch (ProductOperationException e) {
                    e.printStackTrace();
                }
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }


    /**
     * 添加缩略图
     *
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        //先获取基准路径根据不同系统获取不同的路径 作为根路径保存图片
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        //返回路径设置到图片对应的路径
        product.setImgAddr(thumbnailAddr);
    }

    /**
     * 批量添加商品的详情图
     *
     * @param product
     * @param productImgHolderList
     * @throws ProductOperationException
     */
    private void addProductImgList(Product product, List<ImageHolder> productImgHolderList)
            throws ProductOperationException {
        // 获取图片存储路径，这里直接存放到相应店铺的文件夹底下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        // 遍历图片一次处理，添加进productImg实体类中
        for (ImageHolder productImgHolder : productImgHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(productImgHolder, dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        // 如果确实是有图片需要添加的，就执行批量添加操作
        if (productImgList.size() > 0) {
            try {
                int effectedNum = productImgDao.bathInsertProductImg(productImgList);
                if (effectedNum <= 0) {
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            } catch (Exception e) {
                throw new ProductOperationException("创建商品详情图片失败" + e.toString());
            }
        }
    }

    /**
     * 根据Id查询商品信息
     * 通过dao层去获取商品信息
     *
     * @param productId
     * @return
     */
    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductByProductId(productId);

    }

    /**
     * 根据商品对象 图片信息 图片列表信息  对商品信息进行修改
     *
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     * @throws ProductOperationException
     */
    //原来的缩略图是一定有值得
    //1.若缩略图参数是有值得，处理缩略图 若原来有图先删除  在获取新的缩略图的地址 将新图加入
    //2.若商品的详情图类别存在同样需要将图进行缩略图一样的操作处理tb_product_img
    //3.更新tb_product的信息
    @Override
    @Transactional
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
            throws ProductOperationException {
        //首先保证传入的对象不为空 才能对对象进行修改
        if ((product != null && product.getShop() != null && product.getShop().getShopId() != null)) {
            //修改时间这里指的是店铺的
            product.setLastEditTime(new Date());
            //判断原来商店的缩略图是不是有值得？？？ 应该是判断传入的参数存在图片是不是需要改变原来的图片
            if (thumbnail != null) {
                //获取原来商店中商品的信息 需要回去商品详情图的地址
                Product tempProduct = (Product) productDao.queryProductByProductId(product.getProductId());
                //如果有原来是有图片需要更新？ 如果不需要更新的情况？
                if (tempProduct.getImgAddr() != null) {
                    //删除原来的图片 以及原来的目录结构
                    ImageUtil.deletFileOrPath(tempProduct.getImgAddr());
                }
                //添加图片
                addThumbnail(product, thumbnail);
            }
            //传入的参数中是有图片信息的  需要将新图片插入到相应的店铺中
            //这里指的是商品
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                //传入的列表是有值得就需要将原来的删除
                deleteProductImgList(product.getProductId());
                addProductImgList(product, productImgHolderList);
            }
            //这里不用try catch 应该也是可以的
            try {
                //更新商品的信息
                int effectedNum = productDao.updateProduct(product);
                if (effectedNum <= 0) {
                    //终止程序？
                    throw new ProductCategoryOperationException("更新商品信息失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS, product);
            } catch (Exception e) {
                //可以告诉我们是什么异常？但是在这里已经明确知道如果没有影响行数而抛异常？
                //可以使用if()else ()来替换?
                throw new ProductOperationException("创建商品信息失败");

            }

        } else {
            //商品信息为空
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 查询商品信息 状态 及分页等
     *
     * @param productCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Product> productList = productDao.queryProductList(productCondition, rowIndex, pageSize);
        int count = productDao.queryProductCount(productCondition);
        //ProductExecution对象保存了商品的其他很多信息
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    /**
     * 删除某个商品的所有详情图
     *
     * @param productId
     */
    private void deleteProductImgList(Long productId) {
        //根据productId获取原来的图片列表
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        //依次遍历商品的详情列表依次删除  Dao层删除直接根据productId在SQL语句中实现删除不用遍历
        for (ProductImg productImg : productImgList) {
            ImageUtil.deletFileOrPath(productImg.getImgAddr());
        }
        //再从数据库中删除原来的图片的引用信息SQL语句一次性删除
        productImgDao.deleteProductImgByProductId(productId);
    }

}