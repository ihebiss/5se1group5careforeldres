package com.example.carecareforeldres.Entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stock implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idStock;
    private String title;
    private float funds;
    private float capacite;
    private String statut;

    private float fundsLimit;
    private float clothesLimit;
    private float medicationLimit;

    private boolean isSaturated;

    private float maxVolunteerHoursPerEvent = 3 ;

    private int totalSpaces;
    private int usedSpaces;

    @OneToMany(mappedBy = "stock")
   private Set<Aide> aides;
}
