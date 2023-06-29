package jaeseok.numble.mybox.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.io.InputStreamResource;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FileDownloadResponse {
    private String name;
    private InputStreamResource resource;
}
