package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.TrustLevel;
import info.matchingservice.dom.VacancyProfileElementOwner;
import info.matchingservice.dom.Assessment.VacancyProfileAssessment;
import info.matchingservice.dom.Dropdown.Quality;

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
import org.apache.isis.applib.annotation.Immutable;
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
@AutoComplete(repository=VacancyProfiles.class,  action="autoComplete")
@Immutable
public class VacancyProfile extends MatchingSecureMutableObject<VacancyProfile>
implements VacancyProfileElementOwner {

    public VacancyProfile() {
        super("ownedBy, vacancyDescription, weight, profileId");
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
    
    private String vacancyDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MultiLine
    @Named("Omschrijving van 'stoel'")
    public String getVacancyDescription(){
        return vacancyDescription;
    }
    
    public void setVacancyDescription(final String description) {
        this.vacancyDescription = description;
    }
    
    private Need vacancyOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    @Named("Opdracht")
    public Need getVacancyOwner() {
        return vacancyOwner;
    }
    
    public void setVacancyOwner(final Need vacancyOwner) {
        this.vacancyOwner = vacancyOwner;
    }
    
    private Integer weight;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight) {
        this.weight = weight;
    }
    
    // Region actions
    @Named("Bewerk omschrijving stoel")
    public VacancyProfile EditVacancyDescription(
            @Named("Omschrijving van 'stoel'")
            @MultiLine
            String newString
            ){
        this.setVacancyDescription(newString);
        return this;
    }
    
    public String default0EditVacancyDescription() {
        return getVacancyDescription();
    }
    
    @Named("Bewerk gewicht stoel")
    public VacancyProfile EditWeight(
            @Named("Gewicht")
            Integer newInteger
            ){
        this.setWeight(newInteger);
        return this;
    }
    
    public Integer default0EditWeight() {
        return getWeight();
    }
    

    // Region> VacanciesProfileElements
        
    private SortedSet<VacancyProfileElement> vacancyProfileElement = new TreeSet<VacancyProfileElement>();
    
   
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "vacancyProfileElementOwner", dependentElement = "true")
    @Named("Gevraagde profiel elementen")
    public SortedSet<VacancyProfileElement> getVacancyProfileElement() {
        return vacancyProfileElement;
    }
    
    public void setVacancyProfileElement(final SortedSet<VacancyProfileElement> vac){
        this.vacancyProfileElement = vac;
    }
    
    @Named("Nieuw (single) profiel element")
    @Hidden
    public VacancyProfile newVacancyProfileElement(
            @Named("Profiel element beschrijving")
            final String vacancyProfileElementDescription
            ) {
        newVacancyProfileElement(vacancyProfileElementDescription, this, currentUserName());
        return this;
    }
    
    public boolean hideNewVacancyProfileElement(final String vacancyProfileElementDescription){
        return hideNewVacancyProfileElement(vacancyProfileElementDescription, this);
    }
    
    public String validateNewVacancyProfileElement(final String vacancyProfileElementDescription){
        return validateNewVacancyProfileElement(vacancyProfileElementDescription, this);
    }
    
    @Named("Nieuw kwaliteiten element")
    public VacancyProfile newDropdownElement(
            @Named("Profiel element beschrijving")
            final String profileElementDescription,
            @Named("Keywords")
            @MultiLine
            final Quality keyword
            ) {
        newDropdownElement(profileElementDescription, keyword, this, currentUserName());
        return this;
    }    
    
    @Programmatic
    public void newDropdownElement(
            @Named("Profiel element beschrijving")
            final String profileElementDescription,
            @Named("Keywords")
            final Quality keyword,
            final VacancyProfile profileElementOwner, 
            final String ownedBy) {
        vacancyProfileElements.newDropdownElement(profileElementDescription, keyword, profileElementOwner, ownedBy, ProfileElementNature.MULTI_ELEMENT);
    }
    
    @Named("Nieuw getal element")
    public VacancyProfile newFigureElement(
            @Named("Profiel element beschrijving")
            final String profileElementDescription,
            @Named("Getal")
            final Integer figure,
            @Named("Gewicht")
            final Integer weight
            ) {
        newFigureElement(profileElementDescription, figure, weight, this, currentUserName());
        return this;
    } 
    
    // Region> Assessments
    
    private SortedSet<VacancyProfileAssessment> assessments = new TreeSet<VacancyProfileAssessment>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "target", dependentElement = "true")
    @Named("Assessments")
    public SortedSet<VacancyProfileAssessment> getAssessments() {
        return assessments;
    }
   
    public void setAssessments(final SortedSet<VacancyProfileAssessment> assessment) {
        this.assessments = assessment;
    }
    
    public boolean hideAssessments() {
        return super.allowedTrustLevel(TrustLevel.INNER_CIRCLE);
    }  
    
    // helpers
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    public String toString() {
        return "Stoel : " + this.vacancyDescription;
    }
    
    
    // Used in case owner chooses identical vacancyDescription and weight
    @SuppressWarnings("unused")
    private String profileId;

    @Hidden
    public String getProfileId() {
        if (this.getId() != null) {
            return this.getId();
        }
        return "";
    }
    
    public void setProfileId() {
        this.profileId = this.getId();
    }

    
    
    
    @Programmatic
    public void newVacancyProfileElement(final String vacancyProfileElementDescription, final VacancyProfile vacancyProfileOwner, final String ownedBy) {
        vacancyProfileElements.newVacancyProfileElement(vacancyProfileElementDescription, vacancyProfileOwner, ownedBy, ProfileElementNature.SINGLE_ELEMENT);
    }
    
    @Programmatic
    public boolean hideNewVacancyProfileElement(final String vacancyProfileElementDescription, final VacancyProfile vacancyProfileElementOwner){
        // if you have already profile
        QueryDefault<VacancyProfileElement> query = 
                QueryDefault.create(
                        VacancyProfileElement.class, 
                    "findVacancyProfileElementByOwnerVacancyAndNature", 
                    "vacancyProfileElementOwner", vacancyProfileElementOwner,
                    "profileElementNature", ProfileElementNature.SINGLE_ELEMENT);
        return container.firstMatch(query) != null ?
                true        
                :false;
    }
    
    @Programmatic
    public String validateNewVacancyProfileElement(final String vacancyProfileElementDescription, final VacancyProfile vacancyProfileElementOwner){
        // if you have already profile
        QueryDefault<VacancyProfileElement> query = 
                QueryDefault.create(
                        VacancyProfileElement.class, 
                    "findVacancyProfileElementByOwnerVacancyAndNature", 
                    "vacancyProfileElementOwner", vacancyProfileElementOwner,
                    "profileElementNature", ProfileElementNature.SINGLE_ELEMENT);
        return container.firstMatch(query) != null?
                "This VacancyProfile has this single element already!"        
                :null;
    }
    
    @Programmatic
    public void newKeyWordElement(
            final String profileElementDescription,
            final String keywords,
            final VacancyProfile profileElementOwner, 
            final String ownedBy) {
        pe_keywords.newProfileElement(profileElementDescription, keywords, profileElementOwner, ownedBy);
    }
    
    @Programmatic
    public void newFigureElement(
            final String profileElementDescription,
            final Integer figure,
            final Integer weight,
            final VacancyProfile profileElementOwner, 
            final String ownedBy) {
        pe_figures.newProfileElement(profileElementDescription, figure, weight, profileElementOwner, ownedBy);
    }
    //Injects
    
    @Inject
    VacancyProfileElements vacancyProfileElements;
    
    @Inject
    Vpe_Keywords pe_keywords;
    
    @Inject
    Vpe_Figures pe_figures;
        
    @javax.inject.Inject
    private DomainObjectContainer container;

}
