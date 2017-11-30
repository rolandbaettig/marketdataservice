package ch.steponline.portfolio.model;

import ch.steponline.core.model.VersionedEntity;
import ch.steponline.mds.util.SystemDefaultLanguage;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="ASST_AssetProperty",
        uniqueConstraints = {@UniqueConstraint(columnNames={"AssetTradingId","PropertyId"})},
        indexes = {@Index(
                name = "IDX_AssetProperty_AssetTradingId",
                columnList = "AssetTradingId"),
                @Index(
                        name="IDX_AssetProperty_Property",
                        columnList = "PropertyId"),
                @Index(
                        name="IDX_AssetProperty_PropertyList",
                        columnList = "PropertyListId")
        }
)
@Audited
@AuditTable(value = "AssetProperty",schema = "audit")
public class AssetProperty extends VersionedEntity implements Serializable {
    @Id
    @GeneratedValue(generator="asset_seq",strategy = GenerationType.AUTO)
    @SequenceGenerator(name="asset_seq",schema = "portfolio",sequenceName = "Asset_Seq",initialValue = 100000,allocationSize = 1)
    @Column(name = "Id")
    private Long id;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name="AssetTradingId",referencedColumnName = "Id",foreignKey = @ForeignKey(name = "FK_AssetProperty_Asset"))
    @NotNull
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Asset asset;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name="PropertyId",referencedColumnName = "Id",foreignKey = @ForeignKey(name="FK_AssetProperty_PropertyId"))
    @NotNull
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Property property;

    @Column(name = "PropertyValue",length = 255,nullable = true)
    private String propertyValue;

    @ManyToOne(optional=true, fetch = FetchType.LAZY)
    @JoinColumn(name="PropertyListId",referencedColumnName = "Id",foreignKey = @ForeignKey(name="FK_AssetProperty_PropertyListId"))
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private PropertyList listValue;

    @Column(name="ManualEntry",nullable = true)
    private Boolean manualEntry;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "AssetPropertyPropertyListAss", schema = "portfolio",
            joinColumns = {@JoinColumn(name = "AssetPropertyId",foreignKey = @ForeignKey(name = "FK_AssetProperty_PropertyList_AssetPropertyId"))},
            inverseJoinColumns = {@JoinColumn(name = "PropertyListId", foreignKey = @ForeignKey(name="FK_AssetProperty_PropertyList_PropertyListId"))}
    )
    @AuditJoinTable(name = "AssetPropertyPropertyListAss", schema = "audit")
    private Set<PropertyList> multiselectListValues = new HashSet<PropertyList>();

    @Transient
    private String defaultLanguage = SystemDefaultLanguage.getInstance().getLanguage();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        if (this.asset != null && this.asset.getAssetProperties().contains(this)) {
            this.asset.getAssetProperties().remove(this);
        }

        this.asset = asset;

        if (asset != null && !asset.getAssetProperties().contains(this)) {
            asset.getAssetProperties().add(this);
        }
    }

    public String getLabel() {
        return getProperty().getAbbreviation();
    }

    public String getLabel(String language) {
        return getProperty().getAbbreviation(language);
    }

    public String getValueShortText() {
        if (getListValue()!=null) {
            return getListValue().getAbbreviation();
        } else {
            return getPropertyValue();
        }
    }

    public String getValueLongText() {
        if (getListValue()!=null) {
            return getListValue().getDescription();
        } else {
            return getPropertyValue();
        }
    }

    public String getValueShortText(String language) {
        if (getListValue()!=null) {
            return getListValue().getAbbreviation(language);
        } else {
            return getPropertyValue();
        }
    }

    public String getValueLongText(String language) {
        if (getListValue()!=null) {
            return getListValue().getDescription(language);
        } else {
            return getPropertyValue();
        }
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        if (!property.equals(this.property)) {
            this.manualEntry = true;
        }
        this.propertyValue = propertyValue;
    }

    public boolean hasListValueOfDomainNo(Long domainNo) {
        return getListValue()!=null && getListValue().getDomainNo().equals(domainNo);
    }

    public PropertyList getListValue() {
        return listValue;
    }

    public void setListValue(PropertyList listValue) {
        this.listValue = listValue;
    }

    public Boolean getManualEntry() {
        return manualEntry;
    }

    public void setManualEntry(Boolean manualEntry) {
        this.manualEntry = manualEntry;
    }

    public Set<PropertyList> getMultiselectListValues() {
        return multiselectListValues;
    }

    public void setMultiselectListValues(Set<PropertyList> multiselectListValues) {
        this.multiselectListValues = multiselectListValues;
    }



    /***************************
     * Helpers
     **************************/

    /**
     * Zeigt an, ob das Property leer ist oder einen Wert besitzt.
     * @return {@code true}: Das Property ist leer und besitzt keinen Wert oder ist null.<br>
     *         {@code false}: Das Property besitzt einen Wert.
     */
    public boolean isEmpty() {
        if (property == null) {
            return true;
        } else if (property.isListType()) {
            return listValue == null;
        } else if (property.isPropertyValue()) {
            return propertyValue == null || propertyValue.isEmpty();
        }
        return true;
    }


}

