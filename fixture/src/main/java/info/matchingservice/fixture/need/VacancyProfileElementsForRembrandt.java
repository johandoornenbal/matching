package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.VacancyProfiles;

import javax.inject.Inject;

public class VacancyProfileElementsForRembrandt extends VacancyProfileElementAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createVacancyProfileElement(
                "Man; Middelbare leeftijd; Dik; in het bezit van gouden uniform; kan goed stilstaan",
                vacs.allVacancyProfiles().get(3),
                "rembrandt",
                executionContext
                );
        createVacancyProfileElement(
                "Man met lans. Leeftijd niet van belang. Kan goed stilstaan",
                vacs.allVacancyProfiles().get(4),
                "rembrandt",
                executionContext
                );
        createVacancyProfileElement(
                "Bruin; middelgroot; geen poedel; kan goed stilzitten.",
                vacs.allVacancyProfiles().get(5),
                "rembrandt",
                executionContext
                );
    }
    
    @Inject
    VacancyProfiles vacs;
}
