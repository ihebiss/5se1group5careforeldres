package com.example.carecareforeldres.DTO;

import com.example.carecareforeldres.Entity.Etablissement;
import com.example.carecareforeldres.Entity.Medecin;
import com.example.carecareforeldres.Entity.Rdv;
import com.example.carecareforeldres.Entity.Specialite;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedecinDto {
    Integer idMedecin;
    String nom;
    String prenom;
    String mail;
     Boolean disponible;
     Integer user;
    @Enumerated(EnumType.STRING)
    Specialite specialite;
    //List<Rdv> rdvs;
    Etablissement etablissement;

    public static MedecinDto toDto(Medecin Entity){
        return MedecinDto.builder()
                .idMedecin(Entity.getIdMedecin())
                .nom(Entity.getNom())
                .prenom(Entity.getPrenom())
                .user(Entity.getUser())
                .mail(Entity.getMail())
                .specialite(Entity.getSpecialite())
                .disponible(Entity.getDisponible())
                //.rdvs(Entity.getRdvs())
                .etablissement(Entity.getEtablissement())
                .build();
    }
    public static Medecin toEntity(MedecinDto Entity){
        return Medecin.builder()
                .idMedecin(Entity.getIdMedecin())
                .nom(Entity.getNom())
                .prenom(Entity.getPrenom())
                .user(Entity.getUser())
                .mail(Entity.getMail())
                .specialite(Entity.getSpecialite())
                .disponible(Entity.getDisponible())
               // .rdvs(Entity.getRdvs())
                .etablissement(Entity.getEtablissement())
                .build();
    }

}
