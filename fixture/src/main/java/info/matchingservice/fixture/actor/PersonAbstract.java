package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Persons;

import org.apache.isis.applib.fixturescripts.FixtureScript;
import org.apache.isis.applib.value.Blob;
import org.joda.time.LocalDate;

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
            ExecutionContext executionContext
            ) {
        Actor newPerson = persons.createPerson(firstName, middleName, lastName, dateOfBirth, picture, user);
                       
        return executionContext.add(this, newPerson);
    }
    
    
    //region > injected services
    @javax.inject.Inject
    private Persons persons;


}