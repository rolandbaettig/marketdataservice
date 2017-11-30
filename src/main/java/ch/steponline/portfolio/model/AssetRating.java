package ch.steponline.portfolio.model;

import ch.steponline.core.model.VersionedEntity;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name="AssetRating",schema="portfolio",uniqueConstraints = {@UniqueConstraint(columnNames={"AssetId","RatingId","ValidFrom"})},
        indexes = {@Index(
                name = "IDX_AssetRating_AssetId",
                columnList = "AssetId"),
                @Index(
                        name="IDX_AssetRating_Rating_Id",
                        columnList = "RatingId")
        }
)
@Audited
@AuditTable(value = "AssetRating",schema = "audit")
public class AssetRating extends VersionedEntity implements Serializable {
    @Id
    @GeneratedValue(generator="assetrating_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="assetrating_seq",schema = "portfolio",sequenceName = "AssetRating_Seq",initialValue = 100000,allocationSize = 1)
    @Column(name = "Id")
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name="RatingId",referencedColumnName = "Id",foreignKey = @ForeignKey(name="FK_AssetRating_Rating"))
    @NotNull
    @org.hibernate.annotations.OnDelete(action = OnDeleteAction.CASCADE)
    @OptimisticLock(excluded = true)
    private Rating rating;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="AssetId",referencedColumnName = "Id",foreignKey = @ForeignKey(name="FK_AssetRating_Asset"))
    @OptimisticLock(excluded = true)
    private Asset asset;

    @Column(name="ValidFrom", nullable = false)
    @NotNull
    private LocalDate validFrom;

    @Column(name="ValidTo", nullable = true)
    private LocalDate validTo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRatingClass() {
        return rating!=null?rating.getRatingClass():999;
    }

    public String getRatingString() {
        return rating.getRating();
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        if (this.asset != null && this.asset.getRatings().contains(this)) {
            this.asset.getRatings().remove(this);
        }

        this.asset = asset;

        if (asset != null && !asset.getRatings().contains(this)) {
            asset.getRatings().add(this);
        }
    }

    public String getRatingValue() {
        return rating!=null?rating.getRating():null;
    }

    public String getAgencyName() {
        return rating!=null && rating.getAgency()!=null?rating.getAgency().getName():null;
    }

    public LocalDate getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(LocalDate validFrom) {
        this.validFrom = validFrom;
    }

    public LocalDate getValidTo() {
        return validTo;
    }

    public void setValidTo(LocalDate validTo) {
        this.validTo = validTo;
    }
}

