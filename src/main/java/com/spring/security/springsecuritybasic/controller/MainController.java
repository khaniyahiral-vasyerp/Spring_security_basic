package com.spring.security.springsecuritybasic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainController {

    @GetMapping("/")
    public ModelAndView greet() {
        return new ModelAndView("index");
    }

    @GetMapping("/userPage")
    public ModelAndView userPage() {
        return new ModelAndView("userPage");
    }

    @GetMapping("/adminPage")
    public ModelAndView adminPage() {
        return new ModelAndView("adminPage");
    }

    
    

}
