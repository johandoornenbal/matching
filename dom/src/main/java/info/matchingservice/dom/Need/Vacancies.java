package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "50", repositoryFor = Vacancy.class)
@Named("Vacancies")
public class Vacancies extends MatchingDomainService<Vacancy> {

    public Vacancies() {
        super(Vacancies.class, Vacancy.class);
    }
    
    public List<Vacancy> allVacancies() {
        return allInstances();
    }
    
    @Programmatic
    public Vacancy newVacancy(
            final String vacancyDescription,
            final Need vacancyOwner,
            final String ownedBy
            ){
        final Vacancy newVac = newTransientInstance(Vacancy.class);
        newVac.setVacancyDescription(vacancyDescription);
        newVac.setVacancyOwner(vacancyOwner);
        newVac.setOwnedBy(ownedBy);
        persist(newVac);
        return newVac;
    }

}
