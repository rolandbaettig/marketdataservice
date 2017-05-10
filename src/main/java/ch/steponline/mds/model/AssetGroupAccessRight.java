package ch.steponline.mds.model;

import ch.steponline.core.model.VersionedEntity;
import ch.steponline.identity.model.User;

import javax.persistence.*;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 10.05.2017
 * Time: 11:17
 */
@Entity
@Table(name="AssetGroupAccessRight",schema = "mds")
public class AssetGroupAccessRight extends VersionedEntity{

    @EmbeddedId
    private AssetGroupAccessRightPK id;

    @ManyToOne
    @JoinColumn(name="AssetGroupId",referencedColumnName ="Id",foreignKey = @ForeignKey(name="FX_AssetGroupAccessRight_AssetGroup"),insertable = false,updatable = false)
    private AssetGroup assetGroup;

    @ManyToOne
    @JoinColumn(name="UserId",referencedColumnName ="Id",foreignKey = @ForeignKey(name="FX_AssetGroupAccessRight_User"),insertable = false,updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name="AccessRightId",referencedColumnName ="Id",foreignKey = @ForeignKey(name="FX_AssetGroupAccessRight_AccessRight"),insertable = false,updatable = false)
    private AccessRight accessRight;

    public AssetGroup getAssetGroup() {
        return assetGroup;
    }

    public void setAssetGroup(AssetGroup assetGroup) {
        this.assetGroup = assetGroup;
        if (assetGroup!=null) {
            getId().setAssetGroupId(assetGroup.getId());
        } else {
            getId().setAssetGroupId(null);
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        if (user!=null) {
            getId().setUserId(user.getId());
        } else {
            getId().setUserId(null);
        }
    }

    public AccessRight getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRight accessRight) {
        this.accessRight = accessRight;
        if (accessRight!=null) {
            getId().setAccessRightId(accessRight.getId());
        } else {
            getId().setAccessRightId(null);
        }
    }

    public AssetGroupAccessRightPK getId() {
        if (id==null) {
            id=new AssetGroupAccessRightPK();
        }
        return id;
    }

    public void setId(AssetGroupAccessRightPK id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetGroupAccessRight that = (AssetGroupAccessRight) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
