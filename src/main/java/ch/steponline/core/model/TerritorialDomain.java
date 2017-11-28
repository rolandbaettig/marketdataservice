package ch.steponline.core.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 16:57
 */
@Entity
@DiscriminatorValue("TERRITORIAL")
@NamedQueries(value = {
        @NamedQuery(name = "GetNationOfRegion",
                query = "select nation from Domain nation " +
                        "join nation.toDomainRelations domRel " +
                        "join domRel.domainRoleRelationDefinition roleDef " +
                        "join roleDef.fromDomainRole territorial " +
                        "join roleDef.toDomainRole nationRole " +
                        "where domRel.domainFrom.id = :territorialId and territorial.id = 'TERRITORIAL' and nationRole.id = 'NATION'")
})
public class TerritorialDomain extends Domain {

    private static final long serialVersionUID = 3843445586983303871L;

    public List<Nation> getNations() {
        List<Nation> nations=new ArrayList<Nation>();
        for (DomainRelation dr : this.getFromDomainRelations()) {
            if (dr.getDomainRoleRelationDefinition().isTerritorialToNationRelation()) {
                nations.add((Nation)dr.getDomainTo());
            }
        }
        return nations;
    }
}

