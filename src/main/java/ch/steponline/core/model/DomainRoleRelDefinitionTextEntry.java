package ch.steponline.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 10.05.2017
 * Time: 16:42
 */
@Entity
@Table(name = "DomainRoleRelDefTextContainer",schema="core", uniqueConstraints = {@UniqueConstraint(columnNames = {"SourceId", "Language"})},indexes = {
        @Index(name="IDX_DomainRolRelDefTextContainer_Id",columnList = "SourceId")   ,
        @Index(name="IDX_DomainRolRelDefTextContainer_Covering",columnList = "SourceId,Language,Abbreviation,Description")
})
@Audited
@AuditTable(value = "DomainRoleRelDefTextContainer", schema = "audit")
public class DomainRoleRelDefinitionTextEntry extends TextEntry {

    @ManyToOne(optional = false)
    @JoinColumn(name = "SourceId", referencedColumnName = "Id", insertable = false, updatable = false,foreignKey = @ForeignKey(name = "FK_DomainRoleRelDefTextContainer_DomainRoleRelDef"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DomainRoleRelationDefinition domainRoleRelationDefinition;
    private static final long serialVersionUID = 6407401472294074419L;

    public DomainRoleRelDefinitionTextEntry() {
    }

    public DomainRoleRelDefinitionTextEntry(DomainRoleRelationDefinition domainRoleRelationDefinition, String language) {
        super(domainRoleRelationDefinition.getId(), language);
        this.domainRoleRelationDefinition = domainRoleRelationDefinition;
    }

    public DomainRoleRelationDefinition getDomainRoleRelationDefinition() {
        return domainRoleRelationDefinition;
    }
}
