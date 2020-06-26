package sloshbot.raspberrypi_api.controllers.crud;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sloshbot.raspberrypi_api.models.DAOs.DefaultIngredient;
import sloshbot.raspberrypi_api.repositories.DefaultIngredientsRepository;
import sloshbot.raspberrypi_api.util.exceptions.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/crud/v1/defaultingredient")
public class DefaultIngredientController {
    @Autowired
    private DefaultIngredientsRepository defaultIngredientsRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public List<DefaultIngredient> getAllDefaultIngredients() {
        return defaultIngredientsRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<DefaultIngredient> getDefaultIngredientById(@PathVariable(value = "id") int defaultIngredientId)
            throws ResourceNotFoundException {
        DefaultIngredient defaultIngredient = defaultIngredientsRepository.findById(defaultIngredientId)
                .orElseThrow(() -> new ResourceNotFoundException("DefaultIngredient not found for this id ::" + defaultIngredientId));
        return ResponseEntity.ok().body(defaultIngredient);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public DefaultIngredient createDefaultIngredient(@RequestBody DefaultIngredient defaultIngredient) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        defaultIngredient.setCreatedBy(authentication.getName());
        defaultIngredient.setCreatedDate(new Timestamp(DateTime.now().getMillis()));
        return defaultIngredientsRepository.save(defaultIngredient);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<DefaultIngredient> updateDefaultIngredient(@PathVariable(value = "id") int defaultIngredientId,
                                                     @RequestBody DefaultIngredient defaultIngredientDetails)
            throws ResourceNotFoundException {
        DefaultIngredient defaultIngredient = defaultIngredientsRepository.findById(defaultIngredientId)
                .orElseThrow(() -> new ResourceNotFoundException("DefaultIngredient not found for this id ::" + defaultIngredientId));
        if(defaultIngredientDetails.getAmount() != 0)
            defaultIngredient.setAmount(defaultIngredientDetails.getAmount());
        if(defaultIngredientDetails.getDrinkTypeId() != 0)
            defaultIngredient.setDrinkTypeId(defaultIngredientDetails.getDrinkTypeId());
        if(defaultIngredientDetails.getIngredientTypeId() != 0)
            defaultIngredient.setIngredientTypeId(defaultIngredientDetails.getIngredientTypeId());
        defaultIngredient.setModifiedDate(new Timestamp(DateTime.now().getMillis()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        defaultIngredient.setModifiedBy(authentication.getName());
        final DefaultIngredient updatedDefaultIngredient = defaultIngredientsRepository.save(defaultIngredient);
        return ResponseEntity.ok(updatedDefaultIngredient);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERUSER')")
    public boolean deleteDefaultIngredient(@PathVariable(value = "id") int defaultIngredientId)
            throws ResourceNotFoundException {
        DefaultIngredient defaultIngredient = defaultIngredientsRepository.findById(defaultIngredientId)
                .orElseThrow(() -> new ResourceNotFoundException("DefaultIngredient not found for this id :: " + defaultIngredientId));
        defaultIngredientsRepository.delete(defaultIngredient);
        return true;
    }
}
