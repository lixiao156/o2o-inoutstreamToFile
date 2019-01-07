package com.imooc.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author lixw
 * @date created in 14:45 2019/1/4
 */
@Controller
@RequestMapping(value = "shopadmin", method = {RequestMethod.GET})
public class ShopAdminController {
    /**
     *  用来转发的
     *  return "shop/shopoperation"与spring-web.xml中配置的拼接路径（加头加尾）访问页面
     */
    @RequestMapping(value = "/shopoperation")
    public String shopOperation() {
        return "shop/shopoperation";
    }


    /**
     * 转发shoplist.html页面
     * @return
     */
    @RequestMapping(value = "/shoplist")
    public String shopList() {
        return "shop/shoplist";
    }

    /**
     * 编辑路由
     * @return
     */
    @RequestMapping(value = "/shopmanagement")
    public String shopManagement() {
        return "shop/shopmanagement";
    }
}



