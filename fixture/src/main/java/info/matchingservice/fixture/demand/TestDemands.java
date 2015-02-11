package info.matchingservice.fixture.demand;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

public class TestDemands extends DemandAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
    	
    	//** frans **//
        
        createDemand(
                "1 Mensen gezocht voor schilderproject",
                "Het gaat om een groot project waarin ik een veldslag hoop te vast te leggen voor het nageslacht.",
                "Het gaat om een groot project waarin ik een veldslag hoop te vast te leggen voor het nageslacht."
                + " Ik hoop een gedeelte daarvan op zee te laten afspelen op de achtergrond van het doek. Veel figuranten "
                + "die de komende maanden op afroep beschikbaar zijn zijn noodzakelijk voor het slagen van dit monsterproject.",
                10,
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Hals").get(0),
                "frans",
                executionContext
                );
        
        createDemand(
                "2 Groot spectakel",
                "Samengevat: blabla",
                "Het hele verhaal: blablabla",
                10,
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Hals").get(0),
                "frans",
                executionContext
                );
        
        createDemand(
                "3 Wetenschappelijk onderzoek",
                "Samengevat: blabla",
                "Het hele verhaal: blablabla",
                10,
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Hals").get(0),
                "frans",
                executionContext
                );
        
        //** rembrandt **//
        
        createDemand(
                "Hulp gezocht voor De Nachtwacht",
                "Samengevat: blabla",
                "Het hele verhaal: blablabla",
                10,
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Rijn").get(0),
                "rembrandt",
                executionContext
                );
        
        //** michiel **//
        
        createDemand(
                "Ik wil een oorlog beginnen",
                "Samengevat: blabla",
                "Het hele verhaal: blablabla",
                10,
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Ruyter").get(0),
                "michiel",
                executionContext
                );
    }

    @Inject
    private Persons persons;
}
