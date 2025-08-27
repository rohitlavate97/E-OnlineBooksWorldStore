package com.onlineboostore.controller;

import com.onlineboostore.model.UserRegData;
import com.onlineboostore.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRegistrationController {
    @Autowired
    private UserRegisterService userRegisterService;

    @PostMapping("/userRegister")
    public String createUserRegistration(@RequestBody UserRegData userRegData) {
        String userRegService = userRegisterService.createUserRegService(userRegData);
        return "User registration endpoint";
    }
}
