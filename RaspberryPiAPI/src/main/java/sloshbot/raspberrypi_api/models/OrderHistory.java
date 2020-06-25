package sloshbot.raspberrypi_api.models;

import org.joda.time.DateTime;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "OrderHistory")
public class OrderHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "recipeId", nullable = false)
  private int recipeId;
  @Column(name = "userId", nullable = false)
  private int userId;
  @Column(name = "sloshbotId", nullable = false)
  private int sloshbotId;
  @Column(name = "createdDate", nullable = false)
  private Timestamp createdDate;

  public OrderHistory(int recipeId, int userId, int sloshbotId) {
    this.recipeId = recipeId;
    this.userId = userId;
    this.sloshbotId = sloshbotId;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
  }

  public OrderHistory(){}

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

  public int getUserId() {
    return userId;
  }

  public void setUserId(int userId) {
    this.userId = userId;
  }

  public int getSloshbotId() {
    return sloshbotId;
  }

  public void setSloshbotId(int sloshbotId) {
    this.sloshbotId = sloshbotId;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }
  //endregion
}
