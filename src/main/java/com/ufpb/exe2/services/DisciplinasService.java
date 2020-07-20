package com.ufpb.exe2.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ufpb.exe2.DTO.CommentsDTO;
import com.ufpb.exe2.DTO.GradeADTO;
import com.ufpb.exe2.DTO.LikesDTO;
import com.ufpb.exe2.DTO.StartingDTO;
import com.ufpb.exe2.entities.Disciplina;
import com.ufpb.exe2.repositories.DisciplinasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class DisciplinasService {
    @Autowired
    private DisciplinasRepository<Disciplina, Long> disciplinasDAO;

    public List<StartingDTO> getDisciplinas() {
        List<Disciplina> todasDisciplinas = disciplinasDAO.findAll();
        List<StartingDTO> filteredDisciplinas = new ArrayList<>();

        if (todasDisciplinas.isEmpty()) {
            throw new EmptyResultDataAccessException("Run forest", 0);
        }

        for (Disciplina disciplina : todasDisciplinas) {
            Long id = disciplina.getId();
            String nome = disciplina.getNome();
            filteredDisciplinas.add(new StartingDTO(id, nome));
        }
        return filteredDisciplinas;
    }

    public Disciplina setNovaDisciplina(Disciplina disciplina) {
        return disciplinasDAO.save(disciplina);
    }

    public Optional<Disciplina> getDisciplinaById(Long id) {
        return disciplinasDAO.findById(id);
    }

    public LikesDTO incrementLikesById(Long id){
        Disciplina disciplina = disciplinasDAO.findById(id).get();
        disciplina.setLikes(
            disciplina.getLikes() + 1
        );
        disciplinasDAO.save(disciplina);
        Long idAtual = disciplina.getId();
        String nomeAtual = disciplina.getNome();
        int likesAtual = disciplina.getLikes();
        return new LikesDTO(idAtual, nomeAtual, likesAtual);

    }

	public GradeADTO addNota(Long id, double nota) {
        Disciplina disciplina = disciplinasDAO.findById(id).get();
        disciplina.setNota(
            (disciplina.getNota() + nota)/2
        );
        disciplinasDAO.save(disciplina);
        Long idAtual = disciplina.getId();
        String nomeAtual = disciplina.getNome();
        double notaAtual = disciplina.getNota();

        return new GradeADTO(idAtual, nomeAtual, notaAtual);
    }

	public CommentsDTO addComentario(Long id, String comentario) {
        Disciplina disciplina = disciplinasDAO.findById(id).get();
        
        if(disciplina.getComentarios().equals(" ")){
            disciplina.setComentarios(comentario.replace("\n", ">"));
        } else {
            disciplina.setComentarios(
                disciplina.getComentarios() + comentario.replace("\n", ">")
                // " "
            );
        }

        disciplinasDAO.save(disciplina);
        Long idAtual = disciplina.getId();
        String nomeAtual = disciplina.getNome();
        String comentarios = disciplina.getComentarios();

        return new CommentsDTO(idAtual, nomeAtual, comentarios);
    }
    
    public boolean deleteComentario(Long id, int posicao){
        Disciplina disciplina = disciplinasDAO.findById(id).get();
        String[] comentariosArray = disciplina.getComentarios().split(">");
        
        if(comentariosArray.length == 0){
            return false;
        }

        List<String> comentariosList = new ArrayList<>();
        for(String comentario: comentariosArray){
            String trimComentario = comentario.trim();
            comentariosList.add(trimComentario);
        }

        String comentarioToSave = "";

        try{
            comentariosList.remove(posicao - 1);
            for(String comentario : comentariosList){
                comentarioToSave += comentario + ">";
            }
        } catch(IndexOutOfBoundsException ioobe){
            return false;
        }

        disciplina.setComentarios(comentarioToSave);
        disciplinasDAO.save(disciplina);
        return true;
    }

	public List<Disciplina> getRankingNotas() {
        List<Disciplina> todasDisciplinas = disciplinasDAO.findAll();
        List<Disciplina> rankingNotas = todasDisciplinas.stream()
            .sorted(Comparator.comparingDouble(Disciplina::getNota)
                .reversed())
            .collect(Collectors.toList());
        return rankingNotas;
                                            
	}

	public List<Disciplina> getRankingLikes() {
        List<Disciplina> todasDisciplinas = disciplinasDAO.findAll();
        List<Disciplina> rankingLikes = todasDisciplinas.stream()
            .sorted(Comparator.comparingInt(Disciplina::getLikes)
                .reversed())
            .collect(Collectors.toList());
        return rankingLikes;
	}
}

