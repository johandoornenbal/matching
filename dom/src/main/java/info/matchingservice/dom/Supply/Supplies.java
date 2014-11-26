package info.matchingservice.dom.Supply;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.Actor.Actor;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "50", repositoryFor = Supply.class)
public class Supplies extends MatchingDomainService<Supply> {

    public Supplies() {
        super(Supplies.class, Supply.class);
    }

    public List<Supply> allSupplies() {
        return allInstances();
    }

    @Programmatic
    public Supply newSupply(
            String supplyDescription,
            Actor supplyOwner,
            String ownedBy) {
        final Supply newSupply = newTransientInstance(Supply.class);
        newSupply.setSupplyDescription(supplyDescription);
        newSupply.setSupplyOwner(supplyOwner);
        newSupply.setOwnedBy(ownedBy);
        persist(newSupply);
        return newSupply;
    }

}
