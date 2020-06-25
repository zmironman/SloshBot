package sloshbot.raspberrypi_api.models.hibernateModels;

import org.joda.time.DateTime;
import sloshbot.raspberrypi_api.models.HibernatePOJO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "Ingredient")
public class Ingredient extends HibernatePOJO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "name",nullable = false)
  private String name;
  @Column(name = "ingredientTypeId",nullable = false)
  private int ingredientTypeId;
  @Column(name = "alcoholContent",nullable = false)
  private int alcoholContent;
  @Column(name = "price")
  private int price;
  @Column(name = "createdBy",nullable = false)
  private String createdBy;
  @Column(name = "createdDate",nullable = false)
  private Timestamp createdDate;
  @Column(name = "modifiedBy")
  private String modifiedBy;
  @Column(name = "modifiedDate")
  private Timestamp modifiedDate;
  @ManyToOne
  @JoinColumn(name="ingredientTypeId")
  private IngredientType ingredientType;
  @OneToMany(mappedBy = "ingredient")
  private Set<RecipeIngredients> recipeIngredients;
  @OneToMany(mappedBy = "ingredient")
  private Set<Optic> optics;

  //region constructors
  public Ingredient(String name, int ingredientTypeId, int alcoholContent, int price, String createdBy) {
    this.name = name;
    this.ingredientTypeId = ingredientTypeId;
    this.alcoholContent = alcoholContent;
    this.price = price;
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
  }

  public Ingredient(String name, int alcoholContent, int price, String createdBy, IngredientType ingredientType) {
    this.name = name;
    this.ingredientTypeId = ingredientType.getId();
    this.alcoholContent = alcoholContent;
    this.price = price;
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
    this.ingredientType = ingredientType;
  }

  public Ingredient(){}
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

  public int getIngredientTypeId() {
    return ingredientTypeId;
  }

  public void setIngredientTypeId(int ingredientTypeId) {
    this.ingredientTypeId = ingredientTypeId;
  }

  public int getAlcoholContent() {
    return alcoholContent;
  }

  public void setAlcoholContent(int alcoholContent) {
    this.alcoholContent = alcoholContent;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
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

  public IngredientType getIngredientType() {
    return ingredientType;
  }

  public void setIngredientType(IngredientType ingredientType) {
    this.ingredientType = ingredientType;
  }

  public Set<RecipeIngredients> getRecipeIngredients() {
    return recipeIngredients;
  }

  public void setRecipeIngredients(Set<RecipeIngredients> recipeIngredients) {
    this.recipeIngredients = recipeIngredients;
  }

  public Set<Optic> getOptics() {
    return optics;
  }

  public void setOptics(Set<Optic> optics) {
    this.optics = optics;
  }

  //endregion
}
