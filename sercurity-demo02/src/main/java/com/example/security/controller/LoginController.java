package com.example.security.controller;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author lktbz
 * @Date 2021/2/8
 */
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

}