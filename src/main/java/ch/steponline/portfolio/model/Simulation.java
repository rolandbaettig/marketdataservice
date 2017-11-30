package ch.steponline.portfolio.model;

import ch.steponline.core.model.VersionedEntity;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="Simulation",schema = "portfolio")
@Audited
@AuditTable(value="Simulation",schema = "audit")
public class Simulation extends VersionedEntity{
    @Id
    @GeneratedValue(generator="simulation_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="simulation_seq",schema = "portfolio",sequenceName = "Simulation_Seq",initialValue = 100000,allocationSize = 1)
    @Column(name = "Id")
    @ApiModelProperty(value="Id of the portfolio",example="1",position = 1)
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name="PortfolioId", referencedColumnName = "Id")
    @OptimisticLock(excluded = true)
    private Portfolio referencedPortfolio;

    @Column(name="Name",nullable = false,length = 255)
    private String name;

    @Column(name="lastUpdated",nullable = false)
    private LocalDate lastUpdated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Portfolio getReferencedPortfolio() {
        return referencedPortfolio;
    }

    public void setReferencedPortfolio(Portfolio referencedPortfolio) {
        this.referencedPortfolio = referencedPortfolio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
