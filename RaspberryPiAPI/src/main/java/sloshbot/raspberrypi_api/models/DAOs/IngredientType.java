package sloshbot.raspberrypi_api.models.DAOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import sloshbot.raspberrypi_api.models.HibernatePOJO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "IngredientType")
public class IngredientType extends HibernatePOJO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "name", nullable = false)
  private String name;
  @JsonIgnore
  @Column(name = "createdBy", nullable = false)
  private String createdBy;
  @JsonIgnore
  @Column(name = "createdDate", nullable = false)
  private Timestamp createdDate;
  @JsonIgnore
  @Column(name = "modifiedBy")
  private String modifiedBy;
  @JsonIgnore
  @Column(name = "modifiedDate")
  private Timestamp modifiedDate;
  @JsonIgnore
  @OneToMany(mappedBy = "ingredientType")
  private Set<DefaultIngredient> defaultIngredients;
  @JsonIgnore
  @OneToMany(mappedBy ="ingredientType")
  private Set<Ingredient> ingredients;

  //region constructors
  public IngredientType(String name, String createdBy) {
    this.name = name;
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
  }

  public IngredientType(){}
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

  public Set<DefaultIngredient> getDefaultIngredients() {
    return defaultIngredients;
  }

  public void setDefaultIngredients(Set<DefaultIngredient> defaultIngredients) {
    this.defaultIngredients = defaultIngredients;
  }

  public Set<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(Set<Ingredient> ingredients) {
    this.ingredients = ingredients;
  }

  //endregion
}
