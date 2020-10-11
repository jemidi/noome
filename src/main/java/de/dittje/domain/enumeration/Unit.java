package de.dittje.domain.enumeration;

/**
 * The Unit enumeration.
 */
public enum Unit {
    TSP("tsp"),
    TBSP("tbsp"),
    ML("ml"),
    L("l"),
    MG("mg"),
    G("g"),
    KG("kg"),
    CAN("can"),
    PINCH("pinch"),
    PIECE("piece");

    private final String value;


    Unit(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
