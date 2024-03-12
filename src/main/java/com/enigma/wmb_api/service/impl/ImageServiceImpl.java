package com.enigma.wmb_api.service.impl;

import com.enigma.wmb_api.entity.MImage;
import com.enigma.wmb_api.repository.ImageRepository;
import com.enigma.wmb_api.service.ImageService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    private final Path directoryPath;
    private final ImageRepository imageRepository;

    public ImageServiceImpl(
            @Value("${wmb_api.multipart.path-location}") Path directoryPath,
            ImageRepository imageRepository
    ) {
        this.directoryPath = directoryPath;
        this.imageRepository = imageRepository;
    }

    @PostConstruct
    public void initDirectory() {
        if (!Files.exists(directoryPath)) {
            try {
                Files.createDirectory(directoryPath);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }
        }
    }

    @Override
    public MImage create(MultipartFile multipartFile) {
        try {
            // validasi ekstensi file yang bisa di upload
            if (!List.of("image/jpeg", "image/png", "image/jpg", "image/gif").contains(multipartFile.getContentType()))
                throw new ConstraintViolationException("invalid content type", null);

            // simpan dulu di directory
            String uniqueFiles = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();

            // menyimpan path -> /image/___.png
            Path filePath = directoryPath.resolve(uniqueFiles);

            // menyimpan file ke path
            // parameter 1 : menyimpan binary gambar/file
            // parameter 2 : menyimpan lokasi pathnya
            Files.copy(multipartFile.getInputStream(), filePath);

            // simpan path di database
            MImage image = MImage.builder()
                    .name(uniqueFiles)
                    .contentType(multipartFile.getContentType())
                    .size(multipartFile.getSize())
                    .path(filePath.toString())
                    .build();

            imageRepository.saveAndFlush(image);

            return image;
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public Resource getById(String id) {
        try {
            MImage image = imageRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "data not found"));
            Path filePath = Paths.get(image.getPath());
            if (!Files.exists(filePath))
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "data not found");
            return new UrlResource(filePath.toUri());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void deleteById(String id) {
        try {
            MImage image = imageRepository.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "data not found"));
            Path filePath = Paths.get(image.getPath());
            if (!Files.exists(filePath))
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "data not found");
            Files.delete(filePath);
            imageRepository.delete(image);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
