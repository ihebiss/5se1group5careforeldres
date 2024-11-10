package com.example.carecareforeldres.RestController;

import com.example.carecareforeldres.DTO.CommentaireDto;
import com.example.carecareforeldres.Entity.Commentaire;
import com.example.carecareforeldres.Entity.User;
import com.example.carecareforeldres.Service.ICommentaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/commentaires")
public class CommentaireController {


    private final ICommentaireService commentaireService;

//////////////////////////////////////////////////////////////////////////////

    @PostMapping("/evennement/{evennementId}/user/{userId}")
    public CommentaireDto addCommentToEvennement(@PathVariable Long evennementId, @PathVariable Integer userId, @RequestBody CommentaireDto commentaireDto) {
        return commentaireService.addCommentToEvennement(evennementId, userId, commentaireDto);
    }

    @PutMapping("/commentaire/{commentaireId}")
    public CommentaireDto updateCommentaire(@PathVariable Long commentaireId, @RequestBody CommentaireDto newCommentaireDto) {
        return commentaireService.updateCommentaire(commentaireId, newCommentaireDto);
    }

    @DeleteMapping("/commentaire/{commentaireId}")
    public void deleteCommentaire(@PathVariable Long commentaireId) {
        commentaireService.deleteCommentaire(commentaireId);
    }

    @GetMapping("/evennement/{evennementId}")
    public ResponseEntity<List<CommentaireDto>> getCommentairesByEvennementId(@PathVariable Long evennementId) {
        List<CommentaireDto> commentaires = commentaireService.findCommentairesByEvennementId(evennementId);
        return ResponseEntity.ok(commentaires);
    }
}