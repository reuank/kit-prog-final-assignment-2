package task.olympia.models;

import task.constructs.database.Model;

import java.util.ArrayList;
import java.util.HashMap;

import static task.olympia.models.Medal.*;

public class Athlete extends Model {
        private int id;
        private String firstname;
        private String lastname;
        private IocCode iocCode;
        private String countryName;
        private ArrayList<OlympicSport> olympicSports;
        private HashMap<OlympicSport, ArrayList<Competition>> activityMap;

    public Athlete(int id, String firstname, String lastname, String countryName, OlympicSport olympicSport) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.countryName = countryName;
        this.olympicSports = new ArrayList<>();
        this.olympicSports.add(olympicSport);
        this.activityMap = new HashMap<>();
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
        return ((Athlete) other).id == this.id;
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

    public void addOlympicSport(OlympicSport olympicSport) {
        this.olympicSports.add(olympicSport);
        this.activityMap.put(olympicSport, new ArrayList<>());
    }

    public void assignCompetition(Competition competition) {
        if (competition.wasWon()) {
            this.activityMap.get(competition.getOlympicSport()).add(competition);
        }
    }

    public boolean practicesOlympicSport(OlympicSport olympicSport) {
        ArrayList<OlympicSport> olympicSports = getOlympicSports();
        return olympicSports.contains(olympicSport);
    }

    public OlympicSport getLatestOlympicSport() {
        ArrayList<OlympicSport> olympicSports = getOlympicSports();
        return olympicSports.get(olympicSports.size() - 1);
    }

    public ArrayList<OlympicSport> getOlympicSports() {
        return olympicSports;
    }
}