package info.matchingservice.fixture.need;

import info.matchingservice.dom.Demand.Demand;
import info.matchingservice.dom.Demand.DemandProfile;
import info.matchingservice.dom.Demand.DemandProfiles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class VacancyAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected DemandProfile createVacancy(
            String vacancyDescription,
            Demand vacancyOwner,
            String user,
            ExecutionContext executionContext
            ) {
        DemandProfile newVac = vacs.newDemand(vacancyDescription, vacancyOwner, user);
        return executionContext.add(this,newVac);
    }
    
    //region > injected services
    @javax.inject.Inject
    DemandProfiles vacs;
}
