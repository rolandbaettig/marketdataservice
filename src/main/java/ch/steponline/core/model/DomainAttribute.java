package ch.steponline.core.model;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 15:38
 */
@Entity
@Table(name="DomainAttribute",schema="core",indexes = {@Index(name = "IDX_DomainAttribute_Name",columnList = "Name")},uniqueConstraints = {@UniqueConstraint(name = "UQ_DomainAttribute",columnNames = {"DomainId", "Name"})})
@Audited
@AuditTable(value = "DomainAttribute", schema = "audit")
public class DomainAttribute extends VersionedEntity {
    @Id
    @GeneratedValue(generator="domainAttribute_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="domainAttribute_seq",schema = "core",sequenceName = "DomainAttribute_Seq",initialValue = 1000,allocationSize = 1)
    @Column(name = "Id")
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "DomainId",referencedColumnName = "Id",foreignKey = @ForeignKey(name="FK_DomainAttribute_Domain_Id"))
    private Domain domain;

    @Column(name = "Name")
    @NotNull
    @Size(max = 150)
    private String name;

    @Column(name = "Content")
    @NotNull
    @Size(max = 5000)
    private String content;

    private static final long serialVersionUID = 8056818681120958169L;

    //****************************************
    // Bid-Directional Associations
    //****************************************

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        if (this.domain != null && this.domain.getDomainAttributes().contains(this)) {
            this.domain.getDomainAttributes().remove(this);
        }

        this.domain = domain;

        if (domain != null && !domain.getDomainAttributes().contains(this)) {
            domain.getDomainAttributes().add(this);
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

