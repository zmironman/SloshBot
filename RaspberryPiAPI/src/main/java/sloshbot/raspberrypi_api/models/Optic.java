package sloshbot.raspberrypi_api.models;

public class Optic {

  private long id;
  private long ingredientId;
  private long distanceFromHome;
  private long remainingLiquid;
  private long pinNumber;
  private long sloshbotId;
  private String createdBy;
  private java.sql.Timestamp createdDate;
  private String modifiedBy;
  private java.sql.Timestamp modifiedDate;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public long getIngredientId() {
    return ingredientId;
  }

  public void setIngredientId(long ingredientId) {
    this.ingredientId = ingredientId;
  }


  public long getDistanceFromHome() {
    return distanceFromHome;
  }

  public void setDistanceFromHome(long distanceFromHome) {
    this.distanceFromHome = distanceFromHome;
  }


  public long getRemainingLiquid() {
    return remainingLiquid;
  }

  public void setRemainingLiquid(long remainingLiquid) {
    this.remainingLiquid = remainingLiquid;
  }


  public long getPinNumber() {
    return pinNumber;
  }

  public void setPinNumber(long pinNumber) {
    this.pinNumber = pinNumber;
  }


  public long getSloshbotId() {
    return sloshbotId;
  }

  public void setSloshbotId(long sloshbotId) {
    this.sloshbotId = sloshbotId;
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
