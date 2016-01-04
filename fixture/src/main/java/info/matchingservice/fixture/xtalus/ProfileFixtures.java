package info.matchingservice.fixture.xtalus;

import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Xtalus.XtalusService;
import org.apache.isis.applib.fixturescripts.FixtureScript;

import javax.inject.Inject;

/**
 * Created by jonathan on 4-1-16.
 */
public class ProfileFixtures extends FixtureScript {


    @Inject
    Persons persons;

    @Inject
    XtalusService service;


    @Override
    protected void execute(ExecutionContext executionContext) {


        String imgUrl = "https://images.duckduckgo.com/iu/?u=http%3A%2F%2Fehsjournal.org%2Fwp-content%2Fuploads%2F2010%2F03%2FJana-Kollarova-Red-Background1.jpg&f=1";
        String story = "Dit is een verhaal over mijzelf.";

        String profileUrl = "http://lh6.ggpht.com/s_VyyVsph5meqhCeEGjTCM1cbzTfWr6rUpQmINYrktB18aHES2QQ7LxD6QrvPA-7i_glG54dQRCvUBFYT38SVDAO=s800";
        persons.allPersons().forEach(person -> service.createProfile(person, imgUrl, story, profileUrl));


    }




}
