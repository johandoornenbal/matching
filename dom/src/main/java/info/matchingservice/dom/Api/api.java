package info.matchingservice.dom.Api;

import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.Demands;
import info.matchingservice.dom.DemandSupply.Supplies;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.DemandOrSupply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.Profiles;
import info.matchingservice.dom.Tags.Tag;
import info.matchingservice.dom.Tags.Tags;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Action;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.SemanticsOf;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.value.Blob;
import org.joda.time.LocalDate;

@DomainService()
@DomainServiceLayout()
public class api extends AbstractFactoryAndRepository {
	
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
            final Blob picture
			){
		return persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture);
	}
	
    @Programmatic //userName can now also be set by fixtures
    public String validateCreatePerson(
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
    
    //------------------------------------ END createPerson ---------------------------//
    
    //***************************************** findPersons **************************************//
    
    @Action(semantics=SemanticsOf.NON_IDEMPOTENT)
    public List<Person> findPersons(
    		@ParameterLayout(named="searchInLastName")
            final String lastName
            ){
		return persons.findPersons(lastName);
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
    
    //Helpers
    private String currentUserName() {
        return container.getUser().getName();
    }
	
    //Injections
	@Inject
	Persons persons;
	
	@Inject
	Demands demands;
	
	@Inject
	Supplies supplies;
	
	@Inject
	Profiles profiles;
	
	@Inject
	Tags tags;
	
	@Inject
	private DomainObjectContainer container;
	
}
