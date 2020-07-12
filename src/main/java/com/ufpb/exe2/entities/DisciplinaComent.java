package com.ufpb.exe2.entities;

public class DisciplinaComent extends DisciplinaGet{
    
    private String comentarios;
    
    public DisciplinaComent(Long id, String nome, String comentarios) {
        super(id, nome);
        this.comentarios = comentarios;
    }

    public String getComentarios() {
        return this.comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }
}