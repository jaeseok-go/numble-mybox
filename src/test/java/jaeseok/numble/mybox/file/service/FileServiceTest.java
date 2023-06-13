package jaeseok.numble.mybox.file.service;

import jaeseok.numble.mybox.folder.dto.FolderCreateRequestDto;
import jaeseok.numble.mybox.folder.service.FolderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@DisplayName("FileService Class")
@SpringBootTest
class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Autowired
    private FolderService folderService;

    private Long testParentId;

    @BeforeEach
    @DisplayName("테스트용 폴더 생성")
    void createFolder() {
        // testParentId = folderService.create(new FolderCreateRequestDto(null, "tes t_parent"));
    }

    @Nested
    @DisplayName("파일 업로드")
    class Upload {
        @Test
        @DisplayName("성공")
        void success() throws FileNotFoundException, IOException {
            // given
            MockMultipartFile file = new MockMultipartFile("test_file",
                    "original_file",
                    "image/png",
                    new FileInputStream("src/test/resources/img/test_image.png"));


            // when
            // Long fileId = fileService.upload(file, testParentId);

            // then
        }
    }
}