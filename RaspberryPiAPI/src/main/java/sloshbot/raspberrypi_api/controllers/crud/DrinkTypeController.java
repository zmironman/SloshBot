package sloshbot.raspberrypi_api.controllers.crud;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sloshbot.raspberrypi_api.util.exceptions.ResourceNotFoundException;
import sloshbot.raspberrypi_api.models.DAOs.DrinkType;
import sloshbot.raspberrypi_api.repositories.DrinkTypeRepository;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/crud/v1/drinktype")
public class DrinkTypeController {
    @Autowired
    private DrinkTypeRepository drinkTypeRepository;

    @GetMapping("/all")
    public List<DrinkType> getAllDrinkTypes() {
        return drinkTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrinkType> getDrinkTypeById(@PathVariable(value = "id") int drinkTypeId)
            throws ResourceNotFoundException {
        DrinkType drinkType = drinkTypeRepository.findById(drinkTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("DrinkType not found for this id ::" + drinkTypeId));
        return ResponseEntity.ok().body(drinkType);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public DrinkType createDrinkType(@RequestBody DrinkType drinkType) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        drinkType.setCreatedBy(authentication.getName());
        drinkType.setCreatedDate(new Timestamp(DateTime.now().getMillis()));
        return drinkTypeRepository.save(drinkType);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<DrinkType> updateDrinkType(@PathVariable(value = "id") int drinkTypeId,
                                                     @RequestBody DrinkType drinkTypeDetails)
            throws ResourceNotFoundException {
        DrinkType drinkType = drinkTypeRepository.findById(drinkTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("DrinkType not found for this id ::" + drinkTypeId));
        drinkType.setName(drinkTypeDetails.getName());
        drinkType.setModifiedDate(new Timestamp(DateTime.now().getMillis()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        drinkType.setModifiedBy(authentication.getName());
        final DrinkType updatedDrinkType = drinkTypeRepository.save(drinkType);
        return ResponseEntity.ok(updatedDrinkType);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERUSER')")
    public boolean deleteDrinkType(@PathVariable(value = "id") int drinkTypeId)
            throws ResourceNotFoundException {
        DrinkType drinkType = drinkTypeRepository.findById(drinkTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("DrinkType not found for this id :: " + drinkTypeId));
        drinkTypeRepository.delete(drinkType);
        return true;
    }
}
