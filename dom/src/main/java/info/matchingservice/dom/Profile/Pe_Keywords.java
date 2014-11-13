package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.ProfileElementType;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "70", repositoryFor = Pe_Keyword.class)
@Named("Profiel elementen")
@Hidden
public class Pe_Keywords extends MatchingDomainService<Pe_Keyword> {

    public Pe_Keywords() {
        super(Pe_Keywords.class, Pe_Keyword.class);
    }
    
    @Named("Alle pe_keyword elementen")
    public List<Pe_Keyword> allProfileElements() {
        return allInstances();
    }
    
    @Programmatic    
    public Pe_Keyword newProfileElement(
            final String profileElementDescription,
            final String keyWord,
            final Profile profileElementOwner,
            final String ownedBy
            ){
        final Pe_Keyword newProf = newTransientInstance(Pe_Keyword.class);
        newProf.setProfileElementNature(ProfileElementNature.MULTI_ELEMENT); // default
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_KEYWORDS); // default
        newProf.setProfileElementDescription(profileElementDescription);
        newProf.setKeyWords(keyWord);
        newProf.setProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }
    
    @Programmatic    
    public Pe_Keyword newProfileElement(
            final String profileElementDescription,
            final Profile profileElementOwner,
            final String ownedBy,
            final ProfileElementNature nature
            ){
        final Pe_Keyword newProf = newTransientInstance(Pe_Keyword.class);
        newProf.setProfileElementNature(nature);
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_KEYWORDS); // default
        newProf.setProfileElementDescription(profileElementDescription);
        newProf.setProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }

}
