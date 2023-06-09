package jaeseok.numble.mybox.folder.service;

import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.file.service.FileService;
import jaeseok.numble.mybox.folder.domain.Folder;
import jaeseok.numble.mybox.folder.dto.FolderCreateParam;
import jaeseok.numble.mybox.folder.dto.FolderCreateResponse;
import jaeseok.numble.mybox.folder.dto.FolderDeleteResponse;
import jaeseok.numble.mybox.folder.dto.FolderRetrieveResponse;
import jaeseok.numble.mybox.folder.repository.FolderRepository;
import jaeseok.numble.mybox.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class FolderService {

    private final FolderRepository folderRepository;

    private final FileService fileService;

    public FolderCreateResponse create(FolderCreateParam folderCreateParam) {
        Folder parent = folderRepository.findById(folderCreateParam.getParentId())
                .orElseThrow(() -> new MyBoxException(ResponseCode.PARENT_NOT_FOUND));

        String folderName = folderCreateParam.getFolderName();

        validateFolderName(parent, folderName);

        Folder child = parent.createFolder(folderName);
        return new FolderCreateResponse(child);
    }

    private void validateFolderName(Folder parent, String folderName) {
        if (parent.hasFolderName(folderName)) {
            throw new MyBoxException(ResponseCode.FOLDER_NAME_EXIST);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public FolderDeleteResponse delete(Long id) {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new MyBoxException(ResponseCode.FOLDER_NOT_FOUND));

        Long fileCount = fileService.deleteAll(folder.getAllChildFiles());
        Long folderCount = folder.deleteChildFolders();

        if (folder.countChild() == 0) {
            folderRepository.delete(folder);
            folderCount++;
        }

        return new FolderDeleteResponse(fileCount, folderCount);
    }

    public FolderRetrieveResponse retrieveFolder(Long id) {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new MyBoxException(ResponseCode.FOLDER_NOT_FOUND));

        return new FolderRetrieveResponse(folder);
    }

    public Folder retrieveRootFolder(Member member) {
        return folderRepository
                .findFolderByOwnerIdAndParentIsNull(member.getId())
                .orElseThrow(() -> new MyBoxException(ResponseCode.FOLDER_NOT_FOUND));
    }
}
