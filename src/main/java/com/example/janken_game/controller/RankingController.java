package com.example.janken_game.controller;

import com.example.janken_game.entity.User;
import com.example.janken_game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RankingController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/ranking")
    public String showRanking(Model model) {
        List<User> users = userRepository.findAll()
                .stream()
                .sorted((u1, u2) -> Integer.compare(u2.getWinCount(), u1.getWinCount())) // 降順ソート
                .toList();

        model.addAttribute("users", users);
        return "ranking";
    }
}