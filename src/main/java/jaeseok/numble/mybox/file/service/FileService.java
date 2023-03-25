package jaeseok.numble.mybox.file.service;

import jaeseok.numble.mybox.file.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;

    public Long calculateUsageToByte(String memberId) {
        return fileRepository.sumSizeByMemberId(memberId);
    }
}
