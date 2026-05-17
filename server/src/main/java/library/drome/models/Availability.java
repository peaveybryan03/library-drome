package library.drome.models;

public enum Availability {
    NONE("none",  "none"),
    DVD("DVD", "dvd"),
    BLU_RAY("Blu-ray", "blu-ray"),
    BOTH("both", "both");

    private final String name;
    private final String databaseName;

    Availability(String name, String databaseName) {
        this.name = name;
        this.databaseName = databaseName;
    }

    public String getName() {
        return name;
    }

    public String getDatabaseName() {
        return databaseName;
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
