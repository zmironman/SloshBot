package sloshbot.raspberrypi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sloshbot.raspberrypi_api.models.hibernateModels.Optic;

@Repository
public interface OpticRepository extends JpaRepository<Optic, Long> {
}
