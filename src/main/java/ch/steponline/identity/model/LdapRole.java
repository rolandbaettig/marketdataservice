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
 * Time: 09:26
 */
@Entity
@Table(name = "LdapRole", schema="usr", uniqueConstraints = {@UniqueConstraint(name="UQ_LdapRole", columnNames = {"Name"})})
@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(
                name = "LdapRole_All_ByName",
                query = "select lr from LdapRole lr order by lr.name",
                comment = "All LdapRoles order by Name"
        ),
        @org.hibernate.annotations.NamedQuery(
                name = "LdapRole_ByName_Like",
                query = "select lr from LdapRole lr where lr.name like :name",
                comment = "LdapRoles by Name (like)"
        ),
        @org.hibernate.annotations.NamedQuery(
                name = "LdapRole_ByName",
                query = "select lr from LdapRole lr where lr.name = :name",
                comment = "LdapRoles by Name (equals)"
        )
})
@Audited
@AuditTable(value = "LdapRole", schema = "audit")
public class LdapRole extends VersionedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    @NotNull
    @Size(max = 250)
    private String name;

    @Column(name = "Description")
    @Size(max = 500)
    private String description;

    @ManyToMany(mappedBy = "ldapRoles", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Set<UserProfile> userProfiles = new HashSet<UserProfile>();

    private static final long serialVersionUID = -6384155515578394243L;

    //****************************************
    // Constraints
    //****************************************

    public void prepareDelete() {
        // alle Referenzen auf UserProfile lösen
        Set<UserProfile> userProfiles = new HashSet<UserProfile>(getUserProfiles());
        for (UserProfile profile : userProfiles) {
            removeUserProfile(profile);
        }
    }

    //****************************************
    // Bid-Directional Associations
    //****************************************

    public void addUserProfile(UserProfile userProfile) {
        if (userProfile == null) throw new IllegalArgumentException("userProfile my not be null!");

        userProfile.getLdapRoles().add(this);
        userProfiles.add(userProfile);
    }

    public void removeUserProfile(UserProfile userProfile) {
        userProfile.getLdapRoles().remove(this);
        userProfiles.remove(userProfile);
    }

    public Set<UserProfile> getUserProfiles() {
        return userProfiles;
    }

    private void setUserProfiles(Set<UserProfile> userProfiles) {
        this.userProfiles = userProfiles;
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

