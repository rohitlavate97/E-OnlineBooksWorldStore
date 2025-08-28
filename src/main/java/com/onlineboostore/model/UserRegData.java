package com.onlineboostore.model;

import jakarta.persistence.Column;
import lombok.Data;

@Data
public class UserRegData {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long contactId;
}
