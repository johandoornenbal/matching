package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Person;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "40", repositoryFor = PersonNeed.class)
@Named("Opdrachten ('tafels')")
public class PersonNeeds extends MatchingDomainService<PersonNeed> {

    public PersonNeeds() {
        super(PersonNeeds.class, PersonNeed.class);
    }
    
    @Named("Alle 'tafels'")
    public List<PersonNeed> allNeeds() {
        return allInstances();
    }
    
    @Programmatic
    public PersonNeed newNeed(
            final @Named("Opdracht samenvatting") String needDescription,
            final Person needOwner,
            final String ownedBy
            ){
        final PersonNeed newNeed = newTransientInstance(PersonNeed.class);
        newNeed.setNeedDescription(needDescription);
        newNeed.setNeedOwner(needOwner);
        newNeed.setOwnedBy(ownedBy);
        persist(newNeed);
        return newNeed;
    }

}
