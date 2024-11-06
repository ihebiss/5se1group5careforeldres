package com.example.carecareforeldres.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attendence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long attendenceId ;
    private boolean presence;
    private LocalDateTime start ;
    private LocalDateTime end ;
    private Double workedHours;
    private Integer attendance;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JsonIgnore
    private Cuisinier cuisinier;



}
