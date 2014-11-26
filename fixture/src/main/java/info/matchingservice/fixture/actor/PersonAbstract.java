package info.matchingservice.fixture.actor;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Persons;
import info.matchingservice.dom.Demand.DemandProfiles;
import info.matchingservice.dom.Demand.Demands;
import info.matchingservice.dom.Dropdown.Qualities;
import info.matchingservice.dom.Dropdown.Quality;
import info.matchingservice.dom.Profile.ProfileFigureElements;
import info.matchingservice.dom.Profile.ProfileFigures;

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
                       
        return executionContext.add(this, newPerson);
    }
    
//    protected SupplyProfile createProfile(
//            Actor newPerson,
//            String profileName,
//            String user,
//            ExecutionContext executionContext
//            ){
//        SupplyProfile newProfile = profiles.newProfile(profileName, (Person) newPerson, user);
//        getContainer().flush();
//        return executionContext.add(this, newProfile);
//    }
//    
//    protected Actor createProfileFigureElement(
//            Actor newPerson,
//            SupplyProfile newProfile,
//            String profileElementDescription,
//            Integer figure,
//            String user,
//            ExecutionContext executionContext
//            ){
//        peFigures.newProfileElement(profileElementDescription, figure, newProfile, user);
//        getContainer().flush();
//        return executionContext.add(this, newPerson);
//    }
//    
//    protected Demand createNeed(
//            Actor newPerson,
//            String needDescription,
//            Integer weight,
//            String user,
//            ExecutionContext executionContext
//            ){
//        Demand newNeed = needs.newDemand(needDescription, weight, (Person) newPerson, user);
//        getContainer().flush();
//        return executionContext.add(this, newNeed);
//    }
//    
//    protected DemandProfile createVacancyProfile(
//            Actor newPerson,
//            Demand newNeed,
//            String vacancyDescription,
//            Integer weight,
//            String user,
//            ExecutionContext executionContext
//            ){
//        DemandProfile newVacProfile = vacancyprofiles.newDemand(vacancyDescription, weight, newNeed, user);
//        getContainer().flush();
//        return executionContext.add(this, newVacProfile);
//    }
    
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
    private ProfileFigures peFigures;
    
    @Inject
    ProfileFigureElements profileElements;
    
    @Inject
    Demands needs;
    
    @Inject
    DemandProfiles vacancyprofiles;

    
    @Inject
    Qualities qualities;

}