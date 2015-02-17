package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Demands;
import info.matchingservice.dom.Rules.ProfileTypeMatchingRules;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.joda.time.LocalDate;

public abstract class DemandAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Demand createDemand(
            String demandDescription,
            String demandSummary,
            String demandStory,
            LocalDate demandOrSupplyProfileStartDate,
            LocalDate demandOrSupplyProfileEndDate,
            Integer weight,
            DemandSupplyType demandSupplyType,
            Actor demandOwner,
            String ownedBy,
            ExecutionContext executionContext
            ) {
        Demand newDemand = demands.createDemand(demandDescription, demandSummary, demandStory, null, demandOrSupplyProfileStartDate, demandOrSupplyProfileEndDate, weight, demandSupplyType, demandOwner, ownedBy);
        return executionContext.add(this,newDemand);
    }
    
    //region > injected services
    @javax.inject.Inject
    Demands demands;
}
