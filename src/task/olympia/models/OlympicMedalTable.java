package task.olympia.models;

public class OlympicMedalTable {
    private final IocCode iocCode;
    private final int goldCounter;
    private final int silverCounter;
    private final int bronzeCounter;
    private final int medalCounter;

    public OlympicMedalTable(IocCode iocCode, int goldCounter, int silverCounter, int bronzeCounter) {
        this.iocCode = iocCode;
        this.goldCounter = goldCounter;
        this.silverCounter = silverCounter;
        this.bronzeCounter = bronzeCounter;
        this.medalCounter = goldCounter + silverCounter + bronzeCounter;
    }

    public IocCode getIocCode() {
        return iocCode;
    }

    public int getGold() {
        return goldCounter;
    }

    public int getSilver() {
        return silverCounter;
    }

    public int getBronze() {
        return bronzeCounter;
    }

    public int getMedalCounter() {
        return medalCounter;
    }
}
