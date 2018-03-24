package task.olympia.operations;

import task.constructs.database.Database;
import task.constructs.database.Model;
import task.exceptions.DatabaseException;
import task.lang.Message;
import task.olympia.models.*;

import static task.lang.Message.*;

/**
 * Used to write to the database. Could be replaces by a sql interface in the future.
 */
public class Inserter {
    private Database database;
    private Selector selector;

    /**
     * Instantiates a new inserter.
     * @param database the database the inserter can write to.
     * @param selector the selector that can be user to read from the database.
     */
    public Inserter(Database database, Selector selector) {
        this.database = database;
        this.selector = selector;
    }

    /**
     * Tries to insert a new athlete.
     *
     * @param athlete the athlete.
     * @return returns true if insert was successful.
     * @throws DatabaseException thrown if something went wrong.
     */
    public boolean insertAthlete(Athlete athlete) throws DatabaseException {
        Athlete matchingAthlete = this.selector.getAthleteById(athlete.getId());

        IocCode matchingIocCode = this.selector.getIocCodeByCountryName(athlete.getCountryName());
        this.requireHard(matchingIocCode, COUNTRY_NAME);

        OlympicSport matchingOlympicSport = this.selector.getOlympicSport(athlete.getLatestOlympicSport());
        this.requireHard(matchingOlympicSport, OLYMPIC_SPORT);

        athlete.setIocCode(matchingIocCode);

        if (matchingAthlete != null) {
            if (!athlete.equals(matchingAthlete)) {
                throw new DatabaseException(Message.getOwn("this athlete exists but holds other data"));
            }

            if (matchingAthlete.practicesOlympicSport(matchingOlympicSport)) {
                throw new DatabaseException(
                        Message.getOwn(THIS_$STRING$_ALREADY_EXISTS, "athlete-olympic sport combination")
                );
            }

            matchingAthlete.assignOlympicSport(athlete.getLatestOlympicSport());
            return true;
        }

        return this.uniqueInsert(Athlete.class, athlete, ATHLETE);
    }

    /**
     * Tries to insert a new sports venue.
     *
     * @param sportsVenue the sports venue.
     * @return returns true if insert was successful.
     * @throws DatabaseException thrown if something went wrong.
     */
    public boolean insertSportsVenue(SportsVenue sportsVenue) throws DatabaseException {
        IocCode matchingIocCode = this.selector.getIocCodeByCountryName(sportsVenue.getCountryName());
        this.requireHard(matchingIocCode, COUNTRY_NAME);

        sportsVenue.setIocCode(matchingIocCode);

        return this.uniqueInsert(SportsVenue.class, sportsVenue, SPORTS_VENUE);
    }

    /**
     * Tries to insert a new competition.
     *
     * @param competition the competition.
     * @return returns true if insert was successful.
     * @throws DatabaseException thrown if something went wrong.
     */
    public boolean insertCompetition(Competition competition) throws DatabaseException {
        Athlete matchingAthlete = this.selector.getAthleteById(competition.getAthlete().getId());
        this.requireHard(matchingAthlete, ATHLETE);
        competition.setAthlete(matchingAthlete);

        IocCode matchingIocCode = this.selector.getIocCodeByCountryName(competition.getIocCode().getCountryName());
        this.requireHard(matchingIocCode, COUNTRY_NAME);
        competition.setIocCode(matchingIocCode);

        if (matchingIocCode.getYear() > competition.getYear()) {
            throw new DatabaseException(IOC_CODE_YOUNGER_ERROR);
        }

        OlympicSport matchingOlympicSport = this.selector.getOlympicSport(competition.getOlympicSport());
        this.requireHard(matchingOlympicSport, OLYMPIC_SPORT);
        competition.setOlympicSport(matchingOlympicSport);

        if (!matchingAthlete.practicesOlympicSport(matchingOlympicSport)) {
            throw new DatabaseException(ATHLETE_NOT_PRACTICES_SPORT);
        }

        this.uniqueInsert(Competition.class, competition, COMPETITION);

        return true;
    }

    /**
     * Tries to insert a unique item.
     *
     * @param itemClass the class of the item, to find the table.
     * @param item the item.
     * @param name the name of the item, for the error message.
     * @param <T> the type.
     * @return returns true if all went right.
     * @throws DatabaseException thrown if there is already such an item in the table.
     */
    public <T extends Model> boolean uniqueInsert(Class<T> itemClass, T item, Message name) throws DatabaseException {
        if (!this.insertIntoTable(itemClass, item)) {
            throw new DatabaseException(Message.get(THIS_$STRING$_ALREADY_EXISTS, name.get()));
        }

        return true;
    }

    private <T extends Model> boolean insertIntoTable(Class<T> itemClass, T item) {
        if (!this.selector.existsInTable(itemClass, item)) {
            this.database.getTable(itemClass).insert(item);
            return true;
        }

        return false;
    }

    private <T extends Model> void requireHard(T item, Message paramName) throws DatabaseException {
        if (item == null) {
            throw new DatabaseException(Message.get(NOT_EXISTENT, paramName.get()));
        }
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
