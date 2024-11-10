package com.example.carecareforeldres.Service;


import com.example.carecareforeldres.DTO.UserDto;
import com.example.carecareforeldres.Entity.Reclamation;
import com.example.carecareforeldres.Entity.ReponseReclamation;
import com.example.carecareforeldres.Entity.TypeReclamation;
import com.example.carecareforeldres.Entity.User;
import com.example.carecareforeldres.Repository.ReclamationRepository;
import com.example.carecareforeldres.Repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class ReclamationService implements IReclamationService{
    private ReclamationRepository reclamationRepository;
    private UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private static final int MAX_TENTATIVES = 3;
    @Autowired
    private Map<Integer, Integer> tentativesParUtilisateur;

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
    private static final List<String> grosMots = Arrays.asList("gros_mot_1", "gros_mot_2", "gros_mot_3"); // Ajoutez ici vos gros mots à éviter



    // Autres méthodes de service...


    @Override
    public List<UserDto> deuxUtilisateursAvecPlusReclamationsImportant() {
        List<Reclamation> reclamations = classerReclamationsParImportance(); // Récupérer toutes les réclamations

        // Définir le seuil d'importance pour les réclamations
        double seuilImportance = 2.0;

        // Filtrer les réclamations en fonction de l'importance
        List<Reclamation> reclamationsFiltrees = reclamations.stream()
                .filter(reclamation -> reclamation.getImportance() >= seuilImportance)
                .collect(Collectors.toList());

        // Définir le seuil d'importance pour les utilisateurs
        Map<Integer, Integer> userReclamationsCountMap = new HashMap<>();

        // Calculer le nombre de réclamations importantes pour chaque utilisateur
        for (Reclamation reclamation : reclamationsFiltrees) {
            Integer userId = reclamation.getUser().getId();
            // Vérifier si l'utilisateur existe dans la map, sinon l'initialiser à 0
            userReclamationsCountMap.putIfAbsent(userId, 0);
            // Incrémenter le nombre de réclamations importantes pour cet utilisateur
            userReclamationsCountMap.put(userId, userReclamationsCountMap.get(userId) + 1);
        }

        // Convertir la map en une liste d'objets UserDTO avec le nombre de réclamations importantes
        List<UserDto> usersWithReclamationsCount = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : userReclamationsCountMap.entrySet()) {
            User user = userRepository.findById(entry.getKey()).orElse(null);
            if (user != null) {
                UserDto userDTO = user.toDTO(); // Convertir l'objet User en UserDTO
                userDTO.setNombreReclamationsImportant(entry.getValue());
                usersWithReclamationsCount.add(userDTO);
            }
        }

        // Trier les utilisateurs en fonction du nombre de réclamations importantes
        usersWithReclamationsCount.sort(Comparator.comparingInt(UserDto::getNombreReclamationsImportant).reversed());

        // Sélectionner les deux premiers utilisateurs
        List<UserDto> topTwoUsers = usersWithReclamationsCount.stream().limit(2).collect(Collectors.toList());

        return topTwoUsers;
    }

    @Override
    public void envoyerMessageUtilisateur(Integer Id) {
        User user = userRepository.findById(Id).orElseThrow(() -> new EntityNotFoundException("Utilisateur non trouvé avec l'ID : " + Id));
        String message = "Bonjour " + user.getFirstname() + " " + user.getLastname() + ",\n\n"
                + "Nous avons reçu votre réclamation et nous allons la traiter dans les plus brefs délais.\n"
                + "En attendant, n'hésitez pas à nous contacter si vous avez besoin de plus d'informations.\n\n"
                + "Cordialement,\nL'équipe de support";
        // Envoyer le message à l'utilisateur
        String subject = "Nouveau message";
        String to = user.getEmail();
        sendEmail(to, subject, message);
    }

    @Override
    public Reclamation ajouterReclamation(Integer id, Reclamation reclamation) {
        if (tentativesParUtilisateur.getOrDefault(id, 0) >= MAX_TENTATIVES) {
            throw new IllegalStateException("Vous avez dépassé le nombre maximal de tentatives pour ajouter une réclamation contenant des gros mots.");
        }

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String description = reclamation.getDescription().toLowerCase(); // Convertir la description en minuscules pour une correspondance insensible à la casse
            if (contientGrosMots(description)) {
                tentativesParUtilisateur.put(id, tentativesParUtilisateur.getOrDefault(id, 0) + 1);
                if (tentativesParUtilisateur.get(id) >= MAX_TENTATIVES) {
                    // Interdisez à l'utilisateur d'ajouter de nouvelles réclamations s'il dépasse le nombre maximal de tentatives
                    // Vous pouvez mettre en œuvre cette fonctionnalité en mettant à jour une propriété de l'utilisateur dans la base de données
                    // ou en utilisant toute autre méthode de stockage de données
                    interdireUtilisateur(id);
                }
                throw new IllegalArgumentException("La description contient des gros mots. Veuillez la modifier.");
            }
            reclamation.setUser(user);
            LocalDate c = LocalDate.now();
            reclamation.setDateCreation(c);
            String userEmail = reclamation.getUser().getEmail();
            String subject = "Votre réclamation a été ajoutée";
            String message = "Votre réclamation a été ajoutée avec succès.";
            sendEmail(userEmail, subject, message); // Remplir avec la date actuelle
            return reclamationRepository.save(reclamation);
        } else {
            throw new EntityNotFoundException("Aucun utilisateur trouvé avec l'identifiant : " + id);
        }
    }

    private boolean contientGrosMots(String description) {
        for (String grosMot : grosMots) {
            if (description.contains(grosMot)) {
                return true;
            }
        }
        return false;
    }
    private void interdireUtilisateur(Integer idUtilisateur) {
        Optional<User> optionalUser = userRepository.findById(idUtilisateur);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setInterditDeReclamer(true);
            userRepository.save(user);
            throw new UserForbiddenException("L'utilisateur est interdit d'ajouter des réclamations.", user);
        } else {
            throw new EntityNotFoundException("Aucun utilisateur trouvé avec l'identifiant : " + idUtilisateur);
        }
    }

    @Override
    public void supprimerReclamation(Long idReclamation) {
        reclamationRepository.deleteById(idReclamation);
    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public List<Reclamation> getReclamationsByUserId(Integer Id) {
        return reclamationRepository.findByUserId(Id);
    }

    @Override
    public int getTotalReclamationsByDate(LocalDate date) {
        return reclamationRepository.countByDateCreation(date);
    }

    @Override
    public Map<Reclamation, ReponseReclamation> getReclamationsWithResponsesByUserId(Integer id) {
        List<Reclamation> reclamations = reclamationRepository.findByUserId(id);
        Map<Reclamation, ReponseReclamation> reclamationWithResponseMap = new HashMap<>();
        for (Reclamation reclamation : reclamations) {
            ReponseReclamation reponseReclamation = reclamation.getReponseReclamation();
            reclamationWithResponseMap.put(reclamation, reponseReclamation);
        }
        return reclamationWithResponseMap;
    }



    @Override
    public List<Reclamation> getReclamationsByType(TypeReclamation type) {
        return reclamationRepository.findByTypeReclamation(type);
    }








    @Override
    public boolean reclamationEstRepondue(Reclamation reclamation) {
        return reclamation.getReponseReclamation() != null;   }

    @Override
    public List<Reclamation> obtenirReclamationsNonReponduesDepuisDeuxJours() {
        List<Reclamation> reclamationsNonRepondues = new ArrayList<>();
        LocalDate deuxJoursAvant = LocalDate.now().minusDays(2);

        List<Reclamation> reclamations = reclamationRepository.findAll();

        for (Reclamation reclamation : reclamations) {
            if (!reclamationEstRepondue(reclamation)) {
                LocalDate dateCreation = reclamation.getDateCreation();
                long joursDepuisCreation = ChronoUnit.DAYS.between(dateCreation, LocalDate.now());
                if (joursDepuisCreation >= 2) {
                    reclamationsNonRepondues.add(reclamation);
                }
            }
        }

        return reclamationsNonRepondues;
    }

    @Override
    public List<Reclamation> classerReclamationsParImportance() {
        List<Reclamation> reclamations = reclamationRepository.findAll();

        // Calculer l'importance de chaque réclamation
        for (Reclamation reclamation : reclamations) {
            reclamation.setDescriptionLength(reclamation.getDescription().length());
            // Calculer l'importance en utilisant une méthode dédiée
            int keywordsPresence = calculerKeywordsPresence(reclamation.getDescription());
            reclamation.setImportance(calculerImportance(reclamation,keywordsPresence));
        }

        // Trier les réclamations par ordre d'importance décroissante
        Collections.sort(reclamations, Comparator.comparing(Reclamation::getImportance).reversed());

        return reclamations;
    }
    private double calculerImportance(Reclamation reclamation, int keywordsPresence) {
        // Exemple de calcul d'importance basé sur différents critères

        int gravite = reclamation.getGravite(); // Exemple : niveau de gravité de la réclamation (sur une échelle de 1 à 5)
        double impactClient = reclamation.getImpactClient(); // Exemple : niveau d'impact sur les clients (sur une échelle de 1 à 5)
        int recurrence = reclamation.getRecurrence(); // Exemple : fréquence de récurrence du problème (sur une échelle de 1 à 5)
        int descriptionLength = reclamation.getDescriptionLength();
        // Calcul de l'importance en combinant les différents critères
        return  gravite * 0.4 + impactClient * 0.3 + recurrence * 0.3 + descriptionLength * 0.2 + keywordsPresence * 0.1;
    }
    private int calculerKeywordsPresence(String description) {
        // Exemple de calcul de la présence de mots clés

        // Comptez le nombre de mots clés importants dans la description
        int count = 0;
        if (description.toLowerCase().contains("urgent")) {
            count += 3; // Pondération spécifique pour le mot clé "urgent"
        }
        if (description.toLowerCase().contains("problème")) {
            count += 2; // Pondération spécifique pour le mot clé "problème"
        }
        // Ajoutez d'autres mots clés et pondérations selon vos besoins
        if (description.toLowerCase().contains("grave")) {
            count += 2; // Pondération spécifique pour le mot clé "grave"
        }
        if (description.toLowerCase().contains("important")) {
            count += 1; // Pondération spécifique pour le mot clé "important"
        }
        return count;
    }
    // Autres méthodes pour la gestion des réclamations
}


