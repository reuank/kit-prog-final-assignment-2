package task.olympia.parser;

import task.exceptions.ParserException;
import task.olympia.models.IocCode;
import task.olympia.models.SportsVenue;
import task.olympia.parser.types.IocCodeParser;
import task.olympia.parser.types.SportsVenueParser;
import task.userinterface.parser.types.CommandParser;
import task.interfaces.ICommand;

/**
 * Holds instances of the parsers that belong to the olympia and passes the data to them.
 */
public class AppParser {
    IocCodeParser iocCodeParser = new IocCodeParser();
    SportsVenueParser sportsVenueParser = new SportsVenueParser();

    public IocCode parseIocCode(String... args) throws ParserException {
        return iocCodeParser.parse(args);
    }

    public SportsVenue parseSportsVenue(String... args) throws ParserException {
        return sportsVenueParser.parse(args);
    }
}