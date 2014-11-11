package info.matchingservice.fixture.need;

import info.matchingservice.dom.Need.Needs;

import javax.inject.Inject;

public class VacanciesForFrans extends VacancyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {

        createVacancy(
                "Junior hulpschilder",
                "man/vrouw met 2 jaar portretteer ervaring. leeftijd onder de 25 jaar. passie voor olieverf.",
                2,
                needs.allNeeds().get(0),
                "frans",
                executionContext
                );
        createVacancy(
                "Een ervaren senior hulpschilder",
                "man/vrouw met minstens 10 jaar all-round schilderservaring. leeftijd niet van belang. in het bezit van eigen penselen. geen 8 tot 5 mentaliteit",
                4,
                needs.allNeeds().get(0),
                "frans",
                executionContext
                );
        createVacancy(
                "Een schoonmaakster die tevens model wil zijn",
                "vrouw; schoon; goed gevormd en rondborstig; 24 uur standby",
                9,
                needs.allNeeds().get(1),
                "frans",
                executionContext
                );
    }
    
    @Inject
    Needs needs;
}
