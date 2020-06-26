package sloshbot.raspberrypi_api.models.DAOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import sloshbot.raspberrypi_api.models.HibernatePOJO;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "OrderHistory")
public class OrderHistory extends HibernatePOJO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "recipeId", nullable = false)
  private int recipeId;
  @Column(name = "userId", nullable = false)
  private int userId;
  @Column(name = "sloshBotId", nullable = false)
  private int sloshBotId;
  @JsonIgnore
  @Column(name = "createdDate", nullable = false)
  private Timestamp createdDate;
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "recipeId", insertable = false, updatable = false)
  private Recipe recipe;
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "userId", insertable = false, updatable = false)
  private User user;
  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "sloshBotId", insertable = false, updatable = false)
  private SloshBot sloshBot;

  //region constructors
  public OrderHistory(int recipeId, int userId, int sloshBotId) {
    this.recipeId = recipeId;
    this.userId = userId;
    this.sloshBotId = sloshBotId;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
  }

  public OrderHistory(Recipe recipe, User user, SloshBot sloshBot) {
    this.recipeId = recipe.getId();
    this.userId = user.getId();
    this.sloshBotId = sloshBot.getId();
    this.recipe = recipe;
    this.user = user;
    this.sloshBot = sloshBot;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
  }

  public OrderHistory(){}
  //endregion

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

  public int getSloshBotId() {
    return sloshBotId;
  }

  public void setSloshBotId(int sloshBotId) {
    this.sloshBotId = sloshBotId;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public Recipe getRecipe() {
    return recipe;
  }

  public void setRecipe(Recipe recipe) {
    this.recipe = recipe;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public SloshBot getSloshBot() {
    return sloshBot;
  }

  public void setSloshBot(SloshBot sloshBot) {
    this.sloshBot = sloshBot;
  }
  //endregion
}
