package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditHealthWorkerCommand.EditHealthWorkerDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.model.person.Phone;
import seedu.address.model.person.healthworker.HealthWorker;
import seedu.address.model.tag.Tag;

public class EditHealthWorkerDescriptorBuilder {

    private EditHealthWorkerDescriptor descriptor;

    public EditHealthWorkerDescriptorBuilder() {
        this.descriptor = new EditHealthWorkerDescriptor();
    }

    public EditHealthWorkerDescriptorBuilder(EditHealthWorkerDescriptor descriptor) {
        this.descriptor = new EditHealthWorkerDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditHealthWorkerDescriptor} with fields containing {@code healthWorker}'s details
     */
    public EditHealthWorkerDescriptorBuilder(HealthWorker healthWorker) {
        descriptor = new EditHealthWorkerDescriptor();
        descriptor.setName(healthWorker.getName());
        descriptor.setPhone(healthWorker.getPhone());
        descriptor.setEmail(healthWorker.getEmail());
        descriptor.setAddress(healthWorker.getAddress());
        descriptor.setNric(healthWorker.getNric());
        descriptor.setTags(healthWorker.getTags());
        descriptor.setOrganization(healthWorker.getOrganization());
        descriptor.setSkills(healthWorker.getSkills());
    }

    /**
     * Sets the {@code Name} of the {@code EditHealthWorkerDescriptorBuilder} that we are building.
     */
    public EditHealthWorkerDescriptorBuilder withName(String name) {
        this.descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditHealthWorkerDescriptorBuilder} that we are building.
     */
    public EditHealthWorkerDescriptorBuilder withPhone(String phone) {
        this.descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditHealthWorkerDescriptorBuilder} that we are building.
     */
    public EditHealthWorkerDescriptorBuilder withEmail(String email) {
        this.descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditHealthWorkerDescriptorBuilder} that we are building.
     */
    public EditHealthWorkerDescriptorBuilder withAddress(String address) {
        this.descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditHealthWorkerDescriptorBuilder}
     * that we are building.
     */
    public EditHealthWorkerDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        this.descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditHealthWorkerDescriptorBuilder} that we are building.
     */
    public EditHealthWorkerDescriptorBuilder withNric(String nric) {
        this.descriptor.setNric(new Nric(nric));
        return this;
    }

    public EditHealthWorkerDescriptor build() {
        return this.descriptor;
    }
}

