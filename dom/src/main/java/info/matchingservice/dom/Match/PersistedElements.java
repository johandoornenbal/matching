package info.matchingservice.dom.Match;

import org.apache.isis.applib.annotation.DomainService;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;

@DomainService
public class PersistedElements extends MatchingDomainService<ElementComparisonPersisted> {

	public PersistedElements() {
		super(PersistedElements.class, ElementComparisonPersisted.class);
	}
	
	public ElementComparisonPersisted createElementComparisonPersisted(
            Profile demandProfileElementOwner,
            ProfileElement demandProfileElement, 
            ProfileElement matchingSupplyProfileElement, 
            Profile matchingProfileElementOwner, 
            Actor matchingProfileElementActorOwner,
            Integer calculatedMatchingValue
			){
		final ElementComparisonPersisted newEc = newTransientInstance(ElementComparisonPersisted.class);
		newEc.setDemandProfileElementOwner(demandProfileElementOwner);
		newEc.setDemandProfileElement(demandProfileElement);
		newEc.setMatchingProfileElementOwner(matchingProfileElementOwner);
		newEc.setMatchingProfileElementActorOwner(matchingProfileElementActorOwner);
		newEc.setCalculatedMatchingValue(calculatedMatchingValue);
		persistIfNotAlready(newEc);
		return newEc;
	}

}
