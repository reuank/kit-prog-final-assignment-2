package task.olympia.models;

/**
 * The representation of medals.
 */
public enum Medal {
    /**
     * The gold medal.
     */
    GOLD,

    /**
     * The silver medal.
     */
    SILVER,

    /**
     * The bronze medal.
     */
    BRONZE,

    /**
     * Representation of no medal.
     */
    NONE;

    /**
     * @param gold Gold count.
     * @param silver Silver count.
     * @param bronze Bronze count.
     * @return - returns the corresponding medal.
     */
    public static Medal getMedal(int gold, int silver, int bronze) {
        return gold == 1 ? GOLD
                : silver == 1 ? SILVER
                : bronze == 1 ? BRONZE
                : NONE;
    }
}
