package jaeseok.numble.mybox.folder.service;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.file.service.FileService;
import jaeseok.numble.mybox.folder.domain.Folder;
import jaeseok.numble.mybox.folder.dto.FolderCreateParam;
import jaeseok.numble.mybox.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FolderService {

    private final FolderRepository folderRepository;

    private final FileService fileService;

    private final JwtHandler jwtHandler;

    public Long create(FolderCreateParam folderCreateParam) {
        Folder parent = folderRepository.findById(folderCreateParam.getParentId())
                .orElseThrow(() -> new MyBoxException(ResponseCode.PARENT_NOT_FOUND));

        Folder child = parent.addFolder(folderCreateParam.getFolderName());
        return child.getId();
    }

    public Integer delete(Long id) {
        return 0;
    }
}
