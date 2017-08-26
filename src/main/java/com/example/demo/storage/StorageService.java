package com.example.demo.storage;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void init();

    Long store(MultipartFile file, String saveFilePath);
    
    Long convertThumb(String originalFilePath, String thumbFilePath, Integer size, Double quality);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

}
