package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.VacancyProfile;
import info.matchingservice.dom.Need.VacancyProfileElement;
import info.matchingservice.dom.Need.VacancyProfileElements;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class VacancyProfileElementAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected VacancyProfileElement createVacancyProfileElement(
            String vacancyProfileElementDescription,
            VacancyProfile vacancyProfileElementOwner,
            String user,
            ExecutionContext executionContext
            ) {
        VacancyProfileElement newVacProf = vacprofs.newVacancyProfileElement(vacancyProfileElementDescription, vacancyProfileElementOwner, user);
        return executionContext.add(this,newVacProf);
    }
    
    //region > injected services
    @javax.inject.Inject
    VacancyProfileElements vacprofs;
}
