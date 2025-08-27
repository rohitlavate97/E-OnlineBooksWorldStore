package com.onlineboostore.serviceImpl;

import com.onlineboostore.entity.UserRegister;
import com.onlineboostore.model.UserRegData;
import com.onlineboostore.repository.UserRepository;
import com.onlineboostore.service.UserRegisterService;
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