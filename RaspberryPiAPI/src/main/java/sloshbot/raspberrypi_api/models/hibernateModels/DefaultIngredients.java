package sloshbot.raspberrypi_api.models.hibernateModels;

import org.joda.time.DateTime;
import sloshbot.raspberrypi_api.models.HibernatePOJO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "DefaultIngredients")
public class DefaultIngredients extends HibernatePOJO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "drinkTypeID", nullable = false)
  private int drinkTypeId;
  @Column(name = "ingredientTypeId", nullable = false)
  private int ingredientTypeId;
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
  @JoinColumn(name="drinkTypeID")
  private DrinkType drinkType;
  @ManyToOne
  @JoinColumn(name="ingredientTypeId")
  private IngredientType ingredientType;

  //region constructors
  public DefaultIngredients(int drinkTypeId, int ingredientTypeId, int amount, String createdBy) {
    this.drinkTypeId = drinkTypeId;
    this.ingredientTypeId = ingredientTypeId;
    this.amount = amount;
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
  }

  public DefaultIngredients(int amount, String createdBy, DrinkType drinkType, IngredientType ingredientType) {
    this.drinkTypeId = drinkType.getId();
    this.ingredientTypeId = ingredientType.getId();
    this.amount = amount;
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
    this.drinkType = drinkType;
    this.ingredientType = ingredientType;
  }

  public DefaultIngredients(){}
  //endregion

  //region getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getDrinkTypeId() {
    return drinkTypeId;
  }

  public void setDrinkTypeId(int drinkTypeId) {
    this.drinkTypeId = drinkTypeId;
  }

  public int getIngredientTypeId() {
    return ingredientTypeId;
  }

  public void setIngredientTypeId(int ingredientTypeId) {
    this.ingredientTypeId = ingredientTypeId;
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

  public DrinkType getDrinkType() {
    return drinkType;
  }

  public void setDrinkType(DrinkType drinkType) {
    this.drinkType = drinkType;
  }

  public IngredientType getIngredientType() {
    return ingredientType;
  }

  public void setIngredientType(IngredientType ingredientType) {
    this.ingredientType = ingredientType;
  }

  //endregion
}
