package jaeseok.numble.mybox.folder.controller;

import jaeseok.numble.mybox.common.response.MyBoxResponse;
import jaeseok.numble.mybox.folder.dto.FolderCreateParam;
import jaeseok.numble.mybox.folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class FolderController {

    private final FolderService folderService;

    @PostMapping("/v1/folder")
    public ResponseEntity createFolder(@RequestBody FolderCreateParam folderCreateParam) {
        MyBoxResponse myBoxResponse = new MyBoxResponse(folderService.create(folderCreateParam));
        return ResponseEntity.ok(myBoxResponse);
    }

    @DeleteMapping("/v1/folder/{id}")
    public ResponseEntity deleteFolder(@PathVariable Long id) {
        MyBoxResponse myBoxResponse = new MyBoxResponse(folderService.delete(id));
        return ResponseEntity.ok(myBoxResponse);
    }

    @GetMapping("/v1/folder/{id}")
    public ResponseEntity retrieveFolder(@PathVariable Long id) {
        MyBoxResponse myBoxResponse = new MyBoxResponse(folderService.retrieveFolder(id));
        return ResponseEntity.ok(myBoxResponse);
    }
}
