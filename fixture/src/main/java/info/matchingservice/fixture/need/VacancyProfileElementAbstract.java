package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.DemandProfile;
import info.matchingservice.dom.Need.DemandProfileElement;
import info.matchingservice.dom.Need.DemandProfileElements;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class VacancyProfileElementAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected DemandProfileElement createVacancyProfileElement(
            String vacancyProfileElementDescription,
            DemandProfile vacancyProfileElementOwner,
            String user,
            ExecutionContext executionContext
            ) {
        DemandProfileElement newVacProf = vacprofs.newVacancyProfileElement(vacancyProfileElementDescription, vacancyProfileElementOwner, user);
        return executionContext.add(this,newVacProf);
    }
    
    //region > injected services
    @javax.inject.Inject
    DemandProfileElements vacprofs;
}
