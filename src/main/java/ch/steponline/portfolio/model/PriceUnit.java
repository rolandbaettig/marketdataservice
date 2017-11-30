package ch.steponline.portfolio.model;

public enum PriceUnit {
    NORMAL(1.0),PERCENT(100.0),PROMILLE(1000.0);

    private Double factor;
    PriceUnit(Double factor) {
        this.factor=factor;
    }
}
