package sloshbot.raspberrypi_api.models.DAOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "Recipe")
public class Recipe {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "drinkTypeId", nullable = false)
  private int drinkTypeId;
  @Column(name = "featured", nullable = false)
  private boolean featured;
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
  @ManyToOne
  @JoinColumn(name="drinkTypeId", insertable = false, updatable = false)
  private DrinkType drinkType;
  @JsonIgnore
  @OneToMany(mappedBy = "recipe", fetch = FetchType.EAGER)
  private Set<RecipeIngredient> recipeIngredients;
  @JsonIgnore
  @OneToMany(mappedBy = "recipe")
  private Set<OrderHistory> orderHistory;

  //region constructors
  public Recipe(String name, int drinkTypeId, String createdBy) {
    this.name = name;
    this.drinkTypeId = drinkTypeId;
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
  }

  public Recipe(String name, String createdBy, DrinkType drinkType, Set<RecipeIngredient> recipeIngredients) {
    this.name = name;
    this.drinkTypeId = drinkType.getId();
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
    this.drinkType = drinkType;
    this.recipeIngredients = recipeIngredients;
  }

  public Recipe(){}
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

  public int getDrinkTypeId() {
    return drinkTypeId;
  }

  public void setDrinkTypeId(int drinkTypeId) {
    this.drinkTypeId = drinkTypeId;
  }

  public boolean getFeatured() {
    return featured;
  }

  public void setFeatured(boolean featured) {
    this.featured = featured;
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

  public boolean isFeatured() {
    return featured;
  }

  public DrinkType getDrinkType() {
    return drinkType;
  }

  public void setDrinkType(DrinkType drinkType) {
    this.drinkType = drinkType;
  }

  public Set<RecipeIngredient> getRecipeIngredients() {
    return recipeIngredients;
  }

  public void setRecipeIngredients(Set<RecipeIngredient> recipeIngredients) {
    this.recipeIngredients = recipeIngredients;
  }

  public Set<OrderHistory> getOrderHistory() {
    return orderHistory;
  }

  public void setOrderHistory(Set<OrderHistory> orderHistory) {
    this.orderHistory = orderHistory;
  }
  //endregion
}
