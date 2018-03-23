package task.olympia.models;

public class AthleteSummaryEntry {
    private final Athlete athlete;
    private final int medalCount;

    public AthleteSummaryEntry(Athlete athlete, int medalCount) {
        this.athlete = athlete;
        this.medalCount = medalCount;
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public int getMedalCount() {
        return medalCount;
    }

    public int getId() {
        return this.athlete.getId();
    }

    public String getFirstName() {
        return this.athlete.getFirstname();
    }

    public String getLastName() {
        return this.athlete.getLastname();
    }
}
