package com.example.janken_game.config;

import com.example.janken_game.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// このクラスが設定クラスであることを示す
@Configuration
public class SecurityConfig {

    @Autowired
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // 認証プロバイダの設定
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userService);
        authProvider.setPasswordEncoder(passwordEncoder);
        return authProvider;
    }

    // ユーザー認証処理の設定
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http // HTTPリクエストごとのアクセスルール
            .authenticationProvider(authenticationProvider())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
        "/register", "/register/complete", "/login", "/login/**", 
                    "/style.css",  "/js/**", "/images/**", "/main.js",
                    "/h2-console/**"
                ).permitAll() // 未ログインでもアクセス可能
                .anyRequest().authenticated() // それ以外はログインが必要
            )
            // ログイン時の設定
            .formLogin(login -> login
                .loginPage("/login") // ログインページ
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?error")
                .permitAll()
            )
            // ログアウト時の設定
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout") // ログアウト後の遷移先
                .permitAll()
            )

            // Frameの表示とCSRF無効化を行う(H2コンソール用の設定)
            .csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"))
            .headers(headers -> headers.frameOptions().sameOrigin());

        return http.build();
    }

    // 認証マネージャーを取得するBean（ログイン処理に必要）
    @Bean
    public AuthenticationManager authenticationManager(
        AuthenticationConfiguration authenticationConfiguration
    ) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService;
    }
}