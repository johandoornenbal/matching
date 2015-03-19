package info.matchingservice.dom.Match;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.NatureOfService;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Profile.ProfileElement;

@DomainService(nature=NatureOfService.DOMAIN, repositoryFor=PersistedProfileElementComparison.class)
public class PersistedProfileElementComparisons extends
		MatchingDomainService<PersistedProfileElementComparison> {

	public PersistedProfileElementComparisons() {
		super(PersistedProfileElementComparisons.class, PersistedProfileElementComparison.class);
	}
	
	public List<PersistedProfileElementComparison> findProfileElementComparisons(final String ownedBy)
	{
        return allMatches("findProfileElementComparisonByOwner",
        		"ownedBy", ownedBy);
	}
	
	public List<PersistedProfileElementComparison> findProfileElementComparisonsByElement(final ProfileElement element)
	{
        return allMatches("findProfileElementComparisonByElements",
        		"element", element);
	}
	
	public void deleteProfileElementComparisons(final String ownedBy)
	{
		
		List<PersistedProfileElementComparison> tempList = this.findProfileElementComparisons(ownedBy);
		for (PersistedProfileElementComparison e: tempList) 
		{
		
			e.deletePersistedProfileElementComparison();
			
		}
		
	}

}
