package com.ufpb.exe2.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.Proxy;

@Entity
@Proxy(lazy=false)
public class Disciplina {
    @Id
    @GeneratedValue
    public Long id;
    @JsonProperty("nome")
    private String nome;
    @JsonProperty("nota")
    private double nota;
    @JsonProperty("likes")
    private int likes;
    @JsonProperty("comentarios")
    private String comentarios;

    public Disciplina(){
        
    }

    public Disciplina(Long id, String nome, double nota, int likes, String comentarios){
        this.id = id;
        this.nome = nome;
        this.nota = nota;
        this.likes = likes;
        this.comentarios = comentarios;
    }

    public Disciplina(String nome, double nota, int likes, String comentarios){
        this.nome = nome;
        this.nota = nota;
        this.likes = likes;
        this.comentarios = comentarios;
    }

    public Disciplina(String nome, double nota){
        this.nome = nome;
        this.nota = nota;
    }

    public Long getId(){
        return this.id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public double getNota(){
        return this.nota;
    }

    public void setNota(double nota){
        this.nota = nota;
    }

    public int getLikes() {
        return this.likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(String comentario){
        this.comentarios = comentario;
    }
}