package ch.steponline.portfolio.model;

import ch.steponline.core.model.TextEntry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "StrategyTextEntry",schema="portfolio", uniqueConstraints = {@UniqueConstraint(columnNames = {"SourceId", "Language"})},
        indexes = {
                @Index(name = "IDX_StrategyTextContainer_AssetId",columnList = "SourceId"),
                @Index(name = "IDX_StrategyTextContainer_Covering",columnList = "SourceId, Language, Description,Abbreviation")
        })
@Audited
@AuditTable(value = "StrategyTextEntry", schema = "audit")
public class StrategyTextEntry extends TextEntry {

    @ManyToOne(optional = false,fetch= FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "SourceId", referencedColumnName = "Id", insertable = false, updatable = false,foreignKey = @ForeignKey(name = "FK_DomainTextContainer_Domain"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @JsonIgnore
    private Strategy strategy;

    private static final long serialVersionUID = -6677599091674725996L;

    public StrategyTextEntry() {
    }


    public StrategyTextEntry(Strategy strategy, String language) {
        super(strategy.getId(), language);
        this.strategy = strategy;
    }

    public Strategy getStrategy() {
        return strategy;
    }

}
