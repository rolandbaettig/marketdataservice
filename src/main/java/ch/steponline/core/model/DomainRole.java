package ch.steponline.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 13:50
 */
@Entity
@Table(name = "DomainRole",schema="core")
@NamedQueries(value = {
        @NamedQuery(name = "DomainRole_All_ByName", query = "select dr from DomainRole dr order by dr.id"),
        @NamedQuery(name = "DomainRole_byIds", query = "select dr from DomainRole dr where dr.id in (:idList) order by dr.id")
})
@Audited
@AuditTable(value = "DomainRole", schema = "audit")
public class DomainRole extends VersionedEntity {
    @Id
    @Column(name = "Id")
    @Size(max = 150)
    private String id;

    @OneToMany(mappedBy = "fromDomainRole", fetch = FetchType.LAZY, cascade = {CascadeType.ALL},orphanRemoval = true)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Set<DomainRoleRelationDefinition> fromDomainRoleRelationDefinitions = new HashSet<DomainRoleRelationDefinition>();

    @OneToMany(mappedBy = "toDomainRole", fetch = FetchType.LAZY)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Set<DomainRoleRelationDefinition> toDomainRoleRelationDefinitions = new HashSet<DomainRoleRelationDefinition>();

    @OneToMany(mappedBy = "domainRole", fetch = FetchType.EAGER)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @JsonIgnore
    private Set<Domain> domains = new HashSet<Domain>();

    private static final long serialVersionUID = -6570102817645411251L;

    public enum ROLES {
        CURRENCY,
        NATION,
        TERRITORIAL
    }
    //****************************************
    // Bid-Directional Associations
    //****************************************

    public void addFromDomainRoleRelationDefinition(DomainRoleRelationDefinition fromDomainRoleRelationDefinition) {
        if (fromDomainRoleRelationDefinition == null)
            throw new IllegalArgumentException("fromDomainRoleRelationDefinition Object is NULL");

        if (fromDomainRoleRelationDefinition.getFromDomainRole() == null || !fromDomainRoleRelationDefinition.getFromDomainRole().equals(this)) {
            fromDomainRoleRelationDefinition.setFromDomainRole(this);
        }

        if (!this.fromDomainRoleRelationDefinitions.contains(fromDomainRoleRelationDefinition)) {
            this.fromDomainRoleRelationDefinitions.add(fromDomainRoleRelationDefinition);
        }
    }

    public void removeFromDomainRoleRelationDefinition(DomainRoleRelationDefinition fromDomainRoleRelationDefinition) {
        if (fromDomainRoleRelationDefinition == null)
            throw new IllegalArgumentException("fromDomainRoleRelationDefinition Object is NULL");

        if (fromDomainRoleRelationDefinition.getFromDomainRole() != null && fromDomainRoleRelationDefinition.getFromDomainRole().equals(this)) {
            this.fromDomainRoleRelationDefinitions.remove(fromDomainRoleRelationDefinition);
            fromDomainRoleRelationDefinition.setFromDomainRole(null);
        }
    }

    public Set<DomainRoleRelationDefinition> getFromDomainRoleRelationDefinitions() {
        return fromDomainRoleRelationDefinitions;
    }

    private void setFromDomainRoleRelationDefinitions(Set<DomainRoleRelationDefinition> fromDomainRoleRelationDefinitions) {
        this.fromDomainRoleRelationDefinitions = fromDomainRoleRelationDefinitions;
    }

    public void addToDomainRoleRelationDefinition(DomainRoleRelationDefinition toDomainRoleRelationDefinition) {
        if (toDomainRoleRelationDefinition == null)
            throw new IllegalArgumentException("toDomainRoleRelationDefinition Object is NULL");

        if (toDomainRoleRelationDefinition.getToDomainRole() == null || !toDomainRoleRelationDefinition.getToDomainRole().equals(this)) {
            toDomainRoleRelationDefinition.setToDomainRole(this);
        }

        if (!this.toDomainRoleRelationDefinitions.contains(toDomainRoleRelationDefinition)) {
            this.toDomainRoleRelationDefinitions.add(toDomainRoleRelationDefinition);
        }
    }

    public void removeToDomainRoleRelationDefinition(DomainRoleRelationDefinition toDomainRoleRelationDefinition) {
        if (toDomainRoleRelationDefinition == null)
            throw new IllegalArgumentException("toDomainRoleRelationDefinition Object is NULL");

        if (toDomainRoleRelationDefinition.getToDomainRole() != null && toDomainRoleRelationDefinition.getToDomainRole().equals(this)) {
            this.toDomainRoleRelationDefinitions.remove(toDomainRoleRelationDefinition);
            toDomainRoleRelationDefinition.setToDomainRole(null);
        }
    }

    public Set<DomainRoleRelationDefinition> getToDomainRoleRelationDefinitions() {
        return toDomainRoleRelationDefinitions;
    }

    private void setToDomainRoleRelationDefinitions(Set<DomainRoleRelationDefinition> toDomainRoleRelationDefinitions) {
        this.toDomainRoleRelationDefinitions = toDomainRoleRelationDefinitions;
    }

    public void addDomain(Domain domain) {
        if (domain == null)
            throw new IllegalArgumentException("identity Object is NULL");

        if (domain.getDomainRole() == null || !domain.getDomainRole().equals(this)) {
            domain.setDomainRole(this);
        }

        if (!this.getDomains().contains(domain)) {
            this.getDomains().add(domain);
        }
    }

    public void removeDomain(Domain domain) {
        if (domain == null) throw new IllegalArgumentException("identity Object is NULL");

        if (domain.getDomainRole() != null && domain.getDomainRole().equals(this)) {
            this.getDomains().remove(domain);
            domain.setDomainRole(null);
        }
    }

    public Set<Domain> getDomains() {
        return domains;
    }

    private void setDomains(Set<Domain> domains) {
        this.domains = domains;
    }

    //****************************************
    // Getters / Setters
    //****************************************


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

