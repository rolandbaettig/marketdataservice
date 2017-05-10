package ch.steponline.core.model;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 16:55
 */
@Entity
@DiscriminatorValue("CURRENCY")
@NamedQueries(value = {
        @NamedQuery(name = "AllCurrencies", query = "select c from Currency c order by c.sortNo"),
        @NamedQuery(name = "AllValidCurrencies", query = "select c from Currency c " +
                "where c.validFrom <= :validDate and (c.validTo is null or c.validTo >= :validDate) " +
                "order by c.sortNo"),
        @NamedQuery(name = "ValidCurrencyByIsoAlphabetic", query = "select c from Currency c where c.isoAlphabetic=:isoAlphabetic and c.validFrom <= :validDate and (c.validTo is null or c.validTo >= :validDate) " +
                "order by c.sortNo")
})
// Muss bei der Subclass nochmals angegeben werden, da sonst ein Nullpointer erfolgt von Envers
@org.hibernate.annotations.FilterDefs(value={
        @FilterDef(name = "EvalDate",parameters = {@ParamDef(name="evalDate",type="date")})
    }
)
public class Currency extends Domain{

   private static final long serialVersionUID = 1607107941976356395L;

    public String getIsoCode() {
        return getIsoAlphabetic();
    }

}

