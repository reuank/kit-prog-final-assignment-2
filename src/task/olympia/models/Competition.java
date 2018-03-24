package task.olympia.models;

import task.constructs.database.Model;

/**
 * The model for a competition
 */
public class Competition extends Model {
    private Athlete athlete;
    private int year;
    private IocCode iocCode;
    private OlympicSport olympicSport;
    private Medal medal;

    /**
     * Instantiates a new Competition
     * @param athlete the athlete
     * @param year the year
     * @param iocCode the ioc code
     * @param olympicSport the olympic sport
     * @param medal the medal
     */
    public Competition(Athlete athlete, int year, IocCode iocCode, OlympicSport olympicSport, Medal medal) {
        this.athlete = athlete;
        this.year = year;
        this.iocCode = iocCode;
        this.olympicSport = olympicSport;
        this.medal = medal;
    }

    @Override
    public boolean equals(Object other) {
        return ((Competition) other).athlete.equals(this.athlete)
                && ((Competition) other).getYear() == this.year
                && ((Competition) other).getOlympicSport().equals(this.olympicSport);
    }

    /**
     * @return - returns the athlete
     **/
    public Athlete getAthlete() {
        return athlete;
    }

    /**
     * Sets the athlete
     *
     * @param athlete - the new athlete
     **/
    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    /**
     * @return - returns the year
     **/
    public int getYear() {
        return year;
    }

    /**
     * Sets the year
     *
     * @param year - the new year
     **/
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * @return - returns the iocCode
     **/
    public IocCode getIocCode() {
        return iocCode;
    }

    /**
     * Sets the iocCode
     *
     * @param iocCode - the new iocCode
     **/
    public void setIocCode(IocCode iocCode) {
        this.iocCode = iocCode;
    }

    /**
     * @return - returns the olympicSport
     **/
    public OlympicSport getOlympicSport() {
        return olympicSport;
    }

    /**
     * Sets the olympicSport
     *
     * @param olympicSport - the new olympicSport
     **/
    public void setOlympicSport(OlympicSport olympicSport) {
        this.olympicSport = olympicSport;
    }

    /**
     * @return returns 1
     */
    public int getMedalValue() {
        return this.wasWon() ? 1 : 0;
    }

    /**
     * @return returns the medal assigned to this competition
     */
    public Medal getMedal() {
        return medal;
    }

    /**
     * Sets the medal.
     *
     * @param medal the new medal.
     */
    public void setMedal(Medal medal) {
        this.medal = medal;
    }

    /**
     * @return returns the id of the connected athlete.
     */
    public int getAthleteId() {
        return getAthlete().getId();
    }

    private boolean wasWon() {
        return this.medal != Medal.NONE;
    }
}
