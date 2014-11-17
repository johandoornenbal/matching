package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.ProfileElementType;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "70", repositoryFor = Vpe_Keyword.class)
@Named("Profiel elementen")
@Hidden
public class Vpe_Keywords extends MatchingDomainService<Vpe_Keyword> {

    public Vpe_Keywords() {
        super(Vpe_Keywords.class, Vpe_Keyword.class);
    }
    
    @Named("Alle pe_keyword elementen")
    public List<Vpe_Keyword> allProfileElements() {
        return allInstances();
    }
    
    @Programmatic    
    public Vpe_Keyword newProfileElement(
            final String profileElementDescription,
            final String keyWord,
            final VacancyProfile profileElementOwner,
            final String ownedBy
            ){
        final Vpe_Keyword newProf = newTransientInstance(Vpe_Keyword.class);
        newProf.setProfileElementNature(ProfileElementNature.MULTI_ELEMENT); // default
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_KEYWORDS); // default
        newProf.setVacancyProfileElementDescription(profileElementDescription);
        newProf.setKeyWords(keyWord);
        newProf.setVacancyProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }
    
    @Programmatic    
    public Vpe_Keyword newProfileElement(
            final String profileElementDescription,
            final String keyWord,
            final Integer weight,
            final VacancyProfile profileElementOwner,
            final String ownedBy
            ){
        final Vpe_Keyword newProf = newTransientInstance(Vpe_Keyword.class);
        newProf.setProfileElementNature(ProfileElementNature.MULTI_ELEMENT); // default
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_KEYWORDS); // default
        newProf.setVacancyProfileElementDescription(profileElementDescription);
        newProf.setKeyWords(keyWord);
        newProf.setWeight(weight);
        newProf.setVacancyProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }
    
    @Programmatic    
    public Vpe_Keyword newProfileElement(
            final String profileElementDescription,
            final VacancyProfile profileElementOwner,
            final String ownedBy,
            final ProfileElementNature nature
            ){
        final Vpe_Keyword newProf = newTransientInstance(Vpe_Keyword.class);
        newProf.setProfileElementNature(nature);
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_KEYWORDS); // default
        newProf.setVacancyProfileElementDescription(profileElementDescription);
        newProf.setVacancyProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }

}
