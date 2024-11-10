package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.DTO.CommentaireDto;
import com.example.carecareforeldres.DTO.EvennementDto;
import com.example.carecareforeldres.Entity.Commentaire;
import com.example.carecareforeldres.Entity.Evennement;
import com.example.carecareforeldres.Entity.User;
import com.example.carecareforeldres.Repository.CommantaireRepository;
import com.example.carecareforeldres.Repository.UserRepository;
import com.example.carecareforeldres.auth.AuthenticationService;
import com.example.carecareforeldres.mapper.CommentaireMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentaireService implements ICommentaireService {

    private final CommantaireRepository commentaireRepository;
    private final EvennementService evennementService;
    private final UserService userService;
    private final CommentaireMapper commentaireMapper; // Ajout du mapper



    @Override
    public List<CommentaireDto> findCommentairesByEvennementId(Long evennementId) {
        List<Commentaire> commentaires = commentaireRepository.findByEvennementId(evennementId);
        return commentaireMapper.toDtos(commentaires); // Convertit les entités en DTOs
    }

    @Override
    public CommentaireDto addCommentToEvennement(Long evennementId, Integer userId, CommentaireDto commentaireDto) {
        User user = userService.getUserById(userId);
        if (user == null) {
            throw new RuntimeException("Utilisateur non trouvé avec l'ID: " + userId);
        }

        Evennement evennement = evennementService.getEvennementById(evennementId);
        if (evennement == null) {
            throw new RuntimeException("Événement non trouvé avec l'ID: " + evennementId);
        }

        Commentaire commentaire = commentaireMapper.toEntity(commentaireDto); // Convertit le DTO en entité
        commentaire.setEvennement(evennement);
        commentaire.setUser(user);

        Commentaire savedCommentaire = commentaireRepository.save(commentaire);
        return commentaireMapper.toDto(savedCommentaire); // Convertit l'entité sauvegardée en DTO
    }

    @Override
    public CommentaireDto updateCommentaire(Long commentaireId, CommentaireDto newCommentaireDto) {
        Commentaire commentaire = commentaireRepository.findById(commentaireId)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé avec l'ID: " + commentaireId));

        // Mettre à jour les champs du commentaire existant avec les nouvelles valeurs du DTO
        commentaire.setContenu(newCommentaireDto.getContenu());
        commentaire.setStatut(newCommentaireDto.getStatut());

        Commentaire updatedCommentaire = commentaireRepository.save(commentaire);
        return commentaireMapper.toDto(updatedCommentaire); // Convertit l'entité mise à jour en DTO
    }

    @Override
    public void deleteCommentaire(Long commentaireId) {
        Optional<Commentaire> optionalCommentaire = commentaireRepository.findById(commentaireId);
        if (optionalCommentaire.isPresent()) {
            Commentaire commentaire = optionalCommentaire.get();
            commentaireRepository.delete(commentaire);
        } else {
            throw new RuntimeException("Commentaire non trouvé avec l'ID: " + commentaireId);
        }
    }


}