package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Dropdown.Qualities;
import info.matchingservice.dom.Dropdown.Quality;
import info.matchingservice.dom.Need.PersonNeed;
import info.matchingservice.dom.Need.PersonNeeds;
import info.matchingservice.dom.Need.VacancyProfile;
import info.matchingservice.dom.Need.VacancyProfiles;
import info.matchingservice.dom.Need.Vpe_Figures;
import info.matchingservice.dom.Need.Vpe_Keywords;
import info.matchingservice.dom.Profile.Pe_Figures;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileElements;
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
        
        Profile newProfile = createProfile(newPerson, profileName, user, executionContext);
        
        createProfileFigureElement(newPerson, newProfile, profileElementDescription, figure, user, executionContext);
        createProfileFigureElement(newPerson, newProfile, profileElementDescription2, figure2, user, executionContext);
        
        PersonNeed newNeed = createNeed(newPerson, needDescription, needWeight, user, executionContext);
        
        VacancyProfile newVacProfile = createVacancyProfile(newPerson, newNeed, vacancyDescription, vacancyWeight, user, executionContext);
        
        createVacancyProfileFigureElement(newPerson, newVacProfile, VacancyProfileElementDescription, vacFigure, elemWeight1, user, executionContext);
        createVacancyProfileFigureElement(newPerson, newVacProfile, VacancyProfileElementDescription2, vacFigure2, elemWeight2, user, executionContext);
               
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
    
    protected Actor createProfileFigureElement(
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
    
    protected PersonNeed createNeed(
            Actor newPerson,
            String needDescription,
            Integer weight,
            String user,
            ExecutionContext executionContext
            ){
        PersonNeed newNeed = personNeeds.newNeed(needDescription, weight, (Person) newPerson, user);
        getContainer().flush();
        return executionContext.add(this, newNeed);
    }
    
    protected VacancyProfile createVacancyProfile(
            Actor newPerson,
            PersonNeed newNeed,
            String vacancyDescription,
            Integer weight,
            String user,
            ExecutionContext executionContext
            ){
        VacancyProfile newVacProfile = vacancyprofiles.newVacancy(vacancyDescription, weight, newNeed, user);
        getContainer().flush();
        return executionContext.add(this, newVacProfile);
    }
    
    protected Actor createVacancyProfileFigureElement(
            Actor newPerson,
            VacancyProfile newVacProfile,
            String profileElementDescription,
            Integer figure,
            Integer weight,
            String user,
            ExecutionContext executionContext
            ){
        vpeFigures.newProfileElement(profileElementDescription, figure, weight, newVacProfile, user);
        getContainer().flush();
        return executionContext.add(this, newPerson);
    }
    
    protected Actor createVacancyProfileTextElement(
            Actor newPerson,
            VacancyProfile newVacProfile,
            String profileElementDescription,
            String text,
            Integer weight,
            String user,
            ExecutionContext executionContext
            ){
        vpeKeywords.newProfileElement(profileElementDescription, text, weight, newVacProfile, user);
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
    
    @Inject
    ProfileElements profileElements;
    
    @Inject
    PersonNeeds personNeeds;
    
    @Inject
    VacancyProfiles vacancyprofiles;
    
    @Inject
    Vpe_Figures vpeFigures;
    
    @Inject
    Vpe_Keywords vpeKeywords;
    
    @Inject
    Qualities qualities;

}