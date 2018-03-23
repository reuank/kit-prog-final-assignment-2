package task.olympia.models;

import task.constructs.database.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Athlete extends Model {
    private int id;
    private String firstname;
    private String lastname;
    private IocCode iocCode;
    private String countryName;
    private ArrayList<OlympicSport> olympicSports;

    public Athlete(int id, String firstname, String lastname, String countryName, OlympicSport olympicSport) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.countryName = countryName;
        this.olympicSports = new ArrayList<>();
        this.assignOlympicSport(olympicSport);
    }

    public Athlete(int id) {
        this.id = id;
        this.firstname = "";
        this.lastname = "";
        this.countryName = "";
        this.olympicSports = new ArrayList<>();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Athlete
                && ((Athlete) other).id == this.id
                && ((Athlete) other).getIocCode().equals(this.iocCode)
                && ((Athlete) other).getFirstname().equals(this.firstname)
                && ((Athlete) other).getLastname().equals(this.lastname);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public IocCode getIocCode() {
        return iocCode;
    }

    public void setIocCode(IocCode iocCode) {
        this.iocCode = iocCode;
        this.countryName = iocCode.getCountryName();
    }

    public String getCountryName() {
        return countryName;
    }

    public OlympicSport getLatestOlympicSport() {
        return this.olympicSports.get(olympicSports.size() - 1);
    }

    public void assignOlympicSport(OlympicSport olympicSport) {
        this.olympicSports.add(olympicSport);
    }

    public boolean practicesOlympicSport(OlympicSport olympicSport) {
        return this.olympicSports.contains(olympicSport);
    }
}