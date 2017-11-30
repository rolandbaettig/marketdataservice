package ch.steponline.portfolio.model;

import ch.steponline.core.model.Domain;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;


/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 16:57
 */
@Entity
@DiscriminatorValue("PORTFOLIO_RISKCATEGORY")
@NamedQueries(value = {
        @NamedQuery(name = "ValidRiskCategories",
                query = "select distinct n from RiskCategory n " +
                        "join fetch n.textEntries tc " +
                        "where n.validFrom<=:evalDate and (n.validTo is null or n.validTo>=:evalDate) " +
                        "order by n.sortNo,n.isoAlphabetic"),
})
@Audited
@AuditTable(value = "Domain", schema = "audit")
public class RiskCategory extends Domain implements Serializable {


}
