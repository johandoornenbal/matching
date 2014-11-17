package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "50", repositoryFor = VacancyProfile.class)
@Named("'Stoelen'")
public class VacancyProfiles extends MatchingDomainService<VacancyProfile> {

    public VacancyProfiles() {
        super(VacancyProfiles.class, VacancyProfile.class);
    }
    
    @Named("Alle 'stoelen'")
    public List<VacancyProfile> allVacancyProfiles() {
        return allInstances();
    }
    
    @Programmatic
    public VacancyProfile newVacancy(
            final String vacancyDescription,
            final Need vacancyOwner,
            final String ownedBy
            ){
        final VacancyProfile newVac = newTransientInstance(VacancyProfile.class);
        newVac.setVacancyDescription(vacancyDescription);
        newVac.setVacancyOwner(vacancyOwner);
        newVac.setOwnedBy(ownedBy);
        persist(newVac);
        return newVac;
    }
    
    @Programmatic
    public VacancyProfile newVacancy(
            final String vacancyDescription,
            final Integer weight,
            final Need vacancyOwner,
            final String ownedBy
            ){
        final VacancyProfile newVac = newTransientInstance(VacancyProfile.class);
        newVac.setVacancyDescription(vacancyDescription);
        newVac.setWeight(weight);
        newVac.setVacancyOwner(vacancyOwner);
        newVac.setOwnedBy(ownedBy);
        persist(newVac);
        return newVac;
    }

}
