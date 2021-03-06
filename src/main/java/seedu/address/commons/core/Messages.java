package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n\n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_HEALTHWORKER_LISTED_OVERVIEW = "%1$d health workers listed";
    public static final String MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX = "The request index "
        + "provided is invalid";
    public static final String MESSAGE_INVALID_HEALTHWORKER_DISPLAYED_INDEX = "The healthworker "
        + "index provided is invalid.";
    public static final String MESSAGE_REQUESTS_LISTED_OVERVIEW = "%1$d requests listed!";
    public static final String MESSAGE_INVALID_REQUEST_COMMAND_FORMAT = "Invalid request command "
        + "format! \n%1$s";
    public static final String MESSAGE_REQUEST_ONGOING_CANNOT_CLEAR = "There is at least one "
        + "ongoing request in the list, request list cannot be cleared.";
    public static final String MESSAGE_REQUEST_COMPLETED_CANNOT_ASSIGN = "Completed request "
        + "cannot be assigned. To make changes to a completed request, use edit instead.";

}
