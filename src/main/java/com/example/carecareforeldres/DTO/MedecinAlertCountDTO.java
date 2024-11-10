package com.example.carecareforeldres.DTO;

import com.example.carecareforeldres.Entity.Medecin;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MedecinAlertCountDTO {
    private Medecin medecin;
    private Long alertCount;
}
