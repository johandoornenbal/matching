package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.Qualities;
import info.matchingservice.dom.Dropdown.Quality;
import info.matchingservice.dom.Need.DemandProfile;
import info.matchingservice.dom.Need.DemandProfiles;
import info.matchingservice.dom.Need.Need;
import info.matchingservice.dom.Need.Needs;
import info.matchingservice.dom.Profile.ProfileFigureElements;
import info.matchingservice.dom.Profile.ProfileFigures;
import info.matchingservice.dom.Profile.SupplyProfile;
import info.matchingservice.dom.Profile.SupplyProfiles;

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
            String profileElementDescription2,
            Integer figure2,
            Quality quality,
            String needDescription,
            Integer needWeight,
            String vacancyDescription,
            Integer vacancyWeight,
            String VacancyProfileElementDescription,
            Integer vacFigure,
            Integer elemWeight1,
            String VacancyProfileElementDescription2,
            Integer vacFigure2,
            Integer elemWeight2,
            String VacancyProfileElementDescription3,
            String vacText,
            Integer elemWeight3,
            String user,
            ExecutionContext executionContext
            ) {
        Actor newPerson = persons.newPerson(uniquePartyId, firstName, middleName, lastName, user);
        
        SupplyProfile newProfile = createProfile(newPerson, profileName, user, executionContext);
        
        createProfileFigureElement(newPerson, newProfile, profileElementDescription, figure, user, executionContext);
        createProfileFigureElement(newPerson, newProfile, profileElementDescription2, figure2, user, executionContext);
        
        Need newNeed = createNeed(newPerson, needDescription, needWeight, user, executionContext);
        
        DemandProfile newVacProfile = createVacancyProfile(newPerson, newNeed, vacancyDescription, vacancyWeight, user, executionContext);
        
//        createVacancyProfileFigureElement(newPerson, newVacProfile, VacancyProfileElementDescription, vacFigure, elemWeight1, user, executionContext);
//        createVacancyProfileFigureElement(newPerson, newVacProfile, VacancyProfileElementDescription2, vacFigure2, elemWeight2, user, executionContext);
//               
        return executionContext.add(this, newPerson);
    }
    
    protected SupplyProfile createProfile(
            Actor newPerson,
            String profileName,
            String user,
            ExecutionContext executionContext
            ){
        SupplyProfile newProfile = profiles.newProfile(profileName, (Person) newPerson, user);
        getContainer().flush();
        return executionContext.add(this, newProfile);
    }
    
    protected Actor createProfileFigureElement(
            Actor newPerson,
            SupplyProfile newProfile,
            String profileElementDescription,
            Integer figure,
            String user,
            ExecutionContext executionContext
            ){
        peFigures.newProfileElement(profileElementDescription, figure, newProfile, user);
        getContainer().flush();
        return executionContext.add(this, newPerson);
    }
    
    protected Need createNeed(
            Actor newPerson,
            String needDescription,
            Integer weight,
            String user,
            ExecutionContext executionContext
            ){
        Need newNeed = needs.newNeed(needDescription, weight, (Person) newPerson, user);
        getContainer().flush();
        return executionContext.add(this, newNeed);
    }
    
    protected DemandProfile createVacancyProfile(
            Actor newPerson,
            Need newNeed,
            String vacancyDescription,
            Integer weight,
            String user,
            ExecutionContext executionContext
            ){
        DemandProfile newVacProfile = vacancyprofiles.newDemand(vacancyDescription, weight, newNeed, user);
        getContainer().flush();
        return executionContext.add(this, newVacProfile);
    }
    
//    protected Actor createVacancyProfileFigureElement(
//            Actor newPerson,
//            DemandProfile newVacProfile,
//            String profileElementDescription,
//            Integer figure,
//            Integer weight,
//            String user,
//            ExecutionContext executionContext
//            ){
//        vpeFigures.newProfileElement(profileElementDescription, figure, weight, newVacProfile, user);
//        getContainer().flush();
//        return executionContext.add(this, newPerson);
//    }
    

    
    //region > injected services
    @javax.inject.Inject
    private Persons persons;
    
    @Inject
    private SupplyProfiles profiles;
    
    @Inject
    private ProfileFigures peFigures;
    
    @Inject
    ProfileFigureElements profileElements;
    
    @Inject
    Needs needs;
    
    @Inject
    DemandProfiles vacancyprofiles;

    
    @Inject
    Qualities qualities;

}