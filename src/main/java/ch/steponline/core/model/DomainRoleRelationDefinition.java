package ch.steponline.core.model;

import ch.steponline.mds.util.SystemDefaultLanguage;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 13:59
 */
@Entity
@Table(name = "DomainRoleRelationDefinition",schema = "core",
        indexes = {@Index(name = "IDX_DomainRoleRelationDef_FromDomainRole",columnList = "FromDomainRoleid"),
                    @Index(name="IDX_DomainRoleRelationDef_ToDomainRole",columnList = "ToDomainRoleid")
        })
@Audited
@AuditTable(value = "DomainRoleRelationDefinition", schema = "audit")
public class DomainRoleRelationDefinition extends VersionedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO,generator = "domainRoleRelationDefinition_seq")
    @SequenceGenerator(name="domainRoleRelationDefinition_seq",sequenceName = "DomainRoleRelationDefinition_Seq",initialValue = 1000,allocationSize = 1)
    @Column(name = "Id")
    private Long id;

    @Column(name="Description",nullable=false)
    @NotNull
    private String description;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "FromDomainRoleId", referencedColumnName = "Id",foreignKey = @ForeignKey(name = "FK_DomainRoleRelationDef_DomainRoleFrom_Id" ),columnDefinition = "VARCHAR(150)")
    private DomainRole fromDomainRole;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "ToDomainRoleId", referencedColumnName = "Id",foreignKey = @ForeignKey(name = "FK_DomainRoleRelationDef_DomainRoleTo_Id" ),columnDefinition = "VARCHAR(150)")
    @NotNull
    private DomainRole toDomainRole;

    @Column(name = "IsMandatory")
    @NotNull
    private Boolean mandatory = Boolean.FALSE;

    @Column(name = "IsFactorVisible")
    @NotNull
    private Boolean factorVisible = Boolean.TRUE;

    @OneToMany(mappedBy = "domainRoleRelationDefinition", fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<DomainRelation> domainRelations = new HashSet<DomainRelation>();

    @OneToMany(mappedBy = "domainRoleRelationDefinition",fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @org.hibernate.annotations.BatchSize(size = 100)
    private Set<DomainRoleRelDefinitionTextEntry> textEntries = new HashSet<DomainRoleRelDefinitionTextEntry>();

    private static final long serialVersionUID = -4148379895969568856L;

    //****************************************
    // Bid-Directional Associations
    //****************************************

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DomainRole getFromDomainRole() {
        return fromDomainRole;
    }

    public void setFromDomainRole(DomainRole fromDomainRole) {
        if (this.fromDomainRole != null && this.fromDomainRole.getFromDomainRoleRelationDefinitions().contains(this)) {
            this.fromDomainRole.getFromDomainRoleRelationDefinitions().remove(this);
        }

        this.fromDomainRole = fromDomainRole;

        if (fromDomainRole != null && !fromDomainRole.getFromDomainRoleRelationDefinitions().contains(this)) {
            fromDomainRole.getFromDomainRoleRelationDefinitions().add(this);
        }
    }

    public DomainRole getToDomainRole() {
        return toDomainRole;
    }

    public void setToDomainRole(DomainRole toDomainRole) {
        if (this.toDomainRole != null && this.toDomainRole.getToDomainRoleRelationDefinitions().contains(this)) {
            this.toDomainRole.getToDomainRoleRelationDefinitions().remove(this);
        }

        this.toDomainRole = toDomainRole;

        if (toDomainRole != null && !toDomainRole.getToDomainRoleRelationDefinitions().contains(this)) {
            toDomainRole.getToDomainRoleRelationDefinitions().add(this);
        }
    }

    public void addDomainRelation(DomainRelation domainRelation) {
        if (domainRelation == null)
            throw new IllegalArgumentException("domainRelation Object is NULL");

        if (domainRelation.getDomainRoleRelationDefinition() == null || !domainRelation.getDomainRoleRelationDefinition().equals(this)) {
            domainRelation.setDomainRoleRelationDefinition(this);
        }

        if (!this.domainRelations.contains(domainRelation)) {
            this.domainRelations.add(domainRelation);
        }
    }

    public void removeDomainRelation(DomainRelation domainRelation) {
        if (domainRelation == null) throw new IllegalArgumentException("domainRelation Object is NULL");

        if (domainRelation.getDomainRoleRelationDefinition() != null && domainRelation.getDomainRoleRelationDefinition().equals(this)) {
            this.domainRelations.remove(domainRelation);
            domainRelation.setDomainRoleRelationDefinition(null);
        }
    }

    public Set<DomainRelation> getDomainRelations() {
        return domainRelations;
    }

    private void setDomainRelations(Set<DomainRelation> domainRelations) {
        this.domainRelations = domainRelations;
    }

    public Set<DomainRoleRelDefinitionTextEntry> getTextEntries() {
        return textEntries;
    }

    public void setTextEntries(Set<DomainRoleRelDefinitionTextEntry> textEntries) {
        this.textEntries = textEntries;
    }

    //****************************************
    // Helpers / Delegation
    //****************************************
    public String getAbbreviation() {
        TextEntry textEntry=getTextEntry(getDefaultLanguage());
        if (textEntry!=null) return textEntry.getAbbreviation();
        throw new IllegalArgumentException("No TextEntry found for Object DomainRoleRelationDefinition Id: "+getId()+" and Language: "+getDefaultLanguage());
    }

    private String getDefaultLanguage() {
        return SystemDefaultLanguage.getInstance().getLanguage();
    }

    private TextEntry getTextEntry(String language) {
        for (TextEntry textEntry : getTextEntries()) {
            if (textEntry.getId().getLanguage().equals(language)) return textEntry;
        }
        return null;
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

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public Boolean getFactorVisible() {
        return factorVisible;
    }

    public void setFactorVisible(Boolean factorVisible) {
        this.factorVisible = factorVisible;
    }

    public boolean isTerritorialToNationRelation()  {
        return (getFromDomainRole().getId().equals("TERRITORIAL")
                && getToDomainRole().getId().equals("NATION"));
    }

    public boolean isNationToCurrencyRelation()  {
        return (getFromDomainRole().getId().equals("NATION")
                && getToDomainRole().getId().equals("CURRENCY"));
    }

}

