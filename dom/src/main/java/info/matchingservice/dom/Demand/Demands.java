package info.matchingservice.dom.Demand;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Actor;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "50", repositoryFor = Demand.class)
public class Demands extends MatchingDomainService<Demand> {

    public Demands() {
        super(Demands.class, Demand.class);
    }
    
    public List<Demand> allDemands() {
        return allInstances();
    }

    @Programmatic
    public Demand newDemand(
            String demandDescription,
            final Integer weight,
            Actor demandOwner,
            String ownedBy) {
        final Demand newNeed = newTransientInstance(Demand.class);
        newNeed.setDemandDescription(demandDescription);
        newNeed.setWeight(weight);
        newNeed.setDemandOwner(demandOwner);
        newNeed.setOwnedBy(ownedBy);
        persist(newNeed);
        return newNeed;
    }

}
