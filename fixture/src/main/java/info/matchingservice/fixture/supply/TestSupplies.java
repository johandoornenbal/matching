package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

import org.joda.time.LocalDate;

public class TestSupplies extends SupplyAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
    	
    	//** frans **//
        
        createSupply(
                "PERSON_SUPPLY_OF",
                10,
                new LocalDate(2015, 01, 01),
                new LocalDate(2015, 12, 31),
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Hals").get(0),
                "frans",
                executionContext
                );
        
        //** gerard **//
        
        createSupply(
                "PERSON_SUPPLY_OF",
                10,
                new LocalDate(2015, 07, 01),
                new LocalDate(2015, 12, 31),
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Dou").get(0),
                "gerard",
                executionContext
                );
        
        //** rembrandt **//
        createSupply(
                "PERSON_SUPPLY_OF",
                10,
                new LocalDate(2015, 01, 01),
                new LocalDate(2015, 12, 31),
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Rijn").get(0),
                "rembrandt",
                executionContext
                );
        
        //** michiel **//
        createSupply(
                "PERSON_SUPPLY_OF",
                10,
                new LocalDate(2015, 01, 01),
                new LocalDate(2017, 12, 31),
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Ruyter").get(0),
                "michiel",
                executionContext
                );
        
        //** antoni **//
        createSupply(
                "PERSON_SUPPLY_OF",
                10,
                new LocalDate(2015, 01, 01),
                new LocalDate(2015, 12, 31),
                DemandSupplyType.PERSON_DEMANDSUPPLY,
                persons.findPersons("Leeuw").get(0),
                "antoni",
                executionContext
                );


       
    }

    @Inject
    private Persons persons;
}
