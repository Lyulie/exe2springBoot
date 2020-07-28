package com.ufpb.exe2.controllers;

import javax.servlet.ServletException;

import com.ufpb.exe2.entities.Usuario;
import com.ufpb.exe2.services.JWTService;
import com.ufpb.exe2.services.LoginResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class LoginController {
    
    @Autowired
    private JWTService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> autentica(
        @RequestBody Usuario usuario
    ) throws ServletException{
        
        return new ResponseEntity<>(
            jwtService.autentica(usuario),
            HttpStatus.OK
        );
    }
}