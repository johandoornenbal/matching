package info.matchingservice.fixture.actor;

import org.joda.time.LocalDate;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.value.Blob;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Persons;

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
        Actor newPerson = persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, null, user);
        newPerson.setActivated(activated);
                       
        return executionContext.add(this, newPerson);
    }
    
    
    //region > injected services
    @javax.inject.Inject
    private Persons persons;


}