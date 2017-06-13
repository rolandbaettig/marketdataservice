package ch.steponline.core.dto;

import ch.steponline.core.model.Domain;
import ch.steponline.core.model.TextEntry;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Roland on 13.06.17.
 */
public class DomainDTO implements Serializable{

        private Long id;

        private String domainRole;

        private Date validFrom = new Date();

        private Date validTo;

        private Long domainNo;

        private Boolean custom = Boolean.TRUE;

        private String isoAlphabetic;

        private String isoNumeric;

        private Double sortNo;

        private List<DomainTextEntryDTO> textEntries = new ArrayList<DomainTextEntryDTO>();

    public DomainDTO() {
    }

    public DomainDTO(Domain domain) {
        this.id=domain.getId();
        this.custom=domain.getCustom();
        this.domainNo=domain.getDomainNo();
        this.domainRole=domain.getDomainRole().getId();
        this.isoAlphabetic=domain.getIsoAlphabetic();
        this.isoNumeric=domain.getIsoNumeric();
        this.sortNo=domain.getSortNo();
        for (TextEntry textEntry:domain.getTextEntries()) {
            textEntries.add(new DomainTextEntryDTO(textEntry));
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

    public List<DomainTextEntryDTO> getTextEntries() {
        return textEntries;
    }

    public void setTextEntries(List<DomainTextEntryDTO> textEntries) {
        this.textEntries = textEntries;
    }
}
