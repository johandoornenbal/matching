package info.matchingservice.dom.Api.Viewmodels;

import info.matchingservice.dom.Actor.Person;
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
            final CommunicationChannels communicationChannels
    ) {
        super(person);
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.middleName = person.getMiddleName();
        this.birthDay = person.getDateOfBirth().toString();
        this.roles = person.getRoles();
        this.pictureLink = person.getPictureLink();
        this.profile = new PersonProfileViewModel(person);
        List<DemandViewModel> demands = new ArrayList<>();
        for (Demand demand : person.getCollectDemands()) {
            DemandViewModel viewModel = new DemandViewModel(demand);
            demands.add(viewModel);
        }
        this.demands = demands;

        List<SupplyViewModel> supplies = new ArrayList<>();
        for (Supply supply : person.getCollectSupplies()) {
            SupplyViewModel viewModel = new SupplyViewModel(supply);
            supplies.add(viewModel);
        }
        this.supplies = supplies;

        List<AssessmentViewModel> assessmentsReceivedByActor = new ArrayList<>();
        for (Assessment assessment : person.getCollectAssessmentsReceivedByActor()) {
            AssessmentViewModel viewmodel = new AssessmentViewModel(assessment);
            assessmentsReceivedByActor.add(viewmodel);
        }
        this.assessmentsReceivedByActor = assessmentsReceivedByActor;

        List<CommunicationChannelViewModel> addresses = new ArrayList<>();
        List<CommunicationChannelViewModel> emailAddresses = new ArrayList<>();
        List<CommunicationChannelViewModel> phones = new ArrayList<>();

        try {
            Address address = (Address) communicationChannels.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.ADDRESS_MAIN).get(0);
            this.addressMain = address.getAddress();
            this.postalCodeMain = address.getPostalCode();
            this.townMain = address.getTown();
        } catch (Exception e) {
            // Ignore
        }

        try {
            Email email = (Email) communicationChannels.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.EMAIL_MAIN).get(0);
            this.emailMain = email.getEmail();
        } catch (Exception e) {
            // Ignore
        }

        try {
            Phone phone = (Phone) communicationChannels.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.PHONE_MAIN).get(0);
            this.phoneMain = phone.getPhoneNumber();
        } catch (Exception e) {
            // Ignore
        }
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

    //region > birthDay (property)
    private String birthDay;

    @MemberOrder(sequence = "5")
    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(final String birthDay) {
        this.birthDay = birthDay;
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

    //region > pictureLink (property)
    private String pictureLink;

    @MemberOrder(sequence = "7")
    public String getPictureLink() {
        return pictureLink;
    }

    public void setPictureLink(final String pictureLink) {
        this.pictureLink = pictureLink;
    }
    //endregion

    //region > addressMain (property)
    private String addressMain;

    @MemberOrder(sequence = "1")
    public String getAddressMain() {
        return addressMain;
    }

    public void setAddressMain(final String addressMain) {
        this.addressMain = addressMain;
    }
    //endregion

    //region > postalCodeMain (property)
    private String postalCodeMain;

    @MemberOrder(sequence = "1")
    public String getPostalCodeMain() {
        return postalCodeMain;
    }

    public void setPostalCodeMain(final String postalCodeMain) {
        this.postalCodeMain = postalCodeMain;
    }
    //endregion

    //region > townMain (property)
    private String townMain;

    @MemberOrder(sequence = "1")
    public String getTownMain() {
        return townMain;
    }

    public void setTownMain(final String townMain) {
        this.townMain = townMain;
    }
    //endregion

    //region > emailMain (property)
    private String emailMain;

    @MemberOrder(sequence = "1")
    public String getEmailMain() {
        return emailMain;
    }

    public void setEmailMain(final String emailMain) {
        this.emailMain = emailMain;
    }
    //endregion

    //region > phoneMain (property)
    private String phoneMain;

    @MemberOrder(sequence = "1")
    public String getPhoneMain() {
        return phoneMain;
    }

    public void setPhoneMain(final String phoneMain) {
        this.phoneMain = phoneMain;
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
    private List<SupplyViewModel> supplies;

    @MemberOrder(sequence = "1")
    public List<SupplyViewModel> getSupplies() {
        return supplies;
    }

    public void setSupplies(final List<SupplyViewModel> supplies) {
        this.supplies = supplies;
    }
    //endregion

    //region > demands (property)
    private List<DemandViewModel> demands;

    @MemberOrder(sequence = "1")
    public List<DemandViewModel> getDemands() {
        return demands;
    }

    public void setDemands(final List<DemandViewModel> demands) {
        this.demands = demands;
    }
    //endregion

    //region > AssessmentsReceivedByActor (property)
    private List<AssessmentViewModel> assessmentsReceivedByActor;

    @MemberOrder(sequence = "1")
    public List<AssessmentViewModel> getAssessmentsReceivedByActor() {
        return assessmentsReceivedByActor;
    }

    public void setAssessmentsReceivedByActor(final List<AssessmentViewModel> assessmentsReceivedByActor) {
        this.assessmentsReceivedByActor = assessmentsReceivedByActor;
    }
    //endregion



}
