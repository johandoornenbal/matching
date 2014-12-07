package info.matchingservice.dom.Actor;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Assessment.Assessment;
import info.matchingservice.dom.DemandSupply.Demand;
import info.matchingservice.dom.DemandSupply.DemandSupplyType;
import info.matchingservice.dom.DemandSupply.Demands;
import info.matchingservice.dom.DemandSupply.Supplies;
import info.matchingservice.dom.DemandSupply.Supply;
import info.matchingservice.dom.Match.ProfileMatch;

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
    
    
    //Region> DEMANDS /////////////////////////////////////////////////////////////
    
    private SortedSet<Demand> myDemands = new TreeSet<Demand>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "demandOwner", dependentElement = "true")
    public SortedSet<Demand> getMyDemands() {
        return myDemands;
    }
   
    public void setMyDemands(final SortedSet<Demand> myDemands) {
        this.myDemands = myDemands;
    }
    
    public Demand newDemand(
            @MultiLine
            final String demandDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType
            ) {
        return newDemand(demandDescription, weight, demandSupplyType, this, currentUserName());
    }
    
    
    //helpers
    @Programmatic
    public Demand newDemand(
            @MultiLine 
            final String demandDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType,
            final Actor demandOwner, 
            final String ownedBy){
        return demands.newDemand(demandDescription, weight, demandSupplyType, demandOwner, ownedBy);
    }
    
    
    //Region> SUPPLIES /////////////////////////////////////////////////////////////

    private SortedSet<Supply> mySupplies = new TreeSet<Supply>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "supplyOwner", dependentElement = "true")
    public SortedSet<Supply> getMySupplies() {
        return mySupplies;
    }
   
    public void setMySupplies(final SortedSet<Supply> supplies) {
        this.mySupplies = supplies;
    }
    
    public Supply newSupply(
            @MultiLine
            final String needDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType){
        return newSupply(needDescription, weight, demandSupplyType, this, currentUserName());
    }
    
    //helpers
    @Programmatic
    public Supply newSupply(
            @MultiLine 
            final String demandDescription,
            final Integer weight,
            final DemandSupplyType demandSupplyType,
            final Actor demandOwner, 
            final String ownedBy){
        return supplies.newSupply(demandDescription, weight, demandSupplyType, demandOwner, ownedBy);
    }
    
    
    //END Region> SUPPLIES /////////////////////////////////////////////////////////////
    
    //Region> Saved Matches /////////////////////////////////////////////////////////////
    
    private SortedSet<ProfileMatch> mySavedMatches = new TreeSet<ProfileMatch>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "ownerActor", dependentElement = "true")
    @Named("Mijn bewaarde 'matches'")
    public SortedSet<ProfileMatch> getMySavedMatches() {
        return mySavedMatches;
    }
    
    public void setMySavedMatches(final SortedSet<ProfileMatch> mySavedMatches){
        this.mySavedMatches = mySavedMatches;
    }
    
    //END Region> Saved Matches /////////////////////////////////////////////////////////////
    
    //Region> Assessments Given  /////////////////////////////////////////////////////////////
    
    private SortedSet<Assessment> assessmentsGivenByMe = new TreeSet<Assessment>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "ownerActor", dependentElement = "true")
    @Named("Feedback die ik gegeven heb")
    public SortedSet<Assessment> getAssessmentsGivenByMe() {
        return assessmentsGivenByMe;
    }
    
    public void setAssessmentsGivenByMe(final SortedSet<Assessment> assessmentsGivenByMe){
        this.assessmentsGivenByMe = assessmentsGivenByMe;
    }
    
    //Region> Assessments Received  /////////////////////////////////////////////////////////////
    
    private SortedSet<Assessment> assessmentsReceivedByMe = new TreeSet<Assessment>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "targetOwnerActor", dependentElement = "true")
    @Named("Feedback die ik ontvangen heb")
    public SortedSet<Assessment> getAssessmentsReceivedByMe() {
        return assessmentsReceivedByMe;
    }
    
    public void setAssessmentsReceivedByMe(final SortedSet<Assessment> assessmentsReceivedByMe){
        this.assessmentsReceivedByMe = assessmentsReceivedByMe;
    }
    
    //END Region> ASSESSMENTS /////////////////////////////////////////////////////////////
    
    // Region>HELPERS ////////////////////////////
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    // Region>injections ////////////////////////////
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    Supplies supplies;
    
    @Inject
    Demands demands;    

}