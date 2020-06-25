package sloshbot.raspberrypi_api.controllers.crud;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sloshbot.raspberrypi_api.models.DAOs.Ingredient;
import sloshbot.raspberrypi_api.repositories.IngredientRepository;
import sloshbot.raspberrypi_api.util.exceptions.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/crud/v1/ingredient")
public class IngredientController {
    @Autowired
    private IngredientRepository ingredientRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable(value = "id") Long ingredientId)
            throws ResourceNotFoundException {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id ::" + ingredientId));
        return ResponseEntity.ok().body(ingredient);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public Ingredient createIngredient(@RequestBody Ingredient ingredient) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ingredient.setCreatedBy(authentication.getName());
        ingredient.setCreatedDate(new Timestamp(DateTime.now().getMillis()));
        return ingredientRepository.save(ingredient);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<Ingredient> updateIngredient(@PathVariable(value = "id") Long ingredientId,
                                                     @RequestBody Ingredient ingredientDetails)
            throws ResourceNotFoundException {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id ::" + ingredientId));
        if(ingredientDetails.getAlcoholContent() != 0)
            ingredient.setAlcoholContent(ingredientDetails.getAlcoholContent());
        if(ingredientDetails.getIngredientTypeId() != 0)
            ingredient.setIngredientTypeId(ingredientDetails.getIngredientTypeId());
        if(ingredientDetails.getPrice() != 0)
            ingredient.setPrice(ingredientDetails.getPrice());
        ingredient.setModifiedDate(new Timestamp(DateTime.now().getMillis()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ingredient.setModifiedBy(authentication.getName());
        final Ingredient updatedIngredient = ingredientRepository.save(ingredient);
        return ResponseEntity.ok(updatedIngredient);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERUSER')")
    public boolean deleteIngredient(@PathVariable(value = "id") Long ingredientId)
            throws ResourceNotFoundException {
        Ingredient ingredient = ingredientRepository.findById(ingredientId)
                .orElseThrow(() -> new ResourceNotFoundException("Ingredient not found for this id :: " + ingredientId));
        ingredientRepository.delete(ingredient);
        return true;
    }
}
