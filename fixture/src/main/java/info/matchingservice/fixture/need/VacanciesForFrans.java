package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.Needs;

import javax.inject.Inject;

public class VacanciesForFrans extends VacancyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createVacancy(
                "Een jonge hulpschilder",
                needs.allNeeds().get(0),
                "frans",
                executionContext
                );
        createVacancy(
                "Een ervaren senior hulpschilder",
                needs.allNeeds().get(0),
                "frans",
                executionContext
                );
        createVacancy(
                "Een schoonmaakster die tevens model wil zijn",
                needs.allNeeds().get(1),
                "frans",
                executionContext
                );
    }
    
    @Inject
    Needs needs;
}
