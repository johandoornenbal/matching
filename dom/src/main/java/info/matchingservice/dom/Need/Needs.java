package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Actor;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "50", repositoryFor = Need.class)
@Named("Tafels")
public class Needs extends MatchingDomainService<Need> {

    public Needs() {
        super(Needs.class, Need.class);
    }
    
    @Named("Alle tafels")
    public List<Need> allNeeds() {
        return allInstances();
    }

    @Programmatic
    public Need newNeed(
            @Named("Opdracht samenvatting") String needDescription,
            final Integer weight,
            Actor needOwner,
            String ownedBy) {
        final Need newNeed = newTransientInstance(Need.class);
        newNeed.setNeedDescription(needDescription);
        newNeed.setWeight(weight);
        newNeed.setNeedOwner(needOwner);
        newNeed.setOwnedBy(ownedBy);
        persist(newNeed);
        return newNeed;
    }

}
