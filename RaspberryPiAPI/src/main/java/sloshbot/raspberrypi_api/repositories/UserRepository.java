package sloshbot.raspberrypi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sloshbot.raspberrypi_api.models.hibernateModels.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
}
