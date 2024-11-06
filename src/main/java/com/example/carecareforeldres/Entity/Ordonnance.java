package com.example.carecareforeldres.Entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ordonnance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id ;
    private String titre;

    private Integer dosage;

    private Integer frequence;

    private Integer duree;

    private LocalDate dateAjout;

    private String instructions;

    //@ManyToOne
@Column(name = "patient")
   private Integer patient;

   private Integer idMedecin;

   @ManyToMany
   @JoinTable(name = "ordenance_medicament",joinColumns = @JoinColumn(name = "ord_id"), inverseJoinColumns = @JoinColumn(name = "med_id"))

   List<Medicament>medicaments;

}
