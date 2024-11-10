package com.example.carecareforeldres.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Achievement")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Achievement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAchievement;

    private Long idShelter;
    private String nomShelter;
    private String cause;
    private String imageCause;
    private float montantTotalAide;
    private float objectifFinancier;
    private float montantRestant;


}
