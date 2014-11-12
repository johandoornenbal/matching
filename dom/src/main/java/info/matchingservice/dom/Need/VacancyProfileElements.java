package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "60", repositoryFor = VacancyProfileElement.class)
@Named("Vacancy Profile Elements")
@Hidden
public class VacancyProfileElements extends MatchingDomainService<VacancyProfileElement> {

    public VacancyProfileElements() {
        super(VacancyProfileElements.class, VacancyProfileElement.class);
    }
    
    public List<VacancyProfileElement> allVacancyProfileElements() {
        return allInstances();
    }
    
    @Programmatic
    
    public VacancyProfileElement newVacancyProfileElement(
            final String vacancyProfileElementDescription,
            final VacancyProfile vacancyProfileElementOwner,
            final String ownedBy
            ){
        final VacancyProfileElement newVacProf = newTransientInstance(VacancyProfileElement.class);
        newVacProf.setVacancyProfileElementDescription(vacancyProfileElementDescription);
        newVacProf.setVacancyProfileElementOwner(vacancyProfileElementOwner);
        newVacProf.setOwnedBy(ownedBy);
        persist(newVacProf);
        return newVacProf;
    }

}
