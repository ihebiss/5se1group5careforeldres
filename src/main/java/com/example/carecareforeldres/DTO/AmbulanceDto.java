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
public class AmbulanceDto {
    Long idAmb;
    String marque;
    String matricule;
    String image;
    Boolean busy;
    Double x;
    Double y;
    String adresse;
    @Enumerated(EnumType.STRING)
    EtatAmb etatAmb;
    LocalDate dateDernEntret;
    Etablissement etablissement;
    Ambilancier ambilancier;
    //List<Patient> patients;
    public static AmbulanceDto toDto(Ambulance Entity){
        return AmbulanceDto.builder()
                .idAmb(Entity.getIdAmb())
                .busy(Entity.getBusy())
                .marque(Entity.getMarque())
                .matricule(Entity.getMatricule())
                .etatAmb(Entity.getEtatAmb())
                .etablissement(Entity.getEtablissement())
               // .patients(Entity.getPatients())
                .ambilancier(Entity.getAmbilancier())
                .dateDernEntret(Entity.getDateDernEntret())
                .image(Entity.getImage())
                .x(Entity.getX())
                .y(Entity.getY())
                .adresse(Entity.getAdresse())
                .build();
    }
    public static Ambulance toEntite(AmbulanceDto Entity){
        return Ambulance.builder()
                .idAmb(Entity.getIdAmb())
                .busy(Entity.getBusy())
                .marque(Entity.getMarque())
                .matricule(Entity.getMatricule())
                .etatAmb(Entity.getEtatAmb())
                .etablissement(Entity.getEtablissement())
                //.patients(Entity.getPatients())
                .ambilancier(Entity.getAmbilancier())
                .dateDernEntret(Entity.getDateDernEntret())
                .image(Entity.getImage())
                .x(Entity.getX())
                .y(Entity.getY())
                .adresse(Entity.getAdresse())
                .build();
    }
}
