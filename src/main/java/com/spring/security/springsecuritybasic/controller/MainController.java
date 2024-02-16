package com.spring.security.springsecuritybasic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/greeting")
public class MainController {

    @GetMapping("")
    public String greet() {
        return "Hello Welcome";
    }

    @GetMapping("/secured")
    public String securedGreeting() {
        return "Hello Everyone";
    }

}
