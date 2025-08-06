package com.easylink.easylink.vibe_service.application.service;

import com.easylink.easylink.vibe_service.application.port.out.FileStoragePort;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class FileStorageService implements FileStoragePort {
    @Override
    public String save(MultipartFile file) throws IOException {



        return "";
    }
}
