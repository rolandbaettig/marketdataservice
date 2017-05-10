package ch.steponline.core.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Baettig
 * Date: 09.05.2017
 * Time: 14:28
 */
@Embeddable
public class TextEntryPK implements Serializable{
    public TextEntryPK() {
    }

    public TextEntryPK(Long sourceId, String language) {
        this.sourceId = sourceId;
        this.language = language;
    }

    @Column(name="SourceId",nullable=false)
    private Long sourceId;

    @Column(name="Language",nullable = false)
    private String language;

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TextEntryPK that = (TextEntryPK) o;

        if (sourceId != null ? !sourceId.equals(that.sourceId) : that.sourceId != null) return false;
        return language != null ? language.equals(that.language) : that.language == null;
    }

    @Override
    public int hashCode() {
        int result = sourceId != null ? sourceId.hashCode() : 0;
        result = 31 * result + (language != null ? language.hashCode() : 0);
        return result;
    }
}
