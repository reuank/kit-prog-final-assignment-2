package task.olympia.serializers;

import task.olympia.models.*;
import task.olympia.serializers.types.*;

import java.util.List;

/**
 * Holds instances of the serializers that belong to the olympia application and passes the data to them.
 */
public class AppSerializer {
    private IocCodesSerializer iocCodesSerializer = new IocCodesSerializer();
    private SportsVenuesSerializer sportsVenuesSerializer = new SportsVenuesSerializer();
    private OlympicSportsSerializer olympicSportsSerializer = new OlympicSportsSerializer();
    private SummaryAthletesSerializer summaryAthletesSerializer = new SummaryAthletesSerializer();
    private OlympicMedalTableSerializer olympicMedalTableSerializer = new OlympicMedalTableSerializer();

    /**
     * Serializes an IocCode List.
     *
     * @param iocCodes The ioc code list.
     * @return returns the serialized list.
     */
    public List<String> serializeIocCodes(List<IocCode> iocCodes) {
        return this.iocCodesSerializer.serialize(iocCodes);
    }

    /**
     * Serializes an sports venue List.
     *
     * @param sportsVenues The sportsVenues list.
     * @return returns the serialized list.
     */
    public List<String> serializeSportsVenues(List<SportsVenue> sportsVenues) {
        return this.sportsVenuesSerializer.serialize(sportsVenues);
    }

    /**
     * Serializes an sportDisciplines List.
     *
     * @param sportDisciplines The sportDisciplines list.
     * @return returns the serialized list.
     */
    public List<String> serializeOlympicSports(List<OlympicSport> sportDisciplines) {
        return this.olympicSportsSerializer.serialize(sportDisciplines);
    }

    /**
     * Serializes an athleteSummary List.
     *
     * @param athleteSummary The athleteSummary list.
     * @return returns the serialized list.
     */
    public List<String> serializeSummaryAthletes(List<AthleteSummaryEntry> athleteSummary) {
        return this.summaryAthletesSerializer.serialize(athleteSummary);
    }

    /**
     * Serializes an olympicMedalTable List.
     *
     * @param olympicMedalTable The olympicMedalTable list.
     * @return returns the serialized list.
     */
    public List<String> serializeOlympicMedalTable(List<OlympicMedalTableEntry> olympicMedalTable) {
        return this.olympicMedalTableSerializer.serialize(olympicMedalTable);
    }
}