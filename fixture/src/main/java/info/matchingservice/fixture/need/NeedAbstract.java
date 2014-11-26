package info.matchingservice.fixture.need;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Need.DemandProfile;
import info.matchingservice.dom.Need.DemandProfiles;
import info.matchingservice.dom.Need.Need;
import info.matchingservice.dom.Need.Needs;

import javax.inject.Inject;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class NeedAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected Need createNeed(
            Person needOwner,
            String needDescription,
            Integer weight,
            String vacancyDescription,
            Integer vacancyWeight,
            String VacancyProfileElementDescription,
            Integer vacFigure,
            Integer elemWeight1,
            String user,
            ExecutionContext executionContext
            ) {
        Need newNeed = needs.newNeed(needDescription, weight, needOwner, user);
        
        DemandProfile newVacProfile = createVacancyProfile(needOwner, newNeed, vacancyDescription, vacancyWeight, user, executionContext);
        
//        createVacancyProfileFigureElement(newNeed, newVacProfile, VacancyProfileElementDescription, vacFigure, elemWeight1, user, executionContext);
        return executionContext.add(this,newNeed);
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
    
//    protected Need createVacancyProfileFigureElement(
//            Need newNeed,
//            DemandProfile newVacProfile,
//            String profileElementDescription,
//            Integer figure,
//            Integer weight,
//            String user,
//            ExecutionContext executionContext
//            ){
//        vpeFigures.newProfileElement(profileElementDescription, figure, weight, newVacProfile, user);
//        getContainer().flush();
//        return executionContext.add(this, newNeed);
//    }
    
    //region > injected services
    @javax.inject.Inject
    Needs needs;
    
    @Inject
    DemandProfiles vacancyprofiles;
    
//    @Inject
//    VacancyProfileFigureElements vpeFigures;
}
