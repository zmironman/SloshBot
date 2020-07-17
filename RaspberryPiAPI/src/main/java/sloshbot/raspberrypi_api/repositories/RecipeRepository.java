package sloshbot.raspberrypi_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sloshbot.raspberrypi_api.models.DAOs.Recipe;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
    @Query("select r from #{#entityName} r where r.featured = true")
    List<Recipe> GetAllFeaturedRecipes();
}
