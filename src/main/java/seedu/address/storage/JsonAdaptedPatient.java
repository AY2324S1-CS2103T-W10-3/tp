package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Appointmentdate;
import seedu.address.model.person.Birthdate;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Service;
import seedu.address.model.person.patients.Patient;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPatient {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;

    private final String gender;
    private final String birthdate;
    private final String appointment;

    private final String service;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();


    /**
     * Constructs a {@code JsonAdaptedPatient} with the given patient details.
     */
    @JsonCreator
    public JsonAdaptedPatient(@JsonProperty("name") String name,
        @JsonProperty("phone") String phone,
        @JsonProperty("birthdate") String birthdate,
        @JsonProperty("gender") String gender,
        @JsonProperty("appointmentdate") String appointment,
        @JsonProperty("service") String service,
        @JsonProperty("address") String address,
        @JsonProperty("email") String email,
        @JsonProperty("tags") List<JsonAdaptedTag> tags) {

        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.birthdate = birthdate;
        this.gender = gender;
        this.appointment = appointment;
        this.service = service;
        if (tags != null) {
            this.tags.addAll(tags);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPatient(Patient source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        gender = source.getGender().value;
        birthdate = source.getBirthdate().value;
        appointment = source.getAppointmentdate().value;
        service = source.getService().value;
        tags.addAll(source.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted
     *                               person.
     */
    public Patient toModelType() throws IllegalValueException {
        final List<Tag> patientags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            patientags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        if (birthdate == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Birthdate.class.getSimpleName()));
        }
        if (!Birthdate.isValidDate(birthdate)) {
            throw new IllegalValueException(Birthdate.MESSAGE_CONSTRAINTS);
        }
        final Birthdate modelBirthdate = new Birthdate(birthdate);

        if (gender == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Gender.class.getSimpleName()));
        }
        if (!Gender.isValidGender(gender)) {
            throw new IllegalValueException(Gender.MESSAGE_CONSTRAINTS);
        }
        final Gender modelGender = new Gender(gender);

        if (appointment == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Appointmentdate.class.getSimpleName()));
        }
        if (!Appointmentdate.isValidDate(appointment)) {
            throw new IllegalValueException(Appointmentdate.MESSAGE_CONSTRAINTS);
        }
        final Appointmentdate modelAppointment = new Appointmentdate(appointment);

        if (service == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Service.class.getSimpleName()));
        }
        if (!Service.isValidService(service)) {
            throw new IllegalValueException(Service.MESSAGE_CONSTRAINTS);
        }
        final Service modelService = new Service(service);

        final Set<Tag> modelTags = new HashSet<>(patientags);

        return new Patient(modelName, modelPhone, modelBirthdate, modelGender, modelAppointment,
            modelService,
            modelAddress, modelEmail, modelTags);
    }

}
