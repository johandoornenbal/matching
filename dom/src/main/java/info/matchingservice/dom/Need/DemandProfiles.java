package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "50", repositoryFor = DemandProfile.class)
@Named("'Stoelen'")
public class DemandProfiles extends MatchingDomainService<DemandProfile> {

    public DemandProfiles() {
        super(DemandProfiles.class, DemandProfile.class);
    }
    
    @Named("Alle 'stoelen'")
    public List<DemandProfile> allVacancyProfiles() {
        return allInstances();
    }
    
    @Programmatic
    public DemandProfile newVacancy(
            final String vacancyDescription,
            final Need vacancyOwner,
            final String ownedBy
            ){
        final DemandProfile newVac = newTransientInstance(DemandProfile.class);
        newVac.setProfileName(vacancyDescription);
        newVac.setVacancyOwner(vacancyOwner);
        newVac.setOwnedBy(ownedBy);
        persist(newVac);
        return newVac;
    }
    
    @Programmatic
    public DemandProfile newVacancy(
            final String vacancyDescription,
            final Integer weight,
            final Need vacancyOwner,
            final String ownedBy
            ){
        final DemandProfile newVac = newTransientInstance(DemandProfile.class);
        newVac.setProfileName(vacancyDescription);
        newVac.setWeight(weight);
        newVac.setVacancyOwner(vacancyOwner);
        newVac.setOwnedBy(ownedBy);
        persist(newVac);
        return newVac;
    }

}
