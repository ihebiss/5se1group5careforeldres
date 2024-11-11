package com.example.carecareforeldres.DTO;

import com.example.carecareforeldres.Entity.User;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EvennementDto {
    private Long id;
    private String nomevennement ;
    private String discription ;
    private LocalDate date;
    private Integer placeMax  ;
    private Float  lng ;
    private Float  lat ;
    private String image ;
    private String etat ;
    private String type;
    private List<UserDto> users ;




}