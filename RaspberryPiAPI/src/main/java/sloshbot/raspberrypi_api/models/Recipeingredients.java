package sloshbot.raspberrypi_api.models;

import java.sql.Timestamp;

public class Recipeingredients {

  private int recipeId;
  private int ingredientId;
  private int amount;
  private String createdBy;
  private Timestamp createdDate;
  private String modifiedBy;
  private Timestamp modifiedDate;


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

}
