package com.example.carecareforeldres.mapper;

import com.example.carecareforeldres.DTO.UserDto;
import com.example.carecareforeldres.Entity.Evennement;
import com.example.carecareforeldres.DTO.EvennementDto;
import com.example.carecareforeldres.Entity.User;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")

public interface EvennementMapper {
   //@Mapping(source =  "ali",target = "etat")
   Evennement toEntity(EvennementDto dto);

   @Mapping(target=  "users", source = "users", qualifiedByName = "userList")
   EvennementDto toDto(Evennement event);

   List<EvennementDto> toDtos(List<Evennement> entities);

   @BeanMapping(ignoreByDefault = true)
   @Mapping(target = "id", source = "id")
   @Mapping(target = "firstname", source = "firstname")
   @Mapping(target = "lastname", source = "lastname")
   UserDto toDtoUser(User user);

   @Named("userList")
   default List<UserDto> toDtoUserList(List<User> users){
      return users.stream().map(this::toDtoUser).collect(Collectors.toList());
   }

}

