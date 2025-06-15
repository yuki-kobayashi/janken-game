package com.example.janken_game.repository;

import com.example.janken_game.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

// JpaRepository<User, Long>を継承することで、Userエンティティの基本的なDB操作を自動生成
// Springが自動的にSQLを組み立て、ユーザーを検索できる
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}