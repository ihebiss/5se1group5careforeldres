package com.example.carecareforeldres.DTO;
import com.example.carecareforeldres.Entity.Medecin;
import com.example.carecareforeldres.Entity.Patient;
import com.example.carecareforeldres.Entity.Rdv;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RdvDto {
    Long idRDV;
    LocalDateTime dateRDV;
    boolean archiver;
    Patient patient;
    Medecin medecin;

    public static RdvDto toDto(Rdv Entity){
        return RdvDto.builder()
                .idRDV(Entity.getIdRDV())
                .dateRDV(Entity.getDateRDV())
                .archiver(Entity.isArchiver())
                .patient(Entity.getPatient())
                .medecin(Entity.getMedecin())
                .build();
    }
    public static Rdv toEntity(RdvDto Entity){
        return Rdv.builder()
                .idRDV(Entity.getIdRDV())
                .dateRDV(Entity.getDateRDV())
                .archiver(Entity.isArchiver())
                .patient(Entity.getPatient())
                .medecin(Entity.getMedecin())
                .build();
    }
}

