package sloshbot.raspberrypi_api.models;

public class Defaultingredients {

  private long drinkTypeId;
  private long ingredientTypeId;
  private long amount;
  private String createdBy;
  private java.sql.Timestamp createdDate;
  private String modifiedBy;
  private java.sql.Timestamp modifiedDate;


  public long getDrinkTypeId() {
    return drinkTypeId;
  }

  public void setDrinkTypeId(long drinkTypeId) {
    this.drinkTypeId = drinkTypeId;
  }


  public long getIngredientTypeId() {
    return ingredientTypeId;
  }

  public void setIngredientTypeId(long ingredientTypeId) {
    this.ingredientTypeId = ingredientTypeId;
  }


  public long getAmount() {
    return amount;
  }

  public void setAmount(long amount) {
    this.amount = amount;
  }


  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }


  public java.sql.Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(java.sql.Timestamp createdDate) {
    this.createdDate = createdDate;
  }


  public String getModifiedBy() {
    return modifiedBy;
  }

  public void setModifiedBy(String modifiedBy) {
    this.modifiedBy = modifiedBy;
  }


  public java.sql.Timestamp getModifiedDate() {
    return modifiedDate;
  }

  public void setModifiedDate(java.sql.Timestamp modifiedDate) {
    this.modifiedDate = modifiedDate;
  }

}
