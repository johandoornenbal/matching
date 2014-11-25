package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.Needs;

import javax.inject.Inject;

public class VacanciesForRembrandt extends VacancyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createVacancy(
                "Een gezette corporaal",
//                "man van middelbare leeftijd; gezet of ronduit dik; in het bezit van gouden uniform; kan goed stilstaan; onderkin is pre",
//                3,
                needs.allNeeds().get(2),
                "rembrandt",
                executionContext
                );
        createVacancy(
                "Een lansdrager",
//                "Man in het bezit van lans. Leeftijd niet van belang. Kan goed stilstaan. Lengte groter dan 1.80 is pre.",
//                4,
                needs.allNeeds().get(2),
                "rembrandt",
                executionContext
                );
        createVacancy(
                "Hond",
//                "Woef. Geen chihuahua en/of poedel.",
//                1,
                needs.allNeeds().get(2),
                "rembrandt",
                executionContext
                );
    }
    
    @Inject
    Needs needs;
}
