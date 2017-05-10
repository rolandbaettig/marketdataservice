package ch.steponline.core.model;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 16:09
 */
@Entity
@Table(name="EntityRevision",schema = "core")
@RevisionEntity(EntityRevisionListener.class)
public class EntityRevison extends DefaultRevisionEntity {

    @Column(name = "UserName")
    @Size(max = 150)
    private String username;

    @Column(name = "RevisionDate")
    @Temporal(TemporalType.TIMESTAMP)
    private Date revisionDate = new Date();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getRevisionDate() {
        return revisionDate;
    }

    public void setRevisionDate(Date revisionDate) {
        this.revisionDate = revisionDate;
    }
}
