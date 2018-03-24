package task.olympia.operations;

import task.constructs.database.Database;
import task.constructs.database.Model;
import task.constructs.database.Table;
import task.exceptions.DatabaseException;
import task.lang.Message;
import task.olympia.models.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static task.lang.Message.NOT_EXISTENT;

/**
 * Used to read in the database. Provides optimized queries to the app.
 */
public class Selector {
    private Database database;

    /**
     * Instantiates a new Selector
     * @param database The database the selector is working on
     */
    public Selector(Database database) {
        this.database = database;
    }

    /* ------------- SPECIFIC: GET OBJECT REFERENCES ------------- */

    /**
     * @param countryName the country name
     * @return returns the corresponding ioc code
     */
    public IocCode getIocCodeByCountryName(String countryName) {
        return getFirstWhereMatches(IocCode.class, row -> row.getCountryName().equals(countryName));
    }

    /**
     * @param olympicSport the olympic sport
     * @return returns the corresponding olympic sport object in the database
     */
    public OlympicSport getOlympicSport(OlympicSport olympicSport) {
        return getFirstWhereMatches(OlympicSport.class, row -> row.equals(olympicSport));
    }

    /**
     * @param id the athlete id.
     * @return returns the corresponding athlete object in the database
     */
    public Athlete getAthleteById(int id) {
        return getFirstWhereMatches(Athlete.class, row -> row.getId() == id);
    }


    /* ------------- SPECIFIC: GET LISTS AND TABLES ------------- */

    /**
     * @return Returns the sorted ioc codes list
     */
    public List<IocCode> getIocCodesSorted() {
        Comparator<IocCode> byDeterminationYear = Comparator.comparingInt(IocCode::getYear);
        Comparator<IocCode> byId = Comparator.comparingInt(IocCode::getId);

        return this.getSortedList(IocCode.class, byDeterminationYear.thenComparing(byId));
    }

    /**
     * Generates the sorted sports venues list by a country name.
     * @param countryName the country name.
     * @return returns the sported sports venues list.
     * @throws DatabaseException thrown if the country was not found.
     */
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

    /**
     * @return Returns the sorted olympic sports list
     */
    public List<OlympicSport> getOlympicSportsSorted() {
        Comparator<OlympicSport> bySportType = Comparator.comparing(OlympicSport::getSportType);
        Comparator<OlympicSport> bySportDiscipline = Comparator.comparing(OlympicSport::getSportDiscipline);

        return this.getSortedList(OlympicSport.class, bySportType.thenComparing(bySportDiscipline));
    }

    /**
     * Generates the athlete summary for an olympic sport.
     *
     * @param olympicSport the olympic sport.
     * @return returns the athlete summary list.
     * @throws DatabaseException thrown if the olympic sport was not found.
     */
    public List<AthleteSummaryEntry> getAthleteSummary(OlympicSport olympicSport) throws DatabaseException {
        if (!existsInTable(OlympicSport.class, olympicSport)) {
            throw new DatabaseException(Message.get(NOT_EXISTENT, "olympic sport"));
        }

        List<Athlete> relevantAthletesList = getAthletesBySport(olympicSport);
        List<AthleteSummaryEntry> athleteSummary = joinAthletesToSportMedals(relevantAthletesList, olympicSport);

        Comparator<AthleteSummaryEntry> byMedalCount = Comparator.comparingInt(AthleteSummaryEntry::getMedalCount);
        Comparator<AthleteSummaryEntry> byId = Comparator.comparingInt(AthleteSummaryEntry::getId);

        athleteSummary.sort(byMedalCount.reversed().thenComparing(byId));

        return athleteSummary;
    }

    /**
     * @return Returns the olympic medal table
     */
    public List<OlympicMedalTableEntry> getOlympicMedalTable() {
        List<OlympicMedalTableEntry> medalTable = getCorrespondingTable(IocCode.class).getRows().stream()
                .map(iocCode -> new OlympicMedalTableEntry(iocCode, getMedalMapByIocCode(iocCode)))
                .collect(Collectors.toList());

        medalTable.sort(Comparator.comparing(OlympicMedalTableEntry::getGold).reversed()
                .thenComparing(Comparator.comparing(OlympicMedalTableEntry::getSilver).reversed())
                .thenComparing(Comparator.comparing(OlympicMedalTableEntry::getBronze).reversed())
                .thenComparing(Comparator.comparing(OlympicMedalTableEntry::getIocId)));

        return medalTable;
    }

    /* ------------- SPECIFIC: PRIVATE GETTER ------------- */
    private Map<Medal, Integer> getMedalMapByIocCode(IocCode iocCode) {
        List<Medal> medalList = getCompetitionsByIocCode(iocCode).stream()
                .map(Competition::getMedal)
                .collect(Collectors.toList());

        EnumMap<Medal, Integer> medalMap = new EnumMap<>(Medal.class);

        medalList.forEach(medal -> {
            medalMap.putIfAbsent(medal, 0);
            medalMap.put(medal, medalMap.get(medal) + 1);
        });

        return medalMap;
    }

    private List<Competition> getCompetitionsByIocCode(IocCode iocCode) {
        return getAllWhereMatches(Competition.class, competition -> competition.getIocCode().equals(iocCode));
    }

    private List<Athlete> getAthletesBySport(OlympicSport olympicSport) {
        return this.getAllWhereMatches(Athlete.class, row -> row.practicesOlympicSport(olympicSport));
    }

    private List<AthleteSummaryEntry> joinAthletesToSportMedals(List<Athlete> relevantAthletes, OlympicSport sport) {
        return relevantAthletes.stream()
                .map(athlete -> new AthleteSummaryEntry(athlete, this.getAllWhereMatches(
                        Competition.class,
                        row -> row.getAthleteId() == athlete.getId()
                                && row.getOlympicSport().equals(sport))
                        .stream().mapToInt(Competition::getMedalValue).sum()))
                .collect(Collectors.toList());
    }

    private boolean countryNameExists(String countryName) {
        return this.getCorrespondingTable(IocCode.class).anyMatch(code -> code.getCountryName().equals(countryName));
    }

    /* ------------- GENERAL: GETTER ------------- */
    /**
     * Checks whether an item is in a table
     *
     * @param itemClass the item class to find the table
     * @param item the item
     * @param <T> the type
     * @return returns true if the item was fount
     */
    public <T extends Model> boolean existsInTable(Class<T> itemClass, T item) {
        return this.database.getTable(itemClass)
                .getRows()
                .stream()
                .anyMatch(row -> row.equals(item));
    }

    /**
     * Gets the first entry where the predicate matches.
     *
     * @param itemClass the item class to find the table
     * @param predicate the predicate that shall apply
     * @param <T> the type
     * @return returns the first entry where the predicate matches.
     */
    private <T extends Model> T getFirstWhereMatches(Class<T> itemClass, Predicate<T> predicate) {
        if (this.getCorrespondingTable(itemClass).anyMatch(predicate)) {
            return getAllWhereMatches(itemClass, predicate).get(0);
        }

        return null;
    }

    private <T extends Model> List<T> getAllWhereMatches(Class<T> itemClass, Predicate<T> predicate) {
        Table<T> table = this.database.getTable(itemClass);
        if (table.anyMatch(predicate)) {
            return table.getRows().stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }


    /* ------------- GENERAL: GET LISTS AND TABLES ------------- */
    private  <T extends Model> Table<T> getCorrespondingTable(Class<T> itemClass) {
        return this.database.getTable(itemClass);
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

    /**
     * Sets the database
     *
     * @param database - the new database
     **/
    public void setDatabase(Database database) {
        this.database = database;
    }
}
