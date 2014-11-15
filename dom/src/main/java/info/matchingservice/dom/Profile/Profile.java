package info.matchingservice.dom.Profile;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.Actor.Person;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.AutoComplete;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Programmatic;
import org.apache.isis.applib.annotation.Render;
import org.apache.isis.applib.annotation.Render.Type;
import org.apache.isis.applib.query.QueryDefault;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
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
                    + "WHERE ownedBy == :ownedBy")
})
@AutoComplete(repository=Profiles.class,  action="autoComplete")
public class Profile extends MatchingSecureMutableObject<Profile> {

    public Profile() {
        super("profileName");
    }
    
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
    
    private String profileName;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Profiel naam")
    public String getProfileName() {
        return profileName;
    }
    
    public void setProfileName(final String test) {
        this.profileName = test;
    }

    private Integer testFigureForMatching;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Named("Cijfer voor matching")
    public Integer getTestFigureForMatching(){
        return testFigureForMatching;
    }
    
    public void setTestFigureForMatching(final Integer testfigure) {
        this.testFigureForMatching = testfigure;
    }
    
    private String testFieldForMatching;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MultiLine
    @Named("Tekst voor matching")
    public String getTestFieldForMatching(){
        return testFieldForMatching;
    }
    
    public void setTestFieldForMatching(final String testtext) {
        this.testFieldForMatching = testtext;
    }
    
    
    private Person profileOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Named("Eigenaar profiel")
    @Disabled
    public Person getProfileOwner() {
        return profileOwner;
    }
    
    public void setProfileOwner(final Person owner) {
        this.profileOwner =owner;
    }
    
    public String toString() {
        return "Profiel: " + this.profileName;
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
    
    @Named("Nieuw KeyWord element")
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
    
    // helpers
    
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
    //Injects
    
    @Inject
    ProfileElements profileElements;
    
    @Inject
    Pe_Keywords pe_keywords;
        
    @javax.inject.Inject
    private DomainObjectContainer container;

}
