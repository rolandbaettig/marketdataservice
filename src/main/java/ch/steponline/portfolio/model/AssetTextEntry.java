package ch.steponline.portfolio.model;

import ch.steponline.core.model.TextEntry;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;

@Entity
@Table(name = "AssetTextEntry",schema="portfolio", uniqueConstraints = {@UniqueConstraint(columnNames = {"SourceId", "Language"})},
        indexes = {
                @Index(name = "IDX_AssetTextContainer_AssetId",columnList = "SourceId"),
                @Index(name = "IDX_AssetTextContainer_Covering",columnList = "SourceId, Language, Description,Abbreviation")
        })
@Audited
@AuditTable(value = "AssetTextEntry", schema = "audit")
public class AssetTextEntry extends TextEntry {

    @ManyToOne(optional = false,fetch= FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "SourceId", referencedColumnName = "Id", insertable = false, updatable = false,foreignKey = @ForeignKey(name = "FK_DomainTextContainer_Domain"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @JsonIgnore
    private Asset asset;
    private static final long serialVersionUID = -6677599091674725996L;

    public AssetTextEntry() {
    }

    public AssetTextEntry(Asset asset, String language) {
        super(asset.getId(), language);
        this.asset = asset;
    }

    public Asset getAsset() {
        return asset;
    }

}
