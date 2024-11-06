package com.example.carecareforeldres.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Patient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idpatient;
    private Integer user;
    String nom;
    String prenom;
    String mail;
    Double x ;
    Double y ;
    private String adresse;
    @Enumerated(EnumType.STRING)
    private TypePatient typatient;
    private Boolean archiver;
    private Float poid;
    private Float longueur;
   @Column(name = "datedenais")
    private LocalDate datedeNais;
    @Enumerated(EnumType.STRING)
    private Sexe sexe;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "patient")
    List<Rdv>rdvs;
    @ManyToOne(cascade = CascadeType.ALL)
    Ambulance ambulance;
    @ManyToOne(cascade = CascadeType.ALL)
    Etablissement etablissement;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    Infermier infermier;
    @ManyToOne(cascade = CascadeType.ALL)
    Morgue morgue;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    Medecin medecin;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "patient")
    List<Repas> repas;
    @JsonIgnore
    @OneToMany(mappedBy = "patient",cascade = CascadeType.ALL)
    List<Alert>alerts;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "patients")
    List<Maladie> maladies=new ArrayList<>();
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "patient")
    List<LikeDislikePlat> likeDislikePlats;


    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "Patienttts")
    List<Activity> activity = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "patient")
    List<LikeDislikeActivity> likeDislikeActivities = new ArrayList<>();


    @ManyToMany
    @JsonIgnore
    @JoinTable(
            name = "patient_activity_favori",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<Activity> activityFavoris;
    public List<Activity> getActivityFavoris() {
        return activityFavoris;
    }

    public void setActivityFavoris(List<Activity> activityFavoris) {
        this.activityFavoris = activityFavoris;
    }

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Meeting> meetings;
}
