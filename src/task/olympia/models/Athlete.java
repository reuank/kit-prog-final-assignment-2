package task.olympia.models;

import task.constructs.database.Model;

public class Athlete extends Model {
        private Integer id;
        private String firstname;
        private String lastname;
        private String countryName;
        private SportDiscipline[] sportDisciplines;

    public Athlete(int id, String firstname, String lastname, String countryName, SportDiscipline[] sportDisciplines) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.countryName = countryName;
        this.sportDisciplines = sportDisciplines;
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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public SportDiscipline[] getSportDisciplines() {
        return sportDisciplines;
    }

    public void setSportDisciplines(SportDiscipline[] sportDisciplines) {
        this.sportDisciplines = sportDisciplines;
    }
}