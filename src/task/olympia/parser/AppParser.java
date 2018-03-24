package task.olympia.parser;

import task.exceptions.ParserException;
import task.olympia.models.*;
import task.olympia.parser.types.*;

/**
 * Holds instances of the parsers that belong to the olympia application and passes the data to them.
 */
public class AppParser {
    private IocCodeParser iocCodeParser = new IocCodeParser();
    private SportsVenueParser sportsVenueParser = new SportsVenueParser();
    private OlympicSportParser olympicSportParser = new OlympicSportParser();
    private AthleteParser athleteParser = new AthleteParser();
    private CompetitionParser competitionParser = new CompetitionParser();

    /**
     * Parses an ioc code.
     *
     * @param args the String arguments that shall be parsed.
     * @return - returns a new ioc code object.
     * @throws ParserException thrown if any errors occurred during parsing.
     */
    public IocCode parseIocCode(String... args) throws ParserException {
        return iocCodeParser.parse(args);
    }

    /**
     * Parses a sports venue.
     *
     * @param args the String arguments that shall be parsed.
     * @return - returns a new sports venue object.
     * @throws ParserException thrown if any errors occurred during parsing.
     */
    public SportsVenue parseSportsVenue(String... args) throws ParserException {
        return sportsVenueParser.parse(args);
    }

    /**
     * Parses an olympic sport.
     *
     * @param args the String arguments that shall be parsed.
     * @return - returns a new olympic sport object.
     * @throws ParserException thrown if any errors occurred during parsing.
     */
    public OlympicSport parseOlympicSport(String... args) throws ParserException {
        return olympicSportParser.parse(args);
    }

    /**
     * Parses an athlete.
     *
     * @param args the String arguments that shall be parsed.
     * @return - returns a new athlete object.
     * @throws ParserException thrown if any errors occurred during parsing.
     */
    public Athlete parseAthlete(String... args) throws ParserException {
        return athleteParser.parse(args);
    }

    /**
     * Parses a competition.
     *
     * @param args the String arguments that shall be parsed.
     * @return - returns a new competition object.
     * @throws ParserException thrown if any errors occurred during parsing.
     */
    public Competition parseCompetition(String... args) throws ParserException {
        return competitionParser.parse(args);
    }
}