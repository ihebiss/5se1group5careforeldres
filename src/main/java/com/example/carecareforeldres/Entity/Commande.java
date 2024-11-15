package com.example.carecareforeldres.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

@Entity
@Table(name = "commande")
public class Commande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCommande")
    private Long idCommande; // Clé primaire
    @Enumerated(EnumType.STRING)
    private StatutsCom statut;
    @Temporal(TemporalType.DATE)
    private Date datecommande;
    private float montantTotal;
    @Enumerated(EnumType.STRING)
    private ModePay modePay;
    @ElementCollection
    @CollectionTable(name = "commande_produits", joinColumns = @JoinColumn(name = "commande_id"))
    @Column(name = "id_produit")
    private List<Long> idProduits;
    @JsonIgnore
    @ManyToOne
    User user;
    @JsonIgnore
    @OneToOne(mappedBy="commande")
    @JsonBackReference
    private Panier panier;
public Commande(Date datecommande, float montantTotal, List<Long> idProduits) {
        this.datecommande = datecommande;
        this.montantTotal = montantTotal;
        this.idProduits = idProduits;
    }


    // Ajout du constructeur qui prend une liste d'ID de produits
    public Commande(List<Long> idProduits) {
        this.idProduits = idProduits;
    }

    // Getters et setters
    public List<Long> getIdProduits() {
        return idProduits;
    }

    public void setIdProduits(List<Long> idProduits) {
        this.idProduits = idProduits;
    }
}
