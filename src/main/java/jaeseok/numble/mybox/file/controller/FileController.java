package jaeseok.numble.mybox.file.controller;

import jaeseok.numble.mybox.common.response.MyBoxResponse;
import jaeseok.numble.mybox.file.dto.FileDownloadResponse;
import jaeseok.numble.mybox.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class FileController {
    private final FileService fileService;

    @PostMapping(value = "/v1/{parentId}/file")
    public ResponseEntity upload(@RequestParam(required = false)  MultipartFile file, @PathVariable Long parentId) {
        MyBoxResponse myBoxResponse = new MyBoxResponse(fileService.upload(file, parentId));
        return ResponseEntity.ok(myBoxResponse);
    }

    @GetMapping("/v1/{fileId}")
    public ResponseEntity download(@PathVariable Long fileId) {
        FileDownloadResponse fileDownloadResponse = fileService.download(fileId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .cacheControl(CacheControl.noCache())
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileDownloadResponse.getName())
                .body(fileDownloadResponse.getResource());
    }

    @DeleteMapping("/v1/{fileId}")
    public ResponseEntity delete(@PathVariable Long fileId) {
        MyBoxResponse myBoxResponse = new MyBoxResponse(fileService.delete(fileId));
        return ResponseEntity.ok(myBoxResponse);
    }
}
