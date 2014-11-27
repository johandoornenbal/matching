package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingDomainService;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.Programmatic;

@DomainService(repositoryFor = ProfileElementNumeric.class)
public class ProfileElementNumerics extends MatchingDomainService<ProfileElementNumeric> {

    public ProfileElementNumerics() {
        super(ProfileElementNumerics.class, ProfileElementNumeric.class);
    }

    @Programmatic
    public ProfileElementNumeric newProfileElementNumeric(
            final String description,
            final Integer weight,
            final Integer numericValue,
            final ProfileElementCategory profileElementCategory,
            final Profile profileOwner,
            final ProfileElementNature nature
            ){
        final ProfileElementNumeric newProfileElement = newTransientInstance(ProfileElementNumeric.class);
        newProfileElement.setProfileElementDescription(description);
        newProfileElement.setWeight(weight);
        newProfileElement.setNumericValue(numericValue);
        newProfileElement.setDisplayValue(numericValue.toString());
        newProfileElement.setProfileElementCategory(profileElementCategory);
        newProfileElement.setProfileElementOwner(profileOwner);
        newProfileElement.setProfileElementNature(nature);
        newProfileElement.setOwnedBy(currentUserName());
        persist(newProfileElement);
        return newProfileElement;
    }
    
    @Programmatic
    public ProfileElementNumeric newProfileElementNumeric(
            final String description,
            final Integer weight,
            final Integer numericValue,
            final ProfileElementCategory profileElementCategory,
            final Profile profileOwner,
            final ProfileElementNature nature,
            final String ownedBy
            ){
        final ProfileElementNumeric newProfileElement = newTransientInstance(ProfileElementNumeric.class);
        newProfileElement.setProfileElementDescription(description);
        newProfileElement.setWeight(weight);
        newProfileElement.setNumericValue(numericValue);
        newProfileElement.setDisplayValue(numericValue.toString());
        newProfileElement.setProfileElementCategory(profileElementCategory);
        newProfileElement.setProfileElementOwner(profileOwner);
        newProfileElement.setProfileElementNature(nature);
        newProfileElement.setOwnedBy(ownedBy);
        persist(newProfileElement);
        return newProfileElement;
    }
    
    // Region>helpers ///////////////////////////////
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
}
