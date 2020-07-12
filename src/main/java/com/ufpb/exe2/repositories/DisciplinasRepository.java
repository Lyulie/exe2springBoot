package com.ufpb.exe2.repositories;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufpb.exe2.entities.Disciplina;

@Repository
public interface DisciplinasRepository<
    T, ID extends Serializable
> extends JpaRepository<Disciplina, Long> {

}