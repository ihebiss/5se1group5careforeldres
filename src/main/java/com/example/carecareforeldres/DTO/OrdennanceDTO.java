package com.example.carecareforeldres.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class OrdennanceDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String titre;

    private Integer dosage;

    private Integer frequence;

    private Integer duree;

    private LocalDate dateAjout;

    private String instructions;

    private Integer patient;

    private Integer idMedecin;

    private List<Integer> mdicamentsIds =new ArrayList<>();

}
