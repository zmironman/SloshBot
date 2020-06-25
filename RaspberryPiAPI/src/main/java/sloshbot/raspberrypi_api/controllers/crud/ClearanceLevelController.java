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

import sloshbot.raspberrypi_api.models.DAOs.ClearanceLevel;
import sloshbot.raspberrypi_api.repositories.ClearanceLevelRepository;
import sloshbot.raspberrypi_api.util.exceptions.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/crud/v1/clearancelevel")
public class ClearanceLevelController {
    @Autowired
    private ClearanceLevelRepository clearanceLevelRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public List<ClearanceLevel> getAllClearanceLevels() {
        return clearanceLevelRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<ClearanceLevel> getClearanceLevelById(@PathVariable(value = "id") Long clearanceLevelId)
            throws ResourceNotFoundException {
        ClearanceLevel clearanceLevel = clearanceLevelRepository.findById(clearanceLevelId)
                .orElseThrow(() -> new ResourceNotFoundException("ClearanceLevel not found for this id ::" + clearanceLevelId));
        return ResponseEntity.ok().body(clearanceLevel);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ClearanceLevel createClearanceLevel(@RequestBody ClearanceLevel clearanceLevel) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        clearanceLevel.setCreatedBy(authentication.getName());
        clearanceLevel.setCreatedDate(new Timestamp(DateTime.now().getMillis()));
        return clearanceLevelRepository.save(clearanceLevel);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<ClearanceLevel> updateClearanceLevel(@PathVariable(value = "id") Long clearanceLevelId,
                                                     @RequestBody ClearanceLevel clearanceLevelDetails)
            throws ResourceNotFoundException {
        ClearanceLevel clearanceLevel = clearanceLevelRepository.findById(clearanceLevelId)
                .orElseThrow(() -> new ResourceNotFoundException("ClearanceLevel not found for this id ::" + clearanceLevelId));
        clearanceLevel.setRole(clearanceLevelDetails.getRole());
        clearanceLevel.setModifiedDate(new Timestamp(DateTime.now().getMillis()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        clearanceLevel.setModifiedBy(authentication.getName());
        final ClearanceLevel updatedClearanceLevel = clearanceLevelRepository.save(clearanceLevel);
        return ResponseEntity.ok(updatedClearanceLevel);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERUSER')")
    public boolean deleteClearanceLevel(@PathVariable(value = "id") Long clearanceLevelId)
            throws ResourceNotFoundException {
        ClearanceLevel clearanceLevel = clearanceLevelRepository.findById(clearanceLevelId)
                .orElseThrow(() -> new ResourceNotFoundException("ClearanceLevel not found for this id :: " + clearanceLevelId));
        clearanceLevelRepository.delete(clearanceLevel);
        return true;
    }
}
