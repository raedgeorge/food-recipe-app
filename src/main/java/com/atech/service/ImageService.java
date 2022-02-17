package com.atech.service;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void saveImageFile(int recipeId, MultipartFile file);
}
