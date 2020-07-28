package com.ufpb.exe2.services;

import java.util.Date;
import java.util.Optional;

import javax.servlet.ServletException;

import com.ufpb.exe2.filter.TokenFilter;
import com.ufpb.exe2.repositories.UsuariosRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import com.ufpb.exe2.DTO.UsuarioLoginDTO;
import com.ufpb.exe2.entities.Usuario;

@Service
public class JWTService {
    @Autowired
    private UsuariosRepository ur;
    private UsuariosService us;
    private final String TOKEN_KEY = "login variance";

    public boolean usuarioExiste (
        String authorizationHeader
    ) throws ServletException{
        
        String subject = getSubjectToken(authorizationHeader);
        return us
            .getUsuario(subject)
            .isPresent();
    }

    public boolean usuarioTemPermissao(
        String authorizationHeader, String email
    ) throws ServletException{
        
        String subject = getSubjectToken(authorizationHeader);
        Optional<Usuario> optionalUsuario = us.getUsuario(subject);
        return optionalUsuario.isPresent() &&
            optionalUsuario.get()
                .getEmail()
                .equals(email);
    }

    public String getSubjectToken(String authorizationHeader) throws ServletException {

        if (
            authorizationHeader == null ||
            !authorizationHeader.startsWith("Bearer ")
        ){
            throw new ServletException(
                "Token inexistente ou mal formatado!"
            );
        }

        String token = authorizationHeader.substring(TokenFilter.TOKEN_INDEX);
        String subject = null;

        try{
            subject = Jwts.parser()
                .setSigningKey("login variance")
                .parseClaimsJws(token).getBody()
                .getSubject();
        } catch (SignatureException e) {
            throw new ServletException(
                "Token inválido ou expirado!"
            );
        }
        return subject;
    }

    public String geraToken(String email) {
        return Jwts.builder()
            .setSubject(email)
            .signWith(SignatureAlgorithm.HS512, TOKEN_KEY)
            .setExpiration(
                new Date(System.currentTimeMillis() + (3 * 60) * 1000)
            ).compact();
    }


    public LoginResponse autentica(UsuarioLoginDTO usuario){
        String msgErro = "Usuario e/ou senha invalido(s). Login nao realizado";
        Optional<Usuario> optionalUsuario = ur
            .findByEmail(
                usuario.getEmail()
            );
        
        if (
            optionalUsuario.isPresent() && 
            usuario.getSenha().equals(
                optionalUsuario.get().getSenha()
            )
        ){
            return new LoginResponse(
                geraToken(usuario.getEmail())
            );
        }

        return new LoginResponse(msgErro);

	}
}