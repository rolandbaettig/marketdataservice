package ch.steponline.portfolio.model;

import ch.steponline.core.model.Domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;


@Entity
@DiscriminatorValue("ASSET_TYPE_GROUP")
@NamedQueries(value = {
        @NamedQuery(name = "ValidAssetTypeGroups",
                query = "select distinct n from AssetTypeGroup n " +
                        "join fetch n.textEntries tc " +
                        "where n.validFrom<=:evalDate and (n.validTo is null or n.validTo>=:evalDate) " +
                        "order by n.sortNo,n.isoAlphabetic"),
})
public class AssetTypeGroup extends Domain implements Serializable {


}
