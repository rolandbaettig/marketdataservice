package ch.steponline.core.model;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 15:42
 */
@Entity
@Table(name = "ThirdPartySystemKey",schema="core",indexes = {
        @Index(
                        name = "IDX_CORE_ThirdPartySystemKey_Key1",
                        columnList = "Id, Key1"),
                        @Index(
                                name = "IDX_CORE_ThirdPartySystemKey_Key1ToKey2",
                                columnList = "Id, Key1, Key2"),
                        @Index(
                                name = "IDX_CORE_ThirdPartySystemKey_Key1ToKey3",
                                columnList = "Id, Key1, Key2, Key3"),
                        @Index(
                                name = "IDX_CORE_ThirdPartySystemKey_Type",
                                columnList = "KeyType")
})
@NamedQueries(value = {
        /**
         * Gibt die Zahl identischer ThirdPartySystemKeys der Domains zurück.
         * Identisch heisst in diesem Fall, dass der KeyType und die einzelnen Keys gleich sein müssen.
         * Der Domain mit der Id :id wird nicht berücksichtigt!
         */
        @NamedQuery(name = "CountIdenticalThirdPartySystemKeysOnDomain",
                query = "select count(k) from Domain m join m.thirdPartySystemKeys k " +
                        "where m.id <> :id and k.keyType = :keyType and k.key1 = :key1 " +
                        "and k.key2 = :key2 and k.key3 = :key3 and k.key4 = :key4 and k.key5 = :key5")
})
@Audited
@AuditTable(value = "ThirdPartySystemKey", schema = "audit")
public class ThirdPartySystemKey implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Id")
    private Long id;

    @Column(name = "KeyType")
    @NotNull
    @Size(max = 150)
    private String keyType;

    @Column(name = "Key1")
    @NotNull
    @Size(max = 200)
    private String key1;

    @Column(name = "Key2")
    @Size(max = 200)
    private String key2;

    @Column(name = "Key3")
    @Size(max = 200)
    private String key3;

    @Column(name = "Key4")
    @Size(max = 200)
    private String key4;

    @Column(name = "Key5")
    @Size( max = 200)
    private String key5;

    private static final long serialVersionUID = -3429265150218390099L;

    /**
     * Prüft ob dzwei ThirdPartySystemKeys den gleichen Typ haben.
     * @param o1 Erster ThirdPartySystemKey
     * @param o2 Zweiter ThirdPartySystemKey
     * @return {@code true} Die ThirdPartySystemKeys haben den gleichen Typ<br>
     *         {@code false} Die ThirdPartySystemKeys haben nicht den gleichen Typ.
     */
    public static boolean equalByType(ThirdPartySystemKey o1, ThirdPartySystemKey o2) {
        if (o1 == o2) {
            return true;
        }
        if (o2 == null) {
            return false;
        }
        return areEqual(o1.getKeyType(), o2.getKeyType());
    }

    private static boolean areEqual(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    //****************************************
    // Getters / Setters
    //****************************************

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyType() {
        return keyType;
    }

    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getKey1() {
        return key1;
    }

    public void setKey1(String key1) {
        this.key1 = key1;
    }

    public String getKey2() {
        return key2;
    }

    public void setKey2(String key2) {
        this.key2 = key2;
    }

    public String getKey3() {
        return key3;
    }

    public void setKey3(String key3) {
        this.key3 = key3;
    }

    public String getKey4() {
        return key4;
    }

    public void setKey4(String key4) {
        this.key4 = key4;
    }

    public String getKey5() {
        return key5;
    }

    public void setKey5(String key5) {
        this.key5 = key5;
    }
}

