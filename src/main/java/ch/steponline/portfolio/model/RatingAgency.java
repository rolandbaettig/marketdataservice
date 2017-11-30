package ch.steponline.portfolio.model;

import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="RatingAgency",schema = "portfolio")
public class RatingAgency {
    @Id
    @Column(name="Id",nullable = false)
    private String id;

    @Column(name="Name",nullable = false,length = 255)
    private String name;

    @OneToMany(mappedBy = "agency", cascade = {CascadeType.ALL}, orphanRemoval = true)
    @org.hibernate.annotations.OnDelete(action = OnDeleteAction.CASCADE)
    @OrderBy(value = "ratingType,ratingClass")
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Set<Rating> availableRatings = new HashSet<Rating>();
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Rating> getAvailableRatings() {
        return availableRatings;
    }

    public void setAvailableRatings(Set<Rating> availableRatings) {
        this.availableRatings = availableRatings;
    }
}
