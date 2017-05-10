package ch.steponline.identity.model;

import ch.steponline.core.model.VersionedEntity;
import org.hibernate.envers.AuditJoinTable;
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
 * Time: 09:22
 */
@Entity
@Table(name = "UserProfile",schema="usr",uniqueConstraints = {@UniqueConstraint(name="UQ_UserProfile",columnNames = {"Name"})},indexes = {
        @Index(name = "IDX_UserProfile_Covering",columnList = "Id, Name, Description")
})
@NamedQueries(value = {
        @NamedQuery(name = "UserProfile_FindUnique", query = "select up from UserProfile up where up.name = :name and up.id <> :id"),
        @NamedQuery(name = "UserProfile_All", query = "select up from UserProfile up order by up.name"),
        @NamedQuery(name = "UserProfile_ByName", query = "select up from UserProfile up where up.name = :name")
})
@Audited
@AuditTable(value = "UserProfile", schema = "audit")
public class UserProfile extends VersionedEntity {

    private static final long serialVersionUID = -2908965561670027319L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    @NotNull
    @Size(max = 150)
    private String name;

    @Column(name = "Description")
    @Size(max = 500)
    private String description;

    @ManyToMany(mappedBy = "userProfiles", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Set<User> users = new HashSet<User>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "UserProfileLdapRoleAss",schema = "usr",
            joinColumns = {@JoinColumn(name = "UserProfileId",foreignKey = @ForeignKey(name="FK_UserProfileLdapRole_UserProfileId"))},
            inverseJoinColumns = {@JoinColumn(name = "LdapRoleId",foreignKey = @ForeignKey(name="FK_UserProfileLdapRole_LdapRoleId"))}
    )
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @AuditJoinTable(name = "UserProfileLdapRoleAss", schema = "audit")
    private Set<LdapRole> ldapRoles = new HashSet<LdapRole>();

    //****************************************
    // Bid-Directional Associations
    //****************************************

    public void addUser(User user) {
        if (user == null) throw new IllegalArgumentException("user my not be null!");

        user.getUserProfiles().add(this);
        users.add(user);
    }

    public void removeUser(User user) {
        user.getUserProfiles().remove(this);
        users.remove(user);
    }

    public Set<User> getUsers() {
        return users;
    }

    private void setUsers(Set<User> users) {
        this.users = users;
    }

    public void addLdapRole(LdapRole ldapRole) {
        if (ldapRole == null) throw new IllegalArgumentException("ldapRole my not be null!");

        ldapRole.getUserProfiles().add(this);
        ldapRoles.add(ldapRole);
    }

    public void removeLdapRole(LdapRole ldapRole) {
        ldapRole.getUserProfiles().remove(this);
        ldapRoles.remove(ldapRole);
    }

    public Set<LdapRole> getLdapRoles() {
        return ldapRoles;
    }

    private void setLdapRoles(Set<LdapRole> ldapRoles) {
        this.ldapRoles = ldapRoles;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
