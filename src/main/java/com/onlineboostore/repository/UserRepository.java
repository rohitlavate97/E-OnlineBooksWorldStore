package com.onlineboostore.repository;

import com.onlineboostore.entity.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserRegister, Long> {
}