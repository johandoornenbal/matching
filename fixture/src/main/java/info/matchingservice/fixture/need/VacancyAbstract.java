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
            String testTextForMatching,
            Integer testfigure,
            PersonNeed vacancyOwner,
            String user,
            ExecutionContext executionContext
            ) {
        VacancyProfile newVac = vacs.newVacancy(vacancyDescription, testTextForMatching, testfigure, vacancyOwner, user);
        return executionContext.add(this,newVac);
    }
    
    //region > injected services
    @javax.inject.Inject
    VacancyProfiles vacs;
}
