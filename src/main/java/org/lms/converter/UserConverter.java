package org.lms.converter;

import org.lms.dto.user.UserDTO;
import org.lms.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserConverter {
    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    UserDTO entityToDTO(UserEntity entity);

    List<UserDTO> entityListToDTO(List<UserEntity> entityList);
}
