package info.matchingservice.dom.DemandSupply;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Actor;

import java.util.List;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(repositoryFor = Demand.class)
@DomainServiceLayout(
        named="Aanbod en vraag",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "30"
)
public class Demands extends MatchingDomainService<Demand> {

    public Demands() {
        super(Demands.class, Demand.class);
    }
    
    @ActionLayout(named="Alle vraag")
    public List<Demand> allDemands() {
        return allInstances();
    }

    @Programmatic
    public Demand newDemand(
            final String demandDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType,
            final Actor demandOwner,
            final String ownedBy) {
        final Demand newNeed = newTransientInstance(Demand.class);
        newNeed.setDemandDescription(demandDescription);
        newNeed.setWeight(weight);
        newNeed.setDemandType(demandSupplyType);
        newNeed.setDemandOwner(demandOwner);
        newNeed.setOwnedBy(ownedBy);
        persist(newNeed);
        return newNeed;
    }

}
