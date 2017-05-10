package ch.steponline.mds.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 10.05.2017
 * Time: 10:39
 */
@Embeddable
public class AssetGroupTreePK implements Serializable{
    @ManyToOne
    @JoinColumn(name="ParentId",referencedColumnName ="Id",foreignKey = @ForeignKey(name="FX_AssetGroup_Parent"))
    private AssetGroup parent;

    @ManyToOne
    @JoinColumn(name="ChildId",referencedColumnName = "Id",foreignKey = @ForeignKey(name = "FK_AssetGroup_Child"))
    private AssetGroup child;

    public AssetGroup getParent() {
        return parent;
    }

    public void setParent(AssetGroup parent) {
        this.parent = parent;
    }

    public AssetGroup getChild() {
        return child;
    }

    public void setChild(AssetGroup child) {
        this.child = child;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AssetGroupTreePK that = (AssetGroupTreePK) o;

        if (parent != null ? !parent.equals(that.parent) : that.parent != null) return false;
        return child != null ? child.equals(that.child) : that.child == null;
    }

    @Override
    public int hashCode() {
        int result = parent != null ? parent.hashCode() : 0;
        result = 31 * result + (child != null ? child.hashCode() : 0);
        return result;
    }
}
