package com.example.testsplatform.controller;


import com.example.testsplatform.entity.Test;
import com.example.testsplatform.service.TestService;
import com.example.testsplatform.views.TestView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student")
public class TestController {

    @Autowired
    private TestService testService;

    @JsonView(TestView.TestForMainPage.class)
    @GetMapping("/test")
    public List<Test> test() {
        return testService.checkAvailableTest();
    }
}
