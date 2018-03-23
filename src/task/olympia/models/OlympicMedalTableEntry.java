package task.olympia.models;

import java.util.Map;

public class OlympicMedalTableEntry {
    private final IocCode iocCode;
    private final int goldCounter;
    private final int silverCounter;
    private final int bronzeCounter;
    private final int totalMedals;

    public OlympicMedalTableEntry(IocCode iocCode, Map<Medal, Integer> medalMap) {
        this.iocCode = iocCode;
        this.goldCounter = medalMap.getOrDefault(Medal.GOLD, 0);
        this.silverCounter = medalMap.getOrDefault(Medal.SILVER, 0);
        this.bronzeCounter = medalMap.getOrDefault(Medal.BRONZE, 0);
        this.totalMedals = goldCounter + silverCounter + bronzeCounter;
    }

    public String getIocCode() {
        return this.iocCode.getIocCode();
    }

    public String getCountryName() {
        return this.iocCode.getCountryName();
    }

    public int getIocId () {
        return this.iocCode.getId();
    }

    public int getGold() {
        return this.goldCounter;
    }

    public int getSilver() {
        return this.silverCounter;
    }

    public int getBronze() {
        return this.bronzeCounter;
    }

    public int getTotalMedals() {
        return this.totalMedals;
    }
}
