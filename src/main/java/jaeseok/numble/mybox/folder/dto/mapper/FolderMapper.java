package jaeseok.numble.mybox.folder.dto.mapper;

import jaeseok.numble.mybox.folder.domain.Folder;
import jaeseok.numble.mybox.folder.dto.FolderCreateRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;

@Mapper(imports = LocalDateTime.class)
public interface FolderMapper {
    FolderMapper INSTANCE = Mappers.getMapper(FolderMapper.class);
}
