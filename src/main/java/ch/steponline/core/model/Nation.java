package ch.steponline.core.model;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 16:57
 */
@Entity
@DiscriminatorValue("NATION")
@NamedQueries(value = {
        @NamedQuery(name = "ValidNations",
                query = "select distinct n from Nation n " +
                        "join fetch n.textEntries tc " +
                        "where n.validFrom<=:evalDate and (n.validTo is null or n.validTo>=:evalDate) " +
                        "order by n.sortNo,n.isoAlphabetic"),
        @NamedQuery(name = "NationByIsoCode",
                query = "select n from Nation n " +
                        "join fetch n.textEntries tc " +
                        "where n.isoAlphabetic=:isoCode " +
                        "and n.validFrom<=:evalDate and (n.validTo is null or n.validTo>=:evalDate) " +
                        "order by n.sortNo,n.isoAlphabetic")
})
@Audited
@AuditTable(value = "Domain", schema = "audit")
public class Nation extends TerritorialDomain implements Serializable {

    private static final long serialVersionUID = 8061441391773670408L;

    public List<Currency> getCurrencies(boolean onlyValid) {
        List<Currency> currencies=new ArrayList<Currency>();
        for (DomainRelation dr : this.getFromDomainRelations()) {
            if (dr.getDomainRoleRelationDefinition().isNationToCurrencyRelation()) {
                if (!onlyValid || dr.getValidTo() == null) {
                    currencies.add((Currency) dr.getDomainTo());
                }
            }
        }
        return currencies;
    }


}
