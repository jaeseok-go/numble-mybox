package jaeseok.numble.mybox.folder.service;

import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.folder.domain.Folder;
import jaeseok.numble.mybox.folder.dto.FolderCreateRequestDto;
import jaeseok.numble.mybox.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import static jaeseok.numble.mybox.common.constant.MyBoxConstant.FOLDER_SEPARATOR;

@RequiredArgsConstructor
@Service
public class FolderService {

    private final FolderRepository folderRepository;

    public Folder create(FolderCreateRequestDto folderCreateRequestDto) {
        Folder parent = folderRepository.findById(folderCreateRequestDto.getParentId())
                .orElseThrow(() -> new MyBoxException(ResponseCode.PARENT_NOT_FOUND));

        String folderName = folderCreateRequestDto.getFolderName();

        return folderRepository.save(Folder.builder()
                .parentPath(parent.getParentPath() + FOLDER_SEPARATOR + parent.getName())
                .name(folderName)
                .parent(parent)
                .owner(parent.getOwner())
                .createdAt(LocalDateTime.now())
                .build());
    }
}
