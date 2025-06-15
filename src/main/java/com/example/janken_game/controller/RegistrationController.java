package com.example.janken_game.controller;

import com.example.janken_game.entity.User;
import com.example.janken_game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 登録フォーム表示
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";
    }

    // 登録処理 → 完了画面へリダイレクト
    @PostMapping("/register")
    public String registerUser(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {

        // メールアドレスの入力チェック
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "このメールアドレスはすでに使用されています。");
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            return "register";
        }

        // ユーザー名の入力チェック
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "このユーザー名はすでに使用されています。");
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            return "register";
        }

        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(username, email, encodedPassword);
        userRepository.save(user);

        return "redirect:/register/complete";
    }

    // 登録完了ページ表示
    @GetMapping("/register/complete")
    public String showRegistrationCompletePage() {
        return "register_complete";
    }
}