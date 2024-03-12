package com.enigma.wmb_api.service;

import com.enigma.wmb_api.entity.MImage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    MImage create(MultipartFile multipartFile);

    Resource getById(String id);

    void deleteById(String id);
}