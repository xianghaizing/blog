package com.lyf.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController{
    
    private Logger logger = LoggerFactory.getLogger(IndexController.class);
    
    @RequestMapping("index")
    public String index(){
        logger.info(Thread.currentThread().getName() + " - 访问首页...");
        return "index";
    }
}
