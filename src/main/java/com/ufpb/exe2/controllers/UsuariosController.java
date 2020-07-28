package com.ufpb.exe2.controllers;

import java.util.Optional;

import javax.servlet.ServletException;

import com.ufpb.exe2.entities.Usuario;
import com.ufpb.exe2.services.JWTService;
import com.ufpb.exe2.services.UsuariosService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsuariosController {
    
    private UsuariosService us;
    private JWTService jwtService;

    public UsuariosController(UsuariosService us, JWTService jwtService){
        super();
        this.us = us;
        this.jwtService = jwtService;
    }

    @PostMapping("/api/usuarios")
    public ResponseEntity<Usuario> adicionaUsuario(
        @RequestBody Usuario usuario
    ){
        return new ResponseEntity<>(
            us.adicionaUsuario(usuario),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/auth/usuarios/{email}")
    public ResponseEntity<Usuario> localizarUsuario(
        @PathVariable String email
    ){
        Optional<Usuario>optionalUsuario = us.getUsuario(email);
        if(optionalUsuario.isPresent()){
            return new ResponseEntity<>(
                optionalUsuario.get(),
                HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
            HttpStatus.NOT_FOUND
        );
    }

    @DeleteMapping("/auth/usuarios/{email}")
    public ResponseEntity<Usuario> removeUsuario(
        @PathVariable String email,
        @RequestHeader("Authorization") String header
    ){
        if(us.getUsuario(email).isEmpty()){
            return new ResponseEntity<>(
                HttpStatus.NOT_FOUND
            );
        }

        try{
            if(jwtService.usuarioTemPermissao(header, email)){
                return new ResponseEntity<>(
                    us.removeUsuario(email),
                    HttpStatus.OK
                );
            }
        } catch (ServletException e) {
            return new ResponseEntity<>(
                HttpStatus.FORBIDDEN
            );
        }

        return new ResponseEntity<>(
            HttpStatus.UNAUTHORIZED
        );
    }
}