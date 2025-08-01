package com.cptvolt.expensetracker.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @CrossOrigin
    @GetMapping
    public String securedHello() {
        return "Secured content accessed! You must be important here.";
    }

}
