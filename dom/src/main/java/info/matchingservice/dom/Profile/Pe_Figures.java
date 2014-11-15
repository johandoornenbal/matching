package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.ProfileElementType;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "70", repositoryFor = Pe_Figure.class)
@Hidden
public class Pe_Figures extends MatchingDomainService<Pe_Figure> {

    public Pe_Figures() {
        super(Pe_Figures.class, Pe_Figure.class);
    }
    
    @Named("Alle getal elementen")
    public List<Pe_Figure> allProfileElements() {
        return allInstances();
    }
    
    @Programmatic    
    public Pe_Figure newProfileElement(
            final String profileElementDescription,
            final Integer figure,
            final Profile profileElementOwner,
            final String ownedBy
            ){
        final Pe_Figure newProf = newTransientInstance(Pe_Figure.class);
        newProf.setProfileElementNature(ProfileElementNature.MULTI_ELEMENT); // default
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_FIGURE); // default
        newProf.setProfileElementDescription(profileElementDescription);
        newProf.setFigure(figure);
        newProf.setProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }
    
    @Programmatic    
    public Pe_Figure newProfileElement(
            final String profileElementDescription,
            final Profile profileElementOwner,
            final String ownedBy,
            final ProfileElementNature nature
            ){
        final Pe_Figure newProf = newTransientInstance(Pe_Figure.class);
        newProf.setProfileElementNature(nature);
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_FIGURE); // default
        newProf.setProfileElementDescription(profileElementDescription);
        newProf.setProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }

}
