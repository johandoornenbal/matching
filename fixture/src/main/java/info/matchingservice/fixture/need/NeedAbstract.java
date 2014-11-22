package info.matchingservice.fixture.need;

import javax.inject.Inject;

import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Need.PersonNeed;
import info.matchingservice.dom.Need.PersonNeeds;
import info.matchingservice.dom.Need.VacancyProfile;
import info.matchingservice.dom.Need.VacancyProfiles;
import info.matchingservice.dom.Need.Vpe_Figures;

import org.apache.isis.applib.fixturescripts.FixtureScript;

public abstract class NeedAbstract extends FixtureScript {

    @Override
    protected abstract void execute(ExecutionContext executionContext);
    
    protected PersonNeed createNeed(
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
        PersonNeed newNeed = needs.newNeed(needDescription, weight, needOwner, user);
        
        VacancyProfile newVacProfile = createVacancyProfile(needOwner, newNeed, vacancyDescription, vacancyWeight, user, executionContext);
        
        createVacancyProfileFigureElement(newNeed, newVacProfile, VacancyProfileElementDescription, vacFigure, elemWeight1, user, executionContext);
        return executionContext.add(this,newNeed);
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
    
    protected PersonNeed createVacancyProfileFigureElement(
            PersonNeed newNeed,
            VacancyProfile newVacProfile,
            String profileElementDescription,
            Integer figure,
            Integer weight,
            String user,
            ExecutionContext executionContext
            ){
        vpeFigures.newProfileElement(profileElementDescription, figure, weight, newVacProfile, user);
        getContainer().flush();
        return executionContext.add(this, newNeed);
    }
    
    //region > injected services
    @javax.inject.Inject
    PersonNeeds needs;
    
    @Inject
    VacancyProfiles vacancyprofiles;
    
    @Inject
    Vpe_Figures vpeFigures;
}
