package seedu.address.logic.parser.deliveryman;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.stream.Stream;

import seedu.address.logic.commands.healthworker.AddHealthWorkerCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.common.Name;
import seedu.address.model.deliveryman.Deliveryman;

/**
 * Parses input arguments and creates a new AddHealthWorkerCommand object
 */
public class DeliverymanAddCommandParser implements Parser<AddHealthWorkerCommand> {

    /**
     * Parses a user command into a AddHealthWorkerCommand.
     *
     * @throws ParseException
     */
    public AddHealthWorkerCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME);

        if (!arePrefixesPresent(argumentMultimap, PREFIX_NAME) || !argumentMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddHealthWorkerCommand.MESSAGE_USAGE));
        }

        Name name = ParserUtil.parseName(argumentMultimap.getValue(PREFIX_NAME).get());

        Deliveryman deliveryman = new Deliveryman(name);

        return new AddHealthWorkerCommand(deliveryman);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
