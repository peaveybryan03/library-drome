package library.drome.models;

public enum Availability {
    NONE("none"),
    DVD("DVD"),
    BLU_RAY("Blu-ray"),
    BOTH("both");

    private final String name;

    Availability(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Availability findByName(String name) {
        for (Availability availability : Availability.values()) {
            if (availability.getName().equalsIgnoreCase(name)) {
                return availability;
            }
        }

        String message = String.format("No availability of name: %s.", name);
        throw new RuntimeException(message);
    }
}
