package sloshbot.raspberrypi_api.models;

public class Orderhistory {

  private long id;
  private long recipeId;
  private long userId;
  private long sloshbotId;
  private java.sql.Timestamp createdDate;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getRecipeId() {
    return recipeId;
  }

  public void setRecipeId(long recipeId) {
    this.recipeId = recipeId;
  }


  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }


  public long getSloshbotId() {
    return sloshbotId;
  }

  public void setSloshbotId(long sloshbotId) {
    this.sloshbotId = sloshbotId;
  }


  public java.sql.Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(java.sql.Timestamp createdDate) {
    this.createdDate = createdDate;
  }

}
