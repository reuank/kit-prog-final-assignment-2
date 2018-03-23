package task.olympia.serializers;

import task.olympia.models.*;
import task.olympia.serializers.types.IocCodesSerializer;
import task.olympia.serializers.types.OlympicSportsSerializer;
import task.olympia.serializers.types.SportsVenuesSerializer;
import task.olympia.serializers.types.SummaryAthletesSerializer;

import java.util.List;

public class AppSerializer {
    private IocCodesSerializer iocCodesSerializer = new IocCodesSerializer();
    private SportsVenuesSerializer sportsVenuesSerializer = new SportsVenuesSerializer();
    private OlympicSportsSerializer olympicSportsSerializer = new OlympicSportsSerializer();
    private SummaryAthletesSerializer summaryAthletesSerializer = new SummaryAthletesSerializer();

    public List<String> serializeIocCodes(List<IocCode> iocCodes) {
        return this.iocCodesSerializer.serialize(iocCodes);
    }

    public List<String> serializeSportsVenues(List<SportsVenue> sportsVenues) {
        return this.sportsVenuesSerializer.serialize(sportsVenues);
    }

    public List<String> serializeOlympicSports(List<OlympicSport> sportDisciplines) {
        return this.olympicSportsSerializer.serialize(sportDisciplines);
    }

    public List<String> serializeSummaryAthletes(List<AthleteSummary> athleteSummary) {
        return this.summaryAthletesSerializer.serialize(athleteSummary);
    }
}