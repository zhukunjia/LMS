package org.lms.converter;

import org.lms.dto.library.AddLibraryCmd;
import org.lms.dto.library.LibraryDTO;
import org.lms.entity.LibraryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface  LibraryConverter {
    LibraryConverter INSTANCE = Mappers.getMapper(LibraryConverter.class);

    LibraryEntity addLibraryCmdToEntity(AddLibraryCmd addLibraryCmd);

    LibraryDTO entityToDTO(LibraryEntity entity);

    List<LibraryDTO> entityListToDTO(List<LibraryEntity> entityList);
}
