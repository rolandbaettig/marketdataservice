package ch.steponline.portfolio.model;

public enum RatingType {
    SHORT_TERM("RatingType.SHORT_TERM"),
    OUTLOOK("RatingType.OUTLOOK"),
    INDIVIDUAL("RatingType.INDIVIDUAL"),
    EFFECTIVE("RatingType.EFFECTIVE"),
    SUPPORT("RatingType.SUPPORT"),
    ISSUER("RatingType.ISSUER"),
    PASSIVE("RatingType.PASSIVE"),
    AVERAGE("RatingType.AVERAGE"),// Wird für Fonds gebraucht um das Durchschnittliche Rating angzugeben
    LOWEST("RatingType.LOWEST");// Wird für Fonds gebraucht um das kleinste Rating anzugeben

    private String propertyKey;

    RatingType(String propertyKey) {
        this.propertyKey=propertyKey;
    }

    public String getPropertyKey() {
        return propertyKey;
    }

    public String getPropertyKeyShort() {
        return propertyKey+".short";
    }
}

