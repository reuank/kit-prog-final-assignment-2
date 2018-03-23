package task.olympia.serializers;

import task.olympia.models.*;
import task.olympia.serializers.types.*;

import java.util.List;

public class AppSerializer {
    private IocCodesSerializer iocCodesSerializer = new IocCodesSerializer();
    private SportsVenuesSerializer sportsVenuesSerializer = new SportsVenuesSerializer();
    private OlympicSportsSerializer olympicSportsSerializer = new OlympicSportsSerializer();
    private SummaryAthletesSerializer summaryAthletesSerializer = new SummaryAthletesSerializer();
    private OlympicMedalTableSerializer olympicMedalTableSerializer = new OlympicMedalTableSerializer();

    public List<String> serializeIocCodes(List<IocCode> iocCodes) {
        return this.iocCodesSerializer.serialize(iocCodes);
    }

    public List<String> serializeSportsVenues(List<SportsVenue> sportsVenues) {
        return this.sportsVenuesSerializer.serialize(sportsVenues);
    }

    public List<String> serializeOlympicSports(List<OlympicSport> sportDisciplines) {
        return this.olympicSportsSerializer.serialize(sportDisciplines);
    }

    public List<String> serializeSummaryAthletes(List<AthleteSummaryEntry> athleteSummary) {
        return this.summaryAthletesSerializer.serialize(athleteSummary);
    }

    public List<String> serializeOlympicMedalTable(List<OlympicMedalTableEntry> olympicMedalTable) {
        return this.olympicMedalTableSerializer.serialize(olympicMedalTable);
    }
}