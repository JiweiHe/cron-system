package com.github.bone.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by mike on 16/12/30.
 */

@Controller
public class LoginController {
    private static final String FIXED_PASSWORD = "123456";//FIXME

    @PostMapping("login")
    public String login(@RequestParam String username, @RequestParam String password, HttpServletRequest request) {
        if (username.equals("dev") && FIXED_PASSWORD.equals(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", true);
            return "redirect:/jobDetail/list";
        }
        return "system/login";
    }

    @GetMapping("login")
    public String loginPage() {
        return "system/login";
    }
}
