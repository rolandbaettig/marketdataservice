package ch.steponline.portfolio.model;

import ch.steponline.core.model.Currency;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="ASST_Crossrate")
public class Crossrate implements Serializable {
    @EmbeddedId
    private CrossratePK id;

    @Column(name="FromCurrencyValue")
    private Double fromCurrencyValue;

    @Column(name="ToCurrencyValue")
    private Double toCurrencyValue;

    @Column(name="Crossrate")
    private Double crossrate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FromCurrencyId",referencedColumnName = "Id",insertable = false,updatable = false)
    private Currency currency;

    @Transient
    private Double crossrateManualy;
    private static final long serialVersionUID = -8979973920995483713L;

    public Crossrate() {}

    public Crossrate(Long fromCurrencyId,Long toCurrencyId, Date valueDate) {
        id=new CrossratePK(fromCurrencyId,toCurrencyId,valueDate);
    }

    public CrossratePK getId() {
        return id;
    }

    public void setId(CrossratePK id) {
        this.id = id;
    }

    public Double getFromCurrencyValue() {
        return fromCurrencyValue;
    }

    public void setFromCurrencyValue(Double fromCurrencyValue) {
        this.fromCurrencyValue = fromCurrencyValue;
    }

    public Double getToCurrencyValue() {
        return toCurrencyValue;
    }

    public void setToCurrencyValue(Double toCurrencyValue) {
        this.toCurrencyValue = toCurrencyValue;
    }

    public Double getCrossrate() {
        return crossrate;
    }

    public void setCrossrate(Double crossrate) {
        this.crossrate = crossrate;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Double getCrossrateManualy() {
        return crossrateManualy;
    }

    public void setCrossrateManualy(Double crossrateManualy) {
        this.crossrateManualy = crossrateManualy;
    }
}
