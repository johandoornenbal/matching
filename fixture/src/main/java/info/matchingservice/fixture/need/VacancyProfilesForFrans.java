package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.Vacancies;

import javax.inject.Inject;

public class VacancyProfilesForFrans extends VacancyProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createVacancyProfile(
                "Jonge Man/Vrouw met 2 jaar portretteer ervaring",
                vacs.allVacancies().get(0),
                "frans",
                executionContext
                );
        createVacancyProfile(
                "Man/Vrouw met minstens 10 jaar all-round schilderservaring.",
                vacs.allVacancies().get(1),
                "frans",
                executionContext
                );
        createVacancyProfile(
                "Vrouw; schoon; goed gevormd",
                vacs.allVacancies().get(2),
                "frans",
                executionContext
                );
    }
    
    @Inject
    Vacancies vacs;
}
