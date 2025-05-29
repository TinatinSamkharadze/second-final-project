package ge.tbc.testautomation.data.enums;

public enum PropertyType {
    ENTIRE_HOMES_AND_APARTMENTS("Entire homes & apartments"),
    HOTELS("Hotels"),
    APARTMENTS("Apartments"),
    VILLAS("Villas"),
    VACATION_HOMES("Vacation Homes"),
    GUESTHOUSES("Guesthouses"),
    HOSTELS("Hostels"),
    RYOKANS("Ryokans"),
    LOVE_HOTELS("Love Hotels"),
    CHALETS("Chalets"),
    CAPSULE_HOTELS("Capsule Hotels");

    private final String label;

    PropertyType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
