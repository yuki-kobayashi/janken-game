package com.example.janken_game.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

// データベースのテーブルに対応するエンティティ
@Entity
// テーブル名
@Table(name = "janken_users")
public class User implements UserDetails  {
    // 主キー
    @Id
    // 主キーをデータベース側で自動採番
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //nullはNG、かつ値はユニークとする設定
    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int winCount = 0;
    //デフォルトコンストラクタ
    public User() {}
    // ユーザー名とパスワードを指定してインスタンスを作るコンストラクタ
    public User(String username, String email,  String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    // ゲッター
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getWinCount() {
        return winCount;
    }
    // セッター
    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public void incrementWinCount() {
        this.winCount++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // UserDetailsの実装メソッド
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList(); // 権限なし
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // アカウントは期限切れではない
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // アカウントはロックされていない
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 資格情報は期限切れではない
    }

    @Override
    public boolean isEnabled() {
        return true; // 有効なユーザー
    }
}