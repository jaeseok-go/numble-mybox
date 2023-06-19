package jaeseok.numble.mybox.folder.service;

import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.folder.domain.Folder;
import jaeseok.numble.mybox.folder.dto.FolderCreateParam;
import jaeseok.numble.mybox.folder.dto.FolderCreateResponse;
import jaeseok.numble.mybox.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FolderService {

    private final FolderRepository folderRepository;

    public FolderCreateResponse create(FolderCreateParam folderCreateParam) {
        Folder parent = folderRepository.findById(folderCreateParam.getParentId())
                .orElseThrow(() -> new MyBoxException(ResponseCode.PARENT_NOT_FOUND));

        Folder child = parent.addFolder(folderCreateParam.getFolderName());

        return new FolderCreateResponse(child);
    }

    public Integer delete(Long id) {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new MyBoxException(ResponseCode.FOLDER_NOT_FOUND));

        int deleteCount = folder.countTotalElement();

        folderRepository.delete(folder);

        return deleteCount;
    }
}
