package task.olympia;

import task.constructs.database.Database;
import task.constructs.database.Table;
import task.exceptions.DatabaseException;
import task.olympia.models.*;
import task.olympia.operations.Inserter;
import task.olympia.operations.Selector;
import task.olympia.parser.AppParser;
import task.userinterface.auth.Session;
import task.userinterface.validation.InputValidator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OlympiaApplication {
    private AppParser appParser;
    private Database database;
    private InputValidator inputValidator;
    private Session session;

    private Inserter inserter;
    private Selector selector;

    public OlympiaApplication(AppParser appParser, Database database, InputValidator inputValidator, Session session) {
        this.appParser = appParser;
        this.database = database;
        this.inputValidator = inputValidator;
        this.session = session;

        this.inserter = new Inserter(this);
        this.selector = new Selector(this);
    }

    public AppParser getParser() {
        return appParser;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
    }

    public InputValidator getInputValidator() {
        return inputValidator;
    }

    public Session getSession() {
        return session;
    }

    public boolean addIocCode(IocCode iocCode) throws DatabaseException {
        return this.inserter.addIocCode(iocCode);
    }

    public boolean addSportsVenue(SportsVenue sportsVenue) throws DatabaseException {
       return this.inserter.addSportsVenue(sportsVenue);
    }

    public IocCode getIocCode(String countryName) {
        return this.selector.getIocCode(countryName);
    }

    public List<IocCode> getIocCodes() {
        return this.selector.getIocCodes();
    }

    public void reset() {
        Database newOlympiaDatabase = new Database();
        newOlympiaDatabase.createTables(
                new Table<>(Athlete.class),
                new Table<>(Competition.class),
                new Table<>(IocCode.class),
                new Table<>(SportDiscipline.class),
                new Table<>(SportsVenue.class),
                new Table<>(SportType.class)
        );

        this.database = newOlympiaDatabase;
    }
}
