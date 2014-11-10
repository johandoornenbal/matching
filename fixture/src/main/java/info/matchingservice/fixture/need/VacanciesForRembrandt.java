package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.Needs;

import javax.inject.Inject;

public class VacanciesForRembrandt extends VacancyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createVacancy(
                "Een gezette corporaal",
                needs.allNeeds().get(2),
                "rembrandt",
                executionContext
                );
        createVacancy(
                "Een lansdrager",
                needs.allNeeds().get(2),
                "rembrandt",
                executionContext
                );
        createVacancy(
                "Hond",
                needs.allNeeds().get(2),
                "rembrandt",
                executionContext
                );
    }
    
    @Inject
    Needs needs;
}
