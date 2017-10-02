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
 * Time: 16:15
 */
@Entity
@Table(name = "DomainTextEntry",schema="core", uniqueConstraints = {@UniqueConstraint(columnNames = {"SourceId", "Language"})},
        indexes = {
                @Index(name = "IDX_DomainTextContainer_DomainId",columnList = "SourceId"),
                                @Index(name = "IDX_DomainTextContainer_Covering",columnList = "SourceId, Language, Description,Abbreviation")
        })
@Audited
@AuditTable(value = "DomainTextEntry", schema = "audit")
public class DomainTextEntry extends TextEntry {

    @ManyToOne(optional = false,fetch= FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "SourceId", referencedColumnName = "Id", insertable = false, updatable = false,foreignKey = @ForeignKey(name = "FK_DomainTextContainer_Domain"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @JsonIgnore
    private Domain domain;
    private static final long serialVersionUID = -6677599091674725996L;

    public DomainTextEntry() {
    }

    public DomainTextEntry(Domain domain, String language) {
        super(domain.getId(), language);
        this.domain = domain;
    }

    public Domain getDomain() {
        return domain;
    }

}

