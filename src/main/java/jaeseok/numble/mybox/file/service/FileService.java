package jaeseok.numble.mybox.file.service;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.file.domain.File;
import jaeseok.numble.mybox.file.dto.FileDownloadResponse;
import jaeseok.numble.mybox.file.dto.FileUploadResponse;
import jaeseok.numble.mybox.file.repository.FileRepository;
import jaeseok.numble.mybox.folder.domain.Folder;
import jaeseok.numble.mybox.folder.repository.FolderRepository;
import jaeseok.numble.mybox.storage.StorageHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FileService {
    private final FileRepository fileRepository;
    private final FolderRepository folderRepository;
    private final StorageHandler storageHandler;
    private final JwtHandler jwtHandler;

    @Transactional
    public FileUploadResponse upload(MultipartFile multipartFile, Long parentId) {
        Folder parent = folderRepository.findById(parentId)
                        .orElseThrow(() -> new MyBoxException(ResponseCode.PARENT_NOT_FOUND));

        String fileName = multipartFile.getOriginalFilename();

        validateFileUpload(parent, fileName);

        File uploadedFile = File.builder()
                .name(fileName)
                .size(multipartFile.getSize())
                .owner(parent.getOwner())
                .build();

        parent.addChildFile(uploadedFile);

        fileRepository.save(uploadedFile);

        try {
            storageHandler.upload(multipartFile, uploadedFile.getFileKey());
        } catch (IOException e) {
            throw new MyBoxException(ResponseCode.FILE_UPLOAD_FAIL);
        }

        return new FileUploadResponse(uploadedFile);
    }

    private void validateFileUpload(Folder parent, String fileName) {
        if (!parent.isOwner(jwtHandler.getId())) {
            throw new MyBoxException(ResponseCode.INVALID_TOKEN);
        }

        if (parent.hasFileName(fileName)) {
            throw new MyBoxException(ResponseCode.FILE_NAME_EXIST);
        }
    }

    public FileDownloadResponse download(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(()-> new MyBoxException(ResponseCode.FILE_NOT_FOUND));

        String fileName = file.getName();
        InputStreamResource resource = new InputStreamResource(storageHandler.download(file.getFileKey()));

        return new FileDownloadResponse(fileName, resource);
    }

    @Transactional
    public Long delete(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new MyBoxException(ResponseCode.FILE_NOT_FOUND));

        try {
            fileRepository.delete(file);
            storageHandler.delete(file.getFileKey());
        } catch (Exception e) {
            return 0L;
        }

        return 1L;
    }

    public Long deleteAll(List<File> files) {
        return files.stream()
                .mapToLong(file -> delete(file.getId()))
                .count();
    }

}
