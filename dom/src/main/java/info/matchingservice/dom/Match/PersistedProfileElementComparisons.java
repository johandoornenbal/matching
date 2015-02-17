package info.matchingservice.dom.Match;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileElementTag;

@DomainService(nature=NatureOfService.DOMAIN)
public class PersistedProfileElementComparisons extends MatchingDomainService<PersistedProfileElementComparison> {

	public PersistedProfileElementComparisons() {
		super(PersistedProfileElementComparisons.class, PersistedProfileElementComparison.class);
	}
	
	public PersistedProfileElementComparison createElementComparisonPersisted(
            Profile demandProfileElementOwner,
            ProfileElement demandProfileElement, 
            ProfileElementTag matchingSupplyProfileElement, 
            Profile matchingProfileElementOwner, 
            Actor matchingProfileElementActorOwner,
            Integer calculatedMatchingValue
			){
		final PersistedProfileElementComparison newEc = newTransientInstance(PersistedProfileElementComparison.class);
		newEc.setDemandProfileElementOwner(demandProfileElementOwner);
		newEc.setDemandProfileElement(demandProfileElement);
		newEc.setMatchingProfileElementOwner(matchingProfileElementOwner);
		newEc.setMatchingProfileElementActorOwner(matchingProfileElementActorOwner);
		newEc.setCalculatedMatchingValue(calculatedMatchingValue);
		persistIfNotAlready(newEc);
		return newEc;
	}

}
