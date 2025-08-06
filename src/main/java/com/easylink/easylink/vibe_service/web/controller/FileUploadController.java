package com.easylink.easylink.vibe_service.web.controller;

import com.easylink.easylink.vibe_service.application.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v3/upload")
@RequiredArgsConstructor
public class FileUploadController {

    private final FileStorageService fileStorageService;

    public ResponseEntity<?> upload(@RequestParam("file")MultipartFile file){
        try{
            String imageUrl = fileStorageService.save(file);
            return ResponseEntity.internalServerError().body(imageUrl);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Upload failed.");
        }
    }
}
