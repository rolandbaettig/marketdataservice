package ch.steponline.mds.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 08.05.2017
 * Time: 13:40
 */
@Entity(name = "Provider")
@Table(schema="mds",name="Provider",indexes = {@Index(name="IDX_Provider_Name",columnList = "Name")})
public class Provider implements Serializable{

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

    @OneToMany(mappedBy = "provider",fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<License> licenses = new HashSet<>();

    @OneToMany(mappedBy = "provider",fetch=FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Property> properties;


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

    public Set<License> getLicenses() {
        return licenses;
    }

    public void setLicenses(Set<License> licenses) {
        this.licenses = licenses;
    }

    public void addLicense(License license) {
        if (license==null) throw new IllegalArgumentException("license must be delivered");

        if (license.getProvider() == null || !license.getProvider().equals(this)) {
            license.setProvider(this);
        }

        if (!this.licenses.contains(license)) {
            this.licenses.add(license);
        }
    }


    public boolean removeLicense(License license) {
        if (license == null) {
            throw new IllegalArgumentException("license must be delivered");
        }

        if (license.getProvider() != null && license.getProvider().equals(this)) {
            this.licenses.remove(license);
            license.setProvider(null);
            return true;
        }
        return false;
    }

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        this.properties = properties;
    }

    public void addProperty(Property property) {
        if (property==null) throw new IllegalArgumentException("license must be delivered");

        if (property.getProvider() == null || !property.getProvider().equals(this)) {
            property.setProvider(this);
        }

        if (!this.properties.contains(property)) {
            this.properties.add(property);
        }
    }


    public boolean removeProperty(Property property) {
        if (property == null) {
            throw new IllegalArgumentException("license must be delivered");
        }

        if (property.getProvider() != null && property.getProvider().equals(this)) {
            this.properties.remove(property);
            property.setProvider(null);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Provider provider = (Provider) o;

        return id != null ? id.equals(provider.id) : provider.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
