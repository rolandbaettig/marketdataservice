package ch.steponline.mds.model;

import ch.steponline.core.model.Domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 10.05.2017
 * Time: 11:26
 */
@Entity
@DiscriminatorValue("MDS_ASSET_GROUP")
public class AssetGroup extends Domain {

    @OneToMany(mappedBy = "assetGroup",orphanRemoval = true)
    private Set<AssetGroupAccessRight> accessRights=new HashSet<>();

    public Set<AssetGroupAccessRight> getAccessRights() {
        return accessRights;
    }

    public void setAccessRights(Set<AssetGroupAccessRight> accessRights) {
        this.accessRights = accessRights;
    }
}
