package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.ProfileElementType;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "70", repositoryFor = Vpe_Figure.class)
@Named("Profiel elementen")
@Hidden
public class Vpe_Figures extends MatchingDomainService<Vpe_Figure> {

    public Vpe_Figures() {
        super(Vpe_Figures.class, Vpe_Figure.class);
    }
    
    @Named("Alle getal elementen")
    public List<Vpe_Figure> allProfileElements() {
        return allInstances();
    }
    
    @Programmatic    
    public Vpe_Figure newProfileElement(
            final String profileElementDescription,
            final Integer figure,
            final VacancyProfile profileElementOwner,
            final String ownedBy
            ){
        final Vpe_Figure newProf = newTransientInstance(Vpe_Figure.class);
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
    public Vpe_Figure newProfileElement(
            final String profileElementDescription,
            final VacancyProfile profileElementOwner,
            final String ownedBy,
            final ProfileElementNature nature
            ){
        final Vpe_Figure newProf = newTransientInstance(Vpe_Figure.class);
        newProf.setProfileElementNature(nature);
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_FIGURE); // default
        newProf.setVacancyProfileElementDescription(profileElementDescription);
        newProf.setVacancyProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }

}
