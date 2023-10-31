package com.vib.tdi.core.tdicore.controller;

import com.example.tdiframework.service.UtilsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/core1")
public class HomeController {

    @GetMapping("/test")
    public String test(){
        return "test";
    }
}
