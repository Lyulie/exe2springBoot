package com.ufpb.exe2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GradeADTO {
    private Long id;
    private String nome;
    private double nota;
}