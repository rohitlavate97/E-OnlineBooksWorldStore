package com.onlinebookstore.repository;

import com.onlinebookstore.entity.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Files, Long> {
}
