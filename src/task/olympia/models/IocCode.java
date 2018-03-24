package task.olympia.models;

import task.constructs.database.Model;

/**
 * The model for an ioc code
 */
public class IocCode extends Model {
    private int id;
    private String iocCode;
    private String countryName;
    private int determinationYear;

    /**
     * Instantiates a new ioc code
     * @param id the id
     * @param iocCode the ioc code
     * @param countryName the country name
     * @param determinationYear the determination year
     */
    public IocCode(int id, String iocCode, String countryName, int determinationYear) {
        this.id = id;
        this.iocCode = iocCode;
        this.countryName = countryName;
        this.determinationYear = determinationYear;
    }

    /**
     * Instantiates a new ioc code dummy for identity and uniqueness checks,
     * that is then filled with all the specific attributes.
     *
     * @param countryName the country name
     */
    public IocCode(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object other) {
        return ((IocCode) other).getId() == (this.id)
                || ((IocCode) other).getIocCode().equals(this.iocCode);
    }

    /**
     * @return - returns the id
     **/
    public int getId() {
        return id;
    }

    /**
     * Sets the id
     *
     * @param id - the new id
     **/
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return - returns the iocCode
     **/
    public String getIocCode() {
        return iocCode;
    }

    /**
     * Sets the iocCode
     *
     * @param iocCode - the new iocCode
     **/
    public void setIocCode(String iocCode) {
        this.iocCode = iocCode;
    }

    /**
     * @return - returns the countryName
     **/
    public String getCountryName() {
        return countryName;
    }

    /**
     * Sets the countryName
     *
     * @param countryName - the new countryName
     **/
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /**
     * @return - returns the determinationYear
     **/
    public int getYear() {
        return determinationYear;
    }

    /**
     * Sets the determinationYear
     *
     * @param determinationYear - the new determinationYear
     **/
    public void setYear(int determinationYear) {
        this.determinationYear = determinationYear;
    }
}
