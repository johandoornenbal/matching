package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.VacancyProfileElementOwner;

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
import org.apache.isis.applib.annotation.Immutable;
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
@AutoComplete(repository=VacancyProfiles.class,  action="autoComplete")
@Immutable
public class VacancyProfile extends MatchingSecureMutableObject<VacancyProfile>
implements VacancyProfileElementOwner {

    public VacancyProfile() {
        super("ownedBy, vacancyDescription");
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
            @Named("Omschrijving van 'stoel'")
            @MultiLine
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
    
    @Named("Nieuw steekwoorden element")
    @Hidden
    public VacancyProfile newKeyWordElement(
            @Named("Profiel element beschrijving")
            final String profileElementDescription,
            @Named("Keywords")
            @MultiLine
            final String keywords
            ) {
        newKeyWordElement(profileElementDescription, keywords, this, currentUserName());
        return this;
    }    
    
    @Named("Nieuw getal element")
    public VacancyProfile newFigureElement(
            @Named("Profiel element beschrijving")
            final String profileElementDescription,
            @Named("Getal")
            final Integer figure
            ) {
        newFigureElement(profileElementDescription, figure, this, currentUserName());
        return this;
    } 
    
    // helpers
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    public String toString() {
        return "Stoel : " + this.vacancyDescription;
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
            final VacancyProfile profileElementOwner, 
            final String ownedBy) {
        pe_figures.newProfileElement(profileElementDescription, figure, profileElementOwner, ownedBy);
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
