package jaeseok.numble.mybox.folder.dto;

import jaeseok.numble.mybox.file.domain.File;
import jaeseok.numble.mybox.folder.domain.Folder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class FolderChildResponse {
    List<FolderInfoResponse> folders;
    List<FileInfoResponse> files;

    public FolderChildResponse(List<Folder> folders, List<File> files) {
        this.folders = folders.stream().map(FolderInfoResponse::new).collect(Collectors.toList());
        this.files = files.stream().map(FileInfoResponse::new).collect(Collectors.toList());
    }
}
