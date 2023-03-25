package jaeseok.numble.mybox.folder.controller;

import jaeseok.numble.mybox.common.response.MyBoxResponse;
import jaeseok.numble.mybox.folder.dto.FolderCreateRequestDto;
import jaeseok.numble.mybox.folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class FolderController {

    private final FolderService folderService;

    @PostMapping("/v1/folder")
    public ResponseEntity createFolder(@RequestBody FolderCreateRequestDto folderCreateRequestDto) {
        MyBoxResponse myBoxResponse = new MyBoxResponse(folderService.create(folderCreateRequestDto));
        return ResponseEntity.ok(myBoxResponse);
    }
}
