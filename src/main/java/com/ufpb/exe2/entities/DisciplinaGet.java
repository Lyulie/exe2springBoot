package com.ufpb.exe2.entities;

public class DisciplinaGet {
    private Long id;
    private String nome;

    public DisciplinaGet(String nome){
        this.nome = nome;
    }

    public DisciplinaGet(Long id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
}