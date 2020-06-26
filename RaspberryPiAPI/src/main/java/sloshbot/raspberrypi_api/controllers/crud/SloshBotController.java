package sloshbot.raspberrypi_api.controllers.crud;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sloshbot.raspberrypi_api.models.DAOs.SloshBot;
import sloshbot.raspberrypi_api.repositories.SloshBotRepository;
import sloshbot.raspberrypi_api.util.exceptions.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/crud/v1/sloshbot")
public class SloshBotController {
    @Autowired
    private SloshBotRepository sloshBotRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public List<SloshBot> getAllSloshBots() {
        return sloshBotRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<SloshBot> getSloshBotById(@PathVariable(value = "id") int sloshBotId)
            throws ResourceNotFoundException {
        SloshBot sloshBot = sloshBotRepository.findById(sloshBotId)
                .orElseThrow(() -> new ResourceNotFoundException("SloshBot not found for this id ::" + sloshBotId));
        return ResponseEntity.ok().body(sloshBot);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public SloshBot createSloshBot(@RequestBody SloshBot sloshBot) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        sloshBot.setCreatedBy(authentication.getName());
        sloshBot.setCreatedDate(new Timestamp(DateTime.now().getMillis()));
        return sloshBotRepository.save(sloshBot);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<SloshBot> updateSloshBot(@PathVariable(value = "id") int sloshBotId,
                                                     @RequestBody SloshBot sloshBotDetails)
            throws ResourceNotFoundException {
        SloshBot sloshBot = sloshBotRepository.findById(sloshBotId)
                .orElseThrow(() -> new ResourceNotFoundException("SloshBot not found for this id ::" + sloshBotId));
        if(sloshBotDetails.getName() != null)
            sloshBot.setName(sloshBotDetails.getName());
        if(sloshBotDetails.getOwnerId() != 0)
            sloshBot.setOwnerId(sloshBotDetails.getOwnerId());
        sloshBot.setModifiedDate(new Timestamp(DateTime.now().getMillis()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        sloshBot.setModifiedBy(authentication.getName());
        final SloshBot updatedSloshBot = sloshBotRepository.save(sloshBot);
        return ResponseEntity.ok(updatedSloshBot);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERUSER')")
    public boolean deleteSloshBot(@PathVariable(value = "id") int sloshBotId)
            throws ResourceNotFoundException {
        SloshBot sloshBot = sloshBotRepository.findById(sloshBotId)
                .orElseThrow(() -> new ResourceNotFoundException("SloshBot not found for this id :: " + sloshBotId));
        sloshBotRepository.delete(sloshBot);
        return true;
    }
}
