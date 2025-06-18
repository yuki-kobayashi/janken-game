package com.example.janken_game.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.janken_game.entity.User;
import com.example.janken_game.repository.UserRepository;

import java.util.Random;

@Controller
public class JankenController {

    private final String[] hands = {"グー", "チョキ", "パー"};
    private final UserRepository userRepository;

    @GetMapping("/")
    public String showHome(Model model, Authentication authentication) {
        // ログインしているか判定
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();

            // プレイヤー情報をデータベースから取得
            User user = userRepository.findByEmail(email).orElse(null);

            if (user != null) {
                 // プレイヤー名をテンプレートへ渡す
                model.addAttribute("username", user.getUsername());
            }
        }
        return "index";
    }

    @GetMapping("/play")
    public String playJanken(@RequestParam("hand") String playerHand, Model model) {
        Random random = new Random();
        String cpuHand = hands[random.nextInt(3)];

        String result;
        if (playerHand.equals(cpuHand)) {
            result = "あいこ";
        } else if ((playerHand.equals("グー") && cpuHand.equals("チョキ")) ||
                   (playerHand.equals("チョキ") && cpuHand.equals("パー")) ||
                   (playerHand.equals("パー") && cpuHand.equals("グー"))) {
            result = "あなたの勝ち！";

            // 勝利数カウント
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            User user = userRepository.findByEmail(email).orElse(null);
            if (user != null) {
                user.incrementWinCount();
                userRepository.save(user);
            }
        } else {
            result = "あなたの負け…";
        }

        model.addAttribute("playerHand", playerHand);
        model.addAttribute("cpuHand", cpuHand);
        model.addAttribute("result", result);

        return "result";
    }

    public JankenController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
