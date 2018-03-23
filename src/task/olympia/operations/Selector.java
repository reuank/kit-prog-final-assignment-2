package task.olympia.operations;

import task.constructs.database.Model;
import task.constructs.database.Table;
import task.exceptions.DatabaseException;
import task.lang.Message;
import task.olympia.OlympiaApplication;
import task.olympia.models.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static task.lang.Message.NOT_EXISTENT;

public class Selector {
    private OlympiaApplication app;

    public Selector(OlympiaApplication app) {
        this.app = app;
    }

    /* ------------- SPECIFIC: GET OBJECT REFERENCES ------------- */
    public IocCode getIocCodeByCountryName(String countryName) {
        return getFirstWhereMatches(IocCode.class, row -> row.getCountryName().equals(countryName));
    }

    public OlympicSport getOlympicSport(OlympicSport olympicSport) {
        return getFirstWhereMatches(OlympicSport.class, row -> row.equals(olympicSport));
    }

    public Athlete getAthleteById(int id) {
        return getFirstWhereMatches(Athlete.class, row -> row.getId() == id);
    }


    /* ------------- SPECIFIC: GET LISTS AND TABLES ------------- */
    public List<IocCode> getIocCodesSorted() {
        Comparator<IocCode> byDeterminationYear = Comparator.comparingInt(IocCode::getYear);
        Comparator<IocCode> byId = Comparator.comparingInt(IocCode::getId);

        return this.getSortedList(IocCode.class, byDeterminationYear.thenComparing(byId));
    }

    public List<SportsVenue> getSportsVenuesSorted(String countryName) throws DatabaseException {
        if (!countryNameExists(countryName)) {
            throw new DatabaseException(Message.get(NOT_EXISTENT, "country name"));
        }

        List<SportsVenue> venuesByCountryName = this.getAllWhereMatches(
                SportsVenue.class,
                row -> row.getCountryName().equals(countryName)
        );

        Comparator<SportsVenue> bySeatCount = Comparator.comparingInt(SportsVenue::getSeatcount);
        Comparator<SportsVenue> byId = Comparator.comparingInt(SportsVenue::getId);

        return getSortedList(venuesByCountryName, bySeatCount.thenComparing(byId));
    }

    public List<OlympicSport> getOlympicSportsSorted() {
        Comparator<OlympicSport> bySportType = Comparator.comparing(OlympicSport::getSportType);
        Comparator<OlympicSport> bySportDiscipline = Comparator.comparing(OlympicSport::getSportDiscipline);

        return this.getSortedList(OlympicSport.class, bySportType.thenComparing(bySportDiscipline));
    }

    public List<AthleteSummaryEntry> getAthleteSummary(OlympicSport olympicSport) throws DatabaseException {
        if (!existsInTable(OlympicSport.class, olympicSport)) {
            throw new DatabaseException(Message.get(NOT_EXISTENT, "olympic sport"));
        }

        List<Athlete> relevantAthletesList = getAthletesBySport(olympicSport);
        List<AthleteSummaryEntry> athleteSummary = joinAthletesToSportMedals(relevantAthletesList, olympicSport);

        Comparator<AthleteSummaryEntry> byMedalCount = Comparator.comparingInt(AthleteSummaryEntry::getMedalCount).reversed();
        Comparator<AthleteSummaryEntry> byId = Comparator.comparingInt(AthleteSummaryEntry::getId);

        athleteSummary.sort(byMedalCount.thenComparing(byId));

        return athleteSummary;
    }

    public List<OlympicMedalTableEntry> getOlympicMedalTable() {
        List<OlympicMedalTableEntry> medalTable = getCorrespondingTable(IocCode.class).getRows().stream()
                .map(iocCode -> new OlympicMedalTableEntry(iocCode, getMedalsForIocCode(iocCode)))
                .collect(Collectors.toList());

        medalTable.sort(
                Comparator.comparing(OlympicMedalTableEntry::getGold).reversed()
                        .thenComparing(Comparator.comparing(OlympicMedalTableEntry::getSilver).reversed())
                        .thenComparing(Comparator.comparing(OlympicMedalTableEntry::getBronze).reversed())
                        .thenComparing(Comparator.comparing(OlympicMedalTableEntry::getIocId)));

        return medalTable;
    }

    private Map<Medal, Integer> getMedalsForIocCode(IocCode iocCode) {
        //TODO AUSLAGERN
        List<Competition> competitionList = getAllWhereMatches(
                Competition.class,
                competition -> competition.getIocCode().equals(iocCode));

        List<Medal> medalList = competitionList.stream().map(Competition::getMedal).collect(Collectors.toList());

        EnumMap<Medal, Integer> medalMap = new EnumMap<>(Medal.class);

        medalList.forEach(medal -> {
            medalMap.putIfAbsent(medal, 0);
            medalMap.put(medal, medalMap.get(medal) + 1);
        });

        return medalMap;
    }


    private List<Athlete> getAthletesBySport(OlympicSport olympicSport) {
        return this.getAllWhereMatches(Athlete.class, row -> row.practicesOlympicSport(olympicSport));
    }

    private List<AthleteSummaryEntry> joinAthletesToSportMedals(List<Athlete> relevantAthletes, OlympicSport olympicSport) {
        return relevantAthletes.stream()
                .map(athlete -> new AthleteSummaryEntry(athlete, this.getAllWhereMatches(
                        Competition.class,
                        row -> row.getAthleteId() == athlete.getId()
                                && row.getOlympicSport().equals(olympicSport))
                        .stream().mapToInt(Competition::getMedalValue).sum()))
                .collect(Collectors.toList());
    }

    private boolean countryNameExists(String countryName) {
        return this.getCorrespondingTable(IocCode.class).anyMatch(code -> code.getCountryName().equals(countryName));
    }

    /* ------------- GENERAL: GETTER ------------- */

    /**
     *
     * @param itemClass
     * @param item
     * @param <T>
     * @return
     */
    public <T extends Model> boolean existsInTable(Class<T> itemClass, T item) {
        return this.app.getDatabase().getTable(itemClass)
                .getRows()
                .stream()
                .anyMatch(row -> row.equals(item));
    }

    private <T extends Model> T getFirstWhereMatches(Class<T> itemClass, Predicate<T> predicate) {
        if (this.getCorrespondingTable(itemClass).anyMatch(predicate)) {
            return getAllWhereMatches(itemClass, predicate).get(0);
        }

        return null;
    }

    private <T extends Model> List<T> getAllWhereMatches(Class<T> itemClass, Predicate<T> predicate) {
        Table<T> table = this.app.getDatabase().getTable(itemClass);
        if (table.anyMatch(predicate)) {
            return table.getRows().stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }


    /* ------------- GENERAL: GET LISTS AND TABLES ------------- */
    private  <T extends Model> Table<T> getCorrespondingTable(Class<T> itemClass) {
        return this.app.getDatabase().getTable(itemClass);
    }

    private  <T extends Model> List<T> getSortedList(List<T> list, Comparator<T> comparator) {
        return list == null ? null : list.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    private <T extends Model> List<T> getSortedList(Class<T> itemClass, Comparator<T> comparator) {
        Table<T> table = this.getCorrespondingTable(itemClass);
        return getSortedList(new ArrayList<>(table.getRows()), comparator);
    }
}
