package ch.steponline.core.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 16:26
 */
@MappedSuperclass
public abstract class VersionedEntity implements Serializable{
    @Version
    @Column(name = "Version")
    @NotNull
    private int version = 0;

    public int getVersion() {
        return version;
    }

    @SuppressWarnings("unused")
    private void setVersion(int version) {
        this.version = version;
    }
}
