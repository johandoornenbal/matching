package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.TrustedCircles.TrustedCircleConfigRepo;
import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.value.Blob;
import org.joda.time.LocalDate;

import javax.inject.Inject;

public abstract class PersonAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Actor createPerson (
            String firstName,
            String middleName,
            String lastName,
            LocalDate dateOfBirth,
            Blob picture,
            String user,
            boolean activated,
            ExecutionContext executionContext
            ) {
        Actor newPerson = persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, "link", null, user);
        newPerson.setActivated(activated);
        trustedCircleConfigRepo.findOrCreateConfig(user);
                       
        return executionContext.add(this, newPerson);
    }
    
    
    //region > injected services
    @javax.inject.Inject
    private Persons persons;

    @Inject
    private TrustedCircleConfigRepo trustedCircleConfigRepo;


}