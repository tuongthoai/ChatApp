package com.hcmus.chatserver.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class LoginController {
    @GetMapping("/test")
    public @ResponseBody  String login() {
        return "login";
    }
}
