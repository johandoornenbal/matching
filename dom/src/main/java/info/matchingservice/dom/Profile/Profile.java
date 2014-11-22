package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Assessment.ProfileAssessment;
import info.matchingservice.dom.Dropdown.Qualities;
import info.matchingservice.dom.Dropdown.Quality;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Optional;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.query.QueryDefault;


@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findProfileByOwner", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Profile.Profile "
                    + "WHERE profileOwner == :profileOwner")
})
@AutoComplete(repository=Profiles.class,  action="autoComplete")
public class Profile extends MatchingSecureMutableObject<Profile> {

    public Profile() {
        super("profileName");
    }
    
    //Override for secure object /////////////////////////////////////////////////////////////////////////////////////
    
    private String ownedBy;
    
    @Override
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public String getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(final String owner) {
        this.ownedBy = owner;
    }

    //Immutables /////////////////////////////////////////////////////////////////////////////////////
    
    private Actor profileOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Eigenaar profiel")
    @Disabled
    public Actor getProfileOwner() {
        return profileOwner;
    }
    
    public void setProfileOwner(final Actor owner) {
        this.profileOwner =owner;
    }

    //END Immutables /////////////////////////////////////////////////////////////////////////////////////

    private String profileName;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Profiel naam")
    public String getProfileName() {
        return profileName;
    }
    
    public void setProfileName(final String test) {
        this.profileName = test;
    }
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @Named("Verwijder profiel")
    public Actor DeleteProfile(
            @Optional @Named("Verwijderen OK?") boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Profiel verwijderd");
        return getProfileOwner();
    }
    
    public String validateDeleteProfile(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    // Region> ProfileElements
    
    private SortedSet<ProfileElement> profileElement = new TreeSet<ProfileElement>();
    
   
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "profileElementOwner", dependentElement = "true")
    @Named("Profiel elementen")
    public SortedSet<ProfileElement> getProfileElement() {
        return profileElement;
    }
    
    public void setProfileElement(final SortedSet<ProfileElement> vac){
        this.profileElement = vac;
    }
    
    @Named("Nieuw (single) profiel element")
    @Hidden
    public Profile newProfileElement(
            @Named("Profiel element beschrijving")
            final String profileElementDescription
            ) {
        newProfileElement(profileElementDescription, this, currentUserName());
        return this;
    }
    
    public boolean hideNewProfileElement(final String profileElementDescription){
        return hideNewProfileElement(profileElementDescription, this);
    }
    
    public String validateNewProfileElement(final String profileElementDescription){
        return validateNewProfileElement(profileElementDescription, this);
    }
    
    @Named("Nieuw steekwoorden element")
    @Hidden
    public Profile newKeyWordElement(
            @Named("Profiel element beschrijving")
            final String profileElementDescription,
            @Named("Keywords")
            @MultiLine
            final String keywords
            ) {
        newKeyWordElement(profileElementDescription, keywords, this, currentUserName());
        return this;
    }    

    @Named("Nieuw kwaliteiten element")
    public Profile newDropdownElement(
            @Named("Keyword")
            final Quality keyword
            ) {
        newDropdownElement(keyword, this, currentUserName());
        return this;
    }   
    
    public List<Quality> autoComplete0NewDropdownElement(String search) {
        return qualities.findQualities(search);
    }
       
    @Inject
    Qualities qualities;
    
    @Programmatic
    public void newDropdownElement(
            final Quality keyword,
            final Profile profileElementOwner, 
            final String ownedBy) {
        profileElements.newDropdownElement(keyword, profileElementOwner, ownedBy, ProfileElementNature.MULTI_ELEMENT);
    }
    
    @Named("Nieuw getal element")
    public Profile newFigureElement(
            @Named("Profiel element beschrijving")
            final String profileElementDescription,
            @Named("Getal")
            final Integer figure
            ) {
        newFigureElement(profileElementDescription, figure, this, currentUserName());
        return this;
    }
    
    // Region> Assessments
    
    private SortedSet<ProfileAssessment> assessments = new TreeSet<ProfileAssessment>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "target", dependentElement = "true")
    @Named("Assessments")
    public SortedSet<ProfileAssessment> getAssessments() {
        return assessments;
    }
   
    public void setAssessments(final SortedSet<ProfileAssessment> assessment) {
        this.assessments = assessment;
    }
    
    public boolean hideAssessments() {
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }  
    
    // helpers
    
    public String toString() {
        return "Profiel: " + this.profileName;
    }
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic
    public void newProfileElement(final String profileElementDescription, final Profile profileElementOwner, final String ownedBy) {
        profileElements.newProfileElement(profileElementDescription, profileElementOwner, ownedBy, ProfileElementNature.SINGLE_ELEMENT);
    }
    
    @Programmatic
    public boolean hideNewProfileElement(final String profileElementDescription, final Profile profileElementOwner){
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
    public String validateNewProfileElement(final String profileElementDescription, final Profile profileElementOwner){
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
    public void newKeyWordElement(
            final String profileElementDescription,
            final String keywords,
            final Profile profileElementOwner, 
            final String ownedBy) {
        pe_keywords.newProfileElement(profileElementDescription, keywords, profileElementOwner, ownedBy);
    }
    
    @Programmatic
    public void newFigureElement(
            final String profileElementDescription,
            final Integer figure,
            final Profile profileElementOwner, 
            final String ownedBy) {
        pe_figures.newProfileElement(profileElementDescription, figure, profileElementOwner, ownedBy);
    }
    
    //Injects
    
    @Inject
    ProfileElements profileElements;
    
    @Inject
    Pe_Keywords pe_keywords;
    
    @Inject
    Pe_Figures pe_figures;
        
    @javax.inject.Inject
    private DomainObjectContainer container;

}
