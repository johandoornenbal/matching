package info.matchingservice.dom.Api.Viewmodels;

import java.util.ArrayList;
import java.util.List;

import org.apache.isis.applib.annotation.DomainObject;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Nature;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonalContact;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.CommunicationChannels.Address;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannel;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import info.matchingservice.dom.CommunicationChannels.Email;
import info.matchingservice.dom.CommunicationChannels.Phone;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatch;

/**
 * Created by jodo on 01/11/15.
 *
 * NOTE: In order to preserve or guard business logic all (collection) data should be retrieved via Api
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
        this.assessmentsReceived = assessmentsReceived;

        List<Integer> assessmentsGiven = new ArrayList<>();
        for (Assessment assessment : api.getAssessmentsGiven(person)) {
            assessmentsGiven.add(assessment.getIdAsInt());
        }
        this.assesmentsGiven = assessmentsGiven;

        //all assessments
        List<Integer> assessments = new ArrayList<>();
        assessments.addAll(assessmentsReceived);
        assessments.addAll(assessmentsGiven);
        this.assessments = assessments;

        List<Integer> personalContacts = new ArrayList<>();
        for (PersonalContact contact : api.getPersonalContacts(person)) {
            personalContacts.add(contact.getIdAsInt());
        }
        this.personalContacts = personalContacts;

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

        List<Integer> communicationChannels = new ArrayList<>();
        for (CommunicationChannel channel : api.getCommunicationchannelsForPerson(person)) {
            communicationChannels.add(channel.getIdAsInt());
        }
        this.communicationChannels = communicationChannels;

        // all profilematches
        List<Integer> profileMatches = new ArrayList<>();
        for (ProfileMatch profileMatch : api.getProfileMatchesForPerson(person)){
            profileMatches.add(profileMatch.getIdAsInt());
        }
        this.profileMatches = profileMatches;

        List<Integer> profileMatchesOwned = new ArrayList<>();
        for (ProfileMatch profileMatch : api.getProfileMatchesOwnedByPerson(person)){
            profileMatchesOwned.add(profileMatch.getIdAsInt());
        }
        this.profileMatchesOwned = profileMatchesOwned;

        List<Integer> profileMatchesReferring = new ArrayList<>();
        for (ProfileMatch profileMatch : api.getProfileMatchesReferringToPerson(person)){
            profileMatchesReferring.add(profileMatch.getIdAsInt());
        }
        this.profileMatchesReferring = profileMatchesReferring;

        this.actions = api.getActionsForPerson(person);

        this.activated = person.isActivated();

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

    //region > assessmentsReceived (property)
    private List<Integer> assessmentsReceived;

    @MemberOrder(sequence = "1")
    public List<Integer> getAssessmentsReceived() {
        return assessmentsReceived;
    }

    public void setAssessmentsReceived(final List<Integer> assessmentsReceived) {
        this.assessmentsReceived = assessmentsReceived;
    }
    //endregion

    //region > assessments (property)
    private List<Integer> assessments;

    @MemberOrder(sequence = "1")
    public List<Integer> getAssessments() {
        return assessments;
    }

    public void setAssessments(final List<Integer> assessments) {
        this.assessments = assessments;
    }
    //endregion

    //region > assesmentsGiven (property)
    private List<Integer> assesmentsGiven;

    @MemberOrder(sequence = "1")
    public List<Integer> getAssesmentsGiven() {
        return assesmentsGiven;
    }

    public void setAssesmentsGiven(final List<Integer> assesmentsGiven) {
        this.assesmentsGiven = assesmentsGiven;
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

    //region > communicationChannels (property)
    private List<Integer> communicationChannels;

    @MemberOrder(sequence = "1")
    public List<Integer> getCommunicationChannels() {
        return communicationChannels;
    }

    public void setCommunicationChannels(final List<Integer> communicationChannels) {
        this.communicationChannels = communicationChannels;
    }
    //endregion

    //region > profileMatches (property)
    private List<Integer> profileMatches;

    @MemberOrder(sequence = "1")
    public List<Integer> getProfileMatches() {
        return profileMatches;
    }

    public void setProfileMatches(final List<Integer> profileMatches) {
        this.profileMatches = profileMatches;
    }
    //endregion

    //region > profileMatchesOwned (property)
    private List<Integer> profileMatchesOwned;

    @MemberOrder(sequence = "1")
    public List<Integer> getProfileMatchesOwned() {
        return profileMatchesOwned;
    }

    public void setProfileMatchesOwned(final List<Integer> profileMatchesOwned) {
        this.profileMatchesOwned = profileMatchesOwned;
    }
    //endregion

    //region > profileMatchesReferring (property)
    private List<Integer> profileMatchesReferring;

    @MemberOrder(sequence = "1")
    public List<Integer> getProfileMatchesReferring() {
        return profileMatchesReferring;
    }

    public void setProfileMatchesReferring(final List<Integer> profileMatchesReferring) {
        this.profileMatchesReferring = profileMatchesReferring;
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

    //region > activated (property)
    private Boolean activated;

    @MemberOrder(sequence = "1")
    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(final Boolean activated) {
        this.activated = activated;
    }
    //endregion



}
