package sloshbot.raspberrypi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sloshbot.raspberrypi_api.models.DrinkType;

@Repository
public interface DrinkTypeRepository extends JpaRepository<DrinkType, Long> {
}
