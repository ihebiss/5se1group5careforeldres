package com.example.carecareforeldres.Service;


import com.example.carecareforeldres.DTO.ProduitAvecBeneficeDTO;
import com.example.carecareforeldres.Entity.Commande;
import com.example.carecareforeldres.Entity.Produit;
import com.example.carecareforeldres.Entity.ProduitsAvecBeneficesWrapper;
import com.example.carecareforeldres.Entity.User;
import com.example.carecareforeldres.Repository.CommandeRepository;
import com.example.carecareforeldres.Repository.ProduitRepository;
import com.example.carecareforeldres.Repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@AllArgsConstructor
@Service
public class ProduitService implements IProduitService {

    ProduitRepository produitRepository;
    CommandeRepository commandeRepository;
    UserRepository userRepository;
    @PersistenceContext



    private EntityManager entityManager;






    @Override
    public List<Produit> searchProduitsByNom(String nomproduit) {
        return produitRepository.findByNomproduitStartingWith(nomproduit);
    }


    @Override
    public Produit addProduit(Produit p) {
        return produitRepository.save(p);
    }

    @Override
    public Produit getProduitLePlusCommande() {
        Map<Produit, Integer> produitsCommandes = new HashMap<>();

        // Parcourir toutes les commandes dans la base de données
        List<Commande> commandes = commandeRepository.findAll();
        for (Commande commande : commandes) {
            List<Long> idProduits = commande.getIdProduits();
            // Pour chaque produit dans la commande, incrémentez le nombre de commandes dans la map
            for (Long idProduit : idProduits) {
                Produit produit = produitRepository.findById(idProduit)
                        .orElseThrow(() -> new RuntimeException("Produit non trouvé"));
                produitsCommandes.put(produit, produitsCommandes.getOrDefault(produit, 0) + 1);
            }
        }

        // Trouver le produit le plus commandé
        Produit produitLePlusCommande = null;
        int maxCommandes = 0;
        for (Map.Entry<Produit, Integer> entry : produitsCommandes.entrySet()) {
            if (entry.getValue() > maxCommandes) {
                maxCommandes = entry.getValue();
                produitLePlusCommande = entry.getKey();
            }
        }

        return produitLePlusCommande;
    }

    @Override
    public Produit getProduitLePlusRentable() {
        List<Commande> commandes = commandeRepository.findAll(); // Supposons que vous avez un repository pour les commandes

        Map<Long, Integer> produitsCommandes = new HashMap<>();
        for (Commande commande : commandes) {
            for (Long idProduit : commande.getIdProduits()) {
                produitsCommandes.put(idProduit, produitsCommandes.getOrDefault(idProduit, 0) + 1);
            }
        }

        Map<Long, Float> produitsBenefices = new HashMap<>();
        for (Map.Entry<Long, Integer> entry : produitsCommandes.entrySet()) {
            Long idProduit = entry.getKey();
            Produit produit = produitRepository.findById(idProduit).orElseThrow(() -> new RuntimeException("Produit non trouvé"));
            float benefice = produit.calculerBenefice(entry.getValue());
            produitsBenefices.put(idProduit, benefice);
        }

        Long produitPlusRentableId = Collections.max(produitsBenefices.entrySet(), Map.Entry.comparingByValue()).getKey();
        Produit produitPlusRentable = produitRepository.findById(produitPlusRentableId).orElse(null);

        if (produitPlusRentable != null) {
            System.out.println("Le produit le plus rentable est : " + produitPlusRentable.getNomproduit());
            System.out.println("Bénéfice : " + produitsBenefices.get(produitPlusRentableId));
        } else {
            System.out.println("Aucun produit trouvé");
        }

        return produitPlusRentable; // Retourner le produit le plus rentable
    }

    @Override
    public ProduitsAvecBeneficesWrapper getProduitsAvecBenefices() {
        List<Produit> tousLesProduits = produitRepository.findAll();
        List<Commande> commandes = commandeRepository.findAll();

        Map<Long, Integer> produitsCommandes = new HashMap<>();
        for (Commande commande : commandes) {
            for (Long idProduit : commande.getIdProduits()) {
                produitsCommandes.put(idProduit, produitsCommandes.getOrDefault(idProduit, 0) + 1);
            }
        }

        List<ProduitAvecBeneficeDTO> produitsAvecBenefices = new ArrayList<>();
        float beneficeTotal = 0;
        for (Produit produit : tousLesProduits) {
            float benefice = 0;
            if (produitsCommandes.containsKey(produit.getIdProduit())) {
                benefice = produit.calculerBenefice(produitsCommandes.get(produit.getIdProduit()));
            }
            produitsAvecBenefices.add(new ProduitAvecBeneficeDTO(produit, benefice));
            beneficeTotal += benefice;
        }

        // Tri de la liste par bénéfice décroissant
        produitsAvecBenefices.sort((a, b) -> Float.compare(b.getBenefice(), a.getBenefice()));

        // Affichage des produits avec leurs bénéfices
        for (ProduitAvecBeneficeDTO pb : produitsAvecBenefices) {
            System.out.println(pb);
        }
        System.out.println("Bénéfice total de tous les produits : " + beneficeTotal);

        return new ProduitsAvecBeneficesWrapper(produitsAvecBenefices, beneficeTotal);
    }


    @Override
    public void removeProduit(Long idProduit) {
        produitRepository.deleteById(idProduit);
    }

    @Override
    public Produit updateProduit(Produit p) {
        return produitRepository.save(p);
    }

    @Override
    public List<Produit> retrieveAllProduits() {
        return produitRepository.findAll();
    }

    @Override
    public void addProduitToFavoris(Integer id, Long idProduit) {
        Optional<User> optionalUtilisateur = userRepository.findById(id);
        if (optionalUtilisateur.isPresent()) {
            User utilisateur = optionalUtilisateur.get();
            Produit produit = retrieveProduit(idProduit); // Assurez-vous que cette méthode est correctement implémentée

            // Vérifier si le produit est déjà dans les favoris de l'utilisateur
            if (utilisateur.getProduitsFavoris().contains(produit)) {
                throw new IllegalArgumentException("Le produit est déjà dans les favoris de l'utilisateur.");
            }

            utilisateur.getProduitsFavoris().add(produit);
            userRepository.save(utilisateur);
            log.info("Le produit avec l'ID {} a été ajouté aux favoris de l'utilisateur avec l'ID {}.", idProduit, id);
        } else {
            throw new EntityNotFoundException("Aucun utilisateur trouvé avec l'identifiant : " + id);
        }
    }
    @Override
    public void removeProduitFromFavoris(Integer id, Long idProduit) {
        Optional<User> optionalUtilisateur = userRepository.findById(id);
        if (optionalUtilisateur.isPresent()) {
            User utilisateur = optionalUtilisateur.get();
            Produit produit = retrieveProduit(idProduit); // Assurez-vous que cette méthode est correctement implémentée

            utilisateur.getProduitsFavoris().remove(produit);
            userRepository.save(utilisateur);
            log.info("Le produit avec l'ID {} a été retiré des favoris de l'utilisateur avec l'ID {}.", idProduit, id);
        } else {
            throw new EntityNotFoundException("Aucun utilisateur trouvé avec l'identifiant : " + id);
        }
    }




    @Override
    public List<Produit> getProduitsFavorisByUserId(Integer id) {
        Optional<User> optionalUtilisateur = userRepository.findById(id);
        if (optionalUtilisateur.isPresent()) {
            User utilisateur = optionalUtilisateur.get();
            return utilisateur.getProduitsFavoris();
        } else {
            throw new EntityNotFoundException("Aucun utilisateur trouvé avec l'identifiant : " + id);
        }
    }
    @Override
    public Produit retrieveProduit(Long idProduit) {
        Optional<Produit> optionalProduit = produitRepository.findById(idProduit);
        if (optionalProduit.isPresent()) {
            return optionalProduit.get();
        } else {
            // Gérer le cas où aucun shop correspondant n'est trouvé
            // Par exemple, vous pouvez renvoyer null ou lever une exception spécifique
            throw new EntityNotFoundException("Aucun shop trouvé avec l'identifiant : " + idProduit);
        }
    }

    @Override
    public List<Produit> getAllProduits() {
        return null;
    }

    @Override
    public List<Produit> getAllProductsSortedByPrice(String sortOrder) {
        List<Produit> produits = produitRepository.findAll();

        // Trier les produits par prix
        if ("asc".equalsIgnoreCase(sortOrder)) {
            produits.sort(Comparator.comparing(Produit::getPrix));
        } else if ("desc".equalsIgnoreCase(sortOrder)) {
            produits.sort(Comparator.comparing(Produit::getPrix).reversed());
        }

        return produits;
    }
}

