package com.ufpb.exe2.entities;

public class DisciplinaLikes extends DisciplinaGet{

    private int likes;
    public DisciplinaLikes(Long id, String nome, int likes) {
        super(id, nome);
        this.likes = likes;
    }

    public int getLikes() {
        return this.likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
  
}