package com.example.carecareforeldres.DTO;

import com.example.carecareforeldres.Entity.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    Integer idpatient;
    Integer user;
    String nom;
    String prenom;
    String mail;
    @Enumerated(EnumType.STRING)
     TypePatient typatient;
     Boolean archiver;
    //List<Rdv> rdvs;
    Ambulance ambulance;
    Etablissement etablissement;
    //Infermier infermier;
    //Morgue morgue;
     Float poid;
     Float longueur;
     LocalDate datedeNais;
     Sexe sexe;
    List<Repas> repas;
    Medecin medecin;

    public static PatientDto toDto(Patient Entity){
        return PatientDto.builder()
                .idpatient(Entity.getIdpatient())
                .typatient(Entity.getTypatient())
                .nom(Entity.getNom())
                .prenom(Entity.getPrenom())
                .mail(Entity.getMail())
                .archiver(Entity.getArchiver())
                .poid(Entity.getPoid())
                .longueur(Entity.getLongueur())
                .datedeNais(Entity.getDatedeNais())
                .sexe(Entity.getSexe())
                .user(Entity.getUser())
                .repas(Entity.getRepas())
              //  .rdvs(Entity.getRdvs())
                .ambulance(Entity.getAmbulance())
                .medecin(Entity.getMedecin())
                .etablissement(Entity.getEtablissement())
                //.infermier(Entity.getInfermier())
                //.morgue(Entity.getMorgue())
                .build();
    }
    public static Patient toEntity(PatientDto Entity){
        return Patient.builder()
                .idpatient(Entity.getIdpatient())
                .typatient(Entity.getTypatient())
                .nom(Entity.getNom())
                .prenom(Entity.getPrenom())
                .mail(Entity.getMail())
                .archiver(Entity.getArchiver())
                .poid(Entity.getPoid())
                .longueur(Entity.getLongueur())
                .datedeNais(Entity.getDatedeNais())
                .sexe(Entity.getSexe())
                .user(Entity.getUser())
                .repas(Entity.getRepas())
                //.rdvs(Entity.getRdvs())
                .ambulance(Entity.getAmbulance())
                .medecin(Entity.getMedecin())
                .etablissement(Entity.getEtablissement())
               // .infermier(Entity.getInfermier())
               // .morgue(Entity.getMorgue())
                .build();
    }
}
