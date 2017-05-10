package ch.steponline.mds.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 08.05.2017
 * Time: 15:45
 */
@Entity
@Table(name="Property",schema = "mds",
        indexes = {
                @Index(name="IDX_Property_Name",columnList = "Name",unique = true),
                @Index(name="IDX_Property_Provider",columnList = "ProviderId"),
                @Index(name="IDX_Property_License",columnList = "LicenseId")
        }
)
public class Property implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;

    @Column(name="Name",nullable = false,length = 250)
    @NotNull
    @Size(max = 250)
    private String name;

    @Column(name="Description",length = 500)
    @Size(max = 500)
    private String description;

    @Column(name="Definition",length = 8000)
    @Size(max=8000)
    private String definition;

    @Column(name="FieldType",nullable = false,length = 250)
    @NotNull
    @Enumerated(value = EnumType.STRING)
    @Size(max=250)
    private FieldType fieldType;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name="ProviderId", referencedColumnName = "Id", insertable = false,updatable = false,foreignKey = @ForeignKey(name="FK_Property_Provider"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Provider provider;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name="LicenseId", referencedColumnName = "Id", insertable = false,updatable = false,foreignKey = @ForeignKey(name="FK_Property_License"))
    private License license;

    @Column(name="ProviderPropertyId")
    private String providerPropertyId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FieldType getFieldType() {
        return fieldType;
    }

    public void setFieldType(FieldType fieldType) {
        this.fieldType = fieldType;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public License getLicense() {
        return license;
    }

    public void setLicense(License license) {
        this.license = license;
    }

    public String getProviderPropertyId() {
        return providerPropertyId;
    }

    public void setProviderPropertyId(String providerPropertyId) {
        this.providerPropertyId = providerPropertyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Property property = (Property) o;

        return id != null ? id.equals(property.id) : property.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
