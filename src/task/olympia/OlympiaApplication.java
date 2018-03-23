package task.olympia;

import task.constructs.database.Database;
import task.constructs.database.Model;
import task.constructs.database.Table;
import task.exceptions.DatabaseException;
import task.lang.Message;
import task.olympia.models.*;
import task.olympia.operations.Inserter;
import task.olympia.operations.Selector;
import task.olympia.parser.AppParser;
import task.olympia.serializers.AppSerializer;
import task.userinterface.auth.Session;
import task.userinterface.validation.InputValidator;

import java.util.List;

import static task.lang.Message.ALREADY_EXISTS;
import static task.lang.Message.NOT_EXISTENT;

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

    /* ------------- ADD DATA ------------- */
    public boolean addIocCode(IocCode iocCode) throws DatabaseException {
        return this.inserter.uniqueInsert(IocCode.class, iocCode, "ioc code");
    }

    public boolean addSportsVenue(SportsVenue sportsVenue) throws DatabaseException {
        return this.inserter.insertSportsVenue(sportsVenue);
    }

    public boolean addOlympicSport(OlympicSport olympicSport) throws DatabaseException {
        return this.inserter.uniqueInsert(OlympicSport.class, olympicSport, "olympic sport");
    }

    public boolean addAthlete(Athlete athlete) throws DatabaseException {
        return this.inserter.insertAthlete(athlete);
    }

    public boolean addCompetition(Competition competition) throws DatabaseException {
        return this.inserter.insertCompetition(competition);
    }


    /* ------------- GETTING SORTED DATA ------------- */
    public List<IocCode> getIocCodesSorted() {
        return this.selector.getIocCodesSorted();
    }

    public List<SportsVenue> getSportsVenuesSorted(String countryName) throws DatabaseException {
        return this.selector.getSportsVenuesSorted(countryName);
    }

    public List<OlympicSport> getOlympicSportsSorted() {
        return this.selector.getOlympicSportsSorted();
    }

    public List<AthleteSummary> getAthleteSummary(OlympicSport olympicSport) throws DatabaseException {
        return this.selector.getAthleteSummary(olympicSport);
    }

    public void reset() {
        Database newOlympiaDatabase = new Database();
        newOlympiaDatabase.createTables(
                new Table<>(Athlete.class),
                new Table<>(Competition.class),
                new Table<>(IocCode.class),
                new Table<>(SportsVenue.class),
                new Table<>(OlympicSport.class)
        );

        this.database = newOlympiaDatabase;
    }

    public <T extends Model> void checkIfNull(T item, String itemName) throws DatabaseException {
        if (item == null) {
            throw new DatabaseException(Message.get(NOT_EXISTENT, itemName));
        }
    }
}
