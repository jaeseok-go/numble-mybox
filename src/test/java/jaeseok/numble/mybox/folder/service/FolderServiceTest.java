package jaeseok.numble.mybox.folder.service;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.file.service.FileService;
import jaeseok.numble.mybox.folder.domain.Folder;
import jaeseok.numble.mybox.folder.dto.FolderCreateParam;
import jaeseok.numble.mybox.folder.dto.FolderCreateResponse;
import jaeseok.numble.mybox.folder.repository.FolderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@DisplayName("FolderServiceTest")
@WebMvcTest(FolderService.class)
class FolderServiceTest {

    @Autowired
    private FolderService folderService;

    @MockBean
    private FolderRepository folderRepository;

    @MockBean
    private FileService fileService;

    @MockBean
    private JwtHandler jwtHandler;

    @Nested
    @DisplayName("create")
    class Create {
        private Long parentId = 1L;

        private String folderName = "test";

        private FolderCreateParam folderCreateParam = new FolderCreateParam(parentId, folderName);

        private Folder parent = Folder.builder()
                .id(parentId)
                .name("parent")
                .build();

        @DisplayName("성공")
        @Test
        void success() {
            // given
            BDDMockito.when(folderRepository.findById(any(Long.class)))
                    .thenReturn(Optional.of(parent));
            BDDMockito.when(folderRepository.save(any()))
                    .thenReturn(Folder.builder().name(folderName).build());

            // when
            FolderCreateResponse folderCreateResponse = folderService.create(folderCreateParam);

            // then
            Assertions.assertEquals(folderName, folderCreateResponse.getName());
        }
    }
}