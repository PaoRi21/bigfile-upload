package com.example.uploadapi.upload.controller;

import com.example.uploadapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.example.uploadapi.constants.ApiConstants.BASE_URL_AUTH;
import static com.example.uploadapi.constants.ApiConstants.BASE_URL_UPLOAD;

@RestController
@RequestMapping(BASE_URL_UPLOAD)
public class FileUploadController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/")
    public ResponseEntity<String> login(@RequestParam String username) {
        // Solo para ejemplo: podrías validar con un usuario real
    }
}
