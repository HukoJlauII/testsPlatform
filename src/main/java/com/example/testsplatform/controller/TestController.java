package com.example.testsplatform.controller;


import com.example.testsplatform.entity.Test;
import com.example.testsplatform.service.TestService;
import com.example.testsplatform.service.UserService;
import com.example.testsplatform.views.TestView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/student")
public class TestController {

    @Autowired
    private UserService userService;
    @Autowired
    private TestService testService;


    @GetMapping("/test/{id}")
    public String showTestPage(Model model, @PathVariable("id") Long id) {
        Test test=testService.findTestById(id);
        if (test==null)
        {
            return "pages-error-404";
        }
        model.addAttribute("user", userService.getUserAuth());
        return "testPage";
    }


    @JsonView(TestView.TestForMainPage.class)
    @ResponseBody
    @GetMapping("/test")
    public List<Test> test() {
        return testService.checkAvailableTest();
    }
}
