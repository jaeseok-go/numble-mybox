package jaeseok.numble.mybox.folder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FolderDeleteResponse {
    private Long fileCount;
    private Long folderCount;
}
