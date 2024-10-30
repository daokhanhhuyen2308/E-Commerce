package com.t3h.e_commerce.service.impl;

import com.t3h.e_commerce.exception.CustomExceptionHandler;
import com.t3h.e_commerce.service.IFileService;
import org.springframework.beans.factory.annotation.Value; // Import the correct Value annotation
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileServiceImpl implements IFileService {
    @Value("${storage.root.folder}") // Correct syntax for injecting property
    private String ROOT_UPLOAD_DIR_AVATAR;
    @Value("${storage.root.folder}")
    private final String DEFAULT_AVATAR ="default_avatar.jpg";
    private String ROOT_IMG_PRODUCT;
    @Override
    public Resource loadFile(String filename) {
        return getResource(filename, ROOT_UPLOAD_DIR_AVATAR);
    }

    @Override
    public Resource getImg(String fileName) {
        return getResource(fileName, ROOT_IMG_PRODUCT);
    }

    private Resource getResource(String fileName, String rootImgProduct) {
        String fullPath = rootImgProduct + fileName;

        File file = new File(fullPath);
        try {
            if (file.exists()){
                return new FileSystemResource(file);
            }
        }catch (Exception e){
            throw CustomExceptionHandler.badRequestException(e.getMessage());
        }
        fullPath = ROOT_UPLOAD_DIR_AVATAR  + DEFAULT_AVATAR;
        file = new File(fullPath);
        return new FileSystemResource(file);
    }

}
