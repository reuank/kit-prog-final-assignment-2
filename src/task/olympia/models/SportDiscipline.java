package task.olympia.models;

import task.constructs.database.Model;

public class SportDiscipline extends Model {
    private SportType sportType;
    private String name;

    public SportDiscipline(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object other) {
        return ((SportDiscipline) other).getName().equals(this.getName())
                && ((SportDiscipline) other).getSportType().equals(this.sportType);
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
