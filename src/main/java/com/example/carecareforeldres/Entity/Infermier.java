package com.example.carecareforeldres.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Infermier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInfermier;
    private Boolean disponiblenf;
    private Integer user;
    String nom;
    String prenom;
    String mail;
    @ManyToOne(cascade = CascadeType.ALL)
    Etablissement etablissement;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "infermier")
    List<Patient> patients;
}
