package com.example.carecareforeldres.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String contenu;

    @ManyToOne
    private Medecin medecin;

    @ManyToOne
    private Patient patient;

    private LocalDateTime dateEnvoi;

    private boolean envoye;
}

