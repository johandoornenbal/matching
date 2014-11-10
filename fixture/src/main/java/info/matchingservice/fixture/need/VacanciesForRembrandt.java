package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.Needs;

import javax.inject.Inject;

public class VacanciesForRembrandt extends VacancyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createVacancy(
                "Een gezette corporaal in het bezit van goudkleurig uniform",
                needs.allNeeds().get(2),
                "rembrandt",
                executionContext
                );
        createVacancy(
                "Een lansdrager in het bezit van lans",
                needs.allNeeds().get(2),
                "rembrandt",
                executionContext
                );
        createVacancy(
                "Hond die goed stil kan zitten",
                needs.allNeeds().get(2),
                "rembrandt",
                executionContext
                );
    }
    
    @Inject
    Needs needs;
}
