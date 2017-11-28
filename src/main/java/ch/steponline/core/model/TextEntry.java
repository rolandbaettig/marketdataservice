package ch.steponline.core.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Size;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 14:22
 */
@MappedSuperclass
public abstract class TextEntry extends VersionedEntity {
    @EmbeddedId
    private TextEntryPK id;

    @Column(name = "Abbreviation",length = 150)
    @Size(max=150)
    private String abbreviation;

    @Column(name = "Description",length = 500)
    @Size(max=500)
    private String description;

    @Column(name="Documentation",length = 8000)
    @Size(max=8000)
    private String documentation;

    public TextEntry() {
        id = new TextEntryPK();
    }

    public TextEntry(Long targetId, String language) {
        id = new TextEntryPK(targetId, language);
    }

    public TextEntryPK getId() {
        return id;
    }

    public void setId(TextEntryPK id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextEntry textEntry = (TextEntry) o;

        return id != null ? id.equals(textEntry.id) : textEntry.id == null;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
