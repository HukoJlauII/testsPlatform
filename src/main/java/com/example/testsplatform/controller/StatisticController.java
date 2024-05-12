package com.example.testsplatform.controller;

import com.example.testsplatform.entity.User;
import com.example.testsplatform.service.TestService;
import com.example.testsplatform.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class StatisticController {

    private final TestService testService;

    private final UserService userService;

    @GetMapping("/stats")
    public String showStats(@AuthenticationPrincipal User user, Model model) {
        model.addAttribute("popularTests", testService.getPopularTests());

        model.addAttribute("hardestTest", testService.getHardestTest());

        model.addAttribute("activeUsers", userService.getTopActiveUsers());

        model.addAttribute("user", user);

        return "/statsPage";

    }
}
