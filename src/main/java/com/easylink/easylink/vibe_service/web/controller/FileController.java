package com.easylink.easylink.vibe_service.web.controller;

import com.easylink.easylink.vibe_service.application.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v3/upload")
@RequiredArgsConstructor
public class FileController {

    private final FileStorageService fileStorageService;

    @PostMapping
    public ResponseEntity<?> upload(@RequestParam("file")MultipartFile file, @AuthenticationPrincipal Jwt jwt){
        try{
            String imageUrl = fileStorageService.save(file);
            return ResponseEntity.ok(imageUrl);
        }catch(IllegalArgumentException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.internalServerError().body("Upload failed.");
        }
    }
}
