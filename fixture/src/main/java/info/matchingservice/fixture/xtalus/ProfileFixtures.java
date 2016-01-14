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
import info.matchingservice.dom.Profile.Profiles;
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
    Profiles profiles;



    protected void createCompany(final String name, final String description, final Person person, final String postal, final String city, final String branche, ExecutionContext executionContext, Supply s){


        Profile p = profiles.createSupplyProfile(name, 10, null, null, ProfileType.COMPANY_PROFILE, null, s, s.getOwnedBy());
        p.createStoryElement(description);
        p.createLocationElement(postal, 10);
        p.createCityElement(city, 10);
        p.createBrancheElement(branche, 10);

        executionContext.add(this, p);



    }

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
        executeChild(new TestSupplies(), executionContext);


        String imgUrl = "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fehsjournal.org%2Fwp-content%2Fuploads%2F2010%2F03%2FJana-Kollarova-Red-Background1.jpg&f=1";
        String story = "Dit is een verhaal over mijzelf.";

        String profileUrl = "http://lh6.ggpht.com/s_VyyVsph5meqhCeEGjTCM1cbzTfWr6rUpQmINYrktB18aHES2QQ7LxD6QrvPA-7i_glG54dQRCvUBFYT38SVDAO=s800";
//        persons.allPersons().forEach(person -> service.createProfile(person, imgUrl, story, profileUrl));
//


        for(Person p: persons.allPersons()){

            Education e = service.createEducation(p, "saxion", "natural leadershio", "informatica");
            executionContext.add(this, e);
            p.setImageUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/KevinSpaceyApr09.jpg/220px-KevinSpaceyApr09.jpg");





        }






        for(Supply s : supplies.allSupplies()){


            if(s.getSupplyType() == DemandSupplyType.PERSON_DEMANDSUPPLY){

                if(s.getOwner() instanceof Person){

                    Person p = (Person) s.getOwner();
                    if(!p.getIsStudent()){
                        createCompany("Code Rehab", "It's time for an intervention", p, "7521BE" , "Enschede", "Webdevelopment", executionContext, s);
                    }
                }


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
