package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Profile.Pe_Figures;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.Profiles;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class PersonAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Actor createPerson (
            String uniquePartyId,
            String firstName,
            String middleName,
            String lastName,
            String profileName,
            String profileElementDescription,
            Integer figure,
            String user,
            ExecutionContext executionContext
            ) {
        Actor newPerson = persons.newPerson(uniquePartyId, firstName, middleName, lastName, user);
        
        Profile newProfile = createProfile(newPerson, profileName, user, executionContext);
        
        createProfileElement(newPerson, newProfile, profileElementDescription, figure, user, executionContext);
        
        return executionContext.add(this, newPerson);
    }
    
    protected Profile createProfile(
            Actor newPerson,
            String profileName,
            String user,
            ExecutionContext executionContext
            ){
        Profile newProfile = profiles.newProfile(profileName, (Person) newPerson, user);
        getContainer().flush();
        return executionContext.add(this, newProfile);
    }
    
    protected Actor createProfileElement(
            Actor newPerson,
            Profile newProfile,
            String profileElementDescription,
            Integer figure,
            String user,
            ExecutionContext executionContext
            ){
        peFigures.newProfileElement(profileElementDescription, figure, newProfile, user);
        getContainer().flush();
        return executionContext.add(this, newPerson);
    }
    
    //region > injected services
    @javax.inject.Inject
    private Persons persons;
    
    @Inject
    private Profiles profiles;
    
    @Inject
    private Pe_Figures peFigures;

}