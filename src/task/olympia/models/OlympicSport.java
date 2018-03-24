package task.olympia.models;

import task.constructs.database.Model;

/**
 * The model for an olympic sport.
 */
public class OlympicSport extends Model {
    private String sportType;
    private String sportDiscipline;

    /**
     * Instantiates a new olympic sport.
     * @param sportType the sport type
     * @param sportDiscipline the sport discipline
     */
    public OlympicSport(String sportType, String sportDiscipline) {
        this.sportType = sportType;
        this.sportDiscipline = sportDiscipline;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof OlympicSport
                && ((OlympicSport) other).getSportDiscipline().equals(this.sportDiscipline)
                && ((OlympicSport) other).getSportType().equals(this.sportType);
    }

    /**
     * @return - returns the sportType
     **/
    public String getSportType() {
        return sportType;
    }

    /**
     * Sets the sportType
     *
     * @param sportType - the new sportType
     **/
    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    /**
     * @return - returns the sportDiscipline
     **/
    public String getSportDiscipline() {
        return sportDiscipline;
    }

    /**
     * Sets the sportDiscipline
     *
     * @param sportDiscipline - the new sportDiscipline
     **/
    public void setSportDiscipline(String sportDiscipline) {
        this.sportDiscipline = sportDiscipline;
    }
}