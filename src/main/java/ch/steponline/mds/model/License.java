package ch.steponline.mds.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 08.05.2017
 * Time: 16:07
 */
@Entity
@Table(name="License",schema = "mds",
        indexes = {@Index(name = "IDX_License_Provider",columnList = "ProviderId")}
)
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Long id;

    @Column(name="Name",nullable = false,length = 250)
    @Size(max = 250)
    private String name;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "ProviderId",referencedColumnName = "Id",foreignKey = @ForeignKey(name = "FK_License_Provider"))
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Provider provider;

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

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        if (this.provider != null
                && !this.provider.equals(provider)
                && this.provider.getLicenses().contains(this)) {
            this.provider.getLicenses().remove(this);
        }

        this.provider = provider;

        if (provider != null && !provider.getLicenses().contains(this)) {
            provider.getLicenses().add(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        License license = (License) o;

        return id != null ? id.equals(license.id) : license.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
