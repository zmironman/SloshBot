package sloshbot.raspberrypi_api.models;

import org.hibernate.annotations.Generated;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "Ingredient")
public class Ingredient {

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

  public Ingredient(String name, int ingredientTypeId, int alcoholContent, int price, String createdBy) {
    this.name = name;
    this.ingredientTypeId = ingredientTypeId;
    this.alcoholContent = alcoholContent;
    this.price = price;
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
  }

  public Ingredient(){}

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
  //endregion
}
