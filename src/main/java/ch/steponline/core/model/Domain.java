package ch.steponline.core.model;

import ch.steponline.mds.util.SystemDefaultLanguage;
import org.hibernate.annotations.*;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 14:17
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DomainRoleId", discriminatorType = DiscriminatorType.STRING,length = 150)
@Table(name = "Domain", schema="core" ,
        indexes = {
                @Index(
                                name = "IDX_Domain_Iso",
                                columnList = "IsoAlphabetic, IsoNumeric, SortNo"),
                                @Index(
                                        name = "IDX_Domain_Iso",
                                        columnList = "ValidFrom, ValidTo"),
                                @Index(
                                        name = "IDX_Domain_IsoAlphabetic",
                                        columnList = "IsoAlphabetic"),
                                @Index(
                                        name = "IDX_Domain_SortNo",
                                        columnList = "SortNo"),
                                @Index(
                                        name = "IDX_Domain_DomainRoleId",
                                        columnList = "DomainRoleId")
        },
        uniqueConstraints = {@UniqueConstraint(name="UQ_Domain",columnNames = {"DomainRoleId", "ValidFrom", "DomainNo"})})
@NamedQueries(value = {
        @NamedQuery(name = "DomainByRole", query = "select distinct d from Domain d join fetch d.domainRole r join fetch d.textEntries t where r.id=:role order by d.sortNo"),
        @NamedQuery(name = "DomainByRoleAndIso", query = "select distinct d from Domain d join fetch d.textEntries join fetch d.domainRole r where r.id=:role and d.isoAlphabetic=:isoCode"),
        @NamedQuery(name = "DomainByRoleAndDomainNo", query = "select distinct d from Domain d join fetch d.textEntries join fetch d.domainRole r where r.id=:role and d.domainNo=:domainNo"),
        @NamedQuery(name = "Domain_SelectNextDomainNo", query = "select max(d.domainNo) from Domain d where d.domainRole = :role"),
        @NamedQuery(name = "Domain_SelectNextSortNo", query = "select max(d.sortNo) from Domain d where d.domainRole = :role"),
        @NamedQuery(name = "Domain_FindUnique", query = "select d from Domain d where d.domainRole = :role and d.domainNo = :domainNo and d.validFrom = :validFrom and d.id <> :id")
})
@Audited
@AuditTable(value = "Domain", schema = "audit")
public abstract class Domain extends VersionedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    // @NotNull nicht verwenden wird über @AssetTrue gelöst
    @JoinColumn(name = "DomainRoleId",referencedColumnName = "Id",foreignKey = @ForeignKey(name = "FK_Domain_DomainRoleId"),insertable = false,updatable = false) // Has to be insertable/updateable false because its the DiscriminatorValue and otherwise hibernate would throw ad MappingException
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private DomainRole domainRole;

    @Column(name = "ValidFrom")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date validFrom = new Date();

    @Column(name = "ValidTo")
    @Temporal(TemporalType.DATE)
    private Date validTo;

    @Column(name = "DomainNo", nullable = true)
    @Size(min = 0)
    private Long domainNo;

    @Column(name = "IsCustom")
    @NotNull
    private Integer custom = 1;

    @Column(name = "IsoAlphabetic")
    @Size(max = 50)
    private String isoAlphabetic;

    @Column(name = "IsoNumeric")
    private String isoNumeric;

    @Column(name = "SortNo")
    @NotNull
    @Min(value = 0)
    private Double sortNo;

    @OneToMany(mappedBy = "domain",fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @org.hibernate.annotations.BatchSize(size = 100)
    private Set<DomainTextEntry> textEntries = new HashSet<DomainTextEntry>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "domain",orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @org.hibernate.annotations.BatchSize(size = 100)
    private Set<DomainAttribute> domainAttributes = new HashSet<DomainAttribute>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "domainFrom",orphanRemoval = true)
    @org.hibernate.annotations.BatchSize(size = 100)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Set<DomainRelation> fromDomainRelations = new HashSet<DomainRelation>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "domainTo",orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @org.hibernate.annotations.BatchSize(size = 100)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Set<DomainRelation> toDomainRelations = new HashSet<DomainRelation>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL},orphanRemoval = true)
    @JoinTable(name = "Domain3PKeyAss",schema = "core",
            joinColumns = {@JoinColumn(name = "DomainId",foreignKey = @ForeignKey(name="FK_Domain3PKeyAss_Domain"))},
            inverseJoinColumns = {@JoinColumn(name = "ThirdPartySystemKeyId",foreignKey = @ForeignKey(name="FK__Domain3PKeyAss_3PKeyAss_KeyId"))}
    )
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @AuditJoinTable(name = "Domain3PKeyAss", schema = "audit")
    private Set<ThirdPartySystemKey> thirdPartySystemKeys = new HashSet<ThirdPartySystemKey>();

    private static final long serialVersionUID = 8188280315877590654L;

    //****************************************
    // Bid-Directional Associations
    //****************************************

    public DomainRole getDomainRole() {
        return domainRole;
    }

    public void setDomainRole(DomainRole domainRole) {
        if (this.domainRole != null && this.domainRole.getDomains().contains(this)) {
            this.domainRole.getDomains().remove(this);
        }

        this.domainRole = domainRole;

        if (domainRole != null && !domainRole.getDomains().contains(this)) {
            domainRole.getDomains().add(this);
        }
    }

    public void addDomainAttribute(DomainAttribute domainAttribute) {
        if (domainAttribute == null)
            throw new IllegalArgumentException("domainAttribute Object is NULL");

        if (domainAttribute.getDomain() == null || !domainAttribute.getDomain().equals(this)) {
            domainAttribute.setDomain(this);
        }

        if (!this.domainAttributes.contains(domainAttribute)) {
            this.domainAttributes.add(domainAttribute);
        }
    }

    public void removeDomainAttribute(DomainAttribute domainAttribute) {
        if (domainAttribute == null) throw new IllegalArgumentException("domainAttribute Object is NULL");

        if (domainAttribute.getDomain() != null && domainAttribute.getDomain().equals(this)) {
            this.domainAttributes.remove(domainAttribute);
            domainAttribute.setDomain(null);
        }
    }

    public Set<DomainAttribute> getDomainAttributes() {
        return domainAttributes;
    }

    private void setDomainAttributes(Set<DomainAttribute> domainAttributes) {
        this.domainAttributes = domainAttributes;
    }

    public void addFromDomainRelation(DomainRelation fromDomainRelations) {
        if (fromDomainRelations == null)
            throw new IllegalArgumentException("fromDomainRelations Object is NULL");

        if (fromDomainRelations.getDomainFrom() == null || !fromDomainRelations.getDomainFrom().equals(this)) {
            fromDomainRelations.setDomainFrom(this);
        }

        if (!this.fromDomainRelations.contains(fromDomainRelations)) {
            this.fromDomainRelations.add(fromDomainRelations);
        }
    }

    public void removeFromDomainRelation(DomainRelation fromDomainRelations) {
        if (fromDomainRelations == null) throw new IllegalArgumentException("fromDomainRelations Object is NULL");

        if (fromDomainRelations.getDomainFrom() != null && fromDomainRelations.getDomainFrom().equals(this)) {
            this.fromDomainRelations.remove(fromDomainRelations);
            fromDomainRelations.setDomainFrom(null);
        }
    }

    public Set<DomainRelation> getFromDomainRelations() {
        return fromDomainRelations;
    }

    private void setFromDomainRelations(Set<DomainRelation> fromDomainRelations) {
        this.fromDomainRelations = fromDomainRelations;
    }

    public void addToDomainRelation(DomainRelation toDomainRelation) {
        if (toDomainRelation == null)
            throw new IllegalArgumentException("toDomainRelation Object is NULL");

        if (toDomainRelation.getDomainTo() == null || !toDomainRelation.getDomainTo().equals(this)) {
            toDomainRelation.setDomainTo(this);
        }

        if (!this.toDomainRelations.contains(toDomainRelation)) {
            this.toDomainRelations.add(toDomainRelation);
        }
    }

    public void removeToDomainRelation(DomainRelation toDomainRelation) {
        if (toDomainRelation == null) throw new IllegalArgumentException("toDomainRelation Object is NULL");

        if (toDomainRelation.getDomainTo() != null && toDomainRelation.getDomainTo().equals(this)) {
            this.toDomainRelations.remove(toDomainRelation);
            toDomainRelation.setDomainTo(null);
        }
    }

    public Set<DomainRelation> getToDomainRelations() {
        return toDomainRelations;
    }

    private void setToDomainRelations(Set<DomainRelation> toDomainRelations) {
        this.toDomainRelations = toDomainRelations;
    }

    public Set<DomainTextEntry> getTextEntries() {
        return textEntries;
    }

    public void setTextEntries(Set<DomainTextEntry> textEntries) {
        this.textEntries = textEntries;
    }

//****************************************
    // Unidirektionale Beziehungen
    //****************************************

    public void addThirdPartySystemKey(ThirdPartySystemKey thirdPartySystemKey) {
        if (thirdPartySystemKey == null) throw new IllegalArgumentException("thirdPartySystemKey my not be null!");

        thirdPartySystemKeys.add(thirdPartySystemKey);
    }

    public void removeThirdPartySystemKey(ThirdPartySystemKey thirdPartySystemKey) {
        thirdPartySystemKeys.remove(thirdPartySystemKey);
    }

    public Set<ThirdPartySystemKey> getThirdPartySystemKeys() {
        return thirdPartySystemKeys;
    }

    private void setThirdPartySystemKeys(Set<ThirdPartySystemKey> thirdPartySystemKeys) {
        this.thirdPartySystemKeys = thirdPartySystemKeys;
    }

    //****************************************
    // Helpers / Delegation
    //****************************************
    private String getDefaultLanguage() {
        return SystemDefaultLanguage.getInstance().getLanguage();
    }

    private TextEntry getTextEntry(String language) {
        for (TextEntry textEntry : getTextEntries()) {
            if (textEntry.getId().getLanguage().equals(language)) return textEntry;
        }
        return null;
    }

    public String getAbbreviation() {
        TextEntry textEntry=getTextEntry(getDefaultLanguage());
        if (textEntry!=null) return textEntry.getAbbreviation();
        throw new IllegalArgumentException("No TextEntry found for Object DomainRoleRelationDefinition Id: "+getId()+" and Language: "+getDefaultLanguage());
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

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Long getDomainNo() {
        return domainNo;
    }

    public void setDomainNo(Long domainNo) {
        this.domainNo = domainNo;
    }

    public Boolean getCustom() {
        return custom==0?Boolean.FALSE:Boolean.TRUE;
    }

    public void setCustom(Integer custom) {
        this.custom = custom;
    }

    public String getIsoAlphabetic() {
        return isoAlphabetic;
    }

    public void setIsoAlphabetic(String isoAlphabetic) {
        this.isoAlphabetic = isoAlphabetic;
    }

    public String getIsoNumeric() {
        return isoNumeric;
    }

    public void setIsoNumeric(String isoNumeric) {
        this.isoNumeric = isoNumeric;
    }

    public Double getSortNo() {
        return sortNo;
    }

    public void setSortNo(Double sortNo) {
        this.sortNo = sortNo;
    }
}

