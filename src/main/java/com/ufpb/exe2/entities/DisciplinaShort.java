package com.ufpb.exe2.entities;

public class DisciplinaShort extends DisciplinaGet {
    
    private double nota;

    public DisciplinaShort(Long id, String nome){
        super(id, nome);
    }

    public DisciplinaShort(String nome, double nota) {
        super(nome);
        this.nota = nota;
    }
    
    public DisciplinaShort(Long id, String nome, double nota){
        super(id, nome);
        this.nota = nota;
    }

    public double getNota() {
        return this.nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    
}