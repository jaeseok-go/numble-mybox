package jaeseok.numble.mybox.file.service;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.file.domain.File;
import jaeseok.numble.mybox.file.repository.FileRepository;
import jaeseok.numble.mybox.folder.domain.Element;
import jaeseok.numble.mybox.folder.domain.Folder;
import jaeseok.numble.mybox.folder.repository.FolderRepository;
import jaeseok.numble.mybox.storage.FileKey;
import jaeseok.numble.mybox.storage.StorageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final StorageHandler storageHandler;
    private final JwtHandler jwtHandler;

    @Transactional
    public Long upload(MultipartFile file, Long parentId) {
        Folder parent = folderRepository.findById(parentId)
                        .orElseThrow(() -> new MyBoxException(ResponseCode.PARENT_NOT_FOUND));

        parent.validateOwner(jwtHandler.getId());

        File uploadedFile = fileRepository.save(File.builder()
                .name(file.getName())
                .size(file.getSize())
                .owner(parent.getOwner())
                .build());

        try {
            storageHandler.upload(file, new FileKey(uploadedFile.getCurrentPath()));
        } catch (IOException e) {
            throw new MyBoxException(ResponseCode.FILE_UPLOAD_FAIL);
        }

        return uploadedFile.getId();
    }

    public Integer deleteAll(List<File> files) {
        int deleteCount = storageHandler.deleteAll(files.stream()
                .map(Element::getId)
                .map(FileKey::new)
                .collect(Collectors.toList()));

        fileRepository.deleteAll(files);

        return deleteCount;
    }


    /*
     * SELECT *
     * FROM element
     * WHERE dtype = 'FILE'
     * AND member_id = :memberId
     * AND parent_id = null;
     * */
    public Folder retrieveRootFolder() {
        Long memberId = jwtHandler.getId();

        return Folder.builder().id(1L).build();
    }
}
