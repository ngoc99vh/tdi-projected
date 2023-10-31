package com.example.tdiframework.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/home")
public class HomeController {

    @RequestMapping(value = "/test",method = RequestMethod.GET)
    private String getTest(){
        return "test";
    }
}
