package sloshbot.raspberrypi_api.controllers.crud;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sloshbot.raspberrypi_api.models.DAOs.Optic;
import sloshbot.raspberrypi_api.repositories.OpticRepository;
import sloshbot.raspberrypi_api.util.exceptions.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/crud/v1/optic")
public class OpticController {
    @Autowired
    private OpticRepository opticRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public List<Optic> getAllOptics() {
        return opticRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<Optic> getOpticById(@PathVariable(value = "id") int opticId)
            throws ResourceNotFoundException {
        Optic optic = opticRepository.findById(opticId)
                .orElseThrow(() -> new ResourceNotFoundException("Optic not found for this id ::" + opticId));
        return ResponseEntity.ok().body(optic);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public Optic createOptic(@RequestBody Optic optic) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        optic.setCreatedBy(authentication.getName());
        optic.setCreatedDate(new Timestamp(DateTime.now().getMillis()));
        return opticRepository.save(optic);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<Optic> updateOptic(@PathVariable(value = "id") int opticId,
                                                     @RequestBody Optic opticDetails)
            throws ResourceNotFoundException {
        Optic optic = opticRepository.findById(opticId)
                .orElseThrow(() -> new ResourceNotFoundException("Optic not found for this id ::" + opticId));
        if(opticDetails.getDistanceFromHome() != 0)
            optic.setDistanceFromHome(opticDetails.getDistanceFromHome());
        if(opticDetails.getIngredientId() != 0)
            optic.setIngredientId(opticDetails.getIngredientId());
        if(opticDetails.getPinNumber() != 0)
            optic.setPinNumber(opticDetails.getPinNumber());
        if(opticDetails.getRemainingLiquid() != 0)
            optic.setRemainingLiquid(opticDetails.getRemainingLiquid());
        if(opticDetails.getSloshBotId() != 0)
            optic.setSloshBotId(opticDetails.getSloshBotId());
        optic.setModifiedDate(new Timestamp(DateTime.now().getMillis()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        optic.setModifiedBy(authentication.getName());
        final Optic updatedOptic = opticRepository.save(optic);
        return ResponseEntity.ok(updatedOptic);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERUSER')")
    public boolean deleteOptic(@PathVariable(value = "id") int opticId)
            throws ResourceNotFoundException {
        Optic optic = opticRepository.findById(opticId)
                .orElseThrow(() -> new ResourceNotFoundException("Optic not found for this id :: " + opticId));
        opticRepository.delete(optic);
        return true;
    }
}
