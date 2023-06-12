package jaeseok.numble.mybox.folder.service;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.common.response.ResponseCode;
import jaeseok.numble.mybox.common.response.exception.MyBoxException;
import jaeseok.numble.mybox.file.service.FileService;
import jaeseok.numble.mybox.folder.domain.Folder;
import jaeseok.numble.mybox.folder.dto.FolderCreateRequestDto;
import jaeseok.numble.mybox.folder.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FolderService {

    private final FolderRepository folderRepository;

    private final FileService fileService;

    private final JwtHandler jwtHandler;

    public Long create(FolderCreateRequestDto folderCreateRequestDto) {
        Folder parent = folderRepository.findById(folderCreateRequestDto.getParentId())
                .orElseThrow(() -> new MyBoxException(ResponseCode.PARENT_NOT_FOUND));

        String folderName = folderCreateRequestDto.getFolderName();

        return folderRepository.save(Folder.builder()
                .parentPath(parent.getCurrentPath())
                .name(folderName)
                .parent(parent)
                .owner(parent.getOwner())
                .createdAt(LocalDateTime.now())
                .build()).getId();
    }

    public Integer delete(Long id) {
        Folder folder = folderRepository.findById(id)
                .orElseThrow(() -> new MyBoxException(ResponseCode.FOLDER_NOT_FOUND));

        String memberId = jwtHandler.getId();
        folder.validateOwner(memberId);

        int deleteCount = 0;
        List<Folder> targets = folderRepository
                .findByOwnerAndParentPathStartsWithOrderByParentPathDesc(memberId, folder.getCurrentPath());
        for(Folder target : targets) {
            fileService.deleteChild(target);
            deleteCount++;
            try {
                folderRepository.deleteById(target.getId());
                deleteCount++;
            } catch (Exception e) {
                throw new MyBoxException(ResponseCode.FOLDER_DELETE_FAIL, target.getCurrentPath());
            }
        }

        return deleteCount;
    }
}
