package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Party.Person;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "40", repositoryFor = Need.class)
@Named("Opdrachten ('tafels')")
public class Needs extends MatchingDomainService<Need> {

    public Needs() {
        super(Needs.class, Need.class);
    }
    
    @Named("Alle opdrachten ('tafels'")
    public List<Need> allNeeds() {
        return allInstances();
    }
    
    @Programmatic
    public Need newNeed(
            final @Named("Opdracht samenvatting") String needDescription,
            final Person needOwner,
            final String ownedBy
            ){
        final Need newNeed = newTransientInstance(Need.class);
        newNeed.setNeedDescription(needDescription);
        newNeed.setNeedOwner(needOwner);
        newNeed.setOwnedBy(ownedBy);
        persist(newNeed);
        return newNeed;
    }

}
