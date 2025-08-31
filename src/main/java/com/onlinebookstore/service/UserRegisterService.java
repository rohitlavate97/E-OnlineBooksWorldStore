package com.onlinebookstore.service;

import com.onlinebookstore.entity.UserRegister;
import com.onlinebookstore.model.LoginModel;
import com.onlinebookstore.model.UserRegData;
import org.springframework.web.multipart.MultipartFile;

public interface UserRegisterService {
    public UserRegister createUserRegService(UserRegData userRegData);
    public UserRegister createLoginUser(LoginModel loginModel);
    public UserRegister createUserRegServiceWithFiles(UserRegData userRegData, MultipartFile[] file);
}
