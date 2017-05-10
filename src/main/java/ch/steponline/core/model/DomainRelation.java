package ch.steponline.core.model;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 14:10
 */
@Entity
@Table(name = "DomainRelation",  schema="core",
        indexes = {
            @Index( name = "IDX_DomainRelation_DomainFrom",columnList = "DomainFromId"),
                @Index( name = "IDX_DomainRelation_DomainTo",columnList = "DomainToId"),
                @Index( name = "IDX_DomainRelation_DomainRRelDefinition",columnList = "DomainRoleRelationDefinitionId"),
                @Index( name = "IDX_DomainRelation_FromTo",columnList = "Id, ValidFrom, ValidTo")
        },
        uniqueConstraints = {@UniqueConstraint(name = "UQ_DomainRelation",columnNames = {"DomainFromId", "DomainToId", "ValidFrom"})})
@Audited
@AuditTable(value = "DomainRelation", schema = "audit")
public class DomainRelation extends VersionedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "DomainFromId",referencedColumnName = "Id",foreignKey = @ForeignKey(name="FK_DomainRelation_DomainFrom_Id"))
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @NotNull
    private Domain domainFrom;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "DomainToId",referencedColumnName = "Id",foreignKey = @ForeignKey(name="FK_DomainRelation_DomainTo_Id"))
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @NotNull
    private Domain domainTo;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "DomainRoleRelationDefinitionId",referencedColumnName = "Id",foreignKey = @ForeignKey(name="FK_DomainRelation_DomainRoleRelDef_Id"))
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @NotNull
    private DomainRoleRelationDefinition domainRoleRelationDefinition;

    @Column(name = "ValidFrom")
    @Temporal(TemporalType.DATE)
    @NotNull
    private Date validFrom = new Date();

    @Column(name = "ValidTo")
    @Temporal(TemporalType.DATE)
    private Date validTo;

    @Column(name = "Factor")
    @NotNull
    private Double factor = (double) 1;

    private static final long serialVersionUID = 5649692103959729617L;

    //****************************************
    // Bid-Directional Associations
    //****************************************

    public Domain getDomainFrom() {
        return domainFrom;
    }

    public void setDomainFrom(Domain domainFrom) {
        if (this.domainFrom != null && this.domainFrom.getFromDomainRelations().contains(this)) {
            this.domainFrom.getFromDomainRelations().remove(this);
        }

        this.domainFrom = domainFrom;

        if (domainFrom != null && !domainFrom.getFromDomainRelations().contains(this)) {
            domainFrom.getFromDomainRelations().add(this);
        }
    }

    public Domain getDomainTo() {
        return domainTo;
    }

    public void setDomainTo(Domain domainTo) {
        if (this.domainTo != null && this.domainTo.getToDomainRelations().contains(this)) {
            this.domainTo.getToDomainRelations().remove(this);
        }

        this.domainTo = domainTo;

        if (domainTo != null && !domainTo.getToDomainRelations().contains(this)) {
            domainTo.getToDomainRelations().add(this);
        }
    }

    public DomainRoleRelationDefinition getDomainRoleRelationDefinition() {
        return domainRoleRelationDefinition;
    }

    public void setDomainRoleRelationDefinition(DomainRoleRelationDefinition domainRoleRelationDefinition) {
        if (this.domainRoleRelationDefinition != null && this.domainRoleRelationDefinition.getDomainRelations().contains(this)) {
            this.domainRoleRelationDefinition.getDomainRelations().remove(this);
        }

        this.domainRoleRelationDefinition = domainRoleRelationDefinition;

        if (domainRoleRelationDefinition != null && !domainRoleRelationDefinition.getDomainRelations().contains(this)) {
            domainRoleRelationDefinition.getDomainRelations().add(this);
        }
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

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

}

