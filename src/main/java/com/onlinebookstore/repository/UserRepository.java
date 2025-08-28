package com.onlinebookstore.repository;

import com.onlinebookstore.entity.UserRegister;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserRegister, Long> {
}