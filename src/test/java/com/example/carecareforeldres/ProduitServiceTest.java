package com.example.carecareforeldres;

import com.example.carecareforeldres.Entity.Commande;
import com.example.carecareforeldres.Entity.Produit;
import com.example.carecareforeldres.Repository.CommandeRepository;
import com.example.carecareforeldres.Repository.ProduitRepository;
import com.example.carecareforeldres.Service.ProduitService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ProduitServiceTest {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private CommandeRepository commandeRepository;

    @InjectMocks
    private ProduitService produitService;

    @Test
    public void testGetProduitLePlusRentable() {
        // Arrange
        Produit produit1 = Produit.builder().idProduit(1L).nomproduit("Produit 1").prix(100).prixInitial(50).build();
        Produit produit2 = Produit.builder().idProduit(2L).nomproduit("Produit 2").prix(200).prixInitial(120).build();
        Produit produit3 = Produit.builder().idProduit(3L).nomproduit("Produit 3").prix(300).prixInitial(250).build();

         Date dateCommande = new Date(); // exemple de date
        Commande commande1 = new Commande(dateCommande, 150, Arrays.asList(1L, 2L));
        Commande commande2 = new Commande(dateCommande, 250, Arrays.asList(1L, 3L));
        Commande commande3 = new Commande(dateCommande, 100, Arrays.asList(1L));


        when(produitRepository.findById(1L)).thenReturn(Optional.of(produit1));
        when(produitRepository.findById(2L)).thenReturn(Optional.of(produit2));
        when(produitRepository.findById(3L)).thenReturn(Optional.of(produit3));
        when(commandeRepository.findAll()).thenReturn(Arrays.asList(commande1, commande2, commande3));

        // Act
        Produit produitLePlusRentable = produitService.getProduitLePlusRentable();

        // Assert
        Assertions.assertNotNull(produitLePlusRentable);
        Assertions.assertEquals(produit1, produitLePlusRentable);

    }
}
