package com.easylink.easylink.vibe_service.application.port.out;

import jakarta.annotation.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStoragePort {
    String save(MultipartFile file) throws IOException;
  //  Resource getFile(String imageUrl);
}
