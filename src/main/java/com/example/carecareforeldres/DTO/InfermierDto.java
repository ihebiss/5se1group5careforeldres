package com.example.carecareforeldres.DTO;

import com.example.carecareforeldres.Entity.Etablissement;
import com.example.carecareforeldres.Entity.Infermier;
import com.example.carecareforeldres.Entity.Patient;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InfermierDto {
    Integer idInfermier;
    String nom;
    String prenom;
    String mail;
    Etablissement etablissement;


    public static InfermierDto toDto(Infermier Entity){
        return InfermierDto.builder()
                .idInfermier(Entity.getIdInfermier())
                .nom(Entity.getNom())
                .prenom(Entity.getPrenom())
                .mail(Entity.getMail())
                .etablissement(Entity.getEtablissement())
                .build();
    }
    public static Infermier toEntity(InfermierDto Entity){
        return Infermier.builder()
                .idInfermier(Entity.getIdInfermier())
                .nom(Entity.getNom())
                .prenom(Entity.getPrenom())
                .mail(Entity.getMail())
                .etablissement(Entity.getEtablissement())
                .build();
    }
}
