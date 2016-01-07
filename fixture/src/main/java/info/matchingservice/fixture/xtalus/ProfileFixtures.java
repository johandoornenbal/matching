package info.matchingservice.fixture.xtalus;

import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Api.Api;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Demands;
import info.matchingservice.dom.DemandSupply.Supplies;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElement;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Xtalus.Education;
import info.matchingservice.dom.Xtalus.XtalusService;
import info.matchingservice.fixture.actor.TestPersons;
import info.matchingservice.fixture.actor.TestRoles;
import info.matchingservice.fixture.demand.*;
import info.matchingservice.fixture.supply.TestSupplies;
import info.matchingservice.fixture.supply.TestSupplyProfileElementsPersonProfiles;
import info.matchingservice.fixture.supply.TestSupplyProfiles;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.joda.time.LocalDate;

import javax.inject.Inject;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.SortedSet;
import java.util.stream.Collectors;

/**
 * Created by jonathan on 4-1-16.
 */
public class ProfileFixtures extends FixtureScript {


    @Inject
    private Api api;

    @Inject
    private Persons persons;

    @Inject
    private XtalusService service;




    @Inject
    Supplies supplies;


    @Override
    protected void execute(ExecutionContext executionContext) {

        executeChild(new TestPersons(), executionContext);
        executeChild(new TestRoles(), executionContext);
        executeChild(new TestDemands(), executionContext);
        executeChild(new TestDemandProfiles(), executionContext);
        executeChild(new DemandProfileElementsForFrans(), executionContext);
        executeChild(new DemandProfileElementsForRembrandt(), executionContext);
        executeChild(new DemandProfileElementsForMichiel(), executionContext);
        executeChild(new TestSupplies(), executionContext);
        executeChild(new TestSupplyProfiles(), executionContext);
        executeChild(new TestSupplyProfileElementsPersonProfiles(), executionContext);

        String imgUrl = "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fehsjournal.org%2Fwp-content%2Fuploads%2F2010%2F03%2FJana-Kollarova-Red-Background1.jpg&f=1";
        String story = "Dit is een verhaal over mijzelf.";

        String profileUrl = "http://lh6.ggpht.com/s_VyyVsph5meqhCeEGjTCM1cbzTfWr6rUpQmINYrktB18aHES2QQ7LxD6QrvPA-7i_glG54dQRCvUBFYT38SVDAO=s800";
//        persons.allPersons().forEach(person -> service.createProfile(person, imgUrl, story, profileUrl));
//


        for(Person p: persons.allPersons()){

            Education e = service.createEducation(p, "saxion", "natural leadershio", "informatica");
            executionContext.add(this, e);

        }





        for(Supply s : supplies.allSupplies()){


            if(s.getSupplyType() == DemandSupplyType.PERSON_DEMANDSUPPLY){

                for(Profile p: s.getProfiles()){

                    if(p.getType() == ProfileType.PERSON_PROFILE){

                        System.out.println("PERSON PROFILE OF"+ s.getOwner());
                        p.createStoryElement(story);
                        p.createBackgroundImgElement(imgUrl);


                    }
                }

            }

        }


        persons.allPersons().stream().filter(Person::getIsStudent).forEach(person2 -> service.createInterestProfile(person2, "Leuk bijbaantje", LocalDate.now(), LocalDate.now().plusMonths(1), 5, 60));


    }




}
