package com.example.janken_game.service;

import com.example.janken_game.entity.User;
import com.example.janken_game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service // サービス層ということをSpringに伝えるアノテーション
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // パスワードの暗号化クラス

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String email, String rawPassword) {
        // パスワードをハッシュ化して保存
        String encodedPassword = passwordEncoder.encode(rawPassword);
        User user = new User(username, email, encodedPassword);
        return userRepository.save(user);
    }

    public Optional<User> findByEmail(String email) {
        // メールアドレスからユーザーを検索
        return userRepository.findByEmail(email);
    }
}