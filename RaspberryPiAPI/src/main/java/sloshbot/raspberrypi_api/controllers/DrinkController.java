package sloshbot.raspberrypi_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import sloshbot.raspberrypi_api.models.DAOs.Ingredient;
import sloshbot.raspberrypi_api.repositories.*;

@Controller
@RequestMapping("/api/v1/drink")
public class DrinkController {
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;
    @Autowired
    private IngredientRepository ingredientRepository;
    @Autowired
    private IngredientTypeRepository ingredientTypeRepository;
    @Autowired
    private DrinkTypeRepository drinkTypeRepository;
}
