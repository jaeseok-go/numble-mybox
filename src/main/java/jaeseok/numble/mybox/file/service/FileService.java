package jaeseok.numble.mybox.file.service;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.file.repository.FileRepository;
import jaeseok.numble.mybox.folder.domain.Folder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;
    private final JwtHandler jwtHandler;

    public Long calculateUsageToByte(String memberId) {
        return fileRepository.sumSizeByOwnerId(memberId);
    }

    public Long deleteChild(Folder folder) {
        String memberId = jwtHandler.getId();
        folder.validateOwner(memberId);

        try {
            return fileRepository.deleteByOwnerAndParentPathStartsWith(memberId, folder.getCurrentPath());
        } catch (Exception e) {
            throw new MyBoxException(ResponseCode.FILE_DELETE_FAIL);
        }
    }
}
