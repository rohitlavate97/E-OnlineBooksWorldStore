package com.onlinebookstore.controller;

import com.onlinebookstore.entity.UserRegister;
import com.onlinebookstore.model.LoginModel;
import com.onlinebookstore.model.ResponseMessage;
import com.onlinebookstore.model.UserRegData;
import com.onlinebookstore.service.UserRegisterService;
import com.onlinebookstore.utility.Constants;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;

@RestController
@Api(value = "User Registration and Login Operations", tags = {"User Operations"})
public class UserRegistrationController {
    @Autowired
    private UserRegisterService userRegisterService;

    @ApiOperation(value = "User Registration", notes = "API for registering a new user", response = ResponseMessage.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User Registered Successfully", response = ResponseMessage.class),
            @ApiResponse(code = 400, message = "User Registration Failed", response = ResponseMessage.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseMessage.class)
    })
    @PostMapping("/userRegister")
    public ResponseEntity<ResponseMessage> createUserRegistration(@ApiParam(value = "User Registration Data", required = true) @RequestBody UserRegData userRegData) {
        try {
            if (userRegData == null || userRegData.getEmail() == null || userRegData.getEmail().isBlank() ||
                    userRegData.getPassword() == null || userRegData.getPassword().isBlank()) {
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Email and Password cannot be empty"));
            }
            UserRegister userRegService = userRegisterService.createUserRegService(userRegData);
            if (userRegService != null) {
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "User Registered Successfully", userRegService));
            } else {
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "User Registration not saved successfully", userRegService));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILURE, "User Registration Failed" + e.getMessage()));
        }
    }

    @ApiOperation(value = "User Login", notes = "Authenticate a user and login", response = ResponseMessage.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "User Logged In Successfully", response = ResponseMessage.class),
            @ApiResponse(code = 400, message = "Invalid Email or Password", response = ResponseMessage.class),
            @ApiResponse(code = 500, message = "Internal Server Error", response = ResponseMessage.class)
    })
    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> createlogin(@ApiParam(value = "Login Credentials", required = true) @RequestBody LoginModel loginModel) {
        try {
            if (loginModel == null || loginModel.getEmail() == null || loginModel.getEmail().isBlank() ||
                    loginModel.getPassword() == null || loginModel.getPassword().isBlank()) {
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Email and Password cannot be empty"));
            }
            UserRegister userLogin = userRegisterService.createLoginUser(loginModel);
            if (userLogin != null) {
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_CREATED, Constants.SUCCESS, "User Login Successfully, welcome to E-commerce online BooksStore", userLogin));
            } else {
                return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_BAD_REQUEST, Constants.FAILED, "Invalid Email and Password"));
            }
        } catch (Exception e) {
            return ResponseEntity.ok(new ResponseMessage(HttpURLConnection.HTTP_INTERNAL_ERROR, Constants.FAILURE, "User Login Failed" + e.getMessage()));
        }
    }
}