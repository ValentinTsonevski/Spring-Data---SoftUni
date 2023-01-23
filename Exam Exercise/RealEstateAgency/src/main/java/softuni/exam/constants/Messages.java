package softuni.exam.constants;

public enum Messages {
    ;
    public static final String INVALID_TOWN = "invalid town";
    public static final String SUCCESSFULLY_IMPORTED_TOWN_FORMAT = "Successfully imported town %s - %d";

    public static final String INVALID_AGENT = "invalid agent";
    public static final String SUCCESSFULLY_IMPORTED_AGENT_FORMAT = "Successfully imported agent - %s %s";
    public static final String INVALID_APARTMENT = "invalid apartment";
    public static final String SUCCESSFULLY_IMPORTED_APARTMENT_FORMAT = "Successfully imported apartment - %s %.2f";
    public static final String INVALID_OFFER = "invalid offer";
    public static final String SUCCESSFULLY_IMPORTED_OFFER_FORMAT = "Successfully imported apartment %.2f";
    public static final String EXPORT_OFFER_FORMAT = "Agent %s %s with offer №%d:%n" +
            "-Apartment area: %.2f%n" +
            "--Town: %s%n" +
            "---Price: %.2f$";
}
