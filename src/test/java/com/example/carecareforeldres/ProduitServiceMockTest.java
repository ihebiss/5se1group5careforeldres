package com.example.carecareforeldres;

import com.example.carecareforeldres.Entity.Commande;
import com.example.carecareforeldres.Entity.Produit;
import com.example.carecareforeldres.Repository.CommandeRepository;
import com.example.carecareforeldres.Repository.ProduitRepository;
import com.example.carecareforeldres.Service.ProduitService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@Slf4j
public class ProduitServiceMockTest {

    @MockBean
    private ProduitRepository produitRepository;

    @MockBean
    private CommandeRepository commandeRepository;

    @Autowired
    private ProduitService produitService;

    @Test

    public void testGetProduitLePlusRentable() {
        // Arrange
        Produit produit1 = Produit.builder().idProduit(1L).nomproduit("Produit 1").prix(100).prixInitial(50).build();
        Produit produit2 = Produit.builder().idProduit(2L).nomproduit("Produit 2").prix(200).prixInitial(120).build();
        Produit produit3 = Produit.builder().idProduit(3L).nomproduit("Produit 3").prix(300).prixInitial(250).build();

        Commande commande1 = new Commande(Arrays.asList(1L, 2L));
        Commande commande2 = new Commande(Arrays.asList(1L, 3L));
        Commande commande3 = new Commande(Arrays.asList(1L));

        // Configuration des mocks
        Mockito.when(produitRepository.findById(1L)).thenReturn(Optional.ofNullable(produit1));
        Mockito.when(produitRepository.findById(2L)).thenReturn(Optional.ofNullable(produit2));
        Mockito.when(produitRepository.findById(3L)).thenReturn(Optional.ofNullable(produit3));
        Mockito.when(commandeRepository.findAll()).thenReturn(Arrays.asList(commande1, commande2, commande3));

        // Act
        Produit produitLePlusRentable = produitService.getProduitLePlusRentable();

        // Assert
        Assertions.assertNotNull(produitLePlusRentable);
        Assertions.assertEquals(produit1, produitLePlusRentable);

        // Vérification des interactions avec les mocks
        // Vérification des interactions avec les mocks
        Mockito.verify(produitRepository, Mockito.times(2)).findById(1L);
        Mockito.verify(produitRepository).findById(2L);
        Mockito.verify(produitRepository).findById(3L);
        Mockito.verify(commandeRepository).findAll();

    }
}
