package jaeseok.numble.mybox.file.controller;

import jaeseok.numble.mybox.common.response.MyBoxResponse;
import jaeseok.numble.mybox.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class FileController {
    private final FileService fileService;

    @PostMapping("/v1/{parentId}/file")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file, @PathVariable Long parentId) {
        MyBoxResponse myBoxResponse = new MyBoxResponse(fileService.upload(file, parentId));
        return ResponseEntity.ok(myBoxResponse);
    }
}
