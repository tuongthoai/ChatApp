package com.hcmus.chatserver.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    @GetMapping("/login")
    @Secured("ROLE_USER")
    public String login() {
        return "login";
    }
}
