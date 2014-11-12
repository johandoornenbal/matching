package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.VacancyProfiles;

import javax.inject.Inject;

public class VacancyProfileElementsForFrans extends VacancyProfileElementAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createVacancyProfileElement(
                "Jonge Man/Vrouw met 2 jaar portretteer ervaring",
                vacs.allVacancyProfiles().get(0),
                "frans",
                executionContext
                );
        createVacancyProfileElement(
                "Man/Vrouw met minstens 10 jaar all-round schilderservaring.",
                vacs.allVacancyProfiles().get(1),
                "frans",
                executionContext
                );
        createVacancyProfileElement(
                "Vrouw; schoon; goed gevormd",
                vacs.allVacancyProfiles().get(2),
                "frans",
                executionContext
                );
    }
    
    @Inject
    VacancyProfiles vacs;
}
