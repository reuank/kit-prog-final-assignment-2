package task.olympia;

import task.constructs.database.Database;
import task.constructs.database.Table;
import task.exceptions.DatabaseException;
import task.olympia.models.*;
import task.olympia.operations.Inserter;
import task.olympia.operations.Selector;
import task.olympia.parser.AppParser;
import task.olympia.serializers.AppSerializer;
import task.userinterface.auth.Session;
import task.userinterface.validation.InputValidator;

import java.util.List;

public class OlympiaApplication {
    private AppParser appParser;
    private Database database;
    private AppSerializer appSerializer;
    private InputValidator inputValidator;
    private Session session;

    private Selector selector;
    private Inserter inserter;

    public OlympiaApplication(Database db, AppParser parser, AppSerializer serializer, InputValidator validator, Session session) {
        this.appParser = parser;
        this.database = db;
        this.appSerializer = serializer;
        this.inputValidator = validator;
        this.session = session;

        this.selector = new Selector(this);
        this.inserter = new Inserter(this, this.selector);
    }

    public AppParser getParser() {
        return appParser;
    }

    public Database getDatabase() {
        return database;
    }

    public AppSerializer getSerializer() {
        return appSerializer;
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

    /* ------------- ADDING DATA ------------- */
    public boolean addIocCode(IocCode iocCode) throws DatabaseException {
        return this.inserter.addIocCode(iocCode);
    }

    public boolean addSportsVenue(SportsVenue sportsVenue) throws DatabaseException {
       return this.inserter.addSportsVenue(sportsVenue);
    }

    public boolean addOlympicSport(SportType sportType, SportDiscipline sportDiscipline) throws DatabaseException {
        return this.inserter.addOlympicSport(sportType, sportDiscipline);
    }


    /* ------------- GETTING DATA ------------- */
    public IocCode getIocCode(String countryName) {
        return this.selector.getIocCode(countryName);
    }

    public List<IocCode> getIocCodesSorted() {
        return this.selector.getIocCodesSorted();
    }

    public List<SportsVenue> getSportsVenuesSorted(String countryName) {
        return this.selector.getSportsVenuesSorted(countryName);
    }

    public List<SportDiscipline> getOlympicSportsSorted() {
        return this.selector.getOlympicSportsSorted();
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
