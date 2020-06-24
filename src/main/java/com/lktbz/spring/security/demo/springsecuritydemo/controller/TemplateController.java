package com.lktbz.spring.security.demo.springsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @ClassName TemplateController
 * @Description TODO
 * @Author lktbz
 * @Date 2020/6/23
 */
@RestController("/")

public class TemplateController {
    /**
     * login 跳转页面
     * @return
     */
    @GetMapping("/login")
    public ModelAndView loginDemo(){
        return new ModelAndView("login");
    }

    /**
     * 登录成功跳转页面
     * @return
     */
    @GetMapping("/courses")
    public ModelAndView coursesDemo(){
        return new ModelAndView("courses");
    }
}
