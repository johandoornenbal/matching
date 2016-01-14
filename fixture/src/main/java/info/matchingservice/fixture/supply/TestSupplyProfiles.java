package info.matchingservice.fixture.supply;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Supplies;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.fixture.actor.TestPersons;

import javax.inject.Inject;

import org.joda.time.LocalDate;

import java.util.Optional;

public class TestSupplyProfiles extends SupplyProfileAbstract {

    @Override
    protected void execute(ExecutionContext executionContext) {
        
        //preqs
    	executeChild(new TestPersons(), executionContext);
        executeChild(new TestSupplies(), executionContext);
        
        //** frans **//
        
        createSupplyProfile(
                "PERSON_PROFILE_OF",
                10,
                null,
                null,
                ProfileType.PERSON_PROFILE,
                supplies.findSupplyByDescription("PERSON_SUPPLY_OF", "frans").get(0),
                "frans",
                executionContext
                );
        
        //** gerard **//
        createSupplyProfile(
                "PERSON_PROFILE_OF",
                10,
                new LocalDate(2015, 3, 1),
                new LocalDate(2015, 3, 10),
                ProfileType.PERSON_PROFILE,
                supplies.findSupplyByDescription("PERSON_SUPPLY_OF", "gerard").get(0),
                "gerard",
                executionContext
                );
        
        //** rembrandt **//
        createSupplyProfile(
                "PERSON_PROFILE_OF",
                10,
                new LocalDate(2015, 2, 1),
                new LocalDate(2015, 5, 31),
                ProfileType.PERSON_PROFILE,
                supplies.findSupplyByDescription("PERSON_SUPPLY_OF", "rembrandt").get(0),
                "rembrandt",
                executionContext
                );
        
        //** michiel **//
        createSupplyProfile(
                "PERSON_PROFILE_OF",
                10,
                null,
                null,
                ProfileType.PERSON_PROFILE,
                supplies.findSupplyByDescription("PERSON_SUPPLY_OF", "michiel").get(0),
                "michiel",
                executionContext
                );
        
        //** antoni **//
        createSupplyProfile(
                "PERSON_PROFILE_OF",
                10,
                null,
                null,
                ProfileType.PERSON_PROFILE,
                supplies.findSupplyByDescription("PERSON_SUPPLY_OF", "antoni").get(0),
                "antoni",
                executionContext
                );


    }

    @Inject
    Persons persons;


    @Inject
    private Supplies supplies;
}
