package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Supplies;
import info.matchingservice.dom.DemandSupply.Supply;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.joda.time.LocalDate;

public abstract class SupplyAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Supply createSupply(
            String supplyDescription,
            Integer weight,
            LocalDate demandOrSupplyProfileStartDate,
            LocalDate demandOrSupplyProfileEndDate,
            DemandSupplyType demandSupplyType,
            Actor supplyOwner,
            String ownedBy,
            ExecutionContext executionContext
            ) {
        Supply newSupply = supplies.createSupply(supplyDescription, weight, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, demandSupplyType, supplyOwner, ownedBy);
        return executionContext.add(this,newSupply);
    }
    
    //region > injected services
    @javax.inject.Inject
    Supplies supplies;
}
