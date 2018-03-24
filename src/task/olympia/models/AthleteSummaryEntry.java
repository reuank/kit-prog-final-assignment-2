package task.olympia.models;

/**
 * Used for temporarily storing athlete summary table entries to achieve high database performance.
 */
public class AthleteSummaryEntry {
    private final Athlete athlete;
    private final int medalCount;

    /**
     * Instantiates a single entry of the olympia medal table.
     *
     * @param athlete The corresponding athlete.
     * @param medalCount The corresponding medal count.
     */
    public AthleteSummaryEntry(Athlete athlete, int medalCount) {
        this.athlete = athlete;
        this.medalCount = medalCount;
    }

    /**
     * @return - returns the athlete
     **/
    public Athlete getAthlete() {
        return athlete;
    }

    /**
     * @return - returns the medalCount
     **/
    public int getMedalCount() {
        return medalCount;
    }

    /**
     * @return - returns the athlete id.
     */
    public int getId() {
        return this.athlete.getId();
    }

    /**
     * @return - returns the athletes first name.
     */
    public String getFirstName() {
        return this.athlete.getFirstname();
    }

    /**
     * @return - returns the athletes last name.
     */
    public String getLastName() {
        return this.athlete.getLastname();
    }
}
