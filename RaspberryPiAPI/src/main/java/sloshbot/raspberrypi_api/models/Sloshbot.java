package sloshbot.raspberrypi_api.models;

public class Sloshbot {

  private long id;
  private String name;
  private long ownerId;
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


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public long getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(long ownerId) {
    this.ownerId = ownerId;
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
