package com.onlinebookstore.controller;

import com.onlinebookstore.model.ResponseMessage;
import com.onlinebookstore.model.UserRegData;
import com.onlinebookstore.service.UserRegisterService;
import com.onlinebookstore.utility.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;

@RestController
public class UserRegistrationController {
    @Autowired
    private UserRegisterService userRegisterService;

    @PostMapping("/userRegister")
    public ResponseEntity<ResponseMessage> createUserRegistration(@RequestBody UserRegData userRegData) {
        String userRegService = userRegisterService.createUserRegService(userRegData);
        return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "User Registered Successfully", userRegService));
    }
}
