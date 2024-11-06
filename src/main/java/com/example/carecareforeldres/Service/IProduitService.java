package com.example.carecareforeldres.Service;


import com.example.carecareforeldres.DTO.ProduitAvecBeneficeDTO;
import com.example.carecareforeldres.Entity.Produit;
import com.example.carecareforeldres.Entity.ProduitsAvecBeneficesWrapper;

import java.util.List;
import java.util.Map;

public interface IProduitService {
    public Produit addProduit(Produit p);
    public Produit getProduitLePlusCommande();
    public Produit getProduitLePlusRentable();
    public ProduitsAvecBeneficesWrapper getProduitsAvecBenefices();
    void removeProduit(Long idProduit);
    Produit updateProduit(Produit p);
    List<Produit> retrieveAllProduits();
    void addProduitToFavoris(Integer id, Long idProduit);
    Produit retrieveProduit(Long idProduit);
    List<Produit> getAllProduits();
    List<Produit> getAllProductsSortedByPrice(String sortOrder);
    List<Produit> getProduitsFavorisByUserId(Integer id);
    void removeProduitFromFavoris(Integer id, Long idProduit);

    List<Produit> searchProduitsByNom(String nomproduit);
}
