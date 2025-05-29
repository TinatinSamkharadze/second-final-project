package ge.tbc.testautomation.data.enums;

public enum Meals {
    BREAKFAST_INCLUDED("Breakfast included"),
    KITCHEN_FACILITIES("Kitchen facilities"),
    ALL_MEALS_INCLUDED("All meals included");

    private final String label;

    Meals(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
