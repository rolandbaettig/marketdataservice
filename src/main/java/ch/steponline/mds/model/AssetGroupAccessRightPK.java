package ch.steponline.mds.model;

import ch.steponline.identity.model.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 10.05.2017
 * Time: 13:37
 */
@Embeddable
public class AssetGroupAccessRightPK implements Serializable{
    @Column(name="AssetGroupId",nullable = false)
    private Long assetGroupId;

    @Column(name="UserId",nullable = false)
    private Long userId;

    @Column(name="AccessRightId",nullable = false)
    private Long accessRightId;

    public Long getAssetGroupId() {
        return assetGroupId;
    }

    public void setAssetGroupId(Long assetGroupId) {
        this.assetGroupId = assetGroupId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccessRightId() {
        return accessRightId;
    }

    public void setAccessRightId(Long accessRightId) {
        this.accessRightId = accessRightId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetGroupAccessRightPK that = (AssetGroupAccessRightPK) o;

        if (assetGroupId != null ? !assetGroupId.equals(that.assetGroupId) : that.assetGroupId != null) return false;
        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return accessRightId != null ? accessRightId.equals(that.accessRightId) : that.accessRightId == null;
    }

    @Override
    public int hashCode() {
        int result = assetGroupId != null ? assetGroupId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (accessRightId != null ? accessRightId.hashCode() : 0);
        return result;
    }
}
