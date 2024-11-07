package com.example.carecareforeldres.DTO;

import com.example.carecareforeldres.Entity.Produit;

public class ProduitAvecBeneficeDTO {
    private Produit produit;
    private float benefice;

    public ProduitAvecBeneficeDTO(Produit produit, float benefice) {
        this.produit = produit;
        this.benefice = benefice;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public float getBenefice() {
        return benefice;
    }

    public void setBenefice(float benefice) {
        this.benefice = benefice;
    }

    @Override
    public String toString() {
        return "Produit: " + produit.getNomproduit() + ", Bénéfice: " + benefice;
    }
}
