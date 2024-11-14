package com.example.carecareforeldres;


import com.example.carecareforeldres.Entity.*;
import com.example.carecareforeldres.Repository.CuisinierRepository;
import com.example.carecareforeldres.Repository.RestaurantRepository;
import com.example.carecareforeldres.Repository.UserRepository;
import com.example.carecareforeldres.Service.CuisinierService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.mockito.Mockito.*;

public class CuisinierServiceMockitoTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private CuisinierRepository cuisinierRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CuisinierService cuisinierService;

    private List<Restaurant> restaurants;
    private List<Cuisinier> cuisiniers;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Créer des données factices pour les tests
        Cuisinier chef = new Cuisinier();
        chef.setIdC(1);
        chef.setScore(10);
        chef.setPlats(new ArrayList<>());
        chef.setSalaire(1000.0f);  // Initialiser le salaire

        Cuisinier autreCuisinier = new Cuisinier();
        autreCuisinier.setIdC(2);
        autreCuisinier.setScore(5);
        autreCuisinier.setPlats(new ArrayList<>());
        autreCuisinier.setSalaire(800.0f);  // Initialiser le salaire

        cuisiniers = Arrays.asList(chef, autreCuisinier);

        Restaurant restaurant = new Restaurant();
        restaurant.setCuisiniers(cuisiniers);

        restaurants = Collections.singletonList(restaurant);

        // Configurer les comportements des mocks
        when(restaurantRepository.findAll()).thenReturn(restaurants);
        when(cuisinierRepository.save(any(Cuisinier.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(new User()));
    }


    @Test
    public void testMettreAJourBadgesInteractions() throws Exception {
        // Exécuter la méthode à tester
        cuisinierService.mettreAJourBadges();

        // Vérifier les interactions avec les repositories
        verify(restaurantRepository, times(1)).findAll();
        verify(cuisinierRepository, atLeastOnce()).save(any(Cuisinier.class));
        verify(userRepository, atLeastOnce()).findById(anyInt());
    }
}
