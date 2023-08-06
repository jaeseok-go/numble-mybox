package jaeseok.numble.mybox.folder.service;

import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.file.domain.File;
import jaeseok.numble.mybox.file.service.FileService;
import jaeseok.numble.mybox.folder.domain.Folder;
import jaeseok.numble.mybox.folder.dto.FolderCreateParam;
import jaeseok.numble.mybox.folder.dto.FolderCreateResponse;
import jaeseok.numble.mybox.folder.dto.FolderRetrieveResponse;
import jaeseok.numble.mybox.folder.repository.FolderRepository;
import jaeseok.numble.mybox.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Folder createdChild = folderRepository.save(child);

        return new FolderCreateResponse(createdChild);
    }

    private void validateFolderName(Folder parent, String folderName) {
        if (parent.hasFolderName(folderName)) {
            throw new MyBoxException(ResponseCode.FOLDER_NAME_EXIST);
        }
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Integer delete(Long id) {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new MyBoxException(ResponseCode.FOLDER_NOT_FOUND));

        int count = 0;

        // ConcurrentModificationException를 방지하기 위해 인덱스 역순으로 처리
        List<File> files = folder.getChildFiles();
        for (int i = files.size()-1; i >= 0; i--) {
            File childFile = files.get(i);

            boolean isDeleted = fileService.delete(childFile.getId()) > 0;
            if (isDeleted) {
                folder.removeChildFile(childFile);
                count++;
            }
        }

        // ConcurrentModificationException를 방지하기 위해 인덱스 역순으로 처리
        List<Folder> folders = folder.getChildFolders();
        for (int i = folders.size()-1; i >= 0; i--) {
            Folder childFolder = folders.get(i);

            count += delete(childFolder.getId());
            if (!childFolder.isRemain()) {
                folder.removeChildFolder(childFolder);
            }
        }

        if (!folder.isRemain()) {
            folderRepository.deleteById(id);
            count++;
        }

        return count;
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
