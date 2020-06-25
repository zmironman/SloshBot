package sloshbot.raspberrypi_api.models.hibernateModels;


import org.joda.time.DateTime;
import sloshbot.raspberrypi_api.models.HibernatePOJO;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "DrinkType")
public class DrinkType extends HibernatePOJO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "createdBy", nullable = false)
    private String createdBy;
    @Column(name = "createdDate", nullable = false)
    private Timestamp createdDate;
    @Column(name = "modifiedBy")
    private String modifiedBy;
    @Column(name = "modifiedDate")
    private Timestamp modifiedDate;
    @OneToMany(mappedBy="drinkType")
    private Set<DefaultIngredients> defaultIngredients;
    @OneToMany(mappedBy="drinkType")
    private Set<Recipe> recipes;

    //region constructors

    public DrinkType(String name, String createdBy) {
        this.name = name;
        this.createdBy = createdBy;
        this.createdDate = new Timestamp(DateTime.now().getMillis());
    }

    public DrinkType(){}

    //endregion

    //region getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Timestamp getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Timestamp modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Set<DefaultIngredients> getDefaultIngredients() {
        return defaultIngredients;
    }

    public void setDefaultIngredients(Set<DefaultIngredients> defaultIngredients) {
        this.defaultIngredients = defaultIngredients;
    }

    public Set<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(Set<Recipe> recipes) {
        this.recipes = recipes;
    }
    //endregion
}
