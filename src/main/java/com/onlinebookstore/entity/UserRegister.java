package com.onlinebookstore.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Table(name="user_register")
@Entity
public class UserRegister {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="contactId")
    private Long contactId;

    @CreationTimestamp
    @Column(name="created_date")
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name="updated_date")
    private LocalDateTime updatedDate;
}
