package task.olympia.models;

import task.constructs.database.Model;

import java.util.ArrayList;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Athlete extends Model {
    private int id;
    private String firstname;
    private String lastname;
    private IocCode iocCode;
    private String countryName;
    private ArrayList<OlympicSport> olympicSports;
    private NavigableMap<OlympicSport, ArrayList<Competition>> competitionsMap;

    public Athlete(int id, String firstname, String lastname, String countryName, OlympicSport olympicSport) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.countryName = countryName;
        this.olympicSports = new ArrayList<>();
        this.competitionsMap = new TreeMap<>();
        this.assignOlympicSport(olympicSport);
    }

    public Athlete(int id) {
        this.id = id;
        this.firstname = "";
        this.lastname = "";
        this.countryName = "";
        this.competitionsMap = new TreeMap<>();
    }

    @Override
    public boolean equals(Object other) {
        return ((Athlete) other).id == this.id
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
        this.competitionsMap.put(olympicSport, new ArrayList<>());
    }

    public void assignCompetition(Competition competition) {
        if (competition.wasWon() && this.practicesOlympicSport(competition.getOlympicSport())) {
            this.competitionsMap.get(competition.getOlympicSport()).add(competition);
        }
    }

    public int getMedalCount(OlympicSport olympicSport) {
        if (!practicesOlympicSport(olympicSport)) return 0;

        if (this.competitionsMap.get(olympicSport).isEmpty()) return 0;

        return this.competitionsMap.get(olympicSport).size();
    }

    public int getTotalMedalCount() {
        int sum = 0;
        for (int i = 0; i < olympicSports.size(); i++) {
            sum += getMedalCount(olympicSports.get(i));
        }
        return sum;
        //return olympicSports.stream().mapToInt(this::getMedalCount).sum();
    }

    public boolean practicesOlympicSport(OlympicSport olympicSport) {
        return this.olympicSports.contains(olympicSport);
    }
}