package com.atech.service;

import com.atech.entity.Recipe;
import com.atech.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService{

    private final RecipeRepository recipeRepository;

    @Autowired
    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(int recipeId, MultipartFile file) throws IOException {

        Recipe recipe = recipeRepository.findById(recipeId).get();

        Byte[] byteObjects = new Byte[file.getBytes().length];
        int i = 0;

        for (byte imageByte : file.getBytes()){
            byteObjects[i++] = imageByte;
        }

        recipe.setImage(byteObjects);
        recipeRepository.save(recipe);

    }
}
