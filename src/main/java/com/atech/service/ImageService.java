package com.atech.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {

    void saveImageFile(int recipeId, MultipartFile file) throws IOException;
}
