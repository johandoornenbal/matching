package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.Needs;

import javax.inject.Inject;

public class VacanciesForFrans extends VacancyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createVacancy(
                "Een jonge hulpschilder met 2 jaar ervaring",
                needs.allNeeds().get(0),
                "frans",
                executionContext
                );
        createVacancy(
                "Een ervaren senior hulpschilder met 10 jaar ervaring",
                needs.allNeeds().get(0),
                "frans",
                executionContext
                );
        createVacancy(
                "Een mooie schoonmaakster met ruime ervaring die tevens als model wil fungeren",
                needs.allNeeds().get(1),
                "frans",
                executionContext
                );
    }
    
    @Inject
    Needs needs;
}
