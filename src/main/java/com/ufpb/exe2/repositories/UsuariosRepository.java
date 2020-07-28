package com.ufpb.exe2.repositories;

import java.io.Serializable;
import java.util.Optional;

import com.ufpb.exe2.entities.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuariosRepository<
T, ID extends Serializable
> extends JpaRepository<Usuario, String> {
    
    Optional<Usuario> findByEmail(String email);
}