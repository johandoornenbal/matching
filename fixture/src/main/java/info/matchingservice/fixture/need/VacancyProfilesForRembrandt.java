package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.Vacancies;

import javax.inject.Inject;

public class VacancyProfilesForRembrandt extends VacancyProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createVacancyProfile(
                "Man; Middelbare leeftijd; Dik; in het bezit van gouden uniform; kan goed stilstaan",
                vacs.allVacancies().get(3),
                "rembrandt",
                executionContext
                );
        createVacancyProfile(
                "Man met lans. Leeftijd niet van belang.",
                vacs.allVacancies().get(4),
                "rembrandt",
                executionContext
                );
        createVacancyProfile(
                "Bruin; middelgroot; geen poedel.",
                vacs.allVacancies().get(5),
                "rembrandt",
                executionContext
                );
    }
    
    @Inject
    Vacancies vacs;
}
