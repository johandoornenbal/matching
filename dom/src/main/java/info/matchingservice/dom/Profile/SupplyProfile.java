package info.matchingservice.dom.Profile;

import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Dropdown.Qualities;
import info.matchingservice.dom.Dropdown.Quality;

import java.util.List;

import javax.inject.Inject;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.query.QueryDefault;


@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileByOwner", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.SupplyProfile "
                    + "WHERE profileOwner == :profileOwner")
})
@AutoComplete(repository=SupplyProfiles.class,  action="autoComplete")
public class SupplyProfile extends SuperProfile {
    
    //Immutables /////////////////////////////////////////////////////////////////////////////////////
    
    private Actor profileOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Named("Eigenaar profiel")
    @Disabled
    public Actor getProfileOwner() {
        return profileOwner;
    }
    
    public void setProfileOwner(final Actor owner) {
        this.profileOwner =owner;
    }

    //END Immutables /////////////////////////////////////////////////////////////////////////////////////

    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    @Named("Verwijder profiel")
    public Actor DeleteProfile(@Optional @Named("Verwijderen OK?") boolean areYouSure) {
        container.removeIfNotAlready(this);
        container.informUser("Profiel verwijderd");
        return getProfileOwner();
    }

    public String validateDeleteProfile(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    // Region> ProfileElements
    @Named("Nieuw (single) profiel element")
    @Hidden
    public SupplyProfile newProfileElement(@Named("Profiel element beschrijving")
                final String profileElementDescription) {
        newProfileElement(profileElementDescription, this, currentUserName());
        return this;
    }
    
    @Programmatic
    public void newDropdownElement(final Quality keyword, final SupplyProfile profileElementOwner, final String ownedBy) {
        profileElements.newDropdownElement(keyword, profileElementOwner, ownedBy, ProfileElementNature.MULTI_ELEMENT);
    }

    public boolean hideNewProfileElement(final String profileElementDescription) {
        return hideNewProfileElement(profileElementDescription, this);
    }

    public String validateNewProfileElement(final String profileElementDescription) {
        return validateNewProfileElement(profileElementDescription, this);
    }
    
    @Programmatic
    public void newProfileElement(final String profileElementDescription, final SupplyProfile profileElementOwner, final String ownedBy) {
        profileElements.newProfileElement(profileElementDescription, profileElementOwner, ownedBy, ProfileElementNature.SINGLE_ELEMENT);
    }

    @Programmatic
    public boolean hideNewProfileElement(final String profileElementDescription, final SupplyProfile profileElementOwner) {
        // if you have already profile
        QueryDefault<ProfileElement> query = 
                QueryDefault.create(
                        ProfileElement.class, 
                    "findProfileElementByOwnerProfileAndNature", 
                    "profileElementOwner", profileElementOwner,
                    "profileElementNature", ProfileElementNature.SINGLE_ELEMENT);
        return container.firstMatch(query) != null ?
                true        
                :false;
    }

    @Programmatic
    public String validateNewProfileElement(final String profileElementDescription, final SupplyProfile profileElementOwner) {
        // if you have already profile
        QueryDefault<ProfileElement> query = 
                QueryDefault.create(
                        ProfileElement.class, 
                        "findProfileElementByOwnerProfileAndNature", 
                        "profileElementOwner", profileElementOwner,
                        "profileElementNature", ProfileElementNature.SINGLE_ELEMENT);
        return container.firstMatch(query) != null?
                "This VacancyProfile has this single element already!"        
                :null;
    }

    @Programmatic
    public void newFigureElement(final String profileElementDescription, final Integer figure, final SupplyProfile profileElementOwner, final String ownedBy) {
        pe_figures.newProfileElement(profileElementDescription, figure, profileElementOwner, ownedBy);
    }

    @Inject
    ProfileFigures pe_figures;
    @javax.inject.Inject
    private DomainObjectContainer container;


    @Named("Nieuw kwaliteiten element")
    public SupplyProfile newDropdownElement(@Named("Keyword")
                final Quality keyword) {
        newDropdownElement(keyword, this, currentUserName());
        return this;
    }

    public List<Quality> autoComplete0NewDropdownElement(String search) {
        return qualities.findQualities(search);
    }
    
    @Named("Nieuw getal element")
    public SupplyProfile newFigureElement(@Named("Profiel element beschrijving")
                final String profileElementDescription, @Named("Getal")
                final Integer figure) {
        newFigureElement(profileElementDescription, figure, this, currentUserName());
        return this;
    }

    
    // helpers
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    //Injects
    
    @Inject
    ProfileFigureElements profileElements;

    @Inject
    Qualities qualities;

}
