package seedu.address.logic.commands.request;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestDate;
import seedu.address.model.request.RequestStatus;
import seedu.address.model.tag.Condition;

/**
 * Edits an order in the request book.
 */
public class EditRequestCommand extends EditCommand implements RequestCommand {

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + COMMAND_OPTION
        + ": Edits the details of the order identified "
        + "by the index number used in the displayed request book. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + EDIT_COMMAND_PARAMETERS
        + "Example: " + COMMAND_WORD + " " + COMMAND_OPTION
        + EDIT_COMMAND_EXAMPLE;

    public static final String MESSAGE_EDIT_REQUEST_SUCCESS = "Edited Request: %1$s";

    private final EditRequestDescriptor editRequestDescriptor;

    /**
     * @param index of the request in the filtered request list to edit
     * @param editRequestDescriptor details to edit the request with
     */
    public EditRequestCommand(Index index, EditRequestDescriptor editRequestDescriptor) {
        super(index);
        requireNonNull(editRequestDescriptor);

        this.editRequestDescriptor = new EditRequestDescriptor(editRequestDescriptor);
    }

    /**
     * Creates and returns a {@code Request} with the details of {@code requestToEdit}
     * edited with {@code editRequestDescriptor}.
     */
    private static Request createEditedRequest(Request requestToEdit, EditRequestDescriptor editRequestDescriptor) {
        assert requestToEdit != null;

        Name updatedName = editRequestDescriptor.getName().orElse(requestToEdit.getName());
        Phone updatedPhone = editRequestDescriptor.getPhone().orElse(requestToEdit.getPhone());
        Address updatedAddress = editRequestDescriptor.getAddress().orElse(requestToEdit.getAddress());
        RequestDate updatedRequestDate = editRequestDescriptor.getDate().orElse(requestToEdit.getRequestDate());
        Nric updatedNric = editRequestDescriptor.getNric().orElse(requestToEdit.getNric());
        RequestStatus updatedRequestStatus = requestToEdit.getRequestStatus();
        Set<Condition> updatedConditions = editRequestDescriptor.getConditions().orElse(requestToEdit
                .getConditions());
        String updatedHealthWorker;
        if (requestToEdit.getHealthStaff() != null) {
            updatedHealthWorker = requestToEdit.getHealthStaff();
        } else {
            updatedHealthWorker = null;
        }

        if (updatedHealthWorker == null) {
            return new Request(updatedName, updatedNric, updatedPhone, updatedAddress,
                updatedRequestDate, updatedConditions, updatedRequestStatus);
        } else {
            return new Request(updatedName, updatedNric, updatedPhone, updatedAddress,
                updatedRequestDate, updatedConditions, updatedRequestStatus, updatedHealthWorker);
        }

    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireAllNonNull(model, history);
        List<Request> lastShownList = model.getFilteredRequestList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_REQUEST_DISPLAYED_INDEX);
        }

        Request requestToEdit = lastShownList.get(index.getZeroBased());
        Request editedRequest = createEditedRequest(requestToEdit, editRequestDescriptor);

        if (!requestToEdit.isSameRequest(editedRequest) && model.hasRequest(editedRequest)) {
            throw new CommandException(MESSAGE_DUPLICATE_REQUEST);
        }

        edit(model, requestToEdit, editedRequest);
        return new CommandResult(String.format(MESSAGE_EDIT_REQUEST_SUCCESS, editedRequest));
        // List<Request> lastShownList = model.getF()
    }

    @Override
    public void edit(Model model, Object toEdit, Object edited) {
        model.setRequest((Request) toEdit, (Request) edited);
        model.updateFilteredRequestList(Model.PREDICATE_SHOW_ALL_REQUESTS);
        //model.commitRequestBook();
        commitRequestBook(model);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EditRequestCommand)) {
            return false;
        }

        EditRequestCommand e = (EditRequestCommand) other;
        return index.equals(e.index) && editRequestDescriptor.equals(((EditRequestCommand) other)
            .editRequestDescriptor);
    }

    /**
     * Stores the details to edit the request with. Each non-empty field value will replace the
     * corresponding field value of the order.
     */
    public static class EditRequestDescriptor {
        private Name name;
        private Phone phone;
        private Address address;
        private RequestDate requestDate;
        private Nric nric;
        private Set<Condition> conditions;
        private String healthWorker;

        public EditRequestDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code request} is used internally.
         */
        public EditRequestDescriptor(EditRequestDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setAddress(toCopy.address);
            setDate(toCopy.requestDate);
            setNric(toCopy.nric);
            setConditions(toCopy.conditions);
            setHealthWorker(toCopy.healthWorker);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, address, requestDate, conditions);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<RequestDate> getDate() {
            return Optional.ofNullable(requestDate);
        }

        public void setDate(RequestDate requestDate) {
            this.requestDate = requestDate;
        }

        public Optional<Nric> getNric() {
            return Optional.ofNullable(nric);
        }

        public void setNric(Nric nric) {
            this.nric = nric;
        }

        public Optional<String> getHealthWorker() {
            return Optional.ofNullable(healthWorker);
        }

        public void setHealthWorker(String healthWorker) {
            this.healthWorker = healthWorker;
        }

        /**
         * Returns an unmodifiable condition set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code conditions} is null
         */
        public Optional<Set<Condition>> getConditions() {
            return (conditions != null) ? Optional.of(Collections.unmodifiableSet(conditions))
                : Optional.empty();
        }

        /**
         * Sets {@code conditions} to this object's {@code conditions}
         * A defensive copy of {@code conditions} is used internally.
         */
        public void setConditions(Set<Condition> conditions) {
            this.conditions = (conditions != null) ? new HashSet<>(conditions) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditRequestDescriptor)) {
                return false;
            }

            // state check
            EditRequestDescriptor editRequestDescriptor = (EditRequestDescriptor) other;

            return getName().equals(editRequestDescriptor.getName())
                && getPhone().equals(editRequestDescriptor.getPhone())
                && getNric().equals(editRequestDescriptor.getNric())
                && getAddress().equals(editRequestDescriptor.getAddress())
                && getDate().equals(editRequestDescriptor.getDate())
                && getConditions().equals(editRequestDescriptor.getConditions())
                && getHealthWorker().equals(editRequestDescriptor.getHealthWorker());
        }
    }
}
