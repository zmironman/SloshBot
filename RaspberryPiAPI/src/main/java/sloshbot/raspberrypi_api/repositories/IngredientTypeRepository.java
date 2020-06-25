package sloshbot.raspberrypi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sloshbot.raspberrypi_api.models.DAOs.IngredientType;

@Repository
public interface IngredientTypeRepository extends JpaRepository<IngredientType, Long> {
}
