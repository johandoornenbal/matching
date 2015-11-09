package info.matchingservice.dom.Api.Viewmodels;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonalContact;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.CommunicationChannels.*;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jodo on 01/11/15.
 */
@DomainObject(nature = Nature.VIEW_MODEL)
public class PersonViewModel extends ApiAbstractViewModel {

    public PersonViewModel() {
    }

    public PersonViewModel(
            final Person person,
            final Api api
    ) {
        super(person);
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.middleName = person.getMiddleName();
        this.dateOfBirth = person.getDateOfBirth().toString();
        this.roles = person.getRoles();
        this.imageUrl = person.getImageUrl();

        List<Integer> demands = new ArrayList<>();
        for (Demand demand : api.getDemandsForPerson(person)) {
                demands.add(demand.getIdAsInt());
        }
        this.demands = demands;

        List<Integer> supplies = new ArrayList<>();
        for (Supply supply : api.getSuppliesForPerson(person)) {
                supplies.add(supply.getIdAsInt());
        }
        this.supplies = supplies;

        List<Integer> assessmentsReceived = new ArrayList<>();
        for (Assessment assessment : api.getAssessmentsReceived(person)) {
            assessmentsReceived.add(assessment.getIdAsInt());
        }
        this.demandFeedbackReceived = assessmentsReceived;

        List<Integer> assessmentsGiven = new ArrayList<>();
        for (Assessment assessment : api.getAssessmentsGiven(person)) {
            assessmentsGiven.add(assessment.getIdAsInt());
        }
        this.demandFeedbackGiven = assessmentsGiven;

        List<Integer> personalContacts = new ArrayList<>();
        for (PersonalContact contact : api.getPersonalContacts(person)) {
            personalContacts.add(contact.getIdAsInt());
        }
        this.personalContacts = personalContacts;

        List<CommunicationChannelViewModel> addresses = new ArrayList<>();
        List<CommunicationChannelViewModel> emailAddresses = new ArrayList<>();
        List<CommunicationChannelViewModel> phones = new ArrayList<>();

        try {
            Address address = (Address) api.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.ADDRESS_MAIN).get(0);
            this.mainAddres = address.getAddress();
            this.mainPostalCode = address.getPostalCode();
            this.mainTown = address.getTown();
        } catch (Exception e) {
            // Ignore
        }

        try {
            Email email = (Email) api.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.EMAIL_MAIN).get(0);
            this.mainEmail = email.getEmail();
        } catch (Exception e) {
            // Ignore
        }

        try {
            Phone phone = (Phone) api.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.PHONE_MAIN).get(0);
            this.mainPhone = phone.getPhoneNumber();
        } catch (Exception e) {
            // Ignore
        }

        this.actions = api.getActionsForPerson(person);

    }



    //region > firstName (property)
    private String firstName;

    @MemberOrder(sequence = "2")
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }
    //endregion

    //region > lastName (property)
    private String lastName;

    @MemberOrder(sequence = "3")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }
    //endregion

    //region > middleName (property)
    private String middleName;

    @MemberOrder(sequence = "4")
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(final String middleName) {
        this.middleName = middleName;
    }
    //endregion

    //region > dateOfBirth (property)
    private String dateOfBirth;

    @MemberOrder(sequence = "5")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(final String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    //endregion

    //region > roles (property)
    private String roles;

    @MemberOrder(sequence = "6")
    public String getRoles() {
        return roles;
    }

    public void setRoles(final String role) {
        this.roles = role;
    }
    //endregion

    //region > imageUrl (property)
    private String imageUrl;

    @MemberOrder(sequence = "7")
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }
    //endregion

    //region > mainAddres (property)
    private String mainAddres;

    @MemberOrder(sequence = "1")
    public String getMainAddres() {
        return mainAddres;
    }

    public void setMainAddres(final String mainAddres) {
        this.mainAddres = mainAddres;
    }
    //endregion

    //region > mainPostalCode (property)
    private String mainPostalCode;

    @MemberOrder(sequence = "1")
    public String getMainPostalCode() {
        return mainPostalCode;
    }

    public void setMainPostalCode(final String mainPostalCode) {
        this.mainPostalCode = mainPostalCode;
    }
    //endregion

    //region > mainTown (property)
    private String mainTown;

    @MemberOrder(sequence = "1")
    public String getMainTown() {
        return mainTown;
    }

    public void setMainTown(final String mainTown) {
        this.mainTown = mainTown;
    }
    //endregion

    //region > emailMain (property)
    private String mainEmail;

    @MemberOrder(sequence = "1")
    public String getMainEmail() {
        return mainEmail;
    }

    public void setMainEmail(final String mainEmail) {
        this.mainEmail = mainEmail;
    }
    //endregion

    //region > mainPhone (property)
    private String mainPhone;

    @MemberOrder(sequence = "1")
    public String getMainPhone() {
        return mainPhone;
    }

    public void setMainPhone(final String mainPhone) {
        this.mainPhone = mainPhone;
    }
    //endregion

    //region > profile (property)
    private PersonProfileViewModel profile;

    @MemberOrder(sequence = "8")
    public PersonProfileViewModel getProfile() {
        return profile;
    }

    public void setProfile(final PersonProfileViewModel profile) {
        this.profile = profile;
    }
    //endregion

    //region > addresses (property)
    private List<CommunicationChannelViewModel> addresses;

    @MemberOrder(sequence = "1")
    public List<CommunicationChannelViewModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(final List<CommunicationChannelViewModel> addresses) {
        this.addresses = addresses;
    }
    //endregion

    //region > emailAddresses (property)
    private List<CommunicationChannelViewModel> emailAddresses;

    @MemberOrder(sequence = "1")
    public List<CommunicationChannelViewModel> getEmailAddresses() {
        return emailAddresses;
    }

    public void setEmailAddresses(final List<CommunicationChannelViewModel> emailAddresses) {
        this.emailAddresses = emailAddresses;
    }
    //endregion

    //region > phones (property)
    private List<CommunicationChannelViewModel> phones;

    @MemberOrder(sequence = "1")
    public List<CommunicationChannelViewModel> getPhones() {
        return phones;
    }

    public void setPhones(final List<CommunicationChannelViewModel> phones) {
        this.phones = phones;
    }
    //endregion


    //region > demands (property)
    private List<Integer> supplies;

    @MemberOrder(sequence = "1")
    public List<Integer> getSupplies() {
        return supplies;
    }

    public void setSupplies(final List<Integer> supplies) {
        this.supplies = supplies;
    }
    //endregion

    //region > demands (property)
    private List<Integer> demands;

    @MemberOrder(sequence = "1")
    public List<Integer> getDemands() {
        return demands;
    }

    public void setDemands(final List<Integer> demands) {
        this.demands = demands;
    }
    //endregion

    //region > demandFeedbackReceived (property)
    private List<Integer> demandFeedbackReceived;

    @MemberOrder(sequence = "1")
    public List<Integer> getDemandFeedbackReceived() {
        return demandFeedbackReceived;
    }

    public void setDemandFeedbackReceived(final List<Integer> demandFeedbackReceived) {
        this.demandFeedbackReceived = demandFeedbackReceived;
    }
    //endregion

    //region > demandFeedbackGiven (property)
    private List<Integer> demandFeedbackGiven;

    @MemberOrder(sequence = "1")
    public List<Integer> getDemandFeedbackGiven() {
        return demandFeedbackGiven;
    }

    public void setDemandFeedbackGiven(final List<Integer> demandFeedbackGiven) {
        this.demandFeedbackGiven = demandFeedbackGiven;
    }
    //endregion

    //region > personalContacts (property)
    private List<Integer> personalContacts;

    @MemberOrder(sequence = "1")
    public List<Integer> getPersonalContacts() {
        return personalContacts;
    }

    public void setPersonalContacts(final List<Integer> personalContacts) {
        this.personalContacts = personalContacts;
    }
    //endregion

    //region > actions (property)
    private List<String> actions;

    @MemberOrder(sequence = "1")
    public List<String> getActions() {
        return actions;
    }

    public void setActions(final List<String> actions) {
        this.actions = actions;
    }
    //endregion



}
