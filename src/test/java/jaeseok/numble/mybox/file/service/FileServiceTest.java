package jaeseok.numble.mybox.file.service;

import jaeseok.numble.mybox.common.auth.JwtHandler;
import jaeseok.numble.mybox.file.repository.FileRepository;
import jaeseok.numble.mybox.folder.repository.FolderRepository;
import jaeseok.numble.mybox.storage.StorageHandler;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(FileService.class)
@DisplayName("FileService Class")
class FileServiceTest {

    @MockBean
    private FileRepository fileRepository;

    @MockBean
    private FolderRepository folderRepository;

    @MockBean
    private StorageHandler storageHandler;

    @MockBean
    private JwtHandler jwtHandler;
}