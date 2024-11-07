package com.example.carecareforeldres.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Aide")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Aide implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAide;
    private String description;

    private Float montant;

    private Integer quantiteClothes;

    private String typeMedicament;
    private Integer quantiteMedication;

    private Integer surface;
    private String address;

    private Float duree;
    private LocalDateTime volunteerDateTime;
    private Float volunteerHours;

    @Enumerated(EnumType.STRING)
    private TypeAide typeAide;
    @ManyToOne
    @JsonIgnore
    Stock stock;

    @JsonIgnore
    @ManyToMany(mappedBy="aides", cascade = CascadeType.ALL)
    private List<Shelter> shelters = new ArrayList<>();
    @JsonIgnore
    @ManyToMany(mappedBy="aides", cascade = CascadeType.ALL)
    private List<Donateur> donateurs = new ArrayList<>();
}

