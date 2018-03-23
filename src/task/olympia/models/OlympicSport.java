package task.olympia.models;

import task.constructs.database.Model;

public class OlympicSport extends Model {
    private String sportType;
    private String sportDiscipline;

    public OlympicSport(String sportType, String sportDiscipline) {
        this.sportType = sportType;
        this.sportDiscipline = sportDiscipline;
    }

    @Override
    public boolean equals(Object other) {
        return ((OlympicSport) other).getSportDiscipline().equals(this.sportDiscipline)
                && ((OlympicSport) other).getSportType().equals(this.sportType);
    }

    public String getSportType() {
        return sportType;
    }

    public void setSportType(String sportType) {
        this.sportType = sportType;
    }

    public String getSportDiscipline() {
        return sportDiscipline;
    }

    public void setSportDiscipline(String sportDiscipline) {
        this.sportDiscipline = sportDiscipline;
    }
}