package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.DTO.EvennementDto;
import com.example.carecareforeldres.Entity.Evennement;
import com.example.carecareforeldres.Entity.TypeEnum;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IEvennementService {
        Evennement saveEvennement(EvennementDto evennementDto);
        EvennementDto getEvennementDtoById(Long id);

        Evennement getEvennementById(Long id);
        EvennementDto updateEvennement(EvennementDto evennementDto);
        void deleteEvennement(Long id);
        List<EvennementDto> getAllEvennements();

        List<Object[]> getTopFiveUsersWithMostComments() ;

        List<EvennementDto> getEvennementDtoByDate(Date date);

        EvennementDto toDto(Evennement evennement); // Nouvelle méthode pour la conversion


        boolean isDateOccupied(LocalDate date);
        void registerUserToEvent(Long eventId, Integer userId) ;

        List<Object[]> getUsersWithEventCount(); // Nouvelle méthode pour récupérer les utilisateurs avec le nombre d'événements auxquels ils sont inscrits


        List<String> getUsersNamesByEvent(Long eventId);
        //  void registerUserToEvent(Long eventId, Long userId);
        Map<TypeEnum, Float> calculateAverageParticipantsPerType();
}

