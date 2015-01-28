package info.matchingservice.dom.DemandSupply;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Assessment.DemandAssessment;
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
            name = "findDemandByOwnedByAndType", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.DemandSupply.Demand "
                    + "WHERE ownedBy == :ownedBy && demandType == :demandType")                  
})
@Immutable
public class Demand extends MatchingSecureMutableObject<Demand> {

    public Demand() {
        super("ownedBy, demandDescription, weight");
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
    
    private Actor demandOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Actor getDemandOwner() {
        return demandOwner;
    }
    
    public void setDemandOwner(final Actor needOwner) {
        this.demandOwner = needOwner;
    }
    
    @Programmatic
    public Actor getProfileOwnerIsOwnedBy(){
        return getDemandOwner();
    }
    
    private DemandSupplyType demandType;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    @Hidden
    public DemandSupplyType getDemandType(){
        return demandType;
    }
    
    public void setDemandType(final DemandSupplyType demandType){
        this.demandType = demandType;
    }
 
    //END Immutables /////////////////////////////////////////////////////////////////////////////////////

    private String demandDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MultiLine
    public String getDemandDescription(){
        return demandDescription;
    }
    
    public void setDemandDescription(final String description) {
        this.demandDescription = description;
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

    public Demand editDemandDescription(
            @Named("Omschrijving van de vraag")
            @MultiLine
            final String demandDescription
            ){
        this.setDemandDescription(demandDescription);
        return this;
    }
    
    public String default0EditDemandDescription(){
        return this.getDemandDescription();
    }
    
    @Hidden
    public Demand editWeight(
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
    
    @Named("Vraag verwijderen")
    public Actor DeleteDemand(
            @Optional @Named("Verwijderen OK?") boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Demand deleted");
        return getDemandOwner();
    }
    
    public String validateDeleteDemand(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    //END ACTIONS ////////////////////////////////////////////////////////////////////////////////////////////

    
    // Region> Vacancies
    
    private SortedSet<Profile> demandProfiles = new TreeSet<Profile>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "demandProfileOwner", dependentElement = "true")
    public SortedSet<Profile> getDemandProfiles() {
        return demandProfiles;
    }
    
    public void setDemandProfiles(final SortedSet<Profile> vac){
        this.demandProfiles = vac;
    }
    
    //XTALUS 
    //Nieuwe cursus gezocht
    @Named("Nieuwe cursus zoeken")
    public Profile newCourseDemandProfile(
            @Named("Omschrijving cursus gezocht")
            final  String demandProfileDescription
            ){
        return newDemandProfile(demandProfileDescription, 10, ProfileType.COURSE_PROFILE, this, currentUserName());
    }
    
    // BUSINESS RULE voor hide en validate van de aktie 'nieuw cursus gezocht'
    // alleen tonen op demand van type cursus
    
    public boolean hideNewCourseDemandProfile(
            final  String demandProfileDescription
            ){
        if (this.getDemandType() != DemandSupplyType.COURSE_DEMANDSUPPLY){
            return true;
        }
        
        return false;
    }
    
    public String validateNewCourseDemandProfile(
            final  String demandProfileDescription
            ){
        if (this.getDemandType() != DemandSupplyType.COURSE_DEMANDSUPPLY){
            return "Alleen op type CURSUS";
        }
        
        return null;
    }    

    //XTALUS 
    //Nieuwe persoon gezocht
    @Named("Nieuwe persoon zoeken")
    public Profile newPersonDemandProfile(
            @Named("Omschrijving persoon gezocht")
            final  String demandProfileDescription
            ){
        return newDemandProfile(demandProfileDescription, 10, ProfileType.PERSON_PROFILE, this, currentUserName());
    }
    
    // BUSINESS RULE voor hide en validate van de aktie 'nieuw persoon gezocht'
    // alleen tonen op demand van type persoon
    
    public boolean hideNewPersonDemandProfile(
            final  String demandProfileDescription
            ){
        if (this.getDemandType() != DemandSupplyType.PERSON_DEMANDSUPPLY){
            return true;
        }
        
        return false;
    }
    
    public String validateNewPersonDemandProfile(
            final  String demandProfileDescription
            ){
        if (this.getDemandType() != DemandSupplyType.PERSON_DEMANDSUPPLY){
            return "Alleen op type PERSOON";
        }
        
        return null;
    }    
    
    @Hidden
    public Profile newDemandProfile(
            final  String demandProfileDescription,
            final Integer weight 
            ) {
        return newDemandProfile(demandProfileDescription, weight, ProfileType.PERSON_PROFILE, this, currentUserName());
    }
    
    
    @Programmatic
    public Profile newDemandProfile(
            final String demandProfileDescription,
            final Integer weight,
            final ProfileType profileType,
            final Demand demandProfileOwner, 
            final String ownedBy) {
        return allDemandProfiles.newDemandProfile(demandProfileDescription, weight, profileType, demandProfileOwner, ownedBy);
    }
    
    // Region> Assessments
    
    private SortedSet<DemandAssessment> assessments = new TreeSet<DemandAssessment>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "target", dependentElement = "true")
    @Named("Assessments")
    public SortedSet<DemandAssessment> getAssessments() {
        return assessments;
    }
   
    public void setAssessments(final SortedSet<DemandAssessment> assessment) {
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
        return getDemandDescription() + " - " + getDemandOwner().title();
    }
    
    // Injects
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    Profiles allDemandProfiles;
    
}
