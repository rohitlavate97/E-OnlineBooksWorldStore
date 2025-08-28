package com.onlinebookstore.model;

import lombok.Data;

@Data
public class UserRegData {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Long contactId;
}
