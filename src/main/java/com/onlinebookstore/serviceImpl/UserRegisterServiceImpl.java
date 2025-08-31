package com.onlinebookstore.serviceImpl;

import com.onlinebookstore.entity.Files;
import com.onlinebookstore.entity.UserRegister;
import com.onlinebookstore.model.LoginModel;
import com.onlinebookstore.model.UserRegData;
import com.onlinebookstore.repository.FileRepository;
import com.onlinebookstore.repository.UserRepository;
import com.onlinebookstore.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;

@Service
public class UserRegisterServiceImpl implements UserRegisterService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FileRepository fileRepository;

    @Override
    public UserRegister createUserRegService(UserRegData userRegData) {
        UserRegister user = null;
        try {
            user = new UserRegister();
            user.setFirstName(userRegData.getFirstName());
            user.setLastName(userRegData.getLastName());
            user.setEmail(userRegData.getEmail());
            user.setPassword(Base64.getEncoder().encodeToString(userRegData.getPassword().getBytes()));
            user.setContactId(userRegData.getContactId());
            userRepository.save(user);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public UserRegister createLoginUser(LoginModel loginModel) {
        UserRegister userEmail=userRepository.findByEmail(loginModel.getEmail());
        if(userEmail!=null){
            String decode=new String(Base64.getDecoder().decode(userEmail.getPassword()));
            if(decode.equals(loginModel.getPassword())){
                return userEmail;
            }
        }
        return null;
    }
    @Override
    public UserRegister createUserRegServiceWithFiles(UserRegData userRegData, MultipartFile[] files){
        UserRegister user=null;
        try{
            user=new UserRegister();
            user.setFirstName(userRegData.getFirstName());
            user.setLastName(userRegData.getLastName());
            user.setEmail(userRegData.getEmail());
            user.setPassword(Base64.getEncoder().encodeToString(userRegData.getPassword().getBytes()));
            user.setContactId(userRegData.getContactId());
            userRepository.save(user);
            if(files!=null && files.length>0){
                for(MultipartFile multipartFile : files){
                    Files fis = new Files();
                    fis.setFileType(multipartFile.getContentType());
                    fis.setFileName(multipartFile.getOriginalFilename());
                    fis.setData(multipartFile.getBytes());
                    fileRepository.save(fis);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }
}