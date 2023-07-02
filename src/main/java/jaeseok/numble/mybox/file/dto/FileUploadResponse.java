package jaeseok.numble.mybox.file.dto;

import jaeseok.numble.mybox.file.domain.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileUploadResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private FileSizeResponse size;

    public FileUploadResponse(File file) {
        this.id = file.getId();
        this.name = file.getName();
        this.size = new FileSizeResponse(file.getSize());
        this.createdAt = file.getCreatedAt();
        this.modifiedAt = file.getModifiedAt();
    }
}
