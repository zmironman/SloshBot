package sloshbot.raspberrypi_api.models.DAOs;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.joda.time.DateTime;
import sloshbot.raspberrypi_api.models.HibernatePOJO;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "SloshBot")
public class SloshBot extends HibernatePOJO {

  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "name", nullable = false)
  private String name;
  @Column(name = "ownerId", nullable = false)
  private int ownerId;
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
  @JoinColumn(name = "ownerId", insertable = false, updatable = false)
  private User owner;
  @JsonIgnore
  @OneToMany(mappedBy = "sloshBot")
  private Set<OrderHistory> orderHistory;
  @JsonIgnore
  @OneToMany(mappedBy = "sloshBot")
  private Set<Optic> optics;

  //region constructors
  public SloshBot(String name, int ownerId, String createdBy) {
    this.name = name;
    this.ownerId = ownerId;
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
  }

  public SloshBot(String name, String createdBy, User owner) {
    this.name = name;
    this.ownerId = owner.getId();
    this.createdBy = createdBy;
    this.createdDate = new Timestamp(DateTime.now().getMillis());
    this.owner = owner;
  }

  public SloshBot(){}
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

  public int getOwnerId() {
    return ownerId;
  }

  public void setOwnerId(int ownerId) {
    this.ownerId = ownerId;
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

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public Set<OrderHistory> getOrderHistory() {
    return orderHistory;
  }

  public void setOrderHistory(Set<OrderHistory> orderHistory) {
    this.orderHistory = orderHistory;
  }

  public Set<Optic> getOptics() {
    return optics;
  }

  public void setOptics(Set<Optic> optics) {
    this.optics = optics;
  }
  //endregion
}
