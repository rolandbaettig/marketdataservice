package ch.steponline.identity.model;

import ch.steponline.core.model.VersionedEntity;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 10.05.2017
 * Time: 09:43
 */
@Entity
@Table(name = "Team",schema = "usr",indexes = {@Index(name="IDX_Team_Covering",columnList = "Id,Name")}, uniqueConstraints = {@UniqueConstraint(name="UQ_Team",columnNames = "Name")})
@NamedQueries(value = {
        @NamedQuery(name = "Teams_All", query = "select t from Team t order by t.name"),
        @NamedQuery(name = "Team_FindUnique", query = "select t from Team t where t.name = :name and t.id <> :id")
})
@Audited
@AuditTable(value = "Team", schema = "audit")
public class Team extends VersionedEntity {
    private static final long serialVersionUID = -5361278947286615301L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name", unique = true)
    @NotNull
    @Size( max = 50)
    private String name;

    @ManyToMany(mappedBy = "teams", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Set<User> users = new HashSet<User>();

    //****************************************
    // Bid-Directional Associations
    //****************************************

    public void addUser(User user) {
        if (user == null) throw new IllegalArgumentException("user my not be null!");

        user.getTeams().add(this);
        users.add(user);
    }

    public void removeUser(User user) {
        user.getTeams().remove(this);
        users.remove(user);
    }

    public Set<User> getUsers() {
        return users;
    }

    private void setUsers(Set<User> users) {
        this.users = users;
    }

    //****************************************
    // Getters / Setters
    //****************************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
