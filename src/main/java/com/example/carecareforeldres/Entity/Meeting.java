package com.example.carecareforeldres.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "meeting")
public class Meeting {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long meetId;
    @Temporal(TemporalType.DATE)
    private Date dateMeet;

    private String heure;



    @ManyToOne
    private Patient patient;

    @ManyToOne
    private Organisateur organisateur;
}
