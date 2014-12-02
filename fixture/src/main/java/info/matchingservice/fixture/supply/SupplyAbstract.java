package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Supply.Supplies;
import info.matchingservice.dom.Supply.Supply;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class SupplyAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Supply createSupply(
            String supplyDescription,
            final Integer weight,
            Actor supplyOwner,
            String ownedBy,
            ExecutionContext executionContext
            ) {
        Supply newSupply = supplies.newSupply(supplyDescription, weight, supplyOwner, ownedBy);
        return executionContext.add(this,newSupply);
    }
    
    //region > injected services
    @javax.inject.Inject
    Supplies supplies;
}
