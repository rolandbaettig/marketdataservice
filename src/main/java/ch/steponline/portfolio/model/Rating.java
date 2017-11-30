package ch.steponline.portfolio.model;

import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(name="Rating",schema="portfolio",uniqueConstraints = {@UniqueConstraint(columnNames = {"Rating", "AgencyId","RatingType"})},
        indexes = {@Index(
                name = "IDX_Rating_AgencyId",
                columnList = "AgencyId")
        })
@NamedQueries(value={
        @NamedQuery(name="RatingByRatingType",query = "select r from Rating r where r.ratingType = :ratingType order by r.agency.name,r.ratingClass"),
        @NamedQuery(name="RatingByRatingTypeAndAgency",query = "select r from Rating r where r.ratingType = :ratingType and r.agency.id=:agencyId order by r.ratingClass"),
        @NamedQuery(name="RatingByAgency",query = "select r from Rating r where r.agency.id=:agencyId order by r.ratingType,r.ratingClass")
})
@Audited
@AuditTable(value = "Rating",schema = "audit")
public class Rating implements Serializable {
    @Id
    @GeneratedValue(generator="rating_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="rating_seq",schema = "portfolio",sequenceName = "Rating_Seq",initialValue = 100000,allocationSize = 1)
    @Column(name = "Id")
    private Long id;

    @Column(name="RatingType",length=20,nullable=false)
    @Enumerated(value = EnumType.STRING)
    @NotNull
    private RatingType ratingType;

    /**
     * Agency specific Rating
     */
    @Column(name="Rating",length = 20,nullable = false)
    @NotNull
    @Size(min=1,max=20)
    private String rating;

    /**
     * Defines the Classifier of the Rating so that Ratings from different Agencies
     * can be compared
     */
    @Column(name="RatingClass",nullable=false)
    @NotNull
    private Integer ratingClass;

    @Column(name="RatingNo",nullable=true)
    private Integer ratingNo;

    @ManyToOne(optional = false,cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="AgencyId",referencedColumnName = "Id", foreignKey = @ForeignKey(name = "FK_Rating_Agency"))
    @org.hibernate.annotations.OnDelete(action= OnDeleteAction.CASCADE)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @NotAudited
    private RatingAgency agency;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RatingType getRatingType() {
        return ratingType;
    }

    public void setRatingType(RatingType ratingType) {
        this.ratingType = ratingType;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getRatingClass() {
        return ratingClass;
    }

    public void setRatingClass(Integer ratingClass) {
        this.ratingClass = ratingClass;
    }

    public Integer getRatingNo() {
        return ratingNo;
    }

    public void setRatingNo(Integer ratingNo) {
        this.ratingNo = ratingNo;
    }

    public RatingAgency getAgency() {
        return agency;
    }

    public void setAgency(RatingAgency agency) {
        if (this.agency != null && this.agency.getAvailableRatings().contains(this)) {
            this.agency.getAvailableRatings().remove(this);
        }

        this.agency = agency;

        if (agency != null && !agency.getAvailableRatings().contains(this)) {
            agency.getAvailableRatings().add(this);
        }

    }

    public String toString() {
        return rating+" ("+ratingType.toString()+")";
    }
}

