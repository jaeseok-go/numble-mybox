package jaeseok.numble.mybox.folder.dto;

import jaeseok.numble.mybox.file.domain.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileInfoResponse {
    private Long id;
    private String name;
    private Long size;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public FileInfoResponse(File file) {
        this.id = file.getId();
        this.name = file.getName();
        this.size = file.getSize();
        this.createdAt = file.getCreatedAt();
        this.modifiedAt = file.getModifiedAt();
    }
}
