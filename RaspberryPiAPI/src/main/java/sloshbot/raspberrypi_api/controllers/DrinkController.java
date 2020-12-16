package sloshbot.raspberrypi_api.controllers;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import sloshbot.raspberrypi_api.models.DAOs.Ingredient;
import sloshbot.raspberrypi_api.models.DAOs.Recipe;
import sloshbot.raspberrypi_api.models.DAOs.RecipeIngredient;
import sloshbot.raspberrypi_api.models.payloads.requests.drink.AddIngredientRequest;
import sloshbot.raspberrypi_api.models.payloads.requests.drink.AddRecipeRequest;
import sloshbot.raspberrypi_api.models.payloads.responses.drink.*;
import sloshbot.raspberrypi_api.repositories.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

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

    @PostMapping("/ingredient")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AddIngredientResponse> AddIngredient(@RequestBody AddIngredientRequest request) {
        AddIngredientResponse response = new AddIngredientResponse();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/recipe")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AddRecipeResponse> AddRecipe(@RequestBody AddRecipeRequest request) {
        AddRecipeResponse response = new AddRecipeResponse();
        Recipe newRecipe = new Recipe();


        newRecipe.setCreatedBy(request.getUsername());
        newRecipe.setCreatedDate(new Timestamp(DateTime.now().getMillis()));
        Recipe result = recipeRepository.save(newRecipe);

        return ResponseEntity.ok().body(response);
    }

    @PostMapping("/drinktype")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AddRecipeResponse> AddDrinkType(@RequestBody AddRecipeRequest request) {
        AddRecipeResponse response = new AddRecipeResponse();

        return ResponseEntity.ok().body(response);
    }

    //region Private Methods
    private Set<RecipeIngredient> extractRecipeIngredientsList(String[] ingredients){
        Set<RecipeIngredient> result = null;
        for(int i = 0; i < ingredients.length; i++){
            String ingredientString = ingredients[i];
            int amount = 0;
            if(ingredientString.contains("ounce") || ingredientString.contains("oz")){
                String amountString = ingredientString.substring(0, ingredientString.indexOf(' '));
                if(amountString.contains("/")){
                    String[] amountStringValues = amountString.split("/");
                    amountStringValues[1] = amountStringValues[1].replace("/", "");
                    amountStringValues[0] = amountStringValues[0] + ".0";
                    amount = (int)(Double.parseDouble(amountStringValues[0]) / Double.parseDouble(amountStringValues[1]));
                }
                else{
                    amount = (int)(Double.parseDouble(amountString));
                }

            }
            else if(ingredientString.contains("splash")){

            }
        }
        return result;
    }

    //endregion
}
