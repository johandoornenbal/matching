package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.Actor.Actor;
import info.matchingservice.dom.Assessment.NeedAssessment;

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
public class Need extends MatchingSecureMutableObject<Need> {

    public Need() {
        super("ownedBy, needDescription, weight");
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
    
    private Actor needOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    @Named("Opdrachtgever")
    public Actor getNeedOwner() {
        return needOwner;
    }
    
    public void setNeedOwner(final Actor needOwner) {
        this.needOwner = needOwner;
    }
 
    //END Immutables /////////////////////////////////////////////////////////////////////////////////////

    private String needDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MultiLine
    @Named("Opdracht omschrijving op tafel")
    public String getNeedDescription(){
        return needDescription;
    }
    
    public void setNeedDescription(final String description) {
        this.needDescription = description;
    }
    
    private Integer weight;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight) {
        this.weight = weight;
    }

    //delete action /////////////////////////////////////////////////////////////////////////////////////
    
    @Named("Verwijder tafel")
    public Actor DeleteNeed(
            @Optional @Named("Verwijderen OK?") boolean areYouSure
            ){
        container.removeIfNotAlready(this);
        container.informUser("Tafel verwijderd");
        return getNeedOwner();
    }
    
    public String validateDeleteNeed(boolean areYouSure) {
        return areYouSure? null:"Geef aan of je wilt verwijderen";
    }
    
    // Region> Vacancies
    
    private SortedSet<DemandProfile> vacancyProfiles = new TreeSet<DemandProfile>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "vacancyOwner", dependentElement = "true")
    @Named("Mijn stoelen")
    public SortedSet<DemandProfile> getVacancyProfiles() {
        return vacancyProfiles;
    }
    
    public void setVacancyProfiles(final SortedSet<DemandProfile> vac){
        this.vacancyProfiles = vac;
    }
    
    @Named("Nieuwe stoel")
    public DemandProfile newVacancyProfile(
            @Named("Omschrijving van 'stoel'")
            final  String vacancyDescription,
            @Named("Gewicht")
            final Integer weight 
            ) {
        return newVacancyProfile(vacancyDescription, weight, this, currentUserName());
    }
    
    // Helpers vacancy profile
//    @Programmatic
//    public VacancyProfile newVacancyProfile(
//            final String vacancyDescription,
//            final Need vacancyOwner, 
//            final String ownedBy) {
//        return allvacancies.newVacancy(vacancyDescription, vacancyOwner, ownedBy);
//    }
    
    @Programmatic
    public DemandProfile newVacancyProfile(
            final String vacancyDescription,
            final Integer weight,
            final Need vacancyOwner, 
            final String ownedBy) {
        return allvacancies.newVacancy(vacancyDescription, weight, vacancyOwner, ownedBy);
    }
    
    // Region> Assessments
    
    private SortedSet<NeedAssessment> assessments = new TreeSet<NeedAssessment>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "target", dependentElement = "true")
    @Named("Assessments")
    public SortedSet<NeedAssessment> getAssessments() {
        return assessments;
    }
   
    public void setAssessments(final SortedSet<NeedAssessment> assessment) {
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
        return getNeedDescription() + " - " + getNeedOwner().title();
    }
    
    // Injects
    
    @javax.inject.Inject
    private DomainObjectContainer container;
    
    @Inject
    DemandProfiles allvacancies;
    
}
