package task.olympia.models;

import task.constructs.database.Model;

/**
 * The model for a sports venue
 */
public class SportsVenue extends Model {
    private int id;
    private IocCode iocCode;
    private String countryName;
    private String location;
    private String name;
    private int openingYear;
    private int seatcount;

    /**
     * Instantiates a new sports venue.
     *
     * @param id the id.
     * @param iocCode the ioc code.
     * @param location the location.
     * @param name the name.
     * @param openingYear the opening year.
     * @param seatcount the seatcount.
     */
    public SportsVenue(int id, IocCode iocCode, String location, String name, int openingYear, int seatcount) {
        this.id = id;
        this.iocCode = iocCode;
        this.countryName = iocCode.getCountryName();
        this.location = location;
        this.name = name;
        this.openingYear = openingYear;
        this.seatcount = seatcount;
    }

    /**
     * Instantiates a sporty venue dummy for identity and uniqueness checks,
     * that is then filled with all the specific attributes.
     *
     * @param id the id.
     * @param countryName the country name.
     * @param location the location.
     * @param name the name.
     * @param openingYear the opening year.
     * @param seatcount the seatcount.
     */
    public SportsVenue(int id, String countryName, String location, String name, int openingYear, int seatcount) {
        this.id = id;
        this.countryName = countryName;
        this.location = location;
        this.name = name;
        this.openingYear = openingYear;
        this.seatcount = seatcount;
    }

    @Override
    public boolean equals(Object other) {
        return ((SportsVenue) other).getId() == this.id;
    }

    /**
     * @return - returns the iocCode
     **/
    public IocCode getIocCode() {
        return iocCode;
    }

    /**
     * @return - returns the id
     **/
    public int getId() {
        return id;
    }

    /**
     * Sets the ioccode and updates the coountryname
     *
     * @param iocCode the new ioc code
     */
    public void setIocCode(IocCode iocCode) {
        this.iocCode = iocCode;
        this.countryName = iocCode.getCountryName();
    }

    /**
     * @return - returns the country name
     **/
    public String getCountryName() {
        return countryName;
    }

    /**
     * @return - returns the location
     **/
    public String getLocation() {
        return location;
    }

    /**
     * Sets the location
     *
     * @param location - the new location
     **/
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * @return - returns the name
     **/
    public String getName() {
        return name;
    }

    /**
     * Sets the name
     *
     * @param name - the new name
     **/
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return - returns the openingYear
     **/
    public int getOpeningYear() {
        return openingYear;
    }

    /**
     * Sets the openingYear
     *
     * @param openingYear - the new openingYear
     **/
    public void setOpeningYear(int openingYear) {
        this.openingYear = openingYear;
    }

    /**
     * @return - returns the seatcount
     **/
    public int getSeatcount() {
        return seatcount;
    }

    /**
     * Sets the seatcount
     *
     * @param seatcount - the new seatcount
     **/
    public void setSeatcount(int seatcount) {
        this.seatcount = seatcount;
    }
}
