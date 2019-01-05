package com.imooc.o2o.dao;

import com.imooc.o2o.entity.Area;

import java.util.List;

/**
 * @author lixw
 * @date created in 12:35 2019/1/1
 */
public interface AreaDao {
    /**
     * 返回 列出区域列表
     * @return areaList
     */
   List<Area> queryArea();
}
