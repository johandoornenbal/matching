package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Organisation;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "50", repositoryFor = OrganisationNeed.class)
@Named("Tafels (org)")
public class OrganisationNeeds extends MatchingDomainService<OrganisationNeed> {

    public OrganisationNeeds() {
        super(OrganisationNeeds.class, OrganisationNeed.class);
    }
    
    @Named("Alle organisatie tafels")
    public List<OrganisationNeed> allNeeds() {
        return allInstances();
    }
    
    @Programmatic
    public OrganisationNeed newNeed(
            final @Named("Opdracht samenvatting") String needDescription,
            final Organisation needOwner,
            final String ownedBy
            ){
        final OrganisationNeed newNeed = newTransientInstance(OrganisationNeed.class);
        newNeed.setNeedDescription(needDescription);
        newNeed.setNeedOwner(needOwner);
        newNeed.setOwnedBy(ownedBy);
        persist(newNeed);
        return newNeed;
    }

}
