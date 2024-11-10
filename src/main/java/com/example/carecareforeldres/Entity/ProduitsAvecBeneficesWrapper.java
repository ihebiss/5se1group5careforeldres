package com.example.carecareforeldres.Entity;

import com.example.carecareforeldres.DTO.ProduitAvecBeneficeDTO;

import java.util.List;

public class ProduitsAvecBeneficesWrapper {
    private List<ProduitAvecBeneficeDTO> produitsAvecBenefices;
    private float beneficeTotal;

    public ProduitsAvecBeneficesWrapper(List<ProduitAvecBeneficeDTO> produitsAvecBenefices, float beneficeTotal) {
        this.produitsAvecBenefices = produitsAvecBenefices;
        this.beneficeTotal = beneficeTotal;
    }

    public List<ProduitAvecBeneficeDTO> getProduitsAvecBenefices() {
        return produitsAvecBenefices;
    }

    public void setProduitsAvecBenefices(List<ProduitAvecBeneficeDTO> produitsAvecBenefices) {
        this.produitsAvecBenefices = produitsAvecBenefices;
    }

    public float getBeneficeTotal() {
        return beneficeTotal;
    }

    public void setBeneficeTotal(float beneficeTotal) {
        this.beneficeTotal = beneficeTotal;
    }
}
