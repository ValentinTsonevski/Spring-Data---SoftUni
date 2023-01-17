package softuni.exam.constants;

public enum Messages {
    ;
    public static String INVALID_PART = "Invalid part";
    public static String SUCCESSFULLY_IMPORTED_PART_FORMAT = "Successfully imported part %s - %.2f";
    public static String INVALID_MECHANIC = "Invalid mechanic";
    public static String SUCCESSFULLY_IMPORTED_MECHANIC_FORMAT = "Successfully imported mechanic %s %s";
    public static String INVALID_CAR = "Invalid car";
    public static String SUCCESSFULLY_IMPORTED_CAR_FORMAT = "Successfully imported car %s - %s";
    public static String INVALID_TASK = "Invalid task";
    public static String SUCCESSFULLY_IMPORTED_TASK_FORMAT = "Successfully imported task %.2f";
    public static String TASK_EXPORT_FORMAT = "Car %s %s with %dkm%n" +
            "-Mechanic: %s %s - task №%s:%n" +
            "--Engine %.1f%n" +
            "---Price: %.2f$";
}
