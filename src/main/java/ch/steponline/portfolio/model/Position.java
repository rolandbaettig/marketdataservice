package ch.steponline.portfolio.model;

import ch.steponline.core.model.Currency;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Position", schema = "portfolio")
@Audited
@AuditTable(value="Position", schema = "audit")
public class Position {
    @Id
    @GeneratedValue(generator="position_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="position_seq",schema = "portfolio",sequenceName = "Position_Seq",initialValue = 100000,allocationSize = 1)
    @Column(name = "Id")
    @ApiModelProperty(value="Id of the position",example="1",position = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @OptimisticLock(excluded = true)
    private Portfolio portfolio;

    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    private Asset asset;

    @Column(name="Nominal",nullable = false)
    private Double nominal;

    // The following Columns are just redundancies to get the needed Data faster
    @Column(name="FXRate",nullable=false)
    private Double fxRate;

    @Column(name="AssetCurrencyId",nullable = false)
    private Currency assetCurrency;

    @Column(name="Currency",nullable=false)
    private String currency;

    @Column(name="MarketValue",nullable = false)
    private Double marketValue;

    @Column(name="MaturityDate",nullable = true)
    private LocalDate maturityDate;

    @Column(name="Isin",nullable = true)
    private String isin;

    @Column(name="ValorNo",nullable = true)
    private String valorNo;

    @Column(name="AssetName")
    private String assetName;

    @Column(name="AssetTypeGroup")
    private String assetTypeGroup;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
        this.isin=asset.getIsin();
        this.valorNo=asset.getValorNo();
        this.assetTypeGroup=asset.getAssetTypeGroup().getIsoAlphabetic();
        this.marketValue=asset.getMarketValue();
        this.maturityDate=asset.getMaturityDate();
    }

    public Double getNominal() {
        return nominal;
    }

    public void setNominal(Double nominal) {
        this.nominal = nominal;
    }

    public Double getFxRate() {
        return fxRate;
    }

    public void setFxRate(Double fxRate) {
        this.fxRate = fxRate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(Double marketValue) {
        this.marketValue = marketValue;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
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

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetTypeGroup() {
        return assetTypeGroup;
    }

    public void setAssetTypeGroup(String assetTypeGroup) {
        this.assetTypeGroup = assetTypeGroup;
    }

    public void setAssetCurrency(Currency currency) {
        this.assetCurrency=currency;
        setCurrency(currency.getIsoAlphabetic());
    }
}

