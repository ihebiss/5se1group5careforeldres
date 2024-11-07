package com.example.carecareforeldres;
import com.example.carecareforeldres.Entity.Aide;
import com.example.carecareforeldres.Entity.Stock;
import com.example.carecareforeldres.Entity.TypeAide;
import com.example.carecareforeldres.Repository.StockRepository;
import com.example.carecareforeldres.Service.AideService;
import com.example.carecareforeldres.Service.StockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;


@ExtendWith(SpringExtension.class)
// @SpringBootTest(classes = CarecareforEldresApplication.class)
@SpringBootTest
public class AideServiceTest {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private AideService aideService;
    @Autowired
    private StockService stockService;


    public Aide createMoneyAide(float montant) {
        return Aide.builder()
                .description("Somme d'argent")
                .montant(montant)
                .build();
    }

    public Aide createClothesAide(int quantiteClothes) {
        return Aide.builder()
                .description("Aide en vêtements")
                .quantiteClothes(quantiteClothes)
                .build();
    }

    public Aide createMedicamentAide(String typeMedicament , int quantiteMedication) {
        return Aide.builder()
                .description("Aide en médicaments")
                .typeMedicament(typeMedicament)
                .quantiteMedication(quantiteMedication)
                .build();
    }

    public Aide createAdditionalSpaceAide(String address,int surface) {
        return Aide.builder()
                .description("Aide en espace supplémentaire")
                .surface(surface)
                .address(address)
                .build();
    }

    public Aide createVolunteerHoursAide(float volunteerHours,LocalDateTime volunteerDateTime) {
        return Aide.builder()
                .description("Heures de bénévolat")
                .volunteerHours(volunteerHours)
                .volunteerDateTime(volunteerDateTime)
                .build();
    }


    @Test
    public void testAddMoneyAide() {
        Stock stock = Stock.builder()
                .title("Aide Financière")
                .funds(1000.0f)
                .fundsLimit(10000.0f)
                .build();
        stock = stockRepository.save(stock);

        Aide aideMoney = createMoneyAide(1000.0f);

        Aide savedAide = aideService.addAideeee(aideMoney, stock.getIdStock());
        stock = stockRepository.save(stock);

        Assertions.assertNotNull(savedAide.getIdAide());
        Assertions.assertEquals(TypeAide.MONEY, savedAide.getTypeAide());
        Assertions.assertEquals(stock.getIdStock(), savedAide.getStock().getIdStock());
       Assertions.assertEquals(2000.0f,savedAide.getStock().getFunds() );
        Assertions.assertTrue(stock.getFundsLimit() > savedAide.getStock().getFunds());
        Assertions.assertEquals(1000.0f, savedAide.getMontant());

        aideService.removeAide(savedAide.getIdAide());
        stockService.deleteStock(stock.getIdStock());
    }

    @Test
    public void testAddClothesAide() {
        Stock stock = Stock.builder()
                .title("Aide en vêtements")
                .capacite(100)
                .clothesLimit(1000)
                .build();
        stock = stockRepository.save(stock);

        Aide aideClothes = createClothesAide(50);

        Aide savedAide = aideService.addAideeee(aideClothes, stock.getIdStock());

        Assertions.assertNotNull(savedAide.getIdAide());
        Assertions.assertEquals(TypeAide.CLOTHES, savedAide.getTypeAide());
        Assertions.assertEquals(stock.getIdStock(), savedAide.getStock().getIdStock());

        Assertions.assertEquals(50, savedAide.getQuantiteClothes());
        Stock updatedStock = stockRepository.findById(stock.getIdStock()).orElseThrow();

        Assertions.assertEquals(150, updatedStock.getCapacite());
        Assertions.assertTrue(stock.getClothesLimit()> savedAide.getStock().getCapacite());

        aideService.removeAide(savedAide.getIdAide());
        stockService.deleteStock(stock.getIdStock());
    }

    @Test
    public void testAddMedicamentAide() {
        Stock stock = Stock.builder()
                .title("Aide en médicaments")
                .capacite(300)
                .medicationLimit(3000)
                .build();
        stock = stockRepository.save(stock);

        Aide aideMedicament = createMedicamentAide("Antibiotiques" , 200);

        Aide savedAide = aideService.addAideeee(aideMedicament, stock.getIdStock());

        Assertions.assertNotNull(savedAide.getIdAide());
        Assertions.assertEquals(TypeAide.MEDICAMENT, savedAide.getTypeAide());
        Assertions.assertEquals(stock.getIdStock(), savedAide.getStock().getIdStock());

        Assertions.assertEquals(200, savedAide.getQuantiteMedication());
        Stock updatedStock = stockRepository.findById(stock.getIdStock()).orElseThrow();
        Assertions.assertEquals(500, updatedStock.getCapacite());
        aideService.removeAide(savedAide.getIdAide());
        stockService.deleteStock(stock.getIdStock());
    }

    @Test
    public void testAddAdditionalSpaceAide() {
        Stock stock = Stock.builder()
                .title("Aide en espace supplémentaire")
                .build();
        stock = stockRepository.save(stock);

        Aide aideAdditionalSpace = createAdditionalSpaceAide("El Agba Monastir", 75);

        Aide savedAide = aideService.addAideeee(aideAdditionalSpace, stock.getIdStock());

        Assertions.assertNotNull(savedAide.getIdAide());
        Assertions.assertEquals(TypeAide.ADDITIONAL_SPACE, savedAide.getTypeAide());
        Assertions.assertEquals(stock.getIdStock(), savedAide.getStock().getIdStock());

        Assertions.assertEquals("El Agba Monastir", savedAide.getAddress());

        aideService.removeAide(savedAide.getIdAide());
        stockService.deleteStock(stock.getIdStock());
    }

    @Test
    public void testAddVolunteerHoursAide() {
        Stock stock = Stock.builder()
                .title("Aide en heures d'aide")
                .maxVolunteerHoursPerEvent(3)
                .build();
        stock = stockRepository.save(stock);
        LocalDateTime volunteerDateTime = LocalDateTime.of(2025, 12, 12, 0, 0); // This sets the time to midnight

        Aide aideVolunteerHours = createVolunteerHoursAide(2F, volunteerDateTime);

        Aide savedAide = aideService.addAideeee(aideVolunteerHours, stock.getIdStock());

        Assertions.assertNotNull(savedAide.getIdAide());
        Assertions.assertEquals(TypeAide.VOLUNTEER_HOURS, savedAide.getTypeAide());
        Assertions.assertEquals(stock.getIdStock(), savedAide.getStock().getIdStock());
        Assertions.assertTrue(stock.getMaxVolunteerHoursPerEvent() > savedAide.getVolunteerHours());

        Assertions.assertEquals(2F, savedAide.getVolunteerHours());

        aideService.removeAide(savedAide.getIdAide());
        stockService.deleteStock(stock.getIdStock());
    }


}
