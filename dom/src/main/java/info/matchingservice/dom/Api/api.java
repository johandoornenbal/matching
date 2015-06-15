package info.matchingservice.dom.Api;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.inject.Inject;

import com.google.common.base.Objects;

import org.joda.time.LocalDate;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.Contributed;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.RestrictTo;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.value.Blob;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.PersonRoleType;
import info.matchingservice.dom.Actor.PersonalContact;
import info.matchingservice.dom.Actor.PersonalContacts;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Demands;
import info.matchingservice.dom.DemandSupply.Supplies;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElements;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.ProvidedServices.Service;
import info.matchingservice.dom.ProvidedServices.Services;
import info.matchingservice.dom.Tags.Tag;
import info.matchingservice.dom.Tags.Tags;
import info.matchingservice.dom.TrustLevel;

@DomainService()
@DomainServiceLayout()
public class Api extends AbstractFactoryAndRepository {
	
	//***************************************** activePerson ***********************//
	
	@Action(semantics=SemanticsOf.SAFE)
	public List<Person> activePerson(){
		return persons.activePerson();
	}
	
	//------------------------------------ END activePerson ---------------------------//
	
	//***************************************** getDemandByUniqueId ***********************//
	
	@Action(semantics=SemanticsOf.SAFE)
	public List<Demand> findDemandByUniqueId(final UUID uniqueItemId){
		try
		{
			return demands.findDemandByUniqueItemId(uniqueItemId);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	public String validateFindDemandByUniqueId(final UUID uniqueItemId){
		// implementation of Trusted Circles Business Rule: demands are accessible only for INNER_CIRCLE
		// TODO: this should be transferred to object itself for maintainability; now there are at least to places to adapt code
		// TODO: integration test to verify access
		try
		{	
			if (
					// Not the owner
					!demands.findDemandByUniqueItemId(uniqueItemId).get(0).getOwnedBy().equals(currentUserName())
					
					&&
					
					// At least Inner Circle
					demands.findDemandByUniqueItemId(uniqueItemId).get(0).allowedTrustLevel(TrustLevel.INNER_CIRCLE)
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
	
	//------------------------------------ END getDemandByUniqueId ---------------------------//
	
	//***************************************** getSupplyByUniqueId ***********************//
	
	@Action(semantics=SemanticsOf.SAFE)
	public List<Supply> findSupplyByUniqueId(final UUID uniqueItemId){
		try
		{
			return supplies.findSupplyByUniqueItemId(uniqueItemId);
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	
	//------------------------------------ END getSupplyByUniqueId ---------------------------//
	
	//***************************************** getPersonByUniqueId ***********************//
	
	@Action(semantics=SemanticsOf.SAFE)
	public List<Person> findPersonByUniqueId(final UUID uniqueItemId){
		try
		{
			return persons.findPersonByUniqueItemId(uniqueItemId);
		}
		catch (Exception e)
		{
			return null;
		}
		
	}
	
	
	//------------------------------------ END getPersonByUniqueId ---------------------------//
	
	//***************************************** getProfileByUniqueId ***********************//
	
	@Action(semantics=SemanticsOf.SAFE)
	public List<Profile> findProfileByUniqueId(final UUID uniqueItemId){
		try
		{
			return profiles.findProfileByUniqueItemId(uniqueItemId);
		}
		catch (Exception e)
		{
			return null;
		}
		
	}
	
	public String validateFindProfileByUniqueId(final UUID uniqueItemId){
		// implementation of Trusted Circles Business Rule: demands are accessible only for INNER_CIRCLE
		// TODO: this should be transferred to object itself for maintainability; now there are at least to places to adapt code
		// TODO: integration test to verify access
		try
		{	
			if (
					// Not the owner
					!profiles.findProfileByUniqueItemId(uniqueItemId).get(0).getOwnedBy().equals(currentUserName())
					
					&&
					
					// At least Inner Circle
					profiles.findProfileByUniqueItemId(uniqueItemId).get(0).allowedTrustLevel(TrustLevel.INNER_CIRCLE)
					
					&&
					
					// only for demands
					profiles.findProfileByUniqueItemId(uniqueItemId).get(0).getDemandOrSupply() == DemandOrSupply.DEMAND
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
			@ParameterLayout(named="personRole")
			final PersonRoleType personRoleType
			){
		return persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, personRoleType);
	}
	
    @Programmatic //userName can now also be set by fixtures
    public String validateCreatePerson(
            final String firstName,
            final String middleName,
            final String lastName,
            final LocalDate dateOfBirth,
            final Blob picture,
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
			final Blob picture
	){
		Person newPerson = persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, PersonRoleType.STUDENT);
		return toApiID(newPerson.getOID());
	}

	@Programmatic //userName can now also be set by fixtures
	public String validateCreateStudent(
			final String firstName,
			final String middleName,
			final String lastName,
			final LocalDate dateOfBirth,
			final Blob picture) {

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
			final Blob picture
	){
		return persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, PersonRoleType.STUDENT);
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
			final Blob picture
	){
		Person newPerson = persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, PersonRoleType.PROFESSIONAL);
		return toApiID(newPerson.getOID());
	}

	@Programmatic //userName can now also be set by fixtures
	public String validateCreateProfessional(
			final String firstName,
			final String middleName,
			final String lastName,
			final LocalDate dateOfBirth,
			final Blob picture) {

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
			final Blob picture
	){
		return persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, PersonRoleType.PROFESSIONAL);
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
			final Blob picture
	){
		Person newPerson =  persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, PersonRoleType.PRINCIPAL);
		return toApiID(newPerson.getOID());
	}

	@Programmatic //userName can now also be set by fixtures
	public String validateCreatePrincipal(
			final String firstName,
			final String middleName,
			final String lastName,
			final LocalDate dateOfBirth,
			final Blob picture) {

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
			final Blob picture
	){
		return  persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, PersonRoleType.PRINCIPAL);
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
		if (persons.findPersonUnique(currentUserName())!=null &&
				persons.findPersonUnique(currentUserName()).getActivated()
				){
			return false;
		}
		return true;
	}

	public String validateFindPersons(
			final String lastName
	){

		if (persons.findPersonUnique(currentUserName())!=null &&
				persons.findPersonUnique(currentUserName()).getActivated()
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
		String ApiID = "L_".concat(part1);
		return ApiID;
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

	
}
