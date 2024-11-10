package com.example.carecareforeldres.Service;

import com.example.carecareforeldres.DTO.CommentaireDto;
import com.example.carecareforeldres.Entity.Commentaire;
import com.example.carecareforeldres.Entity.User;

import java.util.List;

public interface ICommentaireService {


    CommentaireDto addCommentToEvennement(Long evennementId, Integer userId, CommentaireDto commentaireDto);
    CommentaireDto updateCommentaire(Long commentaireId, CommentaireDto newCommentaireDto);

    void deleteCommentaire(Long commentaireId);

    List<CommentaireDto> findCommentairesByEvennementId(Long evennementId);

}