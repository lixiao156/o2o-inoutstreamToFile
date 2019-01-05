package com.imooc.o2o.service;

import com.imooc.o2o.entity.Area;

import java.util.List;

/**
 * @author lixw
 * @date created in 15:03 2019/1/1
 */
public interface AreaService {
    /**
     * 查询区域列表
     *
     * @return 区域列表
     */
    List<Area> getAreaList();
}
