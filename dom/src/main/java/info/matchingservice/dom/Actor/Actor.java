package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Need.Need;
import info.matchingservice.dom.Need.Needs;
import info.matchingservice.dom.Profile.SupplyProfile;
import info.matchingservice.dom.Profile.SupplyProfiles;
import info.matchingservice.dom.Profile.SuperProfile;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.VersionStrategy;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
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
@javax.jdo.annotations.Version(
        strategy = VersionStrategy.VERSION_NUMBER,
        column = "version")
@javax.jdo.annotations.Uniques({
    @javax.jdo.annotations.Unique(
            name = "PERSON_ID_UNQ", members = "uniqueActorId")
})
public abstract class Actor extends MatchingSecureMutableObject<Actor> {
    
    
    public Actor() {
        super("uniqueActorId");
    }
        
    public String title() {
        return getUniqueActorId();
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
    
    private String uniqueActorId;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public String getUniqueActorId() {
        return uniqueActorId;
    }
    
    public void setUniqueActorId(final String id) {
        this.uniqueActorId = id;
    }
    
    //Region> PROFILE /////////////////////////////////////////////////////////////
    
    private SortedSet<SupplyProfile> profile = new TreeSet<SupplyProfile>();
   
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "profileOwner", dependentElement = "true")
    @Named("Mijn profiel")
    public SortedSet<SupplyProfile> getProfile() {
        return profile;
    }
   
    public void setProfile(final SortedSet<SupplyProfile> profile) {
        this.profile = profile;
    }
   
    @Named("Maak een profiel")
    public SuperProfile makeProfile(
            @Named("Naam van je profiel")
            final String profileName
            ) {
        return makeProfile(profileName, this, getOwnedBy());
    }
   
    public boolean hideMakeProfile(final String testfield) {
        return hideMakeProfile(testfield, this, getOwnedBy());
    }
   
    public String validateMakeProfile(final String testfield) {
        return validateMakeProfile(testfield, this, getOwnedBy());
    }
    
    // HELPERS Profile
    
    @Programmatic // now values can be set by fixtures
    public SuperProfile makeProfile(
            @Named("Naam van je profiel")
            final String profileName, 
            final Actor actor, 
            final String ownedBy) {
        return profiles.newProfile(profileName, actor, ownedBy);
    }
    
    @Programmatic // now values can be set by fixtures
    public boolean hideMakeProfile(final String testfield, final Actor actor, final String ownedBy) {
        // if you are not the owner
        if (!this.getOwnedBy().equals(currentUserName())){
            return true;
        }
        // if you have already profile
        QueryDefault<SupplyProfile> query = 
                QueryDefault.create(
                        SupplyProfile.class, 
                    "findProfileByOwner", 
                    "profileOwner", this);
        return container.firstMatch(query) != null?
                true        
                :false;
    }
    
    @Programmatic // now values can be set by fixtures
    public String validateMakeProfile(final String testfield, final Actor actor, final String ownedBy) {
        QueryDefault<SupplyProfile> query = 
                QueryDefault.create(
                        SupplyProfile.class, 
                    "findProfileByOwner", 
                    "profileOwner", this);
        return container.firstMatch(query) != null?
                "You already have a profile"        
                :null;
    }
    
    //END Region> PROFILE /////////////////////////////////////////////////////////////
    
    //Region> NEED /////////////////////////////////////////////////////////////
    
    private SortedSet<Need> myNeeds = new TreeSet<Need>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "needOwner", dependentElement = "true")
    @Named("Opdrachten (actorniveau")
    public SortedSet<Need> getMyNeeds() {
        return myNeeds;
    }
   
    public void setMyNeeds(final SortedSet<Need> need) {
        this.myNeeds = need;
    }
    
    @Named("Plaats nieuwe opdracht")
    public Need newNeed(
            @Named("Korte opdrachtomschrijving voor tafel")
            @MultiLine
            final String needDescription,
            @Named("Gewicht")
            final Integer weight
            ) {
        return newNeed(needDescription, weight, this, currentUserName());
    }
    
//    public boolean hideNewNeed(final String needDescription) {
//        return hideNewNeed(needDescription, this);
//    }
    
    //helpers
    @Programmatic
    @Named("Plaats nieuwe opdracht")
    public Need newNeed(
            @Named("Korte opdrachtomschrijving") 
            @MultiLine 
            final String needDescription,
            @Named("Gewicht")
            final Integer weight,
            final Actor needOwner, 
            final String ownedBy){
        return needs.newNeed(needDescription, weight, needOwner, ownedBy);
    }
    
    @Programmatic
//    public boolean hideNewNeed(final String needDescription, final Actor needOwner){
//        // if you are not the owner
//        if (!needOwner.getOwnedBy().equals(currentUserName())){
//            return true;
//        }
        // if you have not Principal Role
//        if (!needOwner.getIsPrincipal()){
//            return true;
//        }
//        return false;
//    }
 
    // Region>HELPERS ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    Needs needs;
    
    @Inject
    private SupplyProfiles profiles;
    

}