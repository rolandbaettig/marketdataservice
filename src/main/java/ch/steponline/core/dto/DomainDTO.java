package ch.steponline.core.dto;

import ch.steponline.core.model.Domain;
import ch.steponline.core.model.TextEntry;
import com.fasterxml.jackson.annotation.JsonFilter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Roland on 13.06.17.
 */
@JsonFilter("DomainFilter")
@ApiModel(value = "DomainDto")
public class DomainDTO implements Serializable{
        public enum POSSIBLE_PROPERTIES{
            id,domainRole,validFrom,validTo,domainNo,custom,isoAlphabetic,isoNumeric,sortNo,textEntries;
        }

        public enum POSSIBLE_CHILDS {
            TextEntry
        }
        @ApiModelProperty(value="Id of the domain",example="1",position = 1)
        private Long id;

        @ApiModelProperty(value="Role of the domain",example="CURRENCY",allowableValues = "CURRENCY,NATION,TERRITORIAL",allowEmptyValue = false,position = 2)
        private String domainRole;

        @ApiModelProperty(value="At which date the DomainDto is valid",example = "2015-09-18",allowEmptyValue = false,position = 3)
        private Date validFrom;

        @ApiModelProperty(value="At which date the DomainDto is invalid",example = "2017-10-13",allowEmptyValue = true,position = 4)
        private Date validTo;

        @ApiModelProperty(value="Unique number for a DomainDto with in a domainRole",position = 5)
        private Long domainNo;

        private Boolean custom = Boolean.TRUE;

        private String isoAlphabetic;

        private String isoNumeric;

        private Double sortNo;

        private List<TextEntryDTO> textEntries = new ArrayList<TextEntryDTO>();

        public static String[] getPossiblePropertiesAsString() {
            return getEnumNames(POSSIBLE_PROPERTIES.class);
        }

        private static String[] getEnumNames(Class<? extends Enum<?>> e) {
            return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
        }

    public DomainDTO() {
    }

    public DomainDTO(Domain domain) {
        this.id=domain.getId();
        this.validFrom=domain.getValidFrom();
        this.validTo=domain.getValidTo();
        this.custom=domain.getCustom();
        this.domainNo=domain.getDomainNo();
        this.domainRole=domain.getDomainRole().getId();
        this.isoAlphabetic=domain.getIsoAlphabetic();
        this.isoNumeric=domain.getIsoNumeric();
        this.sortNo=domain.getSortNo();
        for (TextEntry textEntry:domain.getTextEntries()) {
            textEntries.add(new TextEntryDTO(textEntry));
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDomainRole() {
        return domainRole;
    }

    public void setDomainRole(String domainRole) {
        this.domainRole = domainRole;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Long getDomainNo() {
        return domainNo;
    }

    public void setDomainNo(Long domainNo) {
        this.domainNo = domainNo;
    }

    public Boolean getCustom() {
        return custom;
    }

    public void setCustom(Boolean custom) {
        this.custom = custom;
    }

    public String getIsoAlphabetic() {
        return isoAlphabetic;
    }

    public void setIsoAlphabetic(String isoAlphabetic) {
        this.isoAlphabetic = isoAlphabetic;
    }

    public String getIsoNumeric() {
        return isoNumeric;
    }

    public void setIsoNumeric(String isoNumeric) {
        this.isoNumeric = isoNumeric;
    }

    public Double getSortNo() {
        return sortNo;
    }

    public void setSortNo(Double sortNo) {
        this.sortNo = sortNo;
    }

    public List<TextEntryDTO> getTextEntries() {
        return textEntries;
    }

    public void setTextEntries(List<TextEntryDTO> textEntries) {
        this.textEntries = textEntries;
    }
}
