package com.example.carecareforeldres;

import com.example.carecareforeldres.Entity.Aide;
import com.example.carecareforeldres.Entity.Stock;
import com.example.carecareforeldres.Entity.TypeAide;
import com.example.carecareforeldres.Repository.AideRepository;
import com.example.carecareforeldres.Repository.StockRepository;
import com.example.carecareforeldres.Service.AideService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
public class AideServiceMockitoTest {

    @Autowired

    private AideService aideService;

    @MockBean
    private StockRepository stockRepository;

    @MockBean
    private AideRepository aideRepository;

    @Test
    public void testAddMoneyAide() {
        // Configurer les données de test
        Stock stock = Stock.builder()
                .idStock(66L)
                .title("Aide Financière")
                .funds(1000.0f)
                .fundsLimit(10000.0f)
                .build();

        when(stockRepository.findById(66L)).thenReturn(Optional.of(stock));

        Aide aideMoney = Aide.builder()
                .idAide(66L)
                .description("Somme d'argent")
                .montant(1000.0f)
                .build();

        when(aideRepository.save(any(Aide.class))).thenReturn(aideMoney);

        // Appel de la méthode à tester
        Aide savedAide = aideService.addAideeee(aideMoney, stock.getIdStock());

        // Vérifications
        Assertions.assertEquals(TypeAide.MONEY, savedAide.getTypeAide());
        Assertions.assertEquals(66L, savedAide.getStock().getIdStock());
        Assertions.assertEquals(2000.0f, savedAide.getStock().getFunds());
        Assertions.assertTrue(stock.getFundsLimit() > savedAide.getStock().getFunds());
        Assertions.assertEquals(1000.0f, savedAide.getMontant());

        // Vérification que les méthodes mockées ont été appelées
        verify(stockRepository).findById(66L);
        verify(aideRepository).save(any(Aide.class));
    }

    @Test
    public void testAddClothesAide() {
        Stock stock = Stock.builder()
                .idStock(1L)
                .title("Aide en vêtements")
                .capacite(100)
                .clothesLimit(1000)
                .build();
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        Aide aideClothes = Aide.builder()
                .idAide(1L)
                .description("Aide en vêtements")
                .quantiteClothes(50)
                .build();

        when(aideRepository.save(any(Aide.class))).thenReturn(aideClothes);

        Aide savedAide = aideService.addAideeee(aideClothes, 1L);

        Assertions.assertEquals(TypeAide.CLOTHES, savedAide.getTypeAide());
        Assertions.assertTrue(stock.getClothesLimit()> savedAide.getStock().getCapacite());
        Assertions.assertEquals(1L, savedAide.getStock().getIdStock());
        Assertions.assertEquals(50, savedAide.getQuantiteClothes());

        Assertions.assertEquals(150, savedAide.getStock().getCapacite());

        verify(stockRepository).findById(1L);
        verify(aideRepository).save(any(Aide.class));
    }

    @Test
    public void testAddMedicamentAide() {
        Stock stock = Stock.builder()
                .idStock(6L)
                .title("Aide en médicaments")
                .capacite(300)
                .medicationLimit(3000)
                .build();
        when(stockRepository.findById(6L)).thenReturn(Optional.of(stock));

        Aide aideMedicament = Aide.builder()
                .idAide(1L)
                .typeMedicament("Antibiotiques")
                .quantiteMedication(200)
                .build();

        when(aideRepository.save(any(Aide.class))).thenReturn(aideMedicament);

        Aide savedAide = aideService.addAideeee(aideMedicament, 6L);

        Assertions.assertEquals(TypeAide.MEDICAMENT, savedAide.getTypeAide());
        Assertions.assertEquals(6L, savedAide.getStock().getIdStock());
        Assertions.assertEquals(200, savedAide.getQuantiteMedication());
        Assertions.assertEquals(500, savedAide.getStock().getCapacite());
        verify(stockRepository).findById(6L);
        verify(aideRepository).save(any(Aide.class));
    }

    @Test
    public void testAddAdditionalSpaceAide() {
        Stock stock = Stock.builder()
                .idStock(1L)
                .title("Aide en espace supplémentaire")
                .build();
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        Aide aideAdditionalSpace = Aide.builder()
                .idAide(1L)
                .surface(75)
                .address("EL Agba Monastir")
                .build();

        when(aideRepository.save(any(Aide.class))).thenReturn(aideAdditionalSpace);

        Aide savedAide = aideService.addAideeee(aideAdditionalSpace, 1L);

        Assertions.assertEquals(TypeAide.ADDITIONAL_SPACE, savedAide.getTypeAide());
        Assertions.assertEquals(1L, savedAide.getStock().getIdStock());
        Assertions.assertEquals(75, savedAide.getSurface());
        Assertions.assertEquals(stock.getTotalSpaces(), savedAide.getStock().getTotalSpaces());
        Assertions.assertEquals("EL Agba Monastir", savedAide.getAddress());

        verify(stockRepository).findById(1L);
        verify(aideRepository).save(any(Aide.class));
    }

    @Test
    public void testAddVolunteerHoursAide() {
        LocalDateTime volunteerDateTime = LocalDateTime.of(2025, 12, 12, 8, 0);
        Stock stock = Stock.builder()
                .idStock(1L)
                .title("Aide en heures d'aide")
                .maxVolunteerHoursPerEvent(3)
                .build();
        when(stockRepository.findById(1L)).thenReturn(Optional.of(stock));

        Aide aideVolunteerHours = Aide.builder()
                .idAide(1L)
                .description("Heures de bénévolat")
                .volunteerHours(2F)
                .volunteerDateTime(volunteerDateTime)
                .build();

        when(aideRepository.save(any(Aide.class))).thenReturn(aideVolunteerHours);

        Aide savedAide = aideService.addAideeee(aideVolunteerHours, 1L);

        Assertions.assertEquals(TypeAide.VOLUNTEER_HOURS, savedAide.getTypeAide());
        Assertions.assertEquals(1L, savedAide.getStock().getIdStock());
        Assertions.assertTrue(stock.getMaxVolunteerHoursPerEvent() > savedAide.getVolunteerHours());
        Assertions.assertEquals(2F, savedAide.getVolunteerHours());

        verify(stockRepository).findById(1L);
        verify(aideRepository).save(any(Aide.class));
    }
}
