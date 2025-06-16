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

        // メールアドレスの重複チェック
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "このメールアドレスはすでに使用されています。");
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            return "register";
        }

        // メールアドレスの形式チェック
        if (!email.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            model.addAttribute("error", "有効なメールアドレス形式で入力してください。");
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            return "register";
        }

        // ユーザー名の重複チェック
        if (userRepository.findByUsername(username).isPresent()) {
            model.addAttribute("error", "このユーザー名はすでに使用されています。");
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            return "register";
        }

        // ユーザー名の形式チェック(日本語・英数字OK、記号NG)
        if (!username.matches("^[\\p{IsHan}\\p{IsHiragana}\\p{IsKatakana}a-zA-Z0-9]+$")) {
            model.addAttribute("error", "ユーザー名に記号は使用できません。");
            model.addAttribute("username", username);
            model.addAttribute("email", email);
            return "register";
        }

        // パスワードの形式チェック（半角英数字8文字以上）
        if (!password.matches("^[a-zA-Z0-9]{8,}$")) {
            model.addAttribute("error", "パスワードは半角英数字8文字以上で入力してください。");
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