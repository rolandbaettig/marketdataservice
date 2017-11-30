package ch.steponline.core.model;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.io.Serializable;

@Entity
@DiscriminatorValue("EXPOSUREGROUP")
@org.hibernate.annotations.FilterDefs(value={
        @FilterDef(name = "EvalDate",parameters = {@ParamDef(name="evalDate",type="date")})
}
)
@Audited
@org.hibernate.annotations.Proxy(lazy = false)
public class ExposureGroup extends ExposureClass implements Serializable {

    private static final long serialVersionUID = -9102446274064801003L;


}




