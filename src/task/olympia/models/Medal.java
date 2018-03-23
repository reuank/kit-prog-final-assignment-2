package task.olympia.models;

/**
 * The representation of a medal
 */
public enum Medal {
    GOLD,
    SILVER,
    BRONZE,
    NONE;

    public static Medal getMedal(int gold, int silver, int bronze) {
        return gold == 1 ? GOLD
                : silver == 1 ? SILVER
                : bronze == 1 ? BRONZE
                : NONE;
    }
}
