package task.olympia.operations;

import task.constructs.database.Model;
import task.exceptions.DatabaseException;
import task.lang.Message;
import task.olympia.OlympiaApplication;
import task.olympia.models.*;

import static task.lang.Message.ALREADY_EXISTS;
import static task.lang.Message.NOT_EXISTENT;

public class Inserter {
    private OlympiaApplication app;
    private Selector selector;

    public Inserter(OlympiaApplication app, Selector selector) {
        this.app = app;
        this.selector = selector;
    }

    public boolean insertAthlete(Athlete athlete) throws DatabaseException {
        Athlete matchingAthlete = this.selector.getAthleteById(athlete.getId());

        IocCode matchingIocCode = this.selector.getIocCodeByCountryName(athlete.getCountryName());
        this.requireHard(matchingIocCode, "country name");

        OlympicSport matchingOlympicSport = this.selector.getOlympicSport(athlete.getLatestOlympicSport());
        this.requireHard(matchingOlympicSport, "olympic sport");

        athlete.setIocCode(matchingIocCode);

        if (matchingAthlete != null) {
            if (!athlete.equals(matchingAthlete)) {
                throw new DatabaseException(Message.get("this athlete has exists but holds other data"));
            }

            if (matchingAthlete.practicesOlympicSport(matchingOlympicSport)) {
                throw new DatabaseException(Message.get(ALREADY_EXISTS, "athlete-olympic sport combination"));
            }

            matchingAthlete.assignOlympicSport(athlete.getLatestOlympicSport());
            return true;
        }

        return this.uniqueInsert(Athlete.class, athlete, "athlete");
    }

    public boolean insertSportsVenue(SportsVenue sportsVenue) throws DatabaseException {
        IocCode matchingIocCode = this.selector.getIocCodeByCountryName(sportsVenue.getCountryName());
        this.requireHard(matchingIocCode, "country name");

        sportsVenue.setIocCode(matchingIocCode);

        return this.uniqueInsert(SportsVenue.class, sportsVenue, "sports venue");
    }

    public boolean insertCompetition(Competition competition) throws DatabaseException {
        Athlete matchingAthlete = this.selector.getAthleteById(competition.getAthlete().getId());
        this.requireHard(matchingAthlete, "athlete");
        competition.setAthlete(matchingAthlete);

        IocCode matchingIocCode = this.selector.getIocCodeByCountryName(competition.getIocCode().getCountryName());
        this.requireHard(matchingIocCode, "country name");
        competition.setIocCode(matchingIocCode);

        if (matchingIocCode.getYear() > competition.getYear()) {
            throw new DatabaseException("the corresponding IOC code was registered after this competition");
        }

        OlympicSport matchingOlympicSport = this.selector.getOlympicSport(competition.getOlympicSport());
        this.requireHard(matchingOlympicSport, "olympic sport");
        competition.setOlympicSport(matchingOlympicSport);

        if (!matchingAthlete.practicesOlympicSport(matchingOlympicSport)) {
            throw new DatabaseException("this athlete does not practice this olympic sport");
        }

        this.uniqueInsert(Competition.class, competition, "competition");

        return true;
    }

    public <T extends Model> boolean requireHard(T item, String paramName) throws DatabaseException {
        if (item == null) {
            throw new DatabaseException(Message.get(NOT_EXISTENT, paramName));
        }

        return true;
    }

    public <T extends Model> boolean uniqueInsert(Class<T> itemClass, T item, String name) throws DatabaseException {
        if (!this.insertIntoTable(itemClass, item)) {
            throw new DatabaseException(Message.get(ALREADY_EXISTS, name));
        }

        return true;
    }

    private <T extends Model> boolean insertIntoTable(Class<T> itemClass, T item) {
        if (!this.selector.existsInTable(itemClass, item)) {
            this.app.getDatabase().getTable(itemClass).insert(item);
            return true;
        }

        return false;
    }
}
