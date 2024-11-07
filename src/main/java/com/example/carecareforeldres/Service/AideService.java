package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.Entity.Aide;
import com.example.carecareforeldres.Entity.Stock;
import com.example.carecareforeldres.Entity.TypeAide;
import com.example.carecareforeldres.Repository.AideRepository;
import com.example.carecareforeldres.Repository.StockRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AideService implements  IAideService {

    final  AideRepository aideRepository;

    final StockRepository stockRepository;

    @Override
    public Aide addAideeee(Aide aide, Long idStock) {
        verifyAndSetTypeAide(aide);

        Stock stock = stockRepository.findById(idStock)
                .orElseThrow(() -> new NullPointerException("stock not found"));

        // Vérifier si le stock a suffisamment de ressources disponibles
        if (!isStockSufficient(stock, aide)) {
            throw new IllegalArgumentException("Stock insuffisant pour l'aide demandée.");
        }

        aide.setStock(stock);
        updateStockResources(stock, aide); // Met à jour la capacité du stock
        return aideRepository.save(aide);
    }

    @Override
    public void verifyAndSetTypeAide(Aide aide) {
        // Vérifier si l'aide est argent
        if (aide.getMontant() != null && aide.getMontant() > 0) {
            aide.setTypeAide(TypeAide.MONEY);
            return;
        }
        if (aide.getQuantiteClothes() != null && aide.getQuantiteClothes() > 0) {
            aide.setTypeAide(TypeAide.CLOTHES);
            return;
        }
        if (aide.getQuantiteMedication() != null && aide.getTypeMedicament() != null && aide.getQuantiteMedication() > 0) {
            aide.setTypeAide(TypeAide.MEDICAMENT);
            return;
        }
        if (aide.getVolunteerHours() != null && aide.getVolunteerHours() > 0) {
            aide.setTypeAide(TypeAide.VOLUNTEER_HOURS);
        }
        // Vérifier si l'aide est un espace additionnel
        else if (aide.getAddress() != null && !aide.getAddress().isEmpty()) {
            aide.setTypeAide(TypeAide.ADDITIONAL_SPACE);
        }
        // Sinon, lever une exception pour indiquer que le type n'est pas déterminé
        else {
            throw new IllegalArgumentException("Impossible de déterminer le type d'aide. Veuillez vérifier les attributs de l'aide.");
        }
    }

    // Vérifie si le stock a suffisamment de ressources pour l'aide
    private boolean isStockSufficient(Stock stock, Aide aide) {
        // Mettre à jour l'état de saturation du stock avant de vérifier la suffisance

        if (stock.isSaturated()) {
            return false; // Si le stock est saturé, retourner faux directement
        }

        switch (aide.getTypeAide()) {
            case MONEY:
                return (stock.getFunds() + aide.getMontant()) <= stock.getFundsLimit(); // Vérifier si l'ajout respecte la limite des fonds
            case CLOTHES:
                return (stock.getCapacite() + aide.getQuantiteClothes()) <= stock.getClothesLimit(); // Vérifier la limite des vêtements
            case MEDICAMENT:
                return (stock.getCapacite() + aide.getQuantiteMedication()) <= stock.getMedicationLimit(); // Vérifier la limite des médicaments
            case VOLUNTEER_HOURS:
                // Vérifier si la date et l'heure des heures de bénévolat sont appropriées
                return areVolunteerHoursValid(stock, aide); // Appeler la méthode pour valider les heures de bénévolat
            case ADDITIONAL_SPACE:
                return true;
            default:
                return false;
        }
    }

    // Met à jour la capacité du stock après l'ajout d'une aide
    private void updateStockResources(Stock stock, Aide aide) {
        switch (aide.getTypeAide()) {
            case MONEY:
                // Check if montant is null and only update if it is not null
                if (aide.getMontant() != null) {
                    stock.setFunds(stock.getFunds() + aide.getMontant());
                } else {
                    log.info("Montant est null. Stock non modifié pour les fonds.");
                }
                break; // Added break to prevent fall-through

            case CLOTHES:
                // Check if quantiteMedication is null before updating capacity
                if (aide.getQuantiteClothes() != null) {
                    stock.setCapacite(stock.getCapacite() + aide.getQuantiteClothes());
                } else {
                    log.info("Quantité de médicaments est null. Stock non modifié pour les vêtements.");
                }
                break;

            case MEDICAMENT:
                // Check if quantiteMedication is null before updating capacity
                if (aide.getQuantiteMedication() != null) {
                    stock.setCapacite(stock.getCapacite() + aide.getQuantiteMedication());
                } else {
                    log.info("Quantité de médicaments est null. Stock non modifié pour les médicaments.");
                }
                break;

            case ADDITIONAL_SPACE:
                // This action does not depend on null values
                stock.setTotalSpaces(stock.getTotalSpaces() + 1);
                break;

            default:
                break;
        }
        stockRepository.save(stock); // Sauvegarder les modifications apportées au stock
    }

    private boolean areVolunteerHoursValid(Stock stock, Aide aide) {
        LocalDateTime volunteerDateTime = aide.getVolunteerDateTime();
        LocalDateTime now = LocalDateTime.now();

        return !(volunteerDateTime.isBefore(now) || aide.getVolunteerHours() > stock.getMaxVolunteerHoursPerEvent());

    }




    @Override
    public List<Aide> retrieveAllAide() {
        return aideRepository.findAll();
    }

    @Override
    public Aide addAide(Aide s) {
        return aideRepository.save(s);
    }

    @Override
    public Aide updateAide(Aide s) {
        return aideRepository.save(s);
    }

    @Override
    public Aide retrieveAide(Long idAide) {
        Optional<Aide> optionalAide = aideRepository.findById(idAide);

        if (optionalAide.isPresent()) {
            return optionalAide.get();
        } else {
            throw new NoSuchElementException("Aide with ID " + idAide + " not found");
        }
    }

    @Override
    public void removeAide(Long idAide) {
        aideRepository.deleteById(idAide);
    }

















}
