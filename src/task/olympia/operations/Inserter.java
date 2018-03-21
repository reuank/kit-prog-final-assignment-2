package task.olympia.operations;

import task.constructs.database.Table;
import task.exceptions.DatabaseException;
import task.lang.Message;
import task.olympia.OlympiaApplication;
import task.olympia.models.IocCode;
import task.olympia.models.SportsVenue;

import static task.lang.Message.NOT_EXISTANT;
import static task.lang.Message.NOT_UNIQUE;

public class Inserter {
    private OlympiaApplication app;

    public Inserter(OlympiaApplication app) {
        this.app = app;
    }

    public boolean addIocCode(IocCode iocCode) throws DatabaseException {
        Table<IocCode> table = this.app.getDatabase().getTable(IocCode.class);
        if (table.anyMatch(row -> row.getId() == iocCode.getId() || row.getIocCode().equals(iocCode.getIocCode()))) {
            throw new DatabaseException(Message.get(NOT_UNIQUE, "ioc id or ioc code"));
        }

        table.insert(iocCode);
        return true;
    }


    public boolean addSportsVenue(SportsVenue sportsVenue) throws DatabaseException {
        Table<SportsVenue> table = this.app.getDatabase().getTable(SportsVenue.class);
        IocCode matchingIocCode = this.app.getIocCode(sportsVenue.getCountryName());

        if (matchingIocCode == null) {
            throw new DatabaseException(Message.get(NOT_EXISTANT, "country name"));
        }

        sportsVenue.setIocCode(matchingIocCode);
        table.insert(sportsVenue);

        return true;
    }
}
