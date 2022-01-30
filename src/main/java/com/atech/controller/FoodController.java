package com.atech.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/food")
public class FoodController {

    @GetMapping({"", "/", "food-list", "food-list.html"})
    public String getFoodList(){

        return "food/food-list";
    }

}
