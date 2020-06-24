package sloshbot.raspberrypi_api.controllers;

import org.hibernate.annotations.NotFound;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sloshbot.raspberrypi_api.exceptions.ResourceNotFoundException;
import sloshbot.raspberrypi_api.models.DrinkType;
import sloshbot.raspberrypi_api.repositories.DrinkTypeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/crud")
public class DrinkTypeController {
    @Autowired
    private DrinkTypeRepository drinkTypeRepository;

    @GetMapping("/drinkTypes")
    public List<DrinkType> getAllDrinkTypes() {
        return drinkTypeRepository.findAll();
    }

    @GetMapping("/drinkType/{id}")
    public ResponseEntity<DrinkType> getDrinkTypeById(@PathVariable(value = "id") Long drinkTypeId)
            throws ResourceNotFoundException {
        DrinkType drinkType = drinkTypeRepository.findById(drinkTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("DrinkType not found for this id ::" + drinkTypeId));
        return ResponseEntity.ok().body(drinkType);
    }

    @PutMapping("/drinkType/{id}")
    public ResponseEntity<DrinkType> updateDrinkType(@PathVariable(value = "id") Long drinkTypeId,
                                                     @RequestBody DrinkType drinkTypeDetails)
            throws ResourceNotFoundException {
        DrinkType drinkType = drinkTypeRepository.findById(drinkTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("DrinkType not found for this id ::" + drinkTypeId));
        drinkType.setName(drinkTypeDetails.getName());
        drinkType.setModifiedDate(DateTime.now());
        //TODO: Set up security and accurately set modifiedBy
        drinkType.setModifiedBy("SECURITY NOT IMPLEMENTED");
        final DrinkType updatedDrinkType = drinkTypeRepository.save(drinkType);
        return ResponseEntity.ok(updatedDrinkType);
    }

    @DeleteMapping("/drinkType/{id}")
    public boolean deleteDrinkType(@PathVariable(value = "id") Long drinkTypeId)
            throws ResourceNotFoundException {
        DrinkType drinkType = drinkTypeRepository.findById(drinkTypeId)
                .orElseThrow(() -> new ResourceNotFoundException("DrinkType not found for this id :: " + drinkTypeId));
        drinkTypeRepository.delete(drinkType);
        return true;
    }
}
