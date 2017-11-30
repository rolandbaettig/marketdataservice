package ch.steponline.portfolio.model;

import ch.steponline.core.model.Currency;
import ch.steponline.core.model.Nation;
import ch.steponline.core.model.ThirdPartySystemKey;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.OptimisticLock;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Asset", schema="portfolio")
@Audited
@AuditTable(value="Asset",schema = "audit")
public class Asset extends ch.steponline.core.model.VersionedEntity{
    @Id
    @GeneratedValue(generator="asset_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="asset_seq",schema = "portfolio",sequenceName = "Asset_Seq",initialValue = 100000,allocationSize = 1)
    @Column(name = "Id")
    @ApiModelProperty(value="Id of the asset",example="1",position = 1)
    private Long id;

    @Column(name="Name",nullable=false,length = 255)
    private String name;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name="AssetTypeId",referencedColumnName = "Id",foreignKey = @ForeignKey(name="FK_Asset_AssetType"))
    @NotAudited
    @OptimisticLock(excluded = true)
    private AssetType assetType;

    @ManyToOne(optional=false,fetch=FetchType.LAZY)
    @JoinColumn(name="AssetTypeGroupId",referencedColumnName = "Id",foreignKey = @ForeignKey(name="FK_Asset_AssetTypeGroup"))
    @NotAudited
    @OptimisticLock(excluded = true)
    private AssetTypeGroup assetTypeGroup;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name="IssuerId", referencedColumnName = "Id",foreignKey = @ForeignKey(name="FK_Asset_Issuer"))
    @OptimisticLock(excluded = true)
    private Issuer issuer;

    @Column(length = 50,nullable = true,name = "Isin")
    private String isin;

    @Column(length=50, nullable = true,name="ValorNo")
    private String valorNo;

    @ManyToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name="TradingCurrencyId",referencedColumnName = "Id",foreignKey = @ForeignKey(name="FK_Asset_TradingCurrency"))
    @OptimisticLock(excluded = true)
    private Currency tradingCurrency;

    @ManyToOne(optional = false, fetch=FetchType.EAGER)
    @JoinColumn(name="RiskCurrencyId", referencedColumnName = "Id")
    @OptimisticLock(excluded = true)
    private Currency riskCurrency;

    @ManyToOne(optional=false,fetch = FetchType.EAGER)
    @JoinColumn(name="RiskCountryId",referencedColumnName = "Id")
    @OptimisticLock(excluded = true)
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

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL},orphanRemoval = true)
    @JoinTable(name = "Asset3PKeyAss",schema = "core",
            joinColumns = {@JoinColumn(name = "AssetId",foreignKey = @ForeignKey(name="FK_Asset3PKeyAss_Domain"))},
            inverseJoinColumns = {@JoinColumn(name = "ThirdPartySystemKeyId",foreignKey = @ForeignKey(name="FK_Asset3PKeyAss_3PKeyAss_KeyId"))}
    )
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    @AuditJoinTable(name = "Asset3PKeyAss", schema = "audit")
    private Set<ThirdPartySystemKey> thirdPartySystemKeys = new HashSet<ThirdPartySystemKey>();

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    @org.hibernate.annotations.BatchSize(size = 50)
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Set<AssetProperty> assetProperties = new HashSet<AssetProperty>();

    @Column(name="PriceUnit", length = 20, nullable = false)
    @NotNull
    @Enumerated(EnumType.STRING)
    private PriceUnit priceUnit;

    @OneToMany(mappedBy = "asset",cascade = {CascadeType.ALL},orphanRemoval = true)
    @org.hibernate.annotations.OnDelete(action = org.hibernate.annotations.OnDeleteAction.CASCADE)
    @org.hibernate.annotations.BatchSize(size=50)
    @org.hibernate.annotations.Filter(name="EvalDate",condition="validFrom<=:evalDate and (validTo is Null or validTo>=:evalDate)")
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    //@OrderBy(value="rating.ratingClass,rating.agency.id")
    private Set<AssetRating> ratings=new HashSet<AssetRating>();


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

    public Currency getTradingCurrency() {
        return tradingCurrency;
    }

    public void setTradingCurrency(Currency tradingCurrency) {
        this.tradingCurrency = tradingCurrency;
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

    public AssetType getAssetType() {
        return assetType;
    }

    public void setAssetType(AssetType assetType) {
        this.assetType = assetType;
    }

    public AssetTypeGroup getAssetTypeGroup() {
        return assetTypeGroup;
    }

    public void setAssetTypeGroup(AssetTypeGroup assetTypeGroup) {
        this.assetTypeGroup = assetTypeGroup;
    }

    public Currency getRiskCurrency() {
        return riskCurrency;
    }

    public void setRiskCurrency(Currency riskCurrency) {
        this.riskCurrency = riskCurrency;
    }

    public Nation getRiskCountry() {
        return riskCountry;
    }

    public void setRiskCountry(Nation riskCountry) {
        this.riskCountry = riskCountry;
    }

    public void setTextEntries(Set<AssetTextEntry> textEntries) {
        this.textEntries = textEntries;
    }

    public Issuer getIssuer() {
        return issuer;
    }

    public void setIssuer(Issuer issuer) {
        this.issuer = issuer;
    }

    public Set<ThirdPartySystemKey> getThirdPartySystemKeys() {
        return thirdPartySystemKeys;
    }

    public void setThirdPartySystemKeys(Set<ThirdPartySystemKey> thirdPartySystemKeys) {
        this.thirdPartySystemKeys = thirdPartySystemKeys;
    }

    public void addThirdPartySystemKey(ThirdPartySystemKey thirdPartySystemKey) {
        if (thirdPartySystemKey == null) throw new IllegalArgumentException("thirdPartySystemKey my not be null!");

        thirdPartySystemKeys.add(thirdPartySystemKey);
    }

    public void removeThirdPartySystemKey(ThirdPartySystemKey thirdPartySystemKey) {
        thirdPartySystemKeys.remove(thirdPartySystemKey);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<AssetProperty> getAssetProperties() {
        return assetProperties;
    }

    public void setAssetProperties(Set<AssetProperty> assetProperties) {
        this.assetProperties = assetProperties;
    }

    public void addAssetProperty(AssetProperty assetProperty) {
        if (assetProperty == null) {
            throw new IllegalArgumentException("assetProperty Object is NULL");
        }

        if (assetProperty.getAsset() == null || !assetProperty.getAsset().equals(this)) {
            assetProperty.setAsset(this);
        }

        if (!this.assetProperties.contains(assetProperty)) {
            this.assetProperties.add(assetProperty);
        }
    }

    public void removeAssetProperty(AssetProperty assetProperty) {
        if (assetProperty == null) {
            throw new IllegalArgumentException("assetProperty Object is NULL");
        }

        if (assetProperty.getAsset() != null && assetProperty.getAsset().equals(this)) {
            this.assetProperties.remove(assetProperty);
            assetProperty.setAsset(null);
        }
    }

    public PriceUnit getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(PriceUnit priceUnit) {
        this.priceUnit = priceUnit;
    }

    public Set<AssetRating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<AssetRating> ratings) {
        this.ratings = ratings;
    }

    public void addRating(AssetRating rating) {
        if (rating == null)
            throw new IllegalArgumentException("rating Object is NULL");

        if (rating.getAsset() == null || !rating.getAsset().equals(this)) {
            rating.setAsset(this);
        }

        if (!this.ratings.contains(rating)) {
            this.ratings.add(rating);
        }
    }

    public void removeRating(AssetRating rating) {
        if (rating == null) throw new IllegalArgumentException("rating Object is NULL");

        if (rating.getAsset() != null && rating.getAsset().equals(this)) {
            this.ratings.remove(rating);
            rating.setAsset(null);
        }
    }
}
