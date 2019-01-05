package com.imooc.o2o.dao;

import com.imooc.o2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lixw
 * @date created in 21:07 2019/1/4
 */
public interface ShopCategoryDao {
    /**
     * 查询店铺分类
     * @param shopCategory
     * @return
     */
    List<ShopCategory> queryShopCategory(
            @Param("shopCategoryCondition")
                    ShopCategory shopCategory
    );
}
