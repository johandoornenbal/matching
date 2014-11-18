package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Person;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "50", repositoryFor = PersonNeed.class)
@Named("Tafels (org)")
public class PersonNeeds extends MatchingDomainService<PersonNeed> {

    public PersonNeeds() {
        super(PersonNeeds.class, PersonNeed.class);
    }
    
    @Named("Alle persoonlijke tafels")
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
    
    @Programmatic
    public PersonNeed newNeed(
            final @Named("Opdracht samenvatting") String needDescription,
            final @Named("Gewicht") Integer weight,
            final Person needOwner,
            final String ownedBy
            ){
        final PersonNeed newNeed = newTransientInstance(PersonNeed.class);
        newNeed.setNeedDescription(needDescription);
        newNeed.setWeight(weight);
        newNeed.setNeedOwner(needOwner);
        newNeed.setOwnedBy(ownedBy);
        persist(newNeed);
        return newNeed;
    }

}
