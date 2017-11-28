package ch.steponline.portfolio.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

@Embeddable
public class CrossratePK implements Serializable {

    @Column(name="FromCurrencyId")
    private Long fromCurrencyId;

    @Column(name="ToCurrencyId")
    private Long toCurrencyId;

    @Column(name="ValueDate")
    private Date valueDate;

    public CrossratePK(){}

    public CrossratePK(Long fromCurrencyId,Long toCurrencyId, Date valueDate) {
        this.fromCurrencyId=fromCurrencyId;
        this.toCurrencyId=toCurrencyId;
        this.valueDate=valueDate;
    }

    public Long getFromCurrencyId() {
        return fromCurrencyId;
    }

    public void setFromCurrencyId(Long fromCurrencyId) {
        this.fromCurrencyId = fromCurrencyId;
    }

    public Long getToCurrencyId() {
        return toCurrencyId;
    }

    public void setToCurrencyId(Long toCurrencyId) {
        this.toCurrencyId = toCurrencyId;
    }

    public Date getValueDate() {
        return valueDate;
    }

    public void setValueDate(Date valueDate) {
        this.valueDate = valueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CrossratePK that = (CrossratePK) o;

        if (fromCurrencyId != null ? !fromCurrencyId.equals(that.fromCurrencyId) : that.fromCurrencyId != null)
            return false;
        if (toCurrencyId != null ? !toCurrencyId.equals(that.toCurrencyId) : that.toCurrencyId != null) return false;
        return valueDate != null ? valueDate.equals(that.valueDate) : that.valueDate == null;
    }

    @Override
    public int hashCode() {
        int result = fromCurrencyId != null ? fromCurrencyId.hashCode() : 0;
        result = 31 * result + (toCurrencyId != null ? toCurrencyId.hashCode() : 0);
        result = 31 * result + (valueDate != null ? valueDate.hashCode() : 0);
        return result;
    }
}

