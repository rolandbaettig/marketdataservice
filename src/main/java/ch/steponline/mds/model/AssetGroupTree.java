package ch.steponline.mds.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 10.05.2017
 * Time: 10:37
 */
@Entity
@Table(name="AssetGroupTree",schema = "mds",indexes = {@Index(name="IDX_AssetGroup_Parent",columnList = "ParentId"),@Index(name="IDX_AssetGroup_Child",columnList = "ChildId")})
public class AssetGroupTree {

    @EmbeddedId
    private AssetGroupTreePK id;

    public AssetGroupTreePK getId() {
        return id;
    }

    public void setId(AssetGroupTreePK id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetGroupTree that = (AssetGroupTree) o;

        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
