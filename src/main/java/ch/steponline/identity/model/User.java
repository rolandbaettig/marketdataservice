package ch.steponline.identity.model;

import ch.steponline.core.model.VersionedEntity;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 10.05.2017
 * Time: 09:26
 */
@Entity
@Table(name = "LoginUser",schema="usr",uniqueConstraints = {@UniqueConstraint(name="UQ_LoginUser",columnNames = {"LoginName"})},
        indexes = {@Index(name = "IDX_User_LdapId",columnList = "LdapGUID, LoginName"),
                        @Index(name = "IDX_User_Covering",columnList = "Id, LoginName, FirstName, LastName, RealName")
        }
)
@org.hibernate.annotations.NamedQueries({
        @org.hibernate.annotations.NamedQuery(
                name = "User_Suggestion_Search",
                query = "select u from User u where (u.loginName like :pattern or u.firstName like :pattern or u.lastName like :pattern or u.realName like :pattern or u.shortName like :pattern) and u.active = true order by u.realName",
                comment = "Suchen aller Benutzer für User-Profile Benutzer Zuweisung"
        ),
        @org.hibernate.annotations.NamedQuery(name = "User_FindUnique", query = "select u from User u where u.loginName = :loginName and u.id <> :id"),
        @org.hibernate.annotations.NamedQuery(name = "User_FindByLoginName", query = "select u from User u where u.active = true and u.loginName = :loginName"),
        @org.hibernate.annotations.NamedQuery(name = "User_AllByLdapGUID", query = "select u from User u order by u.ldapGUId")
})
@org.hibernate.annotations.TypeDef(
        name = "encryptedString",
        typeClass = EncryptedStringType.class,
        parameters = {@Parameter(name = "encryptorRegisteredName", value = "hibernateStringEncryptor")}
)
@Audited
@AuditTable(value = "LoginUser", schema = "audit")
public class User extends VersionedEntity {
    private static final long serialVersionUID = -2908965561670027319L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @Column(name = "LdapGUID")
    @Size(max = 200)
    private String ldapGUId;

    @Column(name = "LoginName")
    @NotNull
    @Size(min = 1, max = 30)
    private String loginName;

    @Column(name = "FirstName")
    @Size(max = 200)
    @NotNull
    private String firstName;

    @Column(name = "LastName")
    @Size(max = 250)
    @NotNull
    private String lastName;

    @Column(name = "ShortName")
    @Size(max = 50)
    private String shortName;

    @Column(name = "RealName")
    @Size(max = 500)
    @NotNull
    private String realName;

    @Column(name = "EMail")
    @Size(max = 50)
    @Email
    private String email;

    @Column(name = "PhoneNumber")
    @Size(max = 50)
    private String phoneNumber;

    @Column(name = "AlternativePhoneNumber")
    @Size(max = 150)
    private String alternativePhoneNumber;

    @Column(name = "Active")
    private Boolean active = Boolean.TRUE;

    @Column(name = "Password")
    @Size(min = 8, max = 200)
    @Type(type = "encryptedString")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "UserTeamAss", schema = "usr",
            joinColumns = {@JoinColumn(name = "UserId", foreignKey = @ForeignKey(name="FK_UserTeam_UserId"))},
            inverseJoinColumns = {@JoinColumn(name = "TeamId",foreignKey = @ForeignKey(name="FK_UserTeam_TeamId"))}
    )
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @AuditJoinTable(name = "UserTeamAss", schema = "audit")
    private Set<Team> teams = new HashSet<Team>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "UserUserProfileAss",  schema="usr",
            joinColumns = {@JoinColumn(name = "UserId",foreignKey = @ForeignKey(name="FK_userUserProfile_UserId"))},
            inverseJoinColumns = {@JoinColumn(name = "UserProfileId",foreignKey = @ForeignKey(name="FK_UserUserProfile_UserProfileId"))}
    )
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @AuditJoinTable(name = "UserUserProfileAss", schema = "audit")
    private Set<UserProfile> userProfiles = new HashSet<UserProfile>();

    @Transient
    private List<String> memberOf = new ArrayList<String>();

    //****************************************
    // Bid-Directional Associations
    //****************************************

    public void addTeam(Team team) {
        if (team == null) throw new IllegalArgumentException("team my not be null!");

        team.getUsers().add(this);
        teams.add(team);
    }

    public void removeTeam(Team team) {
        team.getUsers().remove(this);
        teams.remove(team);
    }

    public Set<Team> getTeams() {
        return teams;
    }

    private void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public void addUserProfile(UserProfile userProfile) {
        if (userProfile == null) throw new IllegalArgumentException("userProfile my not be null!");

        userProfile.getUsers().add(this);
        userProfiles.add(userProfile);
    }

    public void removeUserProfile(UserProfile userProfile) {
        userProfile.getUsers().remove(this);
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
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLdapGUId() {
        return ldapGUId;
    }

    public void setLdapGUId(String ldapGUId) {
        this.ldapGUId = ldapGUId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAlternativePhoneNumber() {
        return alternativePhoneNumber;
    }

    public void setAlternativePhoneNumber(String alternativePhoneNumber) {
        this.alternativePhoneNumber = alternativePhoneNumber;
    }

    public Boolean isActive() {
        return active;
    }

    public Boolean getActive() {
        return isActive();
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<String> getMemberOf() {
        return memberOf;
    }

    public void setMemberOf(List<String> memberOf) {
        this.memberOf = memberOf;
    }

    public String getFirstLastName() {
        if (getFirstName() == null || getLastName() == null) {
            return getRealName();
        } else {
            return getFirstName() + " " + getLastName();
        }
    }
}

