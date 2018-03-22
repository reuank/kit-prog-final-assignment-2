package task.olympia.operations;

import task.constructs.database.Model;
import task.constructs.database.Table;
import task.olympia.OlympiaApplication;
import task.olympia.models.IocCode;
import task.olympia.models.SportDiscipline;
import task.olympia.models.SportsVenue;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Selector {
    private OlympiaApplication app;

    public Selector(OlympiaApplication app) {
        this.app = app;
    }

    public IocCode getIocCode(String countryName) {
        Table<IocCode> table = this.app.getDatabase().getTable(IocCode.class);
        return getSingleWhereEquals(table, row -> row.getCountryName().equals(countryName));
    }

    public List<IocCode> getIocCodesSorted() {
        Table<IocCode> table = this.app.getDatabase().getTable(IocCode.class);
        Comparator<IocCode> byDeterminationYear = Comparator.comparingInt(IocCode::getYear);
        Comparator<IocCode> byId = Comparator.comparingInt(IocCode::getId);

        return this.getSortedListFromTable(table, byDeterminationYear.thenComparing(byId));
    }

    public List<SportsVenue> getSportsVenuesSorted(String countryName) {
        Table<SportsVenue> venueTable = this.app.getDatabase().getTable(SportsVenue.class);
        List<SportsVenue> venuesByCountryName = venueTable.getRows()
                .stream()
                .filter(item -> item.getCountryName().equals(countryName))
                .collect(Collectors.toList());

        Comparator<SportsVenue> bySeatCount = Comparator.comparingInt(SportsVenue::getSeatcount);
        Comparator<SportsVenue> byId = Comparator.comparingInt(SportsVenue::getId);

        return getSortedList(venuesByCountryName, bySeatCount.thenComparing(byId));
    }

    public List<SportDiscipline> getOlympicSportsSorted() {
        Table<SportDiscipline> table = this.app.getDatabase().getTable(SportDiscipline.class);
        Comparator<SportDiscipline> bySportType = Comparator.comparing(item -> item.getSportType().getName());
        Comparator<SportDiscipline> bySportDiscipline = Comparator.comparing(SportDiscipline::getName);

        return this.getSortedListFromTable(table, bySportType.thenComparing(bySportDiscipline));
    }

    public <T extends Model> boolean exists(Class<T> itemClass, T item) {
        Table<T> table = this.getCorrespondingTable(itemClass);

        return this.existsInTable(table, item);
    }

    public <T extends Model> boolean existsInTable(Table<T> itemTable, T item) {
        return itemTable.getRows().stream()
                .anyMatch(row -> row.equals(item));
    }

    public <T extends Model> T getObjectIfExistent(Class<T> itemClass, T item) {
        Table<T> table = this.getCorrespondingTable(itemClass);

        return getSingleWhereEquals(table, row -> row.equals(item));
    }

    public <T extends Model> T getSingleWhereEquals(Table<T> table, Predicate<T> predicate) {
        if (!table.isEmpty() && table.anyMatch(predicate)) {
            return table.getRows().stream()
                    .filter(predicate)
                    .collect(Collectors.toList())
                    .get(0);
        }

        return null;
    }

    public <T extends Model> List<T> getSortedList(List<T> list, Comparator<T> comparator) {

        return list.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    public <T extends Model> List<T> getSortedListFromTable(Table<T> table, Comparator<T> comparator) {
        return getSortedList(new ArrayList<>(table.getRows()), comparator);
    }

    public <T extends Model> Table<T> getCorrespondingTable(Class<T> itemClass) {
        return this.app.getDatabase().getTable(itemClass);
    }
}
