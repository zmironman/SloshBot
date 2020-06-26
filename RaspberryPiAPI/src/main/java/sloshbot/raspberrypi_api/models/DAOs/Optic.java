package sloshbot.raspberrypi_api.models.DAOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Optic")
public class Optic {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "ingredientId", nullable = false)
  private int ingredientId;
  @Column(name = "distanceFromHome", nullable = false)
  private int distanceFromHome;
  @Column(name = "remainingLiquid", nullable = false)
  private int remainingLiquid;
  @Column(name = "pinNumber", nullable = false)
  private int pinNumber;
  @Column(name = "sloshBotId", nullable = false)
  private int sloshBotId;
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
  @JoinColumn(name = "ingredientId", insertable = false, updatable = false)
  private Ingredient ingredient;
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "sloshBotId", insertable = false, updatable = false)
  private SloshBot sloshBot;

  //region constructors
  public Optic(int ingredientId, int distanceFromHome, int remainingLiquid, int pinNumber, int sloshBotId, String createdBy) {
    this.ingredientId = ingredientId;
    this.distanceFromHome = distanceFromHome;
    this.remainingLiquid = remainingLiquid;
    this.pinNumber = pinNumber;
    this.sloshBotId = sloshBotId;
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
  }

  public Optic(int distanceFromHome, int remainingLiquid, int pinNumber, String createdBy, Ingredient ingredient, SloshBot sloshBot) {
    this.ingredientId = ingredient.getId();
    this.sloshBotId = sloshBot.getId();
    this.distanceFromHome = distanceFromHome;
    this.remainingLiquid = remainingLiquid;
    this.pinNumber = pinNumber;
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
    this.ingredient = ingredient;
    this.sloshBot = sloshBot;
  }

  public Optic(){}
  //endregion

  //region getters and setters
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getIngredientId() {
    return ingredientId;
  }

  public void setIngredientId(int ingredientId) {
    this.ingredientId = ingredientId;
  }

  public int getDistanceFromHome() {
    return distanceFromHome;
  }

  public void setDistanceFromHome(int distanceFromHome) {
    this.distanceFromHome = distanceFromHome;
  }

  public int getRemainingLiquid() {
    return remainingLiquid;
  }

  public void setRemainingLiquid(int remainingLiquid) {
    this.remainingLiquid = remainingLiquid;
  }

  public int getPinNumber() {
    return pinNumber;
  }

  public void setPinNumber(int pinNumber) {
    this.pinNumber = pinNumber;
  }

  public int getSloshBotId() {
    return sloshBotId;
  }

  public void setSloshBotId(int sloshBotId) {
    this.sloshBotId = sloshBotId;
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

  public Ingredient getIngredient() {
    return ingredient;
  }

  public void setIngredient(Ingredient ingredient) {
    this.ingredient = ingredient;
  }

  public SloshBot getSloshBot() {
    return sloshBot;
  }

  public void setSloshBot(SloshBot sloshBot) {
    this.sloshBot = sloshBot;
  }
  //endregion
}
