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

    public List<Athlete> getAthleteSummary(OlympicSport olympicSport) throws DatabaseException {
        if (!existsInTable(OlympicSport.class, olympicSport)) {
            throw new DatabaseException(Message.get(NOT_EXISTENT, "country name"));
        }

        List<Athlete> relevantAthletesList = this.getAllWhereMatches(
                Athlete.class,
                row -> row.practicesOlympicSport(olympicSport)
        );

        Comparator<Athlete> byMedalCount = Comparator.<Athlete>comparingInt(
                athlete -> athlete.getMedalCount(olympicSport)).reversed();
        Comparator<Athlete> byId = Comparator.comparingInt(Athlete::getId);

        return getSortedList(relevantAthletesList, byMedalCount.thenComparing(byId));
    }

    private boolean countryNameExists(String countryName) {
        return this.getCorrespondingTable(IocCode.class).anyMatch(code -> code.getCountryName().equals(countryName));
    }

    /* ------------- GENERAL: GETTER ------------- */
    public <T extends Model> boolean existsInTable(Class<T> itemClass, T item) {
        return this.app.getDatabase().getTable(itemClass)
                .getRows()
                .stream()
                .anyMatch(row -> row.equals(item));
    }

    public <T extends Model> T getFirstWhereMatches(Class<T> itemClass, Predicate<T> predicate) {
        if (this.getCorrespondingTable(itemClass).anyMatch(predicate)) {
            return getAllWhereMatches(itemClass, predicate).get(0);
        }

        return null;
    }

    public <T extends Model> List<T> getAllWhereMatches(Class<T> itemClass, Predicate<T> predicate) {
        Table<T> table = this.app.getDatabase().getTable(itemClass);
        if (table.anyMatch(predicate)) {
            return table.getRows().stream()
                    .filter(predicate)
                    .collect(Collectors.toList());
        }

        return null;
    }


    /* ------------- GENERAL: GET LISTS AND TABLES ------------- */
    public <T extends Model> Table<T> getCorrespondingTable(Class<T> itemClass) {
        return this.app.getDatabase().getTable(itemClass);
    }

    public <T extends Model> List<T> getSortedList(List<T> list, Comparator<T> comparator) {
        return list == null ? null : list.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public <T extends Model> List<T> getSortedList(Class<T> itemClass, Comparator<T> comparator) {
        Table<T> table = this.getCorrespondingTable(itemClass);
        return getSortedList(new ArrayList<>(table.getRows()), comparator);
    }

    /* ------------- EXCEPTION FACTORY ------------- */
    public <T extends Model> void throwNonExistentException(String itemName) throws DatabaseException {
        throw new DatabaseException(Message.get(NOT_EXISTENT, itemName));
    }

    public <T extends Model> void throwNotUniqueException(String itemName) throws DatabaseException {
        throw new DatabaseException(Message.get(NOT_EXISTENT, itemName));
    }
}
