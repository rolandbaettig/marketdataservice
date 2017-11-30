package ch.steponline.portfolio.model;

import ch.steponline.core.model.Nation;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Issuer",schema = "portfolio")
@Audited
@AuditTable(value = "Issuer",schema = "audit")
public class Issuer extends ch.steponline.core.model.VersionedEntity{
    @Id
    @GeneratedValue(generator="issuer_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="issuer_seq",schema = "portfolio",sequenceName = "Issuer_Seq",initialValue = 100000,allocationSize = 1)
    @Column(name = "Id")
    @ApiModelProperty(value="Id of the issuer",example="1",position = 1)
    private Long id;

    @Column(name="Name",nullable = false,length=255)
    private String name;

    @Column(name="shortName",nullable = true,length = 50)
    private String shortName;

    @Column(name="RiskCountry",nullable = true)
    private Nation riskCountry;

    @OneToMany(mappedBy = "issuer")
    private Set<Asset> assets=new HashSet<>();

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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Nation getRiskCountry() {
        return riskCountry;
    }

    public void setRiskCountry(Nation riskCountry) {
        this.riskCountry = riskCountry;
    }

    public Set<Asset> getAssets() {
        return assets;
    }

    public void setAssets(Set<Asset> assets) {
        this.assets = assets;
    }
}
