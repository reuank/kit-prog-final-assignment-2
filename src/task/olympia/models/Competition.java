package task.olympia.models;

import task.constructs.database.Model;

public class Competition extends Model {
    private Athlete athlete;
    private int year;
    private IocCode iocCode;
    private OlympicSport olympicSport;
    private Medal medal;

    public Competition(Athlete athlete, int year, IocCode iocCode, OlympicSport olympicSport, Medal medal) {
        this.athlete = athlete;
        this.year = year;
        this.iocCode = iocCode;
        this.olympicSport = olympicSport;
        this.medal = medal;
    }

    public Competition(Athlete athlete, int year, IocCode ioc, OlympicSport sport, int gold, int silver, int bronze) {
        this.athlete = athlete;
        this.year = year;
        this.iocCode = ioc;
        this.olympicSport = sport;
        this.medal = Medal.getMedal(gold, silver, bronze);
    }

    @Override
    public boolean equals(Object other) {
        return ((Competition) other).athlete.equals(this.athlete)
                && ((Competition) other).getYear() == this.year
                && ((Competition) other).getOlympicSport().equals(this.olympicSport);
    }

    public Athlete getAthlete() {
        return athlete;
    }

    public void setAthlete(Athlete athlete) {
        this.athlete = athlete;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public IocCode getIocCode() {
        return iocCode;
    }

    public void setIocCode(IocCode iocCode) {
        this.iocCode = iocCode;
    }

    public OlympicSport getSportDiscipline() {
        return olympicSport;
    }

    public void setSportDiscipline(OlympicSport sportDiscipline) {
        this.olympicSport = sportDiscipline;
    }

    public OlympicSport getOlympicSport() {
        return olympicSport;
    }

    public void setOlympicSport(OlympicSport olympicSport) {
        this.olympicSport = olympicSport;
    }

    public boolean wasWon() {
        return this.medal != Medal.NONE;
    }

    public int getMedalValue() {
        return this.wasWon() ? 1 : 0;
    }

    public Medal getMedal() {
        return medal;
    }

    public void setMedal(Medal medal) {
        this.medal = medal;
    }

    public int getAthleteId() {
        return getAthlete().getId();
    }
}
