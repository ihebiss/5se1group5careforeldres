package com.example.carecareforeldres.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Builder
public class Morgue implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMorgue;
    Integer nbCadavre;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    Etablissement etablissement;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "morgue")
    List<Patient> patients;
}
