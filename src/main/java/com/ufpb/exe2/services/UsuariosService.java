package com.ufpb.exe2.services;

import java.util.Optional;

import javax.servlet.ServletException;

import com.ufpb.exe2.entities.Usuario;
import com.ufpb.exe2.repositories.UsuariosRepository;

import org.springframework.stereotype.Service;

@Service
public class UsuariosService {
    UsuariosRepository<Usuario, String> usuariosDAO;

    public UsuariosService(UsuariosRepository<Usuario, String> usuariosDAO) {
        super();
        this.usuariosDAO = usuariosDAO;
    }

    public Usuario adicionaUsuario(Usuario usuario) {
        return this.usuariosDAO.save(usuario);
    }

    public Optional<Usuario> getUsuario(String email) {
        return this.usuariosDAO.findByEmail(email);
    }

    public Usuario removeUsuario(String email) throws ServletException{
        Optional<Usuario>usuario = usuariosDAO.findByEmail(email);
        if (usuario.isPresent()) {
            usuariosDAO.delete(usuario.get());
            return usuario.get();
        } 
        throw new ServletException("Usuário não encontrado");
    }

    public boolean validaUsuarioSenha(Usuario usuario){
        Optional<Usuario> optionalUsuario =  usuariosDAO
            .findByEmail(usuario.getEmail());
        
        if(
            optionalUsuario.isPresent() &&
            optionalUsuario.get()
                .getSenha()
                .equals(usuario.getSenha())
        ){
            return true;
        }
        return false;
    }
}