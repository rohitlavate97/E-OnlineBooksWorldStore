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
        try {
            if (userRegData==null || userRegData.getEmail() == null || userRegData.getEmail().isBlank() ||
                userRegData.getPassword() == null || userRegData.getPassword().isBlank()) {
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Email and Password cannot be empty"));
        }
            String userRegService = userRegisterService.createUserRegService(userRegData);
        if (userRegService != null) {
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "User Registered Successfully", userRegService));
        } else {
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "User Registration not saved successfully", userRegService));
        }
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILURE, "User Registration Failed" + e.getMessage()));
        }
    }
}
