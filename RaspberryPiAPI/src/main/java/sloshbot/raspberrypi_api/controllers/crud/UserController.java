package sloshbot.raspberrypi_api.controllers.crud;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import sloshbot.raspberrypi_api.models.DAOs.User;
import sloshbot.raspberrypi_api.repositories.UserRepository;
import sloshbot.raspberrypi_api.util.exceptions.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/crud/v1/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<User> getUserById(@PathVariable(value = "id") int userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id ::" + userId));
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public User createUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        user.setCreatedBy(authentication.getName());
        user.setCreatedDate(new Timestamp(DateTime.now().getMillis()));
        return userRepository.save(user);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('SUPERUSER')")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") int userId,
                                                     @RequestBody User userDetails)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id ::" + userId));
        if(userDetails.getEmail() != null)
           user.setEmail(userDetails.getEmail());
        if(userDetails.getName() != null)
            user.setName(userDetails.getName());
        user.setModifiedDate(new Timestamp(DateTime.now().getMillis()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        user.setModifiedBy(authentication.getName());
        final User updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPERUSER')")
    public boolean deleteUser(@PathVariable(value = "id") int userId)
            throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + userId));
        userRepository.delete(user);
        return true;
    }
}
