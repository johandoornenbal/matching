package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.Need;
import info.matchingservice.dom.Need.DemandProfile;
import info.matchingservice.dom.Need.DemandProfiles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class VacancyAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected DemandProfile createVacancy(
            String vacancyDescription,
            Need vacancyOwner,
            String user,
            ExecutionContext executionContext
            ) {
        DemandProfile newVac = vacs.newVacancy(vacancyDescription, vacancyOwner, user);
        return executionContext.add(this,newVac);
    }
    
    //region > injected services
    @javax.inject.Inject
    DemandProfiles vacs;
}
