package sloshbot.raspberrypi_api.models.DAOs;

import sloshbot.raspberrypi_api.models.HibernatePOJO;
import sloshbot.raspberrypi_api.models.Roles;

import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "ClearanceLevel")
public class ClearanceLevel extends HibernatePOJO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "role", nullable = false)
    private String role;
    @Column(name = "createdBy", nullable = false)
    private String createdBy;
    @Column(name = "createdDate", nullable = false)
    private Timestamp createdDate;
    @Column(name = "modifiedBy")
    private String modifiedBy;
    @Column(name = "modifiedDate")
    private Timestamp modifiedDate;
    @OneToMany(mappedBy = "clearanceLevel", cascade = CascadeType.ALL)
    private Set<User> users;

    //region constructors
    public ClearanceLevel(long clearance) {
        this.id = (int) clearance;
        switch((int) clearance){
            case 4:
                this.role = Roles.ROLE_SUPERUSER.name();
                break;
            case 3:
                this.role = Roles.ROLE_ADMIN.name();
                break;
            case 2:
                this.role = Roles.ROLE_MODERATOR.name();
                break;
            case 1:
                this.role = Roles.ROLE_USER.name();
                break;
            default:
                this.role = Roles.ROLE_GUEST.name();
                break;
        }
    }

    public ClearanceLevel(){
        id = 0;
        role = Roles.ROLE_GUEST.name();
    }
    //endregion

    //region getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
    //endregion

}
