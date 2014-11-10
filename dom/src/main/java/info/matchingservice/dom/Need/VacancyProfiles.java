package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "60", repositoryFor = VacancyProfile.class)
@Named("Vacancy Profiles")
public class VacancyProfiles extends MatchingDomainService<VacancyProfile> {

    public VacancyProfiles() {
        super(VacancyProfiles.class, VacancyProfile.class);
    }
    
    public List<VacancyProfile> allVacancyProfiles() {
        return allInstances();
    }
    
    @Programmatic
    public VacancyProfile newVacancyProfile(
            final String vacancyProfileDescription,
            final Vacancy vacancyProfileOwner,
            final String ownedBy
            ){
        final VacancyProfile newVacProf = newTransientInstance(VacancyProfile.class);
        newVacProf.setVacancyProfileDescription(vacancyProfileDescription);
        newVacProf.setVacancyProfileOwner(vacancyProfileOwner);
        newVacProf.setOwnedBy(ownedBy);
        persist(newVacProf);
        return newVacProf;
    }

}
