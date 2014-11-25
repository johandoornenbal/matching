package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingDomainService;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.ProfileElementType;

import java.util.List;

import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(menuOrder = "70", repositoryFor = ProfileFigureElement.class)
@Hidden
public class ProfileFigures extends MatchingDomainService<ProfileFigureElement> {

    public ProfileFigures() {
        super(ProfileFigures.class, ProfileFigureElement.class);
    }
    
    @Named("Alle getal elementen")
    public List<ProfileFigureElement> allProfileElements() {
        return allInstances();
    }
    
    @Programmatic    
    public ProfileFigureElement newProfileElement(
            final String profileElementDescription,
            final Integer figure,
            final SuperProfile profileElementOwner,
            final String ownedBy
            ){
        final ProfileFigureElement newProf = newTransientInstance(ProfileFigureElement.class);
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
    public ProfileFigureElement newProfileElement(
            final String profileElementDescription,
            final SuperProfile profileElementOwner,
            final String ownedBy,
            final ProfileElementNature nature
            ){
        final ProfileFigureElement newProf = newTransientInstance(ProfileFigureElement.class);
        newProf.setProfileElementNature(nature);
        newProf.setProfileElementType(ProfileElementType.MATCHABLE_FIGURE); // default
        newProf.setProfileElementDescription(profileElementDescription);
        newProf.setProfileElementOwner(profileElementOwner);
        newProf.setOwnedBy(ownedBy);
        persist(newProf);
        return newProf;
    }

}
