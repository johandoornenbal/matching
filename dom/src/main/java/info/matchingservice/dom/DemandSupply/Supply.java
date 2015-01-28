package info.matchingservice.dom.DemandSupply;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Actor.Person;
import info.matchingservice.dom.Assessment.SupplyAssessment;
import info.matchingservice.dom.Profile.Profile;
import info.matchingservice.dom.Profile.ProfileType;
import info.matchingservice.dom.Profile.Profiles;

import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.Persistent;

import org.apache.isis.applib.DomainObjectContainer;
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
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
            name = "findSupplyByOwnedByAndType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.DemandSupply.Supply "
                    + "WHERE ownedBy == :ownedBy && supplyType == :supplyType")                  
})
@Immutable
public class Supply extends MatchingSecureMutableObject<Supply> {

    public Supply() {
        super("ownedBy, supplyDescription");
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
    
    private Actor supplyOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    @Named("Eigenaar")
    public Actor getSupplyOwner() {
        return supplyOwner;
    }
    
    public void setSupplyOwner(final Actor supplyOwner) {
        this.supplyOwner = supplyOwner;
    }
    
    @Programmatic
    public Actor getProfileOwnerIsOwnedBy(){
        return getSupplyOwner();
    }
    
    private DemandSupplyType supplyType;
    
    @Hidden
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public DemandSupplyType getSupplyType(){
        return supplyType;
    }
    
    public void setSupplyType(final DemandSupplyType supplyType){
        this.supplyType = supplyType;
    }
 
    //END Immutables /////////////////////////////////////////////////////////////////////////////////////

    private String supplyDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getSupplyDescription(){
        return supplyDescription;
    }
    
    public void setSupplyDescription(final String description) {
        this.supplyDescription = description;
    }
    
    private Integer weight;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Hidden
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight) {
        this.weight = weight;
    }

    //ACTIONS ////////////////////////////////////////////////////////////////////////////////////////////
    
    public Supply editSupplyDescription(
            @Named("Omschrijving van het aanbod")
            @MultiLine
            final String supplyDescription
            ){
        this.setSupplyDescription(supplyDescription);
        return this;
    }
    
    public String default0EditSupplyDescription(){
        return this.getSupplyDescription();
    }
    
    @Hidden
    public Supply editWeight(
            @Named("Gewicht")
            final Integer weight
            ){
        this.setWeight(weight);
        return this;
    }
    
    public Integer default0EditWeight(){
        return this.getWeight();
    }
    
    //delete action /////////////////////////////////////////////////////////////////////////////////////

    @Named("Aanbod verwijderen")
    public Actor DeleteSupply(
            @Optional @Named("Verwijderen OK?") boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Supply deleted");
        return getSupplyOwner();
    }
    
    public String validateDeleteSupply(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    
    //END ACTIONS ////////////////////////////////////////////////////////////////////////////////////////////
    
    // Region> aanbod (supply profiles)
    
    private SortedSet<Profile> supplyProfiles = new TreeSet<Profile>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "supplyProfileOwner", dependentElement = "true")
    public SortedSet<Profile> getSupplyProfiles() {
        return supplyProfiles;
    }
    
    public void setSupplyProfiles(final SortedSet<Profile> supplyProfile){
        this.supplyProfiles = supplyProfile;
    }
    
   @Hidden
    public Profile newSupplyProfile(
            final String supplyProfileDescription,
            final Integer weight
            ) {
        return newSupplyProfile(supplyProfileDescription, weight, ProfileType.PERSON_PROFILE, this, currentUserName());
    }
   
   //XTALUS
   //Region> Nieuw persoonlijk profiel ////////////////////////////////////////////////////////
   
   @Named("Nieuw persoonlijk profiel") 
   public Profile newPersonSupplyProfile(){
       return newSupplyProfile("Persoonlijke profiel van " + this.getSupplyOwner().title(), 10, ProfileType.PERSON_PROFILE, this, currentUserName());
   }
   
   // BUSINESS RULE voor hide en validate van de aktie 'nieuw persoonlijk profiel'
   // je kunt slechts een 'persoonlijk profiel' hebben (supplyType PERSONS_DEMANDSUPPLY)
   // alleen tonen op supply van type PERSONS
   // je kunt alleen een persoonlijk profiel aanmaken als je student of ZP-er bent.
   
    public boolean hideNewPersonSupplyProfile(){
              QueryDefault<Profile> query = 
              QueryDefault.create(
                      Profile.class, 
                  "allSupplyProfilesOfTypeByOwner", 
                  "ownedBy", currentUserName(),
                  "profileType", ProfileType.PERSON_PROFILE);
        if (container.firstMatch(query) != null) {
          return true;
        }
        
        if (this.getSupplyType() != DemandSupplyType.PERSON_DEMANDSUPPLY){
            return true;
        }
        
        if (!(((Person) getSupplyOwner()).getIsStudent() || ((Person) getSupplyOwner()).getIsProfessional())){
            return true;
        }
        
        return false;
    }
    
    public String validateNewPersonSupplyProfile(){
            QueryDefault<Profile> query = 
            QueryDefault.create(
                    Profile.class, 
                "allSupplyProfilesOfTypeByOwner", 
                "ownedBy", currentUserName(),
                "profileType", ProfileType.PERSON_PROFILE);
          if (container.firstMatch(query) != null) {
              return "Je hebt al een persoonlijk profiel";
          }
          
          if (!(((Person) getSupplyOwner()).getIsStudent() || ((Person) getSupplyOwner()).getIsProfessional())){
              return "Om een persoonlijk profiel te maken moet je Professional of Student zijn";
          }
          
          if (this.getSupplyType() != DemandSupplyType.PERSON_DEMANDSUPPLY){
              return "Dit kan alleen op een persoonlijk aanbod";
          }
          
          return null;
          
          
    }
    
    //End Region> Nieuw persoonlijk profiel ////////////////////////////////////////////////////////
    
    //Region> Nieuw cursus profiel ////////////////////////////////////////////////////////
    
    @Named("Nieuwe cursus")
    public Profile newCourseSupplyProfile(
            @Named("Naam van de cursus")
            final String supplyProfileDescription
            ) {
        return newSupplyProfile(supplyProfileDescription, 10, ProfileType.COURSE_PROFILE, this, currentUserName());
    }
    
    // BUSINESS RULE voor hide en validate van de aktie 'nieuw cursus profiel'
    // alleen tonen op supply van type cursus
    // je kunt alleen een cursus profiel aanmaken als je ZP-er bent.
    
    public boolean hideNewCourseSupplyProfile(
            final String supplyProfileDescription
            ) {
        if (this.getSupplyType() != DemandSupplyType.COURSE_DEMANDSUPPLY){
            return true;
        }
        
        if (!((Person) getSupplyOwner()).getIsProfessional()){
            return true;
        }
        
        return false;
    }
    
    public String validateNewCourseSupplyProfile(
            final String supplyProfileDescription
            ) {
        if (this.getSupplyType() != DemandSupplyType.COURSE_DEMANDSUPPLY){
            return "Kan alleen op type Cursus";
        }
        
        if (!((Person) getSupplyOwner()).getIsProfessional()){
            return "Je moet ZP-er zijn";
        }
        
        return null;
    }
    //End Region> Nieuw cursus profiel ////////////////////////////////////////////////////////
    
    @Programmatic
    public Profile newSupplyProfile(
            final String supplyProfileDescription,
            final Integer weight,
            final ProfileType profileType,
            final Supply supplyProfileOwner, 
            final String ownedBy) {
        return allSupplyProfiles.newSupplyProfile(supplyProfileDescription, weight, profileType, supplyProfileOwner, ownedBy);
    }
    
    
    // Region> Assessments
    
    private SortedSet<SupplyAssessment> assessments = new TreeSet<SupplyAssessment>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "target", dependentElement = "true")
    public SortedSet<SupplyAssessment> getAssessments() {
        return assessments;
    }
   
    public void setAssessments(final SortedSet<SupplyAssessment> assessment) {
        this.assessments = assessment;
    }
    
    public boolean hideAssessments() {
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }  

    // Helpers
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    public String toString() {
        return getSupplyDescription() + " - " + getSupplyOwner().title();
    }
    
    // Injects
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    Profiles allSupplyProfiles;
    
}
