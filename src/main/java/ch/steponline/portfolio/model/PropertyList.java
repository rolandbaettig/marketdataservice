package ch.steponline.portfolio.model;

import ch.steponline.core.model.Domain;
import ch.steponline.core.model.DomainAttribute;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("PROPERTY_LIST")
@Audited
@NamedQueries(value = {
        @NamedQuery(name = "PropertyListByPropertyId", query = "select distinct p from PropertyList p join p.toDomainRelations dr join dr.domainFrom pr join fetch p.textEntries where pr.id = :propertyId order by p.sortNo")
})
@org.hibernate.annotations.Proxy(lazy = false)
public class PropertyList extends Domain implements Serializable {

    public enum TYPE {
        YES(7910L),NO(7911L),CORPORATE(8023L),GOVERMENT(8021L),COLLATERAL(8020L),IN_SCOPE(12992L),NOT_IN_SCOPE(12994L),NOT_IN_SCOPE_WITHOUT_WAVER(12996L),
        NO_BANKOPINION(13251L),NEGATIV_BANKOPION(13253L),NO_NEGATIV_BANKOPINION(13255L);

        private Long domainNo;

        private TYPE(Long domainNo) {
            this.domainNo=domainNo;
        }

        public Long getDomainNo() {
            return domainNo;
        }
    }

    private static final long serialVersionUID = -4278417408915829690L;

    public Boolean getTrueFalse() {
        Integer value=0;
        try {
            value=Integer.valueOf(getIsoNumeric());
        } catch (NumberFormatException e) {
            return null;
        }
        return value==1;
    }


}
