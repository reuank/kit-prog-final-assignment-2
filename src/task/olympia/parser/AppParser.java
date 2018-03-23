package task.olympia.parser;

import task.exceptions.ParserException;
import task.olympia.models.*;
import task.olympia.parser.types.*;

/**
 * Holds instances of the parsers that belong to the olympia and passes the data to them.
 */
public class AppParser {
    private IocCodeParser iocCodeParser = new IocCodeParser();
    private SportsVenueParser sportsVenueParser = new SportsVenueParser();
    private OlympicSportParser olympicSportParser = new OlympicSportParser();
    private AthleteParser athleteParser = new AthleteParser();
    private CompetitionParser competitionParser = new CompetitionParser();

    public IocCode parseIocCode(String... args) throws ParserException {
        return iocCodeParser.parse(args);
    }

    public SportsVenue parseSportsVenue(String... args) throws ParserException {
        return sportsVenueParser.parse(args);
    }

    public OlympicSport parseOlympicSport(String... args) throws ParserException {
        return olympicSportParser.parse(args);
    }

    public Athlete parseAthlete(String... args) throws ParserException {
        return athleteParser.parse(args);
    }

    public Competition parseCompetition(String... args) throws ParserException {
        return competitionParser.parse(args);
    }
}