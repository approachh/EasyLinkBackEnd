package com.easylink.easylink.vibe_service.application.port.out;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStoragePort {
    String save(MultipartFile file) throws IOException;
}
