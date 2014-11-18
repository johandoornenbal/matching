package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.PersonNeed;
import info.matchingservice.dom.Need.VacancyProfiles;
import info.matchingservice.dom.Need.VacancyProfile;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class VacancyAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected VacancyProfile createVacancy(
            String vacancyDescription,
            PersonNeed vacancyOwner,
            String user,
            ExecutionContext executionContext
            ) {
        VacancyProfile newVac = vacs.newVacancy(vacancyDescription, vacancyOwner, user);
        return executionContext.add(this,newVac);
    }
    
    //region > injected services
    @javax.inject.Inject
    VacancyProfiles vacs;
}
