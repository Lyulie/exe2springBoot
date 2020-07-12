package com.ufpb.exe2.controllers;

import java.util.List;
import java.util.Optional;

import com.ufpb.exe2.entities.Disciplina;
import com.ufpb.exe2.entities.DisciplinaComent;
import com.ufpb.exe2.entities.DisciplinaGet;
import com.ufpb.exe2.entities.DisciplinaLikes;
import com.ufpb.exe2.entities.DisciplinaShort;
import com.ufpb.exe2.services.DisciplinasService;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class DisciplinasController {
    private DisciplinasService ds;

    public DisciplinasController(DisciplinasService ds){
        super();
        this.ds = ds;
    }

    @PostMapping("/disciplinas")
    public ResponseEntity<Disciplina> setNovaDisciplina(
        @RequestBody Disciplina novaDisciplina
    ){
        String nome = novaDisciplina.getNome();
        double nota = novaDisciplina.getNota();
        return new ResponseEntity<Disciplina>(
            ds.setNovaDisciplina(
                new Disciplina(nome, nota, 0, " ")
            ),
            HttpStatus.CREATED
        );
    }

    @GetMapping("/disciplinas")
    public ResponseEntity< List<DisciplinaGet> > getDisciplinas(){
        try{
            return new ResponseEntity< List<DisciplinaGet> >(
            ds.getDisciplinas(),
            HttpStatus.OK
        );
        } catch (EmptyResultDataAccessException e){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
    }

    @GetMapping("/disciplinas/{id}")
    public ResponseEntity<Disciplina> getDisciplinaById(
        @PathVariable("id") Long id
    ){
        Optional<Disciplina>disciplina = ds.getDisciplinaById(id);
        if(disciplina.isPresent()){
            return new ResponseEntity<>(
                disciplina.get(),
                HttpStatus.OK
            );
        }

        return new ResponseEntity<>(
            HttpStatus.NOT_FOUND
        );
    }

    @PutMapping("/disciplinas/likes/{id}")
    public ResponseEntity<DisciplinaLikes> incrementLikesById(
        @PathVariable("id") Long id
    ){
        Optional<Disciplina>disciplina = ds.getDisciplinaById(id);
        if(disciplina.isPresent()){
            return new ResponseEntity<DisciplinaLikes>(
                ds.incrementLikesById(id),
                HttpStatus.OK
            );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/disciplinas/nota/{id}")
    public ResponseEntity<DisciplinaShort> mediaNotas(
        @PathVariable("id") Long id,
        @RequestBody double nota
    ){
        Optional<Disciplina>disciplina = ds.getDisciplinaById(id);
        if(disciplina.isPresent()){
            return new ResponseEntity<DisciplinaShort>(
                ds.addNota(id, nota),
                HttpStatus.OK
            );
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/disciplinas/comentarios/{id}")
    public ResponseEntity<DisciplinaComent> addComentario(
        @PathVariable("id") Long id,
        @RequestBody String comentario
    ){
        Optional<Disciplina> disciplina = ds.getDisciplinaById(id);

        if(disciplina.isPresent()){
            return new ResponseEntity<DisciplinaComent>(
                ds.addComentario(id, comentario), 
                HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
            HttpStatus.NOT_FOUND
        );
    }

    @DeleteMapping("/disciplinas/comentarios/{id}/{posicao}")
    public ResponseEntity<Boolean> deleteComentario(
        @PathVariable("id") Long id,
        @PathVariable("posicao") int posicao
    ){
        Optional<Disciplina>disciplina = ds.getDisciplinaById(id);
        if(disciplina.isPresent()){
            return new ResponseEntity<Boolean>(
                ds.deleteComentario(id, posicao),
                HttpStatus.OK
            );
        }
        return new ResponseEntity<>(
            false,
            HttpStatus.NOT_FOUND
        );
    }

    @GetMapping("/disciplinas/ranking/notas")
    public ResponseEntity< List<Disciplina> >getRankingNotas(){
        return new ResponseEntity< List<Disciplina> >(
            ds.getRankingNotas(),
            HttpStatus.OK
        );
    }

    @GetMapping("/disciplinas/ranking/likes")
    public ResponseEntity< List<Disciplina> >getRankingLikes(){
        return new ResponseEntity< List<Disciplina> >(
            ds.getRankingLikes(),
            HttpStatus.OK
        );
    }

}