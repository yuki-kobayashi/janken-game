package com.example.janken_game.controller;

import com.example.janken_game.entity.User;
import com.example.janken_game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller
public class WelcomeController {

    private final UserRepository userRepository;

    @Autowired
    public WelcomeController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/welcome")
    public String showWelcome(Model model, Authentication authentication) {
        String username = "ゲスト";

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            User user = userRepository.findByEmail(email).orElse(null);
            // ログインしているプレイヤー情報が取得できれば、プレイヤー名を画面表示
            if (user != null) {
                username = user.getUsername();
            }
        }

        model.addAttribute("username", username);
        return "welcome";
    }
}