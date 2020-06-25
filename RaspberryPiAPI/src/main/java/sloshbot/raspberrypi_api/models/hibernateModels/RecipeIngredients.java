package sloshbot.raspberrypi_api.models.hibernateModels;

import org.joda.time.DateTime;
import sloshbot.raspberrypi_api.models.HibernatePOJO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "RecipeIngredients")
public class RecipeIngredients extends HibernatePOJO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "recipeId", nullable = false)
  private int recipeId;
  @Column(name = "ingredientId", nullable = false)
  private int ingredientId;
  @Column(name = "amount", nullable = false)
  private int amount;
  @Column(name = "createdBy", nullable = false)
  private String createdBy;
  @Column(name = "createdDate", nullable = false)
  private Timestamp createdDate;
  @Column(name = "modifiedBy")
  private String modifiedBy;
  @Column(name = "modifiedDate")
  private Timestamp modifiedDate;
  @ManyToOne
  @JoinColumn(name = "recipeId", insertable = false, updatable = false)
  private Recipe recipe;
  @ManyToOne
  @JoinColumn(name = "ingredientId", insertable = false, updatable = false)
  private Ingredient ingredient;

  //region constructors
  public RecipeIngredients(int recipeId, int ingredientId, int amount, String createdBy) {
    this.recipeId = recipeId;
    this.ingredientId = ingredientId;
    this.amount = amount;
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
  }

  public RecipeIngredients(int amount, String createdBy, Recipe recipe, Ingredient ingredient) {
    this.recipeId = recipe.getId();
    this.ingredientId = ingredient.getId();
    this.amount = amount;
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
    this.recipe = recipe;
    this.ingredient = ingredient;
  }

  public RecipeIngredients(){}
  //endregion

  //region getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(int recipeId) {
    this.recipeId = recipeId;
  }

  public int getIngredientId() {
    return ingredientId;
  }

  public void setIngredientId(int ingredientId) {
    this.ingredientId = ingredientId;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
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

  public Recipe getRecipe() {
    return recipe;
  }

  public void setRecipe(Recipe recipe) {
    this.recipe = recipe;
  }

  public Ingredient getIngredient() {
    return ingredient;
  }

  public void setIngredient(Ingredient ingredient) {
    this.ingredient = ingredient;
  }
  //endregion
}
