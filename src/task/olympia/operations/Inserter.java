package task.olympia.operations;

import task.constructs.database.Model;
import task.constructs.database.Table;
import task.exceptions.DatabaseException;
import task.lang.Message;
import task.olympia.OlympiaApplication;
import task.olympia.models.IocCode;
import task.olympia.models.SportDiscipline;
import task.olympia.models.SportType;
import task.olympia.models.SportsVenue;

import static task.lang.Message.IOC_CODE_YOUNGER_ERROR;
import static task.lang.Message.NOT_EXISTENT;
import static task.lang.Message.ALREADY_EXISTS;

public class Inserter {
    private OlympiaApplication app;
    private Selector selector;

    public Inserter(OlympiaApplication app, Selector selector) {
        this.app = app;
        this.selector = selector;
    }

    public boolean addIocCode(IocCode iocCode) throws DatabaseException {
        Table<IocCode> iocCodeTable = this.app.getDatabase().getTable(IocCode.class);

        if (this.selector.exists(IocCode.class, iocCode)) {
            throw new DatabaseException(Message.get(ALREADY_EXISTS, "ioc id or ioc code"));
        }

        this.insertIntoTable(iocCodeTable, iocCode);

        return true;
    }

    public boolean addSportsVenue(SportsVenue sportsVenue) throws DatabaseException {
        Table<SportsVenue> venueTable = this.app.getDatabase().getTable(SportsVenue.class);
        IocCode matchingIocCode = this.selector.getIocCode(sportsVenue.getCountryName());

        if (matchingIocCode == null) {
            throw new DatabaseException(Message.get(NOT_EXISTENT, "country name"));
        }

/*        if (matchingIocCode.getYear() > sportsVenue.getOpeningYear()) {
            throw new DatabaseException((Message.get(IOC_CODE_YOUNGER_ERROR)));
        }*/

        sportsVenue.setIocCode(matchingIocCode);
        if (!this.insertIntoTable(venueTable, sportsVenue)) {
            throw new DatabaseException(Message.get(ALREADY_EXISTS, "sports venue"));
        }

        return true;
    }

    public boolean addOlympicSport(SportType sportType, SportDiscipline sportDiscipline) throws DatabaseException {
        Table<SportType> typeTable = this.app.getDatabase().getTable(SportType.class);
        Table<SportDiscipline> disciplineTable = this.app.getDatabase().getTable(SportDiscipline.class);

        SportType matchingType = this.selector.getObjectIfExistent(SportType.class, sportType);

        if (matchingType == null) {
            typeTable.insert(sportType);
            sportDiscipline.setSportType(sportType);
            disciplineTable.insert(sportDiscipline);
            return true;
        }

        sportDiscipline.setSportType(matchingType);
        SportDiscipline matchingDiscipline = this.selector.getObjectIfExistent(SportDiscipline.class, sportDiscipline);

        if (matchingDiscipline != null) {
            throw new DatabaseException(Message.get(ALREADY_EXISTS, "sport discipline"));
        }

        disciplineTable.insert(sportDiscipline);

        return true;
    }

    public <T extends Model> boolean insertInto(Class<T> itemClass, T item) {
        Table<T> table = this.selector.getCorrespondingTable(itemClass);

        return this.insertIntoTable(table, item);
    }


    public <T extends Model> boolean insertIntoTable(Table<T> itemTable, T item) {
        if (!this.selector.existsInTable(itemTable, item)) {
            itemTable.insert(item);
            return true;
        }

        return false;
    }
}
