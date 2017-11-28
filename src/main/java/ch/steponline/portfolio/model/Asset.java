package ch.steponline.portfolio.model;

import ch.steponline.core.model.Currency;
import ch.steponline.core.model.Nation;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Asset", schema="portfolio")
@Audited
@AuditTable(value="Asset",schema = "audit")
public class Asset {
    @Id
    @GeneratedValue(generator="asset_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="asset_seq",schema = "portfolio",sequenceName = "Asset_Seq",initialValue = 100000,allocationSize = 1)
    @Column(name = "Id")
    @ApiModelProperty(value="Id of the asset",example="1",position = 1)
    private Long id;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @NotAudited
    private AssetType assetType;

    @NotAudited
    private AssetTypeGroup assetTypeGroup;

    @Column(length = 50,nullable = true,name = "Isin")
    private String isin;

    @Column(length=50, nullable = true,name="ValorNo")
    private String valorNo;

    @Column(name="DefaultCurrency", nullable=false)
    private Currency defaultCurrency;

    @Column(name="RiskCurrency",nullable=false)
    private Currency riskCurrency;

    @Column(name="RiskCountry",nullable = false)
    private Nation riskCountry;

    @Column(name="MinDenomination",nullable=false)
    private Double minDenomination;

    @Column(name="MinTradingSize",nullable=false)
    private Double minTradingSize;

    @Column(name="MarketValue",nullable = false)
    private Double marketValue;

    @Column(name="MaturityDate",nullable = true)
    private LocalDate maturityDate;

    @OneToMany(mappedBy = "asset",fetch = FetchType.LAZY, cascade = CascadeType.ALL,orphanRemoval = true)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @org.hibernate.annotations.BatchSize(size = 200)
    private Set<AssetTextEntry> textEntries = new HashSet<AssetTextEntry>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getValorNo() {
        return valorNo;
    }

    public void setValorNo(String valorNo) {
        this.valorNo = valorNo;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public Currency getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(Currency defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public Double getMinDenomination() {
        return minDenomination;
    }

    public void setMinDenomination(Double minDenomination) {
        this.minDenomination = minDenomination;
    }

    public Double getMinTradingSize() {
        return minTradingSize;
    }

    public void setMinTradingSize(Double minTradingSize) {
        this.minTradingSize = minTradingSize;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public Set<AssetTextEntry> getTextEntries() {
        return textEntries;
    }

    public void setTextEntries(Set<AssetTextEntry> textEntries) {
        this.textEntries = textEntries;
    }
}
