package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.Need;
import info.matchingservice.dom.Need.Vacancies;
import info.matchingservice.dom.Need.Vacancy;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class VacancyAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Vacancy createVacancy(
            String vacancyDescription,
            String testTextForMatching,
            Need vacancyOwner,
            String user,
            ExecutionContext executionContext
            ) {
        Vacancy newVac = vacs.newVacancy(vacancyDescription, testTextForMatching, vacancyOwner, user);
        return executionContext.add(this,newVac);
    }
    
    //region > injected services
    @javax.inject.Inject
    Vacancies vacs;
}
