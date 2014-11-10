package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.Vacancy;
import info.matchingservice.dom.Need.VacancyProfile;
import info.matchingservice.dom.Need.VacancyProfiles;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class VacancyProfileAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected VacancyProfile createVacancyProfile(
            String vacancyProfileDescription,
            Vacancy vacancyProfileOwner,
            String user,
            ExecutionContext executionContext
            ) {
        VacancyProfile newVacProf = vacprofs.newVacancyProfile(vacancyProfileDescription, vacancyProfileOwner, user);
        return executionContext.add(this,newVacProf);
    }
    
    //region > injected services
    @javax.inject.Inject
    VacancyProfiles vacprofs;
}
