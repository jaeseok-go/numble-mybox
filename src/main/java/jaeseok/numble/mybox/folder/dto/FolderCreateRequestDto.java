package jaeseok.numble.mybox.folder.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FolderCreateRequestDto {
    private Long parentId;
    private String folderName;

    public FolderCreateRequestDto(String folderName) {
        this.folderName = folderName;
    }
}
