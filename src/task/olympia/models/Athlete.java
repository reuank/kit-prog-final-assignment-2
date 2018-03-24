package task.olympia.models;

import task.constructs.database.Model;

import java.util.ArrayList;

/**
 * The model for an athlete.
 */
public class Athlete extends Model {
    private int id;
    private String firstname;
    private String lastname;
    private IocCode iocCode;
    private String countryName;
    private ArrayList<OlympicSport> olympicSports;

    /**
     * Instantiates a new admin object.
     * @param id The id.
     * @param firstname The firstname.
     * @param lastname The lastname.
     * @param countryName The country name.
     * @param olympicSport The olympic sport.
     */
    public Athlete(int id, String firstname, String lastname, String countryName, OlympicSport olympicSport) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.countryName = countryName;
        this.olympicSports = new ArrayList<>();
        this.assignOlympicSport(olympicSport);
    }

    /**
     * Instantiates a athlete dummy for identity and uniqueness checks,
     * that is then filled with all the specific attributes.
     *
     * @param id The athlete id.
     */
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

    /**
     * @return - returns the id.
     **/
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id - the new id.
     **/
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return - returns the firstname.
     **/
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the firstname.
     *
     * @param firstname - the new firstname.
     **/
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return - returns the lastname.
     **/
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the lastname.
     *
     * @param lastname - the new lastname.
     **/
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return - returns the ioc Code.
     **/
    public IocCode getIocCode() {
        return iocCode;
    }

    /**
     * Sets the ioc code.
     *
     * @param iocCode - the new ioc Code.
     **/
    public void setIocCode(IocCode iocCode) {
        this.iocCode = iocCode;
        this.countryName = iocCode.getCountryName();
    }

    /**
     * @return - returns the countryName.
     **/
    public String getCountryName() {
        return countryName;
    }

    /**
     * @return - returns the olympic sport that has been assigned last to the athlete.
     */
    public OlympicSport getLatestOlympicSport() {
        return this.olympicSports.get(olympicSports.size() - 1);
    }

    /**
     * Assigns an olympic sport to the athlete.
     *
     * @param olympicSport the new olympic sport.
     */
    public void assignOlympicSport(OlympicSport olympicSport) {
        this.olympicSports.add(olympicSport);
    }

    /**
     * Checks whether the athlete does practise a specific olympic sport.
     *
     * @param olympicSport the olympic sport.
     * @return - returns true if the athlete does practise the sport.
     */
    public boolean practicesOlympicSport(OlympicSport olympicSport) {
        return this.olympicSports.contains(olympicSport);
    }
}