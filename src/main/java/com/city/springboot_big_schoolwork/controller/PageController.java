package com.city.springboot_big_schoolwork.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {

    @GetMapping("/")
    public String index(HttpSession session) {
        List<String> roles = (List<String>) session.getAttribute("roles");
        if (roles == null || roles.isEmpty()) {
            return "login";
        }
        // 教师登录后跳转教师管理
        if (roles.contains("TEACHER") && !roles.contains("ADMIN")) {
            return "redirect:/teacher";
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "redirect:/";
    }
}
