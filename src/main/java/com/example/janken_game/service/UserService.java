package com.example.janken_game.service;

import com.example.janken_game.entity.User;
import com.example.janken_game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository; // プレイヤー情報の保存・検索用のリポジトリ
    private final PasswordEncoder passwordEncoder; // パスワードをハッシュ化、検証するためのコンポーネント

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser(String username, String email, String rawPassword) {
        String encodedPassword = passwordEncoder.encode(rawPassword); // パスワードをハッシュ化
        User user = new User(username, email, encodedPassword); // プレイヤーオブジェクト生成
        return userRepository.save(user); // DBに保存
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email); // メールアドレスでプレイヤーを検索
    }

    // メールアドレスで認証
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("メールアドレスが見つかりません: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), new ArrayList<>()
        );
    }
}