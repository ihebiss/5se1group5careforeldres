package com.example.carecareforeldres.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Medicament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMedicament;

    private String nomMed;
    private String image;
    private String descMed;
    private Float prixMed;
    private LocalDate dateMed;

    @Enumerated(EnumType.STRING)
    private CatMedicament catMedicament;



    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "medicaments")
    @JsonIgnore
    private List<Ordonnance>ordonnances;
}
