package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.ProfileElementType;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "70", repositoryFor = VacancyProfileFigureElement.class)
@Named("Profiel elementen")
@Hidden
public class VacancyProfileFigureElements extends MatchingDomainService<VacancyProfileFigureElement> {

    public VacancyProfileFigureElements() {
        super(VacancyProfileFigureElements.class, VacancyProfileFigureElement.class);
    }
    
    @Named("Alle getal elementen")
    public List<VacancyProfileFigureElement> allProfileElements() {
        return allInstances();
    }
    
    @Programmatic    
    public VacancyProfileFigureElement newProfileElement(
            final String profileElementDescription,
            final Integer figure,
            final DemandProfile profileElementOwner,
            final String ownedBy
            ){
        final VacancyProfileFigureElement newProf = newTransientInstance(VacancyProfileFigureElement.class);
        newProf.setProfileElementNature(ProfileElementNature.MULTI_ELEMENT); // default
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_FIGURE); // default
        newProf.setVacancyProfileElementDescription(profileElementDescription);
        newProf.setFigure(figure);
        newProf.setVacancyProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }
    
    @Programmatic    
    public VacancyProfileFigureElement newProfileElement(
            final String profileElementDescription,
            final Integer figure,
            final Integer weight,
            final DemandProfile profileElementOwner,
            final String ownedBy
            ){
        final VacancyProfileFigureElement newProf = newTransientInstance(VacancyProfileFigureElement.class);
        newProf.setProfileElementNature(ProfileElementNature.MULTI_ELEMENT); // default
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_FIGURE); // default
        newProf.setVacancyProfileElementDescription(profileElementDescription);
        newProf.setWeight(weight);
        newProf.setFigure(figure);
        newProf.setVacancyProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }
    
    @Programmatic    
    public VacancyProfileFigureElement newProfileElement(
            final String profileElementDescription,
            final DemandProfile profileElementOwner,
            final String ownedBy,
            final ProfileElementNature nature
            ){
        final VacancyProfileFigureElement newProf = newTransientInstance(VacancyProfileFigureElement.class);
        newProf.setProfileElementNature(nature);
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_FIGURE); // default
        newProf.setVacancyProfileElementDescription(profileElementDescription);
        newProf.setVacancyProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }

}
