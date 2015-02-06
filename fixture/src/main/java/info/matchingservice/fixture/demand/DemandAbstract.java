package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Demands;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class DemandAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Demand createDemand(
            String demandDescription,
            Integer weight,
            DemandSupplyType demandSupplyType,
            Actor demandOwner,
            String ownedBy,
            ExecutionContext executionContext
            ) {
        Demand newDemand = demands.newDemand(demandDescription, "", "", null, weight, demandSupplyType, demandOwner, ownedBy);
        return executionContext.add(this,newDemand);
    }
    
    //region > injected services
    @javax.inject.Inject
    Demands demands;
}
