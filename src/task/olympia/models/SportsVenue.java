package task.olympia.models;

import task.constructs.database.Model;

public class SportsVenue extends Model {
    private int id;
    private IocCode iocCode;
    private String countryName;
    private String location;
    private String name;
    private int openingYear;
    private int seatcount;

    public SportsVenue(int id, IocCode iocCode, String location, String name, int openingYear, int seatcount) {
        this.id = id;
        this.iocCode = iocCode;
        this.location = location;
        this.name = name;
        this.openingYear = openingYear;
        this.seatcount = seatcount;
    }

    public SportsVenue(int id, String countryName, String location, String name, int openingYear, int seatcount) {
        this.id = id;
        this.countryName = countryName;
        this.location = location;
        this.name = name;
        this.openingYear = openingYear;
        this.seatcount = seatcount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public IocCode getIocCode() {
        return iocCode;
    }

    public void setIocCode(IocCode iocCode) {
        this.iocCode = iocCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOpeningYear() {
        return openingYear;
    }

    public void setOpeningYear(int openingYear) {
        this.openingYear = openingYear;
    }

    public int getSeatcount() {
        return seatcount;
    }

    public void setSeatcount(int seatcount) {
        this.seatcount = seatcount;
    }
}
