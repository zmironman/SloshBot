package sloshbot.raspberrypi_api.controllers.crud;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sloshbot.raspberrypi_api.models.DAOs.Recipe;
import sloshbot.raspberrypi_api.repositories.RecipeRepository;
import sloshbot.raspberrypi_api.util.exceptions.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/crud/v1/recipe")
public class RecipeController {
    @Autowired
    private RecipeRepository recipeRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable(value = "id") Long recipeId)
            throws ResourceNotFoundException {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found for this id ::" + recipeId));
        return ResponseEntity.ok().body(recipe);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public Recipe createRecipe(@RequestBody Recipe recipe) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        recipe.setCreatedBy(authentication.getName());
        recipe.setCreatedDate(new Timestamp(DateTime.now().getMillis()));
        return recipeRepository.save(recipe);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable(value = "id") Long recipeId,
                                                     @RequestBody Recipe recipeDetails)
            throws ResourceNotFoundException {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found for this id ::" + recipeId));
        recipe.setFeatured(recipeDetails.getFeatured());
        if(recipeDetails.getDrinkTypeId() != 0)
            recipe.setDrinkTypeId(recipeDetails.getDrinkTypeId());
        if(recipeDetails.getName() != null)
            recipe.setName(recipeDetails.getName());
        recipe.setModifiedDate(new Timestamp(DateTime.now().getMillis()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        recipe.setModifiedBy(authentication.getName());
        final Recipe updatedRecipe = recipeRepository.save(recipe);
        return ResponseEntity.ok(updatedRecipe);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERUSER')")
    public boolean deleteRecipe(@PathVariable(value = "id") Long recipeId)
            throws ResourceNotFoundException {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe not found for this id :: " + recipeId));
        recipeRepository.delete(recipe);
        return true;
    }
}
