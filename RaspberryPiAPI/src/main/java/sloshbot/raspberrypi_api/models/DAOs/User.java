package sloshbot.raspberrypi_api.models.DAOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sloshbot.raspberrypi_api.models.HibernatePOJO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "User")
public class User extends HibernatePOJO {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "username", nullable = false)
  private String username;
  @JsonIgnore
  @Column(name = "password", nullable = false)
  private String password;
  @Column(name = "email", nullable = false)
  private String email;
  @ManyToOne
  @JoinColumn(name="clearanceLevel")
  private ClearanceLevel clearanceLevel;
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
  @OneToMany(mappedBy = "user")
  private Set<OrderHistory> orderHistory;
  @JsonIgnore
  @OneToMany(mappedBy = "owner")
  private Set<SloshBot> sloshBots;

  //region constructors
  public User(String name, String username, String email, String password) {
    this.name = name;
    this.username = username;
    this.password = password;
    this.email = email;
  }

  public User(){}
  //endregion

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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public ClearanceLevel getClearanceLevel() {
    return clearanceLevel;
  }

  public void setClearanceLevel(ClearanceLevel clearanceLevel) {
    this.clearanceLevel = clearanceLevel;
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

  public Set<OrderHistory> getOrderHistory() {
    return orderHistory;
  }

  public void setOrderHistory(Set<OrderHistory> orderHistory) {
    this.orderHistory = orderHistory;
  }

  public Set<SloshBot> getSloshBots() {
    return sloshBots;
  }

  public void setSloshBots(Set<SloshBot> sloshBots) {
    this.sloshBots = sloshBots;
  }

  //endregion
}
