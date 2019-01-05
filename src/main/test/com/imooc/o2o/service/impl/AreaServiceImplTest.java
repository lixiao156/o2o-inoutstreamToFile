package com.imooc.o2o.service.impl;

import com.imooc.o2o.BaseTest;
import com.imooc.o2o.entity.Area;
import com.imooc.o2o.service.AreaService;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;


import java.util.List;

import static org.junit.Assert.*;

public class AreaServiceImplTest extends BaseTest {
    @Autowired
    private AreaService areaService;
    /**
     * 判断查询出来的第一个数据是不是朝阳区
     */
    @Test
    public void getAreaList() {
        System.out.println("kkk");

        List<Area> areaList = areaService.getAreaList();
        System.out.println("fff");
        System.out.println(areaList.get(1).getAreaName());
        assertEquals("朝阳区",areaList.get(0).getAreaName());
    }
}
