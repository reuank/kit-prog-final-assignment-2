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

import static task.lang.Message.IOC_CODE;
import static task.lang.Message.NOT_EXISTENT;
import static task.lang.Message.OLYMPIC_SPORT;

/**
 * The whole application. It holds the database and a selector and an inserter that can read / write in the database.
 * The app provides public access to specific parts of the database.
 */
public class OlympiaApplication {
    private AppParser appParser;
    private Database database;
    private AppSerializer appSerializer;
    private InputValidator inputValidator;
    private Session session;

    private Selector selector;
    private Inserter inserter;

    /**
     * Instantiates a new application
     *
     * @param db The database.
     * @param parser The parser.
     * @param ser The serializer.
     * @param val The validator
     * @param sess The session object.
     */
    public OlympiaApplication(Database db, AppParser parser, AppSerializer ser, InputValidator val, Session sess) {
        this.appParser = parser;
        this.database = db;
        this.appSerializer = ser;
        this.inputValidator = val;
        this.session = sess;

        this.selector = new Selector(this.database);
        this.inserter = new Inserter(this.database, this.selector);
    }


    /**
     * @return - returns the appParser
     **/
    public AppParser getParser() {
        return appParser;
    }

    /**
     * @return - returns the database
     **/
    public Database getDatabase() {
        return database;
    }

    /**
     * @return - returns the appSerializer
     **/
    public AppSerializer getSerializer() {
        return appSerializer;
    }

    /**
     * Sets the database
     *
     * @param database - the new database
     **/
    public void setDatabase(Database database) {
        this.database = database;
    }

    /**
     * @return - returns the inputValidator
     **/
    public InputValidator getInputValidator() {
        return inputValidator;
    }

    /**
     * @return - returns the session
     **/
    public Session getSession() {
        return session;
    }


    /* ------------- ADD DATA ------------- */
    /**
     * Adds a new IOC code if possible.
     *
     * @param iocCode the ioc code.
     * @return - returns true if successful.
     * @throws DatabaseException thrown if the insertion went wrong.
     */
    public boolean addIocCode(IocCode iocCode) throws DatabaseException {
        return this.inserter.uniqueInsert(IocCode.class, iocCode, IOC_CODE);
    }

    /**
     * Adds a new Sports venue if possible.
     *
     * @param sportsVenue the sports Venue.
     * @return - returns true if successful.
     * @throws DatabaseException thrown if the insertion went wrong.
     */
    public boolean addSportsVenue(SportsVenue sportsVenue) throws DatabaseException {
        return this.inserter.insertSportsVenue(sportsVenue);
    }

    /**
     * Adds a new olympic sport if possible.
     *
     * @param olympicSport the olympic Sport.
     * @return - returns true if successful.
     * @throws DatabaseException thrown if the insertion went wrong.
     */
    public boolean addOlympicSport(OlympicSport olympicSport) throws DatabaseException {
        return this.inserter.uniqueInsert(OlympicSport.class, olympicSport, OLYMPIC_SPORT);
    }

    /**
     * Adds a new athlete if possible.
     *
     * @param athlete the athlete.
     * @return - returns true if successful.
     * @throws DatabaseException thrown if the insertion went wrong.
     */
    public boolean addAthlete(Athlete athlete) throws DatabaseException {
        return this.inserter.insertAthlete(athlete);
    }

    /**
     * Adds a new competition if possible.
     *
     * @param competition the competition.
     * @return - returns true if successful.
     * @throws DatabaseException thrown if the insertion went wrong.
     */
    public boolean addCompetition(Competition competition) throws DatabaseException {
        return this.inserter.insertCompetition(competition);
    }


    /* ------------- GETTING SORTED DATA ------------- */

    /**
     * @return - returns the sorted ioc code list.
     */
    public List<IocCode> getIocCodesSorted() {
        return this.selector.getIocCodesSorted();
    }

    /**
     * @return - returns the sorted sports venue list.
     * @param countryName the country name
     * @throws DatabaseException thrown if the country name is not registered.
     */
    public List<SportsVenue> getSportsVenuesSorted(String countryName) throws DatabaseException {
        return this.selector.getSportsVenuesSorted(countryName);
    }

    /**
     * @return - returns the sorted olympic sport list.
     */
    public List<OlympicSport> getOlympicSportsSorted() {
        return this.selector.getOlympicSportsSorted();
    }

    /**
     * @return - returns the athlete summary entry list.
     * @param olympicSport the olympic sport
     * @throws DatabaseException thrown if the olympic sport is not registered.
     */
    public List<AthleteSummaryEntry> getAthleteSummary(OlympicSport olympicSport) throws DatabaseException {
        return this.selector.getAthleteSummary(olympicSport);
    }

    /**
     * @return - returns the olympic medal table entry list.
     */
    public List<OlympicMedalTableEntry> getOlympiaMedalTable() {
        return this.selector.getOlympicMedalTable();
    }

    /**
     * Resets the application, thus instantiates a new database.
     */
    public void reset() {
        Database newOlympiaDatabase = new Database();
        newOlympiaDatabase.createTables(
                new Table<>(Athlete.class),
                new Table<>(Competition.class),
                new Table<>(IocCode.class),
                new Table<>(SportsVenue.class),
                new Table<>(OlympicSport.class)
        );

        this.updateDatabase(newOlympiaDatabase);
    }

    private void updateDatabase(Database newDatabase) {
        this.database = newDatabase;
        this.inserter.setDatabase(this.database);
        this.selector.setDatabase(this.database);
    }
}
