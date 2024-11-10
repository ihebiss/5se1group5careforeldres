package com.example.carecareforeldres.mapper;

import com.example.carecareforeldres.DTO.CommentaireDto;
import com.example.carecareforeldres.DTO.UserDto;
import com.example.carecareforeldres.Entity.Commentaire;
import com.example.carecareforeldres.Entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")

public interface CommentaireMapper {
    Commentaire toEntity( CommentaireDto dto);
    @Mapping(target = "user", source = "user", qualifiedByName = "userDto")
    CommentaireDto toDto(Commentaire comment);

    List<CommentaireDto> toDtos(List<Commentaire> entities);

    @Named("userDto")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    UserDto toDtoUser(User user);
}
