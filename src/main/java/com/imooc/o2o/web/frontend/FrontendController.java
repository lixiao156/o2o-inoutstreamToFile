package com.imooc.o2o.web.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 首页的路由转发功能
 *
 * @author lixw
 * @date created in 22:16 2019/1/15
 */
@Controller
@RequestMapping("/frontend")
public class FrontendController {

    @RequestMapping(value = "index", method = RequestMethod.GET)
    private String index() {
        return "frontend/index";
    }
}
