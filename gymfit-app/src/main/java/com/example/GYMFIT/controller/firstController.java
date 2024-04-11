package com.example.GYMFIT.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class firstController {
    @GetMapping("/api/hello")
    public String hello(){
        return "{\"secondProject(24.03.27~24.04.03 )\" : \"모두 고생하셨습니다\"}";      //"hello world!";
    }
}
