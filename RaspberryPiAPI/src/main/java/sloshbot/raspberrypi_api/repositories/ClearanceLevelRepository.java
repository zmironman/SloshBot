package sloshbot.raspberrypi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sloshbot.raspberrypi_api.models.DAOs.ClearanceLevel;


@Repository
public interface ClearanceLevelRepository extends JpaRepository<ClearanceLevel, Long> {
}
