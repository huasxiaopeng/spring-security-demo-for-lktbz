package com.example.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName SecurityController
 * @Description hello world demo
 * @Author lktbz
 * @Date 2020/12/21
 */
@Controller
public class SecurityController {
    //登录成功跳转页面
    @RequestMapping("/index")
    public ModelAndView index(){
        ModelAndView modelAndView=new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

}
