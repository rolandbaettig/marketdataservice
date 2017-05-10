package ch.steponline.mds.model;

import ch.steponline.core.model.VersionedEntity;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 10.05.2017
 * Time: 10:45
 */
@Entity
@Table(name="AccessRight",schema = "mds")
@AuditTable(value = "AccessRight",schema = "audit")
public class AccessRight extends VersionedEntity{

    @Id
    @Column(name="Id")
    private Long id;

    @Column(name="Permission",nullable = false,length = 150)
    @NotNull
    @Enumerated(EnumType.STRING)
    private PERMISSION permission;

    @Column(name="SecuredObject",nullable = false,length = 150)
    @NotNull
    @Enumerated(EnumType.STRING)
    private OBJECT securedObject;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PERMISSION getPermission() {
        return permission;
    }

    public void setPermission(PERMISSION permission) {
        this.permission = permission;
    }

    public OBJECT getSecuredObject() {
        return securedObject;
    }

    public void setSecuredObject(OBJECT securedObject) {
        this.securedObject = securedObject;
    }

    public static enum PERMISSION {
        CREATE("CREATE"),
        READ("READ"),
        UPDATE("UPDATE"),
        DELETE("DELETE"),
        ADD_RIGHTS("ADD_RIGHTS"),
        REMOVE_RIGHTS("REMOVE_RIGHTS");

        private String id;

        private PERMISSION(String id) {
            this.id = id;
        }

        public String getId() {
            return this.id;
        }

        public String toString() {
            return this.id;
        }
    }

    public static enum OBJECT {
        ASSETGROUP("ASSET_GROUP");

        private String id;

        private OBJECT(String id) {this.id=id;}

        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return id;
        }
    }
}
