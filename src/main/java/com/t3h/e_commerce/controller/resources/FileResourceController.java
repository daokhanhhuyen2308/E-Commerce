package com.t3h.e_commerce.controller.resources;

import com.t3h.e_commerce.service.impl.FileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileResourceController {

    private final FileServiceImpl iFileService;
    @GetMapping("/avatar/{filename}")
    public ResponseEntity<Resource> getAvatar(@PathVariable String filename) {
    Resource file = iFileService.loadFile(filename);
    if (file != null){
    return ResponseEntity.ok().
            header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + filename + "\"").body(file);

    }
    return ResponseEntity.notFound().build();
    }

    @GetMapping("/image-product/{filename}")
    public ResponseEntity<Resource> getImg(@PathVariable String filename) {
        Resource file = iFileService.getImg(filename);
        if(file != null){
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"").body(file);
        }
        return  ResponseEntity.notFound().build();
    }
}
