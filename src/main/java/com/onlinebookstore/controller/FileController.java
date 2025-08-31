package com.onlinebookstore.controller;

import com.onlinebookstore.entity.Files;
import com.onlinebookstore.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class FileController {
    @Autowired
    private FileRepository fileRepository;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file) {
        try {
            Files fis = new Files();
            fis.setFileName(file.getOriginalFilename());
            fis.setFileType(file.getContentType());
            fis.setData(file.getBytes());
            fileRepository.save(fis);
//            return ResponseEntity.status(HttpStatus.OK).body("File uploaded successfully: " + file.getOriginalFilename());
            return ResponseEntity.ok("File inserted successfully: " + file.getOriginalFilename());
        } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file: " + e.getMessage());
        }
    }
    
    @PostMapping("/uploadMultiple")
    public ResponseEntity<List<Object>> uploadMultipleFiles(@RequestParam MultipartFile[] files) {
        List<Object> response = Arrays.stream(files).map(file -> {
            try {
                return uploadFile(file);
            } catch (Exception e) {
                return "Failed to upload file.... "+e.getMessage();
            }
        }).collect(Collectors.toList());
//        return ResponseEntity.status(HttpStatus.OK).body(message.toString());
        return ResponseEntity.ok(response);
    }
}
