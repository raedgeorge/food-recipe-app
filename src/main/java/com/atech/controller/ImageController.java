package com.atech.controller;

import com.atech.commands.RecipeCommand;
import com.atech.service.ImageService;
import com.atech.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Controller
@Slf4j
public class ImageController {

    private final RecipeService recipeService;
    private final ImageService imageService;

    @Autowired
    public ImageController(RecipeService recipeService,
                           ImageService imageService) {

        this.recipeService = recipeService;
        this.imageService = imageService;
    }

    @GetMapping("/recipe/{recipeId}/image")
    public String showUploadForm(@PathVariable("recipeId") int recipeId,
                                 Model model){

        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        model.addAttribute("recipe", recipeCommand);

        log.debug("inside the image controller show upload form");

        return "/image/imageUploadForm";
    }

    @PostMapping("/recipe/{recipeId}/image")
    public String handleImagePost(@PathVariable("recipeId") int recipeId,
                                  @RequestParam("imagefile")MultipartFile file) throws IOException {

        imageService.saveImageFile(recipeId, file);
        log.debug("inside the image controller handle image post");

        return "redirect:/food/"+ recipeId + "/recipe";
    }

    @GetMapping("/recipe/{recipeId}/recipeImage")
    public void renderImageFromDB(@PathVariable("recipeId") int recipeId,
                                  HttpServletResponse response) throws IOException {

        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);

        if (recipeCommand.getImage() != null) {
            byte[] bytes = new byte[recipeCommand.getImage().length];


            int i = 0;
            for (byte wrappedByte : recipeCommand.getImage()) {
                bytes[i++] = wrappedByte;
            }
            response.setContentType("image/jpeg");
            InputStream inputStream = new ByteArrayInputStream(bytes);
            IOUtils.copy(inputStream, response.getOutputStream());
        }
    }
}
