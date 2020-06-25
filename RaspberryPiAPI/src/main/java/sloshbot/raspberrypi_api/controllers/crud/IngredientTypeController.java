package sloshbot.raspberrypi_api.controllers.crud;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sloshbot.raspberrypi_api.models.DAOs.IngredientType;
import sloshbot.raspberrypi_api.repositories.IngredientTypeRepository;
import sloshbot.raspberrypi_api.util.exceptions.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/crud/v1/ingredienttype")
public class IngredientTypeController {
    @Autowired
    private IngredientTypeRepository ingredientTypeRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public List<IngredientType> getAllIngredientTypes() {
        return ingredientTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<IngredientType> getIngredientTypeById(@PathVariable(value = "id") Long ingredientTypeId)
            throws ResourceNotFoundException {
        IngredientType ingredientType = ingredientTypeRepository.findById(ingredientTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("IngredientType not found for this id ::" + ingredientTypeId));
        return ResponseEntity.ok().body(ingredientType);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public IngredientType createIngredientType(@RequestBody IngredientType ingredientType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ingredientType.setCreatedBy(authentication.getName());
        ingredientType.setCreatedDate(new Timestamp(DateTime.now().getMillis()));
        return ingredientTypeRepository.save(ingredientType);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<IngredientType> updateIngredientType(@PathVariable(value = "id") Long ingredientTypeId,
                                                     @RequestBody IngredientType ingredientTypeDetails)
            throws ResourceNotFoundException {
        IngredientType ingredientType = ingredientTypeRepository.findById(ingredientTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("IngredientType not found for this id ::" + ingredientTypeId));
        if(ingredientTypeDetails.getName() != null)
            ingredientType.setName(ingredientTypeDetails.getName());
        ingredientType.setModifiedDate(new Timestamp(DateTime.now().getMillis()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        ingredientType.setModifiedBy(authentication.getName());
        final IngredientType updatedIngredientType = ingredientTypeRepository.save(ingredientType);
        return ResponseEntity.ok(updatedIngredientType);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERUSER')")
    public boolean deleteIngredientType(@PathVariable(value = "id") Long ingredientTypeId)
            throws ResourceNotFoundException {
        IngredientType ingredientType = ingredientTypeRepository.findById(ingredientTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("IngredientType not found for this id :: " + ingredientTypeId));
        ingredientTypeRepository.delete(ingredientType);
        return true;
    }
}
