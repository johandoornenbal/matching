package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.Demands;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class TestDemandProfiles extends DemandProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestDemands(), executionContext);
        
        //** frans **//
        
        createDemandProfile(
                "Gezocht: nieuwsgierige persoon",
                10,
                ProfileType.PERSON_PROFILE,
                demands.findDemandByDescription("schilderproject", "frans").get(0),
                "frans",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht: oorlogszuchtige persoon",
                10,
                ProfileType.PERSON_PROFILE,
                demands.findDemandByDescription("schilderproject", "frans").get(0),
                "frans",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht: meelevende persooonlijkheden",
                10,
                ProfileType.PERSON_PROFILE,
                demands.findDemandByDescription("schilderproject", "frans").get(0),
                "frans",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht: iemand die in is voor aktie",
                10,
                ProfileType.PERSON_PROFILE,
                demands.findDemandByDescription("spectakel", "frans").get(0),
                "frans",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht: onderzoekend persoon",
                10,
                ProfileType.PERSON_PROFILE,
                demands.findDemandByDescription("onderzoek", "frans").get(0),
                "frans",
                executionContext
                );
        
        //** rembrandt **//
        
        createDemandProfile(
                "Gezocht: commandant die wil poseren",
                10,
                ProfileType.PERSON_PROFILE,
                demands.findDemandByDescription("Nachtwacht", "rembrandt").get(0),
                "rembrandt",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht: figurant voor de Nachtwacht",
                10,
                ProfileType.PERSON_PROFILE,
                demands.findDemandByDescription("Nachtwacht", "rembrandt").get(0),
                "rembrandt",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht: model1",
                10,
                ProfileType.PERSON_PROFILE,
                demands.findDemandByDescription("Nachtwacht", "rembrandt").get(0),
                "rembrandt",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht: model2",
                10,
                ProfileType.PERSON_PROFILE,
                demands.findDemandByDescription("Nachtwacht", "rembrandt").get(0),
                "rembrandt",
                executionContext
                );
        
        createDemandProfile(
                "Gezocht: huurling",
                10,
                ProfileType.PERSON_PROFILE,
                demands.findDemandByDescription("oorlog", "michiel").get(0),
                "michiel",
                executionContext
                );
        
        createDemandProfile(
                "Commandant",
                10,
                ProfileType.PERSON_PROFILE,
                demands.findDemandByDescription("oorlog", "michiel").get(0),
                "michiel",
                executionContext
                );
        
        createDemandProfile(
                "infanterist",
                10,
                ProfileType.PERSON_PROFILE,
                demands.findDemandByDescription("oorlog", "michiel").get(0),
                "michiel",
                executionContext
                );
        
        createDemandProfile(
                "iemand met zeebenen",
                10,
                ProfileType.PERSON_PROFILE,
                demands.findDemandByDescription("oorlog", "michiel").get(0),
                "michiel",
                executionContext
                );
        
    }
    
    @Inject
    private Demands demands;
}
