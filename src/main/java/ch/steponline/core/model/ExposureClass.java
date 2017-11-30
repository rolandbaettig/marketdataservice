package ch.steponline.core.model;

import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@DiscriminatorValue("EXPOSURECLASS")
// Muss bei der Subclass nochmals angegeben werden, da sonst ein Nullpointer erfolgt von Envers
@Audited
@NamedQueries(value = {
        @NamedQuery(name = "ExposureClassesByIsoNumeric", query = "select distinct e from ExposureClass e join fetch e.domainRole join fetch e.textEntries where e.isoNumeric = :isoNumeric"),
        @NamedQuery(name = "ExposureClassesColByIsoNumAndIsoAlpha", query = "select distinct e from ExposureClass e join fetch e.domainRole join fetch e.textEntries where e.isoNumeric = :isoNumeric and e.isoAlphabetic > (e.isoNumeric*10) and e.isoAlphabetic < (e.isoNumeric*10+10)"),
        @NamedQuery(name = "ExposureClassesRowByIsoNumAndIsoAlpha", query = "select distinct e from ExposureClass e join fetch e.domainRole join fetch e.textEntries where e.isoNumeric = :isoNumeric and e not in (select ec from ExposureClass ec where ec.isoNumeric = :isoNumeric and ec.isoAlphabetic > (ec.isoNumeric*10) and ec.isoAlphabetic < (ec.isoNumeric*10+10))")//,
        //@NamedQuery(name = "ExposureClassesByRelationExposureGroupAndDefinition", query = "select distinct e from ExposureClass e join fetch e.textEntries tc join fetch e.toDomainRelations tr join fetch tr.domainFrom g join tr.domainRoleRelationDefinition drd where g.id = :exposureGroupId and drd.definitionGroup = :definitionGroup order by e.sortNo")
})
@org.hibernate.annotations.Proxy(lazy = false)
public class ExposureClass extends Domain implements Serializable {

    private static final long serialVersionUID = -9061736691510692949L;


}
