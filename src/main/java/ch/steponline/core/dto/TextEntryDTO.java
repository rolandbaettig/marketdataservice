package ch.steponline.core.dto;

import ch.steponline.core.model.DomainTextEntry;
import ch.steponline.core.model.TextEntry;
import com.fasterxml.jackson.annotation.JsonFilter;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Roland on 13.06.17.
 */
@JsonFilter("TextEntryFilter")
public class TextEntryDTO implements Serializable{
    public enum POSSIBLE_PROPERTIES{language,appreviation,description,documentation}

    private String language;

    private String abbreviation;

    private String description;

    private String documentation;

    public static String[] getPossiblePropertiesAsString() {
        return getEnumNames(POSSIBLE_PROPERTIES.class);
    }

    private static String[] getEnumNames(Class<? extends Enum<?>> e) {
        return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
    }
    public TextEntryDTO() {
    }

    public TextEntryDTO(TextEntry textEntry) {
        this.language=textEntry.getId().getLanguage();
        this.abbreviation=textEntry.getAbbreviation();
        this.description=textEntry.getDescription();
        this.documentation=textEntry.getDocumentation();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
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
}
