package task.olympia.models;

import task.constructs.database.Model;

public class IocCode extends Model {
    private int id;
    private String iocCode;
    private String countryName;
    private int determinationYear;

    public IocCode(int id, String iocCode, String countryName, int determinationYear) {
        this.id = id;
        this.iocCode = iocCode;
        this.countryName = countryName;
        this.determinationYear = determinationYear;
    }

    public IocCode(String countryName) {
        this.countryName = countryName;
    }

    @Override
    public boolean equals(Object other) {
        return ((IocCode) other).getId() == (this.id)
                || ((IocCode) other).getIocCode().equals(this.iocCode);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIocCode() {
        return iocCode;
    }

    public void setIocCode(String iocCode) {
        this.iocCode = iocCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public int getYear() {
        return determinationYear;
    }

    public void setYear(int determinationYear) {
        this.determinationYear = determinationYear;
    }
}
