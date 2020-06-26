package sloshbot.raspberrypi_api.controllers.crud;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sloshbot.raspberrypi_api.models.DAOs.RecipeIngredient;
import sloshbot.raspberrypi_api.repositories.RecipeIngredientRepository;
import sloshbot.raspberrypi_api.util.exceptions.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/crud/v1/recipeingredient")
public class RecipeIngredientController {
    @Autowired
    private RecipeIngredientRepository recipeIngredientRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public List<RecipeIngredient> getAllRecipeIngredients() {
        return recipeIngredientRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<RecipeIngredient> getRecipeIngredientById(@PathVariable(value = "id") int recipeIngredientId)
            throws ResourceNotFoundException {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(recipeIngredientId)
                .orElseThrow(() -> new ResourceNotFoundException("RecipeIngredient not found for this id ::" + recipeIngredientId));
        return ResponseEntity.ok().body(recipeIngredient);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public RecipeIngredient createRecipeIngredient(@RequestBody RecipeIngredient recipeIngredient) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        recipeIngredient.setCreatedBy(authentication.getName());
        recipeIngredient.setCreatedDate(new Timestamp(DateTime.now().getMillis()));
        return recipeIngredientRepository.save(recipeIngredient);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<RecipeIngredient> updateRecipeIngredient(@PathVariable(value = "id") int recipeIngredientId,
                                                     @RequestBody RecipeIngredient recipeIngredientDetails)
            throws ResourceNotFoundException {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(recipeIngredientId)
                .orElseThrow(() -> new ResourceNotFoundException("RecipeIngredient not found for this id ::" + recipeIngredientId));
        recipeIngredient.setAmount(recipeIngredientDetails.getAmount());
        if(recipeIngredientDetails.getIngredientId() != 0)
            recipeIngredient.setIngredientId(recipeIngredientDetails.getIngredientId());
        if(recipeIngredientDetails.getRecipeId() != 0)
            recipeIngredient.setRecipeId(recipeIngredientDetails.getRecipeId());
        recipeIngredient.setModifiedDate(new Timestamp(DateTime.now().getMillis()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        recipeIngredient.setModifiedBy(authentication.getName());
        final RecipeIngredient updatedRecipeIngredient = recipeIngredientRepository.save(recipeIngredient);
        return ResponseEntity.ok(updatedRecipeIngredient);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERUSER')")
    public boolean deleteRecipeIngredient(@PathVariable(value = "id") int recipeIngredientId)
            throws ResourceNotFoundException {
        RecipeIngredient recipeIngredient = recipeIngredientRepository.findById(recipeIngredientId)
                .orElseThrow(() -> new ResourceNotFoundException("RecipeIngredient not found for this id :: " + recipeIngredientId));
        recipeIngredientRepository.delete(recipeIngredient);
        return true;
    }
}
