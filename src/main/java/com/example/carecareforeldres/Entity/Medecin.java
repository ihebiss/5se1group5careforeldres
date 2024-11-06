package com.example.carecareforeldres.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Medecin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMedecin;
    private Boolean disponible;
    private Integer user;
    String nom;
    String prenom;
    String mail;
    Double x ;
    Double y ;
    private String adresse;
    @Enumerated(EnumType.STRING)
    private Specialite specialite;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "medecin",fetch = FetchType.EAGER)
    List<Rdv> rdvs=new ArrayList<>();
    @ManyToOne(cascade = CascadeType.ALL)
    Etablissement etablissement;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "medecin")
    List<Patient>patients;
    @JsonIgnore
    @ManyToMany(mappedBy = "medecinssy",cascade = CascadeType.ALL)
    List<Alert>alertssy;
}
