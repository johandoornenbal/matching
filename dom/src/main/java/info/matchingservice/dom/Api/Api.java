package info.matchingservice.dom.Api;

import com.google.common.base.Objects;
import info.matchingservice.dom.Actor.*;
import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.CommunicationChannels.*;
import info.matchingservice.dom.DemandSupply.*;
import info.matchingservice.dom.Match.ProfileMatch;
import info.matchingservice.dom.Match.ProfileMatches;
import info.matchingservice.dom.Profile.*;
import info.matchingservice.dom.ProvidedServices.Service;
import info.matchingservice.dom.ProvidedServices.Services;
import info.matchingservice.dom.Tags.Tag;
import info.matchingservice.dom.Tags.Tags;
import info.matchingservice.dom.TrustLevel;
import org.apache.isis.applib.AbstractFactoryAndRepository;
import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.query.QueryDefault;
import org.apache.isis.applib.services.wrapper.InteractionException;
import org.apache.isis.applib.services.wrapper.WrapperFactory;
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

	/**
	 *
	 * @return all persons that currentUser is allowed to see
	 */
	@Programmatic
	public List<Person> allActivePersons(){
		// at the moment all active users
		return persons.allActivePersons();
	}

	public Person createNewPerson(
			final String firstName,
			final String middleName,
			final String lastName,
			final String dateOfBirth,
			final String imageUrl,
			final PersonRoleType roleType,
			final String userName,
			final String mainAddress,
			final String mainPostalCode,
			final String mainTown,
			final String mainPhone) {

		final Person person = persons.createPerson(
				firstName,
				middleName,
				lastName,
				new LocalDate(dateOfBirth),
				null,
				imageUrl,
				roleType,
				userName);

		final String fullName;

		if (person.getMiddleName()==null || person.getMiddleName().equals("")) {
			fullName = person.getFirstName() + " " + person.getLastName();
		} else {
			fullName = person.getFirstName() + " " + person.getMiddleName() + " " + person.getLastName();
		}

		person.createSupplyAndProfile(
				"PERSON_SUPPLY_OF " + fullName,
				10,
				DemandSupplyType.PERSON_DEMANDSUPPLY,
				person,
				"PERSON_PROFILE_OF",
				10,
				null,
				null,
				ProfileType.PERSON_PROFILE,
				imageUrl,
				userName
		);

		communicationChannels.createAddress(
				person,
				CommunicationChannelType.ADDRESS_MAIN,
				mainAddress,
				mainPostalCode,
				mainTown,
				userName
		);

		communicationChannels.createPhone(
				person,
				CommunicationChannelType.PHONE_MAIN,
				mainPhone,
				userName
		);

		return person;
	}


								  //***************************************** updatePerson ***********************//
	@Programmatic
	public Person updatePerson(
			final Person person,
			final String firstName,
			final String middleName,
			final String lastName,
			final String dateOfBirth,
			final String imageUrl,
			final String mainEmail,
			final String mainPhone,
			final String mainAddress,
			final String mainPostalCode,
			final String mainTown){

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

		if (mainEmail == null) {
			// email not present in payload
		} else {
			// try update email
			try {
				if (mainEmail.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,4}$")){
					Email emailToUpdate = (Email) communicationChannels.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.EMAIL_MAIN).get(0);
					emailToUpdate.updateEmail(mainEmail);
				} else {
					throw new IllegalArgumentException("Not a valid emailaddress");
				}
			} catch (Exception e){
				container.raiseError(e.getMessage());
			}
		}

		if (mainPhone == null) {
			//mainPhone not present in payload
		} else {

			//update or create mainPhone
			if (communicationChannels.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.PHONE_MAIN).size() > 0) {
				try {
					Phone phoneToUpdate = (Phone) communicationChannels.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.PHONE_MAIN).get(0);
					phoneToUpdate.updatePhone(CommunicationChannelType.PHONE_MAIN, mainPhone);
				} catch (IllegalArgumentException e) {
					container.raiseError(e.getMessage());
				}
			} else {
				communicationChannels.createPhone(person, CommunicationChannelType.PHONE_MAIN, mainPhone);
			}

		}

		// test if mainAddress exists: then update else create
		if (
				communicationChannels.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.ADDRESS_MAIN).size() > 0
				&&
				!(mainAddress==null && mainPostalCode==null && mainTown==null)
				) {

			Address addressToUpdate = (Address) communicationChannels.findCommunicationChannelByPersonAndType(person, CommunicationChannelType.ADDRESS_MAIN).get(0);
			String addressUpdate;
			String postalCodeUpdate;
			String townUpdate;
			//try update address
			if (mainAddress != null) {
				addressUpdate = mainAddress;
			} else {
				addressUpdate = addressToUpdate.getAddress();
			}

			//try update postalCode
			if (mainPostalCode != null) {
				postalCodeUpdate = mainPostalCode;
			} else {
				postalCodeUpdate = addressToUpdate.getPostalCode();
			}

			//try update town
			if (mainTown != null) {
				townUpdate = mainTown;
			} else {
				townUpdate = addressToUpdate.getTown();
			}

			addressToUpdate.updateAddress(CommunicationChannelType.ADDRESS_MAIN,townUpdate,addressUpdate,postalCodeUpdate);

		} else {

			if (mainAddress!=null && mainPostalCode!=null && mainTown!=null) {
				communicationChannels.createAddress(person, CommunicationChannelType.ADDRESS_MAIN, mainAddress, mainPostalCode, mainTown);
			}

		}

		Person wrappedPerson = wrapperFactory.wrap(person);

		try {
			wrappedPerson = wrappedPerson.updatePerson(
					firstName,
					middleName,
					lastName,
					dateOfBirthUpdate,
					null,
					imageUrl
			);
		} catch (InteractionException e) {
			container.raiseError(e.getMessage());
			return null;
		}

		return wrappedPerson;


	}

	///////////

	//***************************************** Collections of Person ***********************//

	@Programmatic
	public List<Demand> getDemandsForPerson(final Person person){

		// apply business logic until way to wrap
		if (person.hideDemands()){
			return new ArrayList<>();
		}
		//TODO: find out how to wrap
		// wrappedPerson.getDemands() does not work
//		Person wrappedPerson = wrapperFactory.wrap(person);
//		for (Demand demand : wrappedPerson.getDemands()){
//			System.out.println(demand.getIdAsInt());
//		}

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

		// do not show when already contacted
		Boolean isContact = personalcontacts.findUniquePersonalContact(currentUserName(), person) != null?
				true  : false;

//		if (!currentUserName().equals(person.getOwnedBy())	&& !isContact ) {
			String addAsPersonalContact = "addAsPersonalContact";
			actions.add(addAsPersonalContact);
//		}

//        if (!currentUserName().equals(person.getOwnedBy())	&& isContact ) {
            String removeAsPersonalContact = "removeAsPersonalContact";
            actions.add(removeAsPersonalContact);
//        }

//		if (!person.hideCreatePersonsSupplyAndProfile()) {
			String addCreateSupplyAndProfile = "createSupplyAndProfile";
			actions.add(addCreateSupplyAndProfile);
//		}

		return actions;
	}

	@Programmatic
	public PersonalContact findOrCreatePersonalContact(final Person contact) {
		// do not contact yourself...
		if (contact.getOwnedBy().equals(currentUserName())){
			return null;
		}

		if (personalcontacts.findUniquePersonalContact(currentUserName(), contact) != null) {
			return personalcontacts.findUniquePersonalContact(currentUserName(), contact);
		} else {
			return personalcontacts.createPersonalContact(contact, currentUserName());
		}
	}

	@Programmatic
	public Person removeAsPersonalContact(final Person contact) {

		if (personalcontacts.findUniquePersonalContact(currentUserName(), contact) != null) {
			personalcontacts.findUniquePersonalContact(currentUserName(), contact).deleteTrustedContact(true);
		}

		return contact;
	}

	@Programmatic
	public Profile createSupplyAndProfile(final Person person){

		if (!person.hideCreatePersonsSupplyAndProfile()) {
			return person.createPersonsSupplyAndProfile();
		}

		return null;
	}

	@Programmatic
	public Profile createSupplyAndProfileAtRegistration(final Person person, final String userName){

		final String fullName;
		if (person.getMiddleName()==null || person.getMiddleName().equals("")) {
			fullName = person.getFirstName() + " " + person.getLastName();
		} else {
			fullName = person.getFirstName() + " " + person.getMiddleName() + " " + person.getLastName();
		}

		return person.createSupplyAndProfile(
				"PERSON_SUPPLY_OF " + fullName,
				10,
				DemandSupplyType.PERSON_DEMANDSUPPLY,
				person,
				"PERSON_PROFILE_OF",
				10,
				null,
				null,
				ProfileType.PERSON_PROFILE,
				null,
				userName);

	}

	@Programmatic
	public Demand createPersonDemand(
			final Person person,
			final String description,
            final String summary,
            final String story,
            final String startDate,
            final String endDate,
			final String imageUrl){

		//check if user has rights to create demand
		if (!currentUserName().equals(person.getOwnedBy())) {
			return null;
		}
		if (!person.getIsPrincipal()){
			return null;
		}

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
				imageUrl,
                person.getOwnedBy()
		);
	}

	public Demand matchDemandApiId(Integer instanceId) {
		Demand chosenDemand = demands.matchDemandApiId(instanceId);

		if (chosenDemand!=null){
			// apply business logic
			Person demandOwner = (Person) chosenDemand.getOwner();
			if (chosenDemand != null && demandOwner.hideDemands()) {
				return null;
			}
		}

		return chosenDemand;
	}

	@Programmatic
	public Demand matchDemandApiIdForOwner(final Integer instanceId) {
		Demand demand = demands.matchDemandApiId(instanceId);
		if (demand == null) {
			return null;
		}
		// check not authorized
		if (!currentUserName().equals(demand.getOwnedBy())){
			return null;
		}
		return demand;
	}

	@Programmatic
	public Demand updateDemand(
			final Integer ownerId,
			final String description,
			final String summary,
			final String story,
			final String startDate,
			final String endDate,
			final String imageUrl,
			final Integer weight) {

		Demand demand = matchDemandApiId(ownerId);

		//check description
		String descriptionEntry;
		if (description.equals("")){
			descriptionEntry = demand.getDescription();
		} else {
			descriptionEntry = description;
		}

		//check dates
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-mm-dd");
		LocalDate startDateEntry = null;
		if (!startDate.equals("")) {
			try {
				startDateEntry = LocalDate.parse(startDate, dtf);
			} catch (Exception e) {
				//ignore
			}
			if (startDateEntry == null) {
				// startDate is not a valid date
				startDateEntry = demand.getStartDate();
			} else {
				// startDate is a Date
				startDateEntry = new LocalDate(startDate);
			}
		}

		LocalDate endDateEntry = null;
		if (!endDate.equals("")) {
			try {
				endDateEntry = LocalDate.parse(endDate, dtf);
			} catch (Exception e) {
				//ignore
			}
			if (endDateEntry == null) {
				// endDate is not a valid date
				endDateEntry = demand.getEndDate();
			} else {
				// endDate is a Date
				endDateEntry = new LocalDate(endDate);
			}
		}

		// if invalid period [endDate before startDate] set both to original values
		if (startDateEntry!=null && endDateEntry!=null && endDateEntry.isBefore(startDateEntry)){
			startDateEntry=demand.getStartDate();
			endDateEntry=demand.getEndDate();
		}

		if (weight > 1) {
			demand.setWeight(weight);
		}

		return demand.updateDemand(descriptionEntry, summary, story, null, startDateEntry, endDateEntry, imageUrl);

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

	@Programmatic
	public List<Assessment> getAllAssessments(final Demand demand){
		List<Assessment> result = new ArrayList<>();
		if (!demand.hideAssessments()) {
			result.addAll(demand.getAssessments());
		}
		return result;
	}

	//***************************************** Supply ***********************//

	public Supply matchSupplyApiId(Integer instanceId) {
		Supply chosenSupply = supplies.matchSupplyApiId(instanceId);

		// apply business logic
		if (chosenSupply!=null) {
			Person supplyOwner = (Person) chosenSupply.getOwner();
			if (chosenSupply != null && supplyOwner.hideSupplies()) {
				return null;
			}
		}

		return chosenSupply;
	}


	@Programmatic
	public Supply matchSupplyApiIdForOwner(final Integer instanceId) {
		Supply supply = supplies.matchSupplyApiId(instanceId);
		if (supply == null) {
			return null;
		}
		// check not authorized
		if (!currentUserName().equals(supply.getOwnedBy())){
			return null;
		}
		return supply;
	}

	/**
	 *
	 * @return all supplies that currentUser is allowed to see
	 */
	@Programmatic
	public List<Supply> allSupplies(){
		return supplies.allSupplies();
	}

	@Programmatic
	public Supply updateSupply(
			final Integer ownerId,
			final String description,
			final String startDate,
			final String endDate,
			final String imageUrl,
			final Integer weight) {

		Supply supply = matchSupplyApiId(ownerId);

		//check description
		String descriptionEntry;
		if (description.equals("")){
			descriptionEntry = supply.getDescription();
		} else {
			descriptionEntry = description;
		}

		//check dates
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-mm-dd");
		LocalDate startDateEntry = null;
		if (!startDate.equals("")) {
			try {
				startDateEntry = LocalDate.parse(startDate, dtf);
			} catch (Exception e) {
				//ignore
			}
			if (startDateEntry == null) {
				// startDate is not a valid date
				startDateEntry = supply.getStartDate();
			} else {
				// startDate is a Date
				startDateEntry = new LocalDate(startDate);
			}
		}

		LocalDate endDateEntry = null;
		if (!endDate.equals("")) {
			try {
				endDateEntry = LocalDate.parse(endDate, dtf);
			} catch (Exception e) {
				//ignore
			}
			if (endDateEntry == null) {
				// endDate is not a valid date
				endDateEntry = supply.getEndDate();
			} else {
				// endDate is a Date
				endDateEntry = new LocalDate(endDate);
			}
		}

		// if invalid period [endDate before startDate] set both to original values
		if (startDateEntry!=null && endDateEntry!=null && endDateEntry.isBefore(startDateEntry)){
			startDateEntry=supply.getStartDate();
			endDateEntry=supply.getEndDate();
		}

		if (weight > 1) {
			supply.setWeight(weight);
		}

		return supply.updateSupply(descriptionEntry, startDateEntry, endDateEntry, imageUrl);

	}

	//***************************************** Collections of Supply ***********************//

	@Programmatic
	public List<Profile> getProfilesForSupply(final Supply supply){
		Person supplyOwner = (Person) supply.getOwner();

		// apply business logic
		if (supplyOwner.hideSupplies()){
			return null;
		}

		return new ArrayList<>(supply.getProfiles());
	}

	@Programmatic
	public List<Assessment> getAllAssessments(final Supply supply){
		List<Assessment> result = new ArrayList<>();
		if (!supply.hideAssessments()) {
			result.addAll(supply.getAssessments());
		}
		return result;
	}


	//***************************************** Profile ***********************//

	@Programmatic
	public Profile matchProfileApiId(Integer instanceId) {

		Profile chosenProfile = profiles.matchProfileApiId(instanceId);

		//apply business logic
		if (chosenProfile != null){
			Person profileOwner = (Person) chosenProfile.getOwner();
			if (chosenProfile.getDemandOrSupply()==DemandOrSupply.DEMAND && profileOwner.hideDemands()){
				return null;
			}
			if (chosenProfile.getDemandOrSupply()==DemandOrSupply.SUPPLY && profileOwner.hideSupplies()){
				return null;
			}
		}

		return chosenProfile;
	}

	@Programmatic
	public Profile matchProfileApiIdForOwner(Integer instanceId) {

		Profile profile = profiles.matchProfileApiId(instanceId);
		if (profile == null) {
			return null;
		}

		// check not authorized
		if (!currentUserName().equals(profile.getOwnedBy())){
			return null;
		}

		// check if type is not supply and personprofile: personal profile cannot be deleted
		if (profile.getDemandOrSupply()==(DemandOrSupply.SUPPLY) && profile.getType() == ProfileType.PERSON_PROFILE ){
			return null;
		}

		return profile;
	}

	@Programmatic
	public Profile createPersonProfile(
			final String name,
			final Integer weight,
			final String startDate,
			final String endDate,
			final Integer demandId,
			final Integer supplyId,
			final String imageUrl,
			final Person person
	){

		if (name==null || name.equals("")){
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

		if (demandId != null) {
			Demand demand = matchDemandApiIdForOwner(demandId);
			if (demand != null) {
				return profiles.createDemandProfile(name, weight, startDateEntry, endDateEntry, ProfileType.PERSON_PROFILE, imageUrl, demand, currentUserName());
			}
		}
		if (supplyId != null) {
			Supply supply = matchSupplyApiIdForOwner(supplyId);
			if (supply != null && !person.hideCreatePersonsSupplyAndProfile()) {
				return profiles.createSupplyProfile(name, weight, startDateEntry, endDateEntry, ProfileType.PERSON_PROFILE, imageUrl, supply, currentUserName());
			}
		}
		return null;
	}

	@Programmatic
	public Profile updateProfile(
			final Integer instanceId,
			final String name,
			final String startDate,
			final String endDate,
			final String imageUrl,
			final Integer weight) {

		Profile profile = matchProfileApiId(instanceId);

		String nameEntry;
		if (name == null || name.equals("")){
			nameEntry = profile.getName();
		} else {
			nameEntry = name;
		}

		//check dates
		DateTimeFormatter dtf = DateTimeFormat.forPattern("yyyy-mm-dd");
		LocalDate startDateEntry = null;
		if (!startDate.equals("")) {
			try {
				startDateEntry = LocalDate.parse(startDate, dtf);
			} catch (Exception e) {
				//ignore
			}
			if (startDateEntry == null) {
				// startDate is not a valid date
				startDateEntry = profile.getStartDate();
			} else {
				// startDate is a Date
				startDateEntry = new LocalDate(startDate);
			}
		}

		LocalDate endDateEntry = null;
		if (!endDate.equals("")) {
			try {
				endDateEntry = LocalDate.parse(endDate, dtf);
			} catch (Exception e) {
				//ignore
			}
			if (endDateEntry == null) {
				// endDate is not a valid date
				endDateEntry = profile.getEndDate();
			} else {
				// endDate is a Date
				endDateEntry = new LocalDate(endDate);
			}
		}

		// if invalid period [endDate before startDate] set both to original values
		if (startDateEntry!=null && endDateEntry!=null && endDateEntry.isBefore(startDateEntry)){
			startDateEntry=profile.getStartDate();
			endDateEntry=profile.getEndDate();
		}


		Integer weightEntry;
		if (weight < 1) {
			weightEntry = profile.getWeight();
		} else {
			weightEntry = weight;
		}

		return profile.updateProfile(nameEntry, weightEntry,startDateEntry, endDateEntry, imageUrl);
	}

	@Programmatic
	public List<Assessment> getAllAssessments(final Profile profile){
		List<Assessment> result = new ArrayList<>();
		if (!profile.hideCollectAssessments()) {
			result.addAll(profile.getCollectAssessments());
		}
		return result;
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


	@Programmatic
	public Phone createPhone(final Person person, final String phone, final CommunicationChannelType communicationChannelType, final String userName){
		return communicationChannels.createPhone(person, communicationChannelType, phone, userName);
	}

	@Programmatic
	public Address createAddress(
			final Person person,
			final String address,
			final String postalCode,
			final String town,
			final CommunicationChannelType communicationChannelType,
			final String userName) {
		return communicationChannels.createAddress(
				person,
				communicationChannelType,
				address,
				postalCode,
				town,
				userName
		);
	}
	
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

	@Inject
	WrapperFactory wrapperFactory;


}
