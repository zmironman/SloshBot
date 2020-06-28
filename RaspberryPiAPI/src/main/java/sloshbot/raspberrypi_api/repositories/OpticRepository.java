package sloshbot.raspberrypi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sloshbot.raspberrypi_api.models.DAOs.Optic;

import java.util.Optional;

@Repository
public interface OpticRepository extends JpaRepository<Optic, Integer> {
    @Query("select o from #{#entityName} o where o.ingredientId = :ingId")
    Optic FindOpticByIngredientId(@Param("ingId")int ingredient);
}
