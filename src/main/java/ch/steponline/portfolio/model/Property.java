package ch.steponline.portfolio.model;

import ch.steponline.core.model.Domain;
import ch.steponline.core.model.DomainAttribute;
import ch.steponline.core.model.DomainRelation;
import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("ASSET_PROPERTY")
@Audited
@org.hibernate.annotations.Proxy(lazy = false)
public class Property extends Domain implements Serializable {
    private static final long serialVersionUID = -5908191198252186782L;

    public enum TYPE {
        NACHRANGIG(12080L),COSI(8170L),ACTIV_PASSIV(8000L),BOND_TYPE(8022L),
        APPROVED_MONEY_MARKET_SECURITY(8246L),ASSET_UNIVERS(12990L),EASY_TRADABLE(12988L),BANK_USUAL_ASSET(12986L),
        BANK_OPINION(13249L),KNOWLEDGE_EXPERIENCE(8186L);

        private Long domainNo;

        private TYPE(Long domainNo) {
            this.domainNo=domainNo;
        }

        public Long getDomainNo() {
            return domainNo;
        }
    }

    public enum PropertyType {
        STRING("STRING"),
        LIST("LIST"),
        MULTISELECT_LIST("MULTISELECT_LIST");

        private String type;

        private PropertyType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    @Transient
    private PropertyType propertyType;

    @Transient
    private List<PropertyList> properties;

    @Transient
    private Boolean editable;

    public PropertyType getPropertyType() {
        if (propertyType == null) {
            for (DomainAttribute a : getDomainAttributes()) {
                if (a.getName().equals("PROPERTY_TYPE")) {
                    if (PropertyType.STRING.getType().equals(a.getContent())) {
                        propertyType = PropertyType.STRING;
                    } else if (PropertyType.LIST.getType().equals(a.getContent())) {
                        propertyType = PropertyType.LIST;
                    } else if (PropertyType.MULTISELECT_LIST.getType().equals(a.getContent())) {
                        propertyType = PropertyType.MULTISELECT_LIST;
                    }
                }
            }
        }
        return propertyType;
    }

    public List<PropertyList> getProperties() {
        if (properties == null) {
            properties = new ArrayList<PropertyList>();
            for (DomainRelation rel : this.getFromDomainRelations()) {
                if (rel.getDomainTo() instanceof PropertyList) {
                    properties.add((PropertyList)rel.getDomainTo());
                }
            }
        }
        return properties;
    }

    public boolean isEditable() {
        if (editable==null) {
            editable=Boolean.TRUE;
            for (DomainAttribute att : getDomainAttributes()) {
                if (att.getName().equals("EDITABLE")) {
                    editable=Boolean.valueOf(att.getContent());
                    break;
                }
            }
        }
        return editable;
    }


    public boolean isListType() {
        return PropertyType.LIST.equals(getPropertyType());
    }

    public boolean isMultiselectListType() {
        return PropertyType.MULTISELECT_LIST.equals(getPropertyType());
    }

    public boolean isPropertyValue() {
        return PropertyType.STRING.equals(getPropertyType());
    }

}
