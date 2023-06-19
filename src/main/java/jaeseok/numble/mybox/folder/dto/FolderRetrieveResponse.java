package jaeseok.numble.mybox.folder.dto;

import jaeseok.numble.mybox.folder.domain.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolderRetrieveResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private FolderChildResponse children;

    public FolderRetrieveResponse(Folder folder) {
        this.id = folder.getId();
        this.name = folder.getName();
        this.createdAt = folder.getCreatedAt();
        this.modifiedAt = folder.getModifiedAt();
        this.children = new FolderChildResponse(folder.getChildFolders(), folder.getChildFiles());
    }
}
