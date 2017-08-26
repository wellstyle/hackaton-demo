package com.example.demo.storage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Service
public class FileSystemStorageService implements StorageService {

    private static final Logger logger = LoggerFactory.getLogger(FileSystemStorageService.class);
    
    private final Path rootLocation;
    
    @Autowired
    public FileSystemStorageService(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    @Override
    public Long store(MultipartFile file, String saveFilePath) {
    	try {
    		String directory = saveFilePath.substring(0, saveFilePath.lastIndexOf("/"));
    		logger.debug("### directory: {}", directory);
    		Files.createDirectories(rootLocation.resolve(directory));
    		return Files.copy(file.getInputStream(), rootLocation.resolve(saveFilePath));
    	} catch (IOException e) {
    		throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
    	}
    }
    
    @Override
    public Long convertThumb(String originalFilePath, String thumbFilePath, Integer size, Double quality) {
        try {
    		String directory = thumbFilePath.substring(0, thumbFilePath.lastIndexOf("/"));
            Files.createDirectories(rootLocation.resolve(directory));
            File thumbFile = new File(rootLocation.resolve(thumbFilePath).toString());
            Thumbnails.of(new File(rootLocation.resolve(originalFilePath).toString()))
	            .size(size, size)
	            .outputQuality(quality)
	            .toFile(thumbFile);
            return thumbFile.length();
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + thumbFilePath, e);
        }
    }
    

    
//    private String createDayDirectory() {
//        Calendar cal = Calendar.getInstance();
//        String year = String.format("%04d", cal.get(Calendar.YEAR));
//        String month = String.format("%02d", cal.get(Calendar.MONTH) + 1);
//        String day = String.format("%02d", cal.get(Calendar.DAY_OF_MONTH));;
//        
//        File f = new File(year);
//        if (!f.mkdirs()) {
//        	logger.error("디렉토리 생성 실패");
//        }
//    }
    
    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(path -> this.rootLocation.relativize(path));
        } catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException("Could not read file: " + filename);

            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (FileAlreadyExistsException ee) {
            logger.debug("### Already Exists '{}'", rootLocation);
            return;
        } catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
