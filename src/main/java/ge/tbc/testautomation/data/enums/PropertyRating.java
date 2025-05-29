package ge.tbc.testautomation.data.enums;

public enum PropertyRating {
    ONE_STAR("1 star"),
    TWO_STARS("2 stars"),
    THREE_STARS("3 stars"),
    FOUR_STARS("4 stars"),
    FIVE_STARS("5 stars");

    private final String label;

    PropertyRating(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
