package com.example.janken_game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class WelcomeController {

    @GetMapping("/welcome")
    public String showWelcome(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        } else {
            model.addAttribute("username", "ゲスト");
        }
        return "welcome";
    }
}