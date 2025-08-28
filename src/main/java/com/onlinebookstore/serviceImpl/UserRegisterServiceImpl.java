package com.onlinebookstore.serviceImpl;

import com.onlinebookstore.entity.UserRegister;
import com.onlinebookstore.model.UserRegData;
import com.onlinebookstore.repository.UserRepository;
import com.onlinebookstore.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public String createUserRegService(UserRegData userRegData) {
        UserRegister user = null;
        try {
            user = new UserRegister();
            user.setFirstName(userRegData.getFirstName());
            user.setLastName(userRegData.getLastName());
            user.setEmail(userRegData.getEmail());
            user.setPassword(Base64.getEncoder().encodeToString(userRegData.getPassword().getBytes()));
            user.setContactId(userRegData.getContactId());
            userRepository.save(user);
            return "User Registration Successful";
        } catch (Exception e) {
            e.printStackTrace();
            return "User Registration Failed";
        }
    }
}