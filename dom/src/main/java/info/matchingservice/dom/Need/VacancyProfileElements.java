package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.ProfileElementType;
import info.matchingservice.dom.Dropdown.Quality;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "60", repositoryFor = VacancyProfileElement.class)
@Named("Profiel element 'stoel'")
@Hidden
public class VacancyProfileElements extends MatchingDomainService<VacancyProfileElement> {

    public VacancyProfileElements() {
        super(VacancyProfileElements.class, VacancyProfileElement.class);
    }
    
    @Named("Alle profiel elementen van alle stoelen")
    public List<VacancyProfileElement> allVacancyProfileElements() {
        return allInstances();
    }
    
    @Programmatic
    public VacancyProfileElement newVacancyProfileElement(
            final String vacancyProfileElementDescription,
            final DemandProfile vacancyProfileElementOwner,
            final String ownedBy
            ){
        final VacancyProfileElement newVacProf = newTransientInstance(VacancyProfileElement.class);
        newVacProf.setProfileElementNature(ProfileElementNature.MULTI_ELEMENT); // default
        newVacProf.setProfileElementType(ProfileElementType.MATCHABLE_KEYWORDS); // default
        newVacProf.setVacancyProfileElementDescription(vacancyProfileElementDescription);
        newVacProf.setVacancyProfileElementOwner(vacancyProfileElementOwner);
        newVacProf.setOwnedBy(ownedBy);
        persist(newVacProf);
        return newVacProf;
    }
    
    @Programmatic
    public VacancyProfileElement newVacancyProfileElement(
            final String vacancyProfileElementDescription,
            final DemandProfile vacancyProfileElementOwner,
            final String ownedBy,
            final ProfileElementNature nature
            ){
        final VacancyProfileElement newVacProf = newTransientInstance(VacancyProfileElement.class);
        newVacProf.setProfileElementNature(nature); // default
        newVacProf.setProfileElementType(ProfileElementType.MATCHABLE_KEYWORDS); // default
        newVacProf.setVacancyProfileElementDescription(vacancyProfileElementDescription);
        newVacProf.setVacancyProfileElementOwner(vacancyProfileElementOwner);
        newVacProf.setOwnedBy(ownedBy);
        persist(newVacProf);
        return newVacProf;
    }

    @Programmatic
    public VacancyProfileDropDownElement newDropdownElement(
            final Quality keyword,
            final Integer weight,
            final DemandProfile vacancyProfileElementOwner,
            final String ownedBy,
            final ProfileElementNature nature           
            ){
        final VacancyProfileDropDownElement newElement = newTransientInstance(VacancyProfileDropDownElement.class);
        newElement.setKeyword(keyword);
        newElement.setWeight(weight);
        newElement.setProfileElementNature(nature);
        newElement.setProfileElementType(ProfileElementType.MATCHABLE_DROPDOWN);
        newElement.setVacancyProfileElementDescription(keyword.toString());
        newElement.setVacancyProfileElementOwner(vacancyProfileElementOwner);
        newElement.setOwnedBy(ownedBy);
        persist(newElement);
        return newElement;
    }
}
