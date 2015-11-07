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
public class ActivePersonViewModel extends ApiAbstractViewModel {

    public ActivePersonViewModel() {
    }

    public ActivePersonViewModel(
            final Person person,
            final CommunicationChannels communicationChannels,
            final String apiNotes
    ) {
        super(person);
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.middleName = person.getMiddleName();
        this.birthDay = person.getDateOfBirth().toString();
        this.roles = person.getRoles();
        this.pictureUrl = person.getPictureUrl();
        this.profile = new PersonProfileViewModel(person);
        List<DemandDeepNestedViewModel> demands = new ArrayList<>();
        for (Demand demand : person.getDemands()) {
            DemandDeepNestedViewModel viewModel = new DemandDeepNestedViewModel(demand);
            demands.add(viewModel);
        }
        this.demands = demands;

        List<SupplyDeepNestedViewModel> supplies = new ArrayList<>();
        for (Supply supply : person.getSupplies()) {
            SupplyDeepNestedViewModel viewModel = new SupplyDeepNestedViewModel(supply);
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
            this.mainAddres = address.getAddress();
            this.mainPostalCode = address.getPostalCode();
            this.mainTown = address.getTown();
        } catch (Exception e) {
            // Ignore
        }

        try {
            Email email = (Email) communicationChannels.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.EMAIL_MAIN).get(0);
            this.mainEmail = email.getEmail();
        } catch (Exception e) {
            // Ignore
        }

        try {
            Phone phone = (Phone) communicationChannels.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.PHONE_MAIN).get(0);
            this.mainPhone = phone.getPhoneNumber();
        } catch (Exception e) {
            // Ignore
        }

        this.apiNotes = apiNotes;
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

    //region > pictureUrl (property)
    private String pictureUrl;

    @MemberOrder(sequence = "7")
    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(final String pictureUrl) {
        this.pictureUrl = pictureUrl;
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
    private List<SupplyDeepNestedViewModel> supplies;

    @MemberOrder(sequence = "1")
    public List<SupplyDeepNestedViewModel> getSupplies() {
        return supplies;
    }

    public void setSupplies(final List<SupplyDeepNestedViewModel> supplies) {
        this.supplies = supplies;
    }
    //endregion

    //region > demands (property)
    private List<DemandDeepNestedViewModel> demands;

    @MemberOrder(sequence = "1")
    public List<DemandDeepNestedViewModel> getDemands() {
        return demands;
    }

    public void setDemands(final List<DemandDeepNestedViewModel> demands) {
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

    //region > apiNotes (property)
    private String apiNotes;

    @MemberOrder(sequence = "1")
    public String getApiNotes() {
        return apiNotes;
    }

    public void setApiNotes(final String apiNotes) {
        this.apiNotes = apiNotes;
    }
    //endregion



}
