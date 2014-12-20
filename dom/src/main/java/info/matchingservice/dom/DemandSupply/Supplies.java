package info.matchingservice.dom.DemandSupply;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Actor;

import java.util.List;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(repositoryFor = Supply.class)
@DomainServiceLayout(
        named="Aanbod en vraag",
        menuBar = DomainServiceLayout.MenuBar.PRIMARY,
        menuOrder = "30"
)
public class Supplies extends MatchingDomainService<Supply> {

    public Supplies() {
        super(Supplies.class, Supply.class);
    }
    
    @ActionLayout(named="Alle aanbod")
    public List<Supply> allSupplies() {
        return allInstances();
    }

    @Programmatic
    public Supply newSupply(
            final String supplyDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType,
            final Actor supplyOwner,
            final String ownedBy) {
        final Supply newSupply = newTransientInstance(Supply.class);
        newSupply.setSupplyDescription(supplyDescription);
        newSupply.setSupplyType(demandSupplyType);
        newSupply.setWeight(weight);
        newSupply.setSupplyOwner(supplyOwner);
        newSupply.setOwnedBy(ownedBy);
        persist(newSupply);
        return newSupply;
    }

}
