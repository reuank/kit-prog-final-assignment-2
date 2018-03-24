package task.olympia.models;

import java.util.Map;

/**
 * Used for temporarily storing olympic medal table entries to achieve high database performance.
 */
public class OlympicMedalTableEntry {
    private final IocCode iocCode;
    private final int gold;
    private final int silver;
    private final int bronze;
    private final int totalMedals;

    /**
     * Instantiates a single entry of the olympia medal table.
     *
     * @param iocCode the corresponding ioc code.
     * @param medalMap the corresponding medal map.
     */
    public OlympicMedalTableEntry(IocCode iocCode, Map<Medal, Integer> medalMap) {
        this.iocCode = iocCode;
        this.gold = medalMap.getOrDefault(Medal.GOLD, 0);
        this.silver = medalMap.getOrDefault(Medal.SILVER, 0);
        this.bronze = medalMap.getOrDefault(Medal.BRONZE, 0);
        this.totalMedals = gold + silver + bronze;
    }

    /**
     * @return - returns the ioc code as a String.
     */
    public String getIocCode() {
        return this.iocCode.getIocCode();
    }

    /**
     * @return - returns the country name.
     */
    public String getCountryName() {
        return this.iocCode.getCountryName();
    }

    /**
     * @return returns the ioc id.
     */
    public int getIocId() {
        return this.iocCode.getId();
    }

    /**
     * @return - returns the gold counter.
     **/
    public int getGold() {
        return gold;
    }

    /**
     * @return - returns the silver counter.
     **/
    public int getSilver() {
        return silver;
    }

    /**
     * @return - returns the bronze counter.
     **/
    public int getBronze() {
        return bronze;
    }

    /**
     * @return - returns the totalMedals counter.
     **/
    public int getTotalMedals() {
        return totalMedals;
    }
}
