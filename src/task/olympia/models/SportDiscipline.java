package task.olympia.models;

import task.constructs.database.Model;

public class SportDiscipline extends Model {
    private int id;
    private SportType sportType;
    private String name;

    public SportDiscipline(int id, SportType sportType, String name) {
        this.id = id;
        this.sportType = sportType;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public SportType getSportType() {
        return sportType;
    }

    public void setSportType(SportType sportType) {
        this.sportType = sportType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
