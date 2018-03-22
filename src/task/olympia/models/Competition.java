package task.olympia.models;

import task.constructs.database.Model;

import java.util.Comparator;

public class Competition extends Model {
    private Athlete athlete;
    private int year;
    private IocCode iocCode;
    private SportDiscipline sportDiscipline;
    private int medalCode;

    public Competition(Athlete athlete, int year, IocCode iocCode, SportDiscipline sportDiscipline, int medalCode) {
        this.athlete = athlete;
        this.year = year;
        this.iocCode = iocCode;
        this.sportDiscipline = sportDiscipline;
        this.medalCode = medalCode;
    }

    @Override
    public boolean equals(Object other) {
        return ((Competition) other).athlete.equals(this.athlete)
                && ((Competition) other).getYear() == this.year;
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

    public SportDiscipline getSportDiscipline() {
        return sportDiscipline;
    }

    public void setSportDiscipline(SportDiscipline sportDiscipline) {
        this.sportDiscipline = sportDiscipline;
    }

    public int getMedalCode() {
        return medalCode;
    }

    public void setMedalCode(int medalCode) {
        this.medalCode = medalCode;
    }
}
