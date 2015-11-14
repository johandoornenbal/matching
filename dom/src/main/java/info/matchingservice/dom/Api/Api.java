package info.matchingservice.dom.Api;

import com.google.common.base.Objects;
import info.matchingservice.dom.Actor.*;
import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannel;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannelType;
import info.matchingservice.dom.CommunicationChannels.CommunicationChannels;
import info.matchingservice.dom.DemandSupply.*;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.Match.ProfileMatches;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElements;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.ProvidedServices.Service;
import info.matchingservice.dom.ProvidedServices.Services;
import info.matchingservice.dom.Tags.Tag;
import info.matchingservice.dom.Tags.Tags;
import info.matchingservice.dom.TrustLevel;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.value.Blob;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@DomainService()
@DomainServiceLayout()
public class Api extends AbstractFactoryAndRepository {
	
	//***************************************** activePerson ***********************//
	
	@Action(semantics=SemanticsOf.SAFE)
	public Person activePerson(){
		return persons.activePerson();
	}

	@Programmatic
	public Person findPersonById(Integer instanceId) {
		return persons.findPersonById(instanceId);
	}


	/////////

	//***************************************** updatePerson ***********************//
	@Programmatic
	public Person updatePerson(
			final Person person,
			final String firstName,
			final String middleName,
			final String lastName,
			final String dateOfBirth,
			final String imageUrl){

		//check ownership (only owner can modify Person)
		if (!currentUserName().equals(person.getOwnedBy())){
			return person;
		}

		//validate and replace values by their originals if not valid
		String firstNameUpdate;
		if (firstName.length()<1){
			firstNameUpdate = person.getFirstName();
		} else {
			firstNameUpdate = firstName;
		}

		String lastNameUpdate;
		if (lastName.length()<1){
			lastNameUpdate = person.getLastName();
		} else {
			lastNameUpdate = lastName;
		}

		LocalDate dateOfBirthUpdate = null;
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-mm-dd");
		try {
			dateOfBirthUpdate = LocalDate.parse(dateOfBirth, dtf);
		} catch (Exception e) {
			//ignore
		}
		if (dateOfBirthUpdate == null) {
			// dateOfBirth is not a valid date
			dateOfBirthUpdate=person.getDateOfBirth();
		} else {
			// dateOfBirth is a Date
			dateOfBirthUpdate = new LocalDate(dateOfBirth);
		}

		return person.updatePerson(
				firstNameUpdate,
				middleName,
				lastNameUpdate,
				dateOfBirthUpdate,
				null,
				imageUrl);
	}

	///////////

	//***************************************** Collections of Person ***********************//

	@Programmatic
	public List<Demand> getDemandsForPerson(final Person person){

		// apply business logic
		if (person.hideDemands()){
			return new ArrayList<>();
		}

		return new ArrayList<>(person.getDemands());

	}

	@Programmatic
	public List<Supply> getSuppliesForPerson(final Person person){

		// apply business logic
		if (person.hideSupplies()){
			return new ArrayList<>();
		}

		return new ArrayList<>(person.getSupplies());

	}

	@Programmatic
	public List<CommunicationChannel> findCommunicationChannelByPersonAndType(Person person, CommunicationChannelType addressMain) {
		return communicationChannels.findCommunicationChannelByPersonAndType(person,addressMain);
	}

	@Programmatic
	public List<Assessment> getAllAssessments(final Person person){
		List<Assessment> result = new ArrayList<>();
		if (!person.hideAssessmentsReceived()) {
			result.addAll(person.getAssessmentsReceived());
		}
		if (!person.hideAssessmentsGiven()) {
			result.addAll(person.getAssessmentsGiven());
		}

		return result;
	}

    @Programmatic
    public List<Assessment> getAssessmentsReceived(final Person person){
        // apply business logic
        if (person.hideAssessmentsReceived()){
            return new ArrayList<>();
        }
        return new ArrayList<>(person.getAssessmentsReceived());
    }

    @Programmatic
    public List<Assessment> getAssessmentsGiven(final Person person){
        // apply business logic
        if (person.hideAssessmentsGiven()){
            return new ArrayList<>();
        }
        return new ArrayList<>(person.getAssessmentsGiven());
    }

    @Programmatic
    public List<PersonalContact> getPersonalContacts(final Person person){
        // apply business logic
        if (person.hidePersonalContacts()){
            return new ArrayList<>();
        }
        return new ArrayList<>(person.getPersonalContacts());
    }

	@Programmatic
	public List<CommunicationChannel> getCommunicationchannelsForPerson(Person person) {
		return communicationChannels.findCommunicationChannelByPerson(person);
	}

	@Programmatic
	public List<ProfileMatch> getProfileMatchesForPerson(Person person) {
		List<ProfileMatch> profileMatches = new ArrayList<>();
		if (!person.hideSavedMatches()){
			profileMatches.addAll(person.getSavedMatches());
		}
		if (!profileMatchRepo.hideCollectProfileMatches(person)){
			profileMatches.addAll(profileMatchRepo.collectProfileMatches(person));
		}
		return profileMatches;
	}

	@Programmatic
	public List<ProfileMatch> getProfileMatchesOwnedByPerson(Person person) {
		List<ProfileMatch> profileMatches = new ArrayList<>();
		if (!person.hideSavedMatches()){
			profileMatches.addAll(person.getSavedMatches());
		}
		return profileMatches;
	}

	@Programmatic
	public List<ProfileMatch> getProfileMatchesReferringToPerson(Person person) {
		List<ProfileMatch> profileMatches = new ArrayList<>();
		if (!profileMatchRepo.hideCollectProfileMatches(person)){
			profileMatches.addAll(profileMatchRepo.collectProfileMatches(person));
		}
		return profileMatches;
	}


	///////////

	//***************************************** Actions of Person ***********************//

	@Programmatic
	public List<String> getActionsForPerson(final Person person){
		List<String> actions = new ArrayList<>();

        if (currentUserName().equals(person.getOwnedBy())) {
            String createAddres = "createAddres";
            actions.add(createAddres);
            String createEmail = "createEmail";
            actions.add(createEmail);
            String createPhone = "createPhone";
            actions.add(createPhone);
        }

		if (currentUserName().equals(person.getOwnedBy()) && person.getIsPrincipal()) {
			String createPersonsDemand = "createPersonDemand";
			actions.add(createPersonsDemand);
		}

		// do not show when already contacted
		QueryDefault<PersonalContact> query =
				QueryDefault.create(
						PersonalContact.class,
						"findPersonalContactUniqueContact",
						"ownedBy", currentUserName(),
						"contact", person);
		Boolean isContact = container.firstMatch(query) != null?
				true  : false;

		if (!currentUserName().equals(person.getOwnedBy())	&& !isContact ) {
			String addAsPersonalContact = "addAsPersonalContact";
			actions.add(addAsPersonalContact);
		}

        if (!currentUserName().equals(person.getOwnedBy())	&& isContact ) {
            String addAsPersonalContact = "removeAsPersonalContact (TODO)";
            actions.add(addAsPersonalContact);
        }

		return actions;
	}

	@Programmatic
	public Demand createPersonDemand(
			final Person person,
			final String description,
            final String summary,
            final String story,
            final String startDate,
            final String endDate){
        //check description
        if (description==null || description==""){
            return null;
        }
        //check dates
        LocalDate startDateEntry = null;
        DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-mm-dd");
        try {
            startDateEntry = LocalDate.parse(startDate, dtf);
        } catch (Exception e) {
            //ignore
        }
        if (startDateEntry == null) {
            // startDate is not a valid date
            startDateEntry=null;
        } else {
            // startDate is a Date
            startDateEntry = new LocalDate(startDate);
        }

        LocalDate endDateEntry = null;
        try {
            endDateEntry = LocalDate.parse(endDate, dtf);
        } catch (Exception e) {
            //ignore
        }
        if (endDateEntry == null) {
            // endDate is not a valid date
            endDateEntry=null;
        } else {
            // endDate is a Date
            endDateEntry = new LocalDate(endDate);
        }

        // if invalid period [endDate before startDate] set both to null
        if (startDateEntry!=null && endDateEntry!=null && endDateEntry.isBefore(startDateEntry)){
            startDateEntry=null;
            endDateEntry=null;
        }


		return demands.createDemand(
				description,
                summary,
                story,
                null,
                startDateEntry,
                endDateEntry,
                10,
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                person,
                person.getOwnedBy()
		);
	}

    //***************************************** Collections of Demand ***********************//

    @Programmatic
    public List<Profile> getProfilesForDemand(final Demand demand){
        Person demandOwner = (Person) demand.getOwner();

        // apply business logic
        if (demandOwner.hideDemands()){
            return null;
        }

        return new ArrayList<>(demand.getProfiles());
    }

	//***************************************** getProfileElementByUniqueId ***********************//

	@Action(semantics=SemanticsOf.SAFE)
	public List<ProfileElement> findProfileElementByUniqueId(final UUID uniqueItemId){
		try
		{
			return profileElements.findProfileElementByUniqueId(uniqueItemId);
		}
		catch (Exception e)
		{
			return null;
		}

	}

	public String validateFindProfileElementByUniqueId(final UUID uniqueItemId){
		// implementation of Trusted Circles Business Rule: demands are accessible only for INNER_CIRCLE

		try
		{
			if (
				// Not the owner
					!profileElements.findProfileElementByUniqueId(uniqueItemId).get(0).getOwnedBy().equals(currentUserName())

							&&

							// At least Inner Circle
							profileElements.findProfileElementByUniqueId(uniqueItemId).get(0).allowedTrustLevel(TrustLevel.INNER_CIRCLE)


					)
			{
				return "NO_ACCESS";
			}
		}
		catch (Exception e)
		{
			return "VALIDATION_FAILURE";
		}

		return null;
	}


	//------------------------------------ END getProfileByUniqueId ---------------------------//
	
	//***************************************** createPerson ***********************//
	
	@Action(semantics=SemanticsOf.NON_IDEMPOTENT)
	public Person createPerson(
            @ParameterLayout(named="firstName")
            final String firstName,
            @ParameterLayout(named="middleName")
            @Parameter(optionality=Optionality.OPTIONAL)
            final String middleName,
            @ParameterLayout(named="lastName")
            final String lastName,
            @ParameterLayout(named="dateOfBirth")
            final LocalDate dateOfBirth,
            @ParameterLayout(named="picture")
            @Parameter(optionality=Optionality.OPTIONAL)
            final Blob picture,
			@ParameterLayout(named="pictureLink")
			@Parameter(optionality=Optionality.OPTIONAL)
			final String pictureLink,
			@ParameterLayout(named="personRole")
			final PersonRoleType personRoleType
			){
		return persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, pictureLink, personRoleType);
	}
	
    @Programmatic //userName can now also be set by fixtures
    public String validateCreatePerson(
            final String firstName,
            final String middleName,
            final String lastName,
            final LocalDate dateOfBirth,
            final Blob picture,
			final String pictureLink,
			final PersonRoleType personRoleType) {
        
        QueryDefault<Person> query = 
                QueryDefault.create(
                        Person.class, 
                    "findPersonUnique", 
                    "ownedBy", currentUserName());        
        return container.firstMatch(query) != null?
        "ONE_INSTANCE_AT_MOST"        
        :null;
        
    }
    
    //------------------------------------ END createPerson ---------------------------//

	//***************************************** createStudent ***********************//

	@Action(semantics=SemanticsOf.NON_IDEMPOTENT)
	public String createStudent(
			@ParameterLayout(named="firstName")
			final String firstName,
			@ParameterLayout(named="middleName")
			@Parameter(optionality=Optionality.OPTIONAL)
			final String middleName,
			@ParameterLayout(named="lastName")
			final String lastName,
			@ParameterLayout(named="dateOfBirth")
			final LocalDate dateOfBirth,
			@ParameterLayout(named="picture")
			@Parameter(optionality=Optionality.OPTIONAL)
			final Blob picture,
			@ParameterLayout(named="pictureLink")
			@Parameter(optionality=Optionality.OPTIONAL)
			final String pictureLink
	){
		Person newPerson = persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, pictureLink, PersonRoleType.STUDENT);
		return toApiID(newPerson.getOID());
	}

	@Programmatic //userName can now also be set by fixtures
	public String validateCreateStudent(
			final String firstName,
			final String middleName,
			final String lastName,
			final LocalDate dateOfBirth,
			final Blob picture,
			final String pictureLink) {

		QueryDefault<Person> query =
				QueryDefault.create(
						Person.class,
						"findPersonUnique",
						"ownedBy", currentUserName());
		return container.firstMatch(query) != null?
				"ONE_INSTANCE_AT_MOST"
				:null;

	}

	@Programmatic
	public Person createStudentApi(
			@ParameterLayout(named="firstName")
			final String firstName,
			@ParameterLayout(named="middleName")
			@Parameter(optionality=Optionality.OPTIONAL)
			final String middleName,
			@ParameterLayout(named="lastName")
			final String lastName,
			@ParameterLayout(named="dateOfBirth")
			final LocalDate dateOfBirth,
			@ParameterLayout(named="picture")
			@Parameter(optionality=Optionality.OPTIONAL)
			final Blob picture,
			@ParameterLayout(named="pictureLink")
			@Parameter(optionality=Optionality.OPTIONAL)
			final String pictureLink
	){
		return persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, pictureLink, PersonRoleType.STUDENT);
	}

	//------------------------------------ END createStudent ---------------------------//

	//***************************************** createProfessional ***********************//

	@Action(semantics=SemanticsOf.NON_IDEMPOTENT)
	public String createProfessional(
			@ParameterLayout(named="firstName")
			final String firstName,
			@ParameterLayout(named="middleName")
			@Parameter(optionality=Optionality.OPTIONAL)
			final String middleName,
			@ParameterLayout(named="lastName")
			final String lastName,
			@ParameterLayout(named="dateOfBirth")
			final LocalDate dateOfBirth,
			@ParameterLayout(named="picture")
			@Parameter(optionality=Optionality.OPTIONAL)
			final Blob picture,
			@ParameterLayout(named="pictureLink")
			@Parameter(optionality=Optionality.OPTIONAL)
			final String pictureLink
	){
		Person newPerson = persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, pictureLink, PersonRoleType.PROFESSIONAL);
		return toApiID(newPerson.getOID());
	}

	@Programmatic //userName can now also be set by fixtures
	public String validateCreateProfessional(
			final String firstName,
			final String middleName,
			final String lastName,
			final LocalDate dateOfBirth,
			final Blob picture,
			final String pictureLink) {

		QueryDefault<Person> query =
				QueryDefault.create(
						Person.class,
						"findPersonUnique",
						"ownedBy", currentUserName());
		return container.firstMatch(query) != null?
				"ONE_INSTANCE_AT_MOST"
				:null;

	}

	@Programmatic
	public Person createProfessionalApi(
			@ParameterLayout(named="firstName")
			final String firstName,
			@ParameterLayout(named="middleName")
			@Parameter(optionality=Optionality.OPTIONAL)
			final String middleName,
			@ParameterLayout(named="lastName")
			final String lastName,
			@ParameterLayout(named="dateOfBirth")
			final LocalDate dateOfBirth,
			@ParameterLayout(named="picture")
			@Parameter(optionality=Optionality.OPTIONAL)
			final Blob picture,
			@ParameterLayout(named="pictureLink")
			@Parameter(optionality=Optionality.OPTIONAL)
			final String pictureLink
	){
		return persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, pictureLink, PersonRoleType.PROFESSIONAL);
	}

	//------------------------------------ END createProfessional ---------------------------//

	//***************************************** createPrincipal ***********************//

	@Action(semantics=SemanticsOf.NON_IDEMPOTENT)
	public String createPrincipal(
			@ParameterLayout(named="firstName")
			final String firstName,
			@ParameterLayout(named="middleName")
			@Parameter(optionality=Optionality.OPTIONAL)
			final String middleName,
			@ParameterLayout(named="lastName")
			final String lastName,
			@ParameterLayout(named="dateOfBirth")
			final LocalDate dateOfBirth,
			@ParameterLayout(named="picture")
			@Parameter(optionality=Optionality.OPTIONAL)
			final Blob picture,
			@ParameterLayout(named="pictureLink")
			@Parameter(optionality=Optionality.OPTIONAL)
			final String pictureLink
	){
		Person newPerson =  persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, pictureLink, PersonRoleType.PRINCIPAL);
		return toApiID(newPerson.getOID());
	}

	@Programmatic //userName can now also be set by fixtures
	public String validateCreatePrincipal(
			final String firstName,
			final String middleName,
			final String lastName,
			final LocalDate dateOfBirth,
			final Blob picture,
			final String pictureLink) {

		QueryDefault<Person> query =
				QueryDefault.create(
						Person.class,
						"findPersonUnique",
						"ownedBy", currentUserName());
		return container.firstMatch(query) != null?
				"ONE_INSTANCE_AT_MOST"
				:null;

	}

	public Person createPrincipalApi(
			@ParameterLayout(named="firstName")
			final String firstName,
			@ParameterLayout(named="middleName")
			@Parameter(optionality=Optionality.OPTIONAL)
			final String middleName,
			@ParameterLayout(named="lastName")
			final String lastName,
			@ParameterLayout(named="dateOfBirth")
			final LocalDate dateOfBirth,
			@ParameterLayout(named="picture")
			@Parameter(optionality=Optionality.OPTIONAL)
			final Blob picture,
			@ParameterLayout(named="pictureLink")
			@Parameter(optionality=Optionality.OPTIONAL)
			final String pictureLink
	){
		return  persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, pictureLink, PersonRoleType.PRINCIPAL);
	}

	//------------------------------------ END createPrincipal ---------------------------//
    
    //***************************************** findPersons **************************************//
    
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public List<Person> findPersons(
    		@ParameterLayout(named="searchInLastName")
            final String lastName
            ){
		return persons.findPersons(lastName);
	}

	//Business Rule: Person of login has to be activated
	public boolean hideFindPersons(
			final String lastName
		){
		if (persons.activePerson(currentUserName())!=null &&
				persons.activePerson(currentUserName()).getActivated()
				){
			return false;
		}
		return true;
	}

	public String validateFindPersons(
			final String lastName
	){

		if (persons.activePerson(currentUserName())!=null &&
				persons.activePerson(currentUserName()).getActivated()
				){
			return null;
		}
		return "PERSON_NOT_ACTIVATED";
	}
    
    //------------------------------------ END findPersons ---------------------------//
    
    //***************************************** findTagsUsedMoreThanThreshold ***********************//
    
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public List<Tag> findTagsUsedMoreThanThreshold(
    		@ParameterLayout(named="searchString")
    		final String tagDescription,
    		@ParameterLayout(named="tagCategoryDescription")
    		final String tagCategoryDescription,
    		@ParameterLayout(named="threshold")
    		final Integer threshold
    		){
    	return tags.findTagsUsedMoreThanThreshold(tagDescription, tagCategoryDescription, threshold);
    }
    
    //------------------------------------ END findTagsUsedMoreThanThreshold ---------------------------//
    
    //***************************************** createPersonalContact ***********************//
    
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    @ActionLayout(contributed=Contributed.AS_NEITHER)
    public PersonalContact createPersonalContact(
            @ParameterLayout(named="contactPerson")
            final Person contactPerson) {
        return personalcontacts.createPersonalContact(contactPerson, currentUserName());
    }
    
    public List<Person> autoComplete0CreatePersonalContact(final String search) {
        return persons.findPersons(search);
    }
    
    /**
     * There should be at most 1 instance for each owner - contact combination.
     * 
     */
    public String validateCreatePersonalContact(final Person contactPerson) {
        
        if (Objects.equal(contactPerson.getOwnedBy(), container.getUser().getName())) {
            return "NO_USE";
        }
        
        QueryDefault<PersonalContact> query = 
                QueryDefault.create(
                        PersonalContact.class, 
                    "findPersonalContactUniqueContact", 
                    "ownedBy", currentUserName(),
                    "contact", contactPerson);
        return container.firstMatch(query) != null?
        "ONE_INSTANCE_AT_MOST"        
        :null;
    }
    
    //----------------------------------------- END createPersonalContact -------------------//


	//***************************************** activatePersonOwnedBy ***********************//

	@Action(semantics=SemanticsOf.NON_IDEMPOTENT)
	public String activatePersonOwnedBy(String ownedBy) {
		return persons.activatePerson(ownedBy);
	}


	//----------------------------------------- END activatePersonOwnedBy -------------------//

	//***************************************** deActivatePersonOwnedBy ***********************//

	@Action(semantics=SemanticsOf.NON_IDEMPOTENT)
	public String deActivatePersonOwnedBy(String ownedBy) {
		return persons.deActivatePerson(ownedBy);
	}


	//----------------------------------------- END activatePersonOwnedBy -------------------//

	//***************************************** deletePersonOwnedBy ***********************//

	@Action(semantics=SemanticsOf.NON_IDEMPOTENT, restrictTo = RestrictTo.PROTOTYPING)
	public void deletePersonOwnedBy(String ownedBy) {
		persons.deletePerson(ownedBy);
	}


	//----------------------------------------- END deletePersonOwnedBy -------------------//

	//***************************************** allProvidedServices ***********************//

	@Action(semantics = SemanticsOf.SAFE)
	public List<Service> allProvidedServices() {
		return services.allServices();
	}

	//----------------------------------------- END allProvidedServices -------------------//
    
    //Helpers
    private String currentUserName() {
        return container.getUser().getName();
    }

	private String toApiID(final String OID){
		String[] parts = OID.split(Pattern.quote("[OID]"));
		String part1 = parts[0];
		return part1;
	}
	
    //Injections
	@Inject
	private Persons persons;
	
	@Inject
	private Demands demands;
	
	@Inject
	private Supplies supplies;
	
	@Inject
	private Profiles profiles;
	
	@Inject
	private Tags tags;
	
	@Inject
	private DomainObjectContainer container;
	
	@Inject
	private PersonalContacts personalcontacts;

	@Inject
	private Services services;

	@Inject
	private ProfileElements profileElements;

	@Inject
	private CommunicationChannels communicationChannels;

	@Inject
	private ProfileMatches profileMatchRepo;

}
