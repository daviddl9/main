package seedu.address.storage;

//import java.util.ArrayList;
import java.util.HashSet;
//import java.util.List;
import java.util.Optional;
import java.util.Set;
//import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.person.patient.Patient;
import seedu.address.model.request.Request;
import seedu.address.model.request.RequestDate;
import seedu.address.model.request.RequestStatus;
import seedu.address.model.tag.ConditionTag;
import seedu.address.model.tag.Conditions;

/**
 * Jackson-friendly version of {@link Request}.
 */
class JsonAdaptedRequest {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Request's %s field is missing!";

    private final String name;
    private final String nric;
    private final String address;
    private final String phone;
    private final String requestDate;
    private final String conditions;
    private final String requestStatus;
    private String healthWorker;

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedRequest(@JsonProperty("name") String name,
                              @JsonProperty("nric") String nric,
                              @JsonProperty("phone") String phone,
                              @JsonProperty("address") String address,
                              @JsonProperty("requestdate") String requestDate,
                              @JsonProperty("conditions") String conditions,
                              @JsonProperty("requestStatus") String requestStatus) {
        this.name = name;
        this.nric = nric;
        this.address = address;
        this.phone = phone;
        this.requestDate = requestDate;
        this.conditions = conditions;
        this.requestStatus = requestStatus;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedRequest(Request source) {
        this.name = source.getName().fullName;
        this.address = source.getAddress().value;
        this.nric = source.getNric().getNric();
        this.phone = source.getPhone().value;
        this.requestDate = source.getRequestDate().toString();
        this.requestStatus = source.getRequestStatus().toString();
        this.conditions = source.getConditions().toString();
        this.healthWorker = source.getHealthStaff();
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Request toModelType() throws IllegalValueException {

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                Name.class.getSimpleName()));
        }

        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }

        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (nric == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Nric.isValidNric(nric)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Nric modelNric = new Nric(nric);

        if (requestDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RequestDate.class.getSimpleName()));
        }

        if (!RequestDate.isValidDate(requestDate)) {
            throw new IllegalValueException(RequestDate.MESSAGE_DATE_CONSTRAINTS);
        }

        final RequestDate modelrequestDate = new RequestDate(this.requestDate);

        if (requestStatus == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RequestStatus.class.getSimpleName()));
        }

        if (!RequestStatus.isValidStatus(requestStatus)) {
            throw new IllegalValueException(RequestStatus.MESSAGE_STATUS_CONSTRAINTS);
        }

        final RequestStatus modelrequestStatus = new RequestStatus(this.requestStatus);

        Set<ConditionTag> set = new HashSet<>();
        String[] conditionsArr = this.conditions.split(" ");
        for (String condition : conditionsArr) {
            ConditionTag conditionTag = ConditionTag.parseString(condition);
            set.add(conditionTag);
        }
        final Conditions modelConditions = new Conditions(set);


        if (healthWorker == null) {
            return new Request(modelName, modelNric, modelPhone, modelAddress, modelrequestDate,
                modelConditions, modelrequestStatus);
        }
        return new Request(modelName, modelNric, modelPhone, modelAddress, modelrequestDate,
            modelConditions, modelrequestStatus, healthWorker);
    }

}
