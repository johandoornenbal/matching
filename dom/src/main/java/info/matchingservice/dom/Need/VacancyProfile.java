package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingSecureMutableObject;

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
public class VacancyProfile extends MatchingSecureMutableObject<VacancyProfile> {

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
    
    private String testFieldForMatching;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MultiLine
    @Named("Tekst om te matchen")
    public String getTestFieldForMatching(){
        return testFieldForMatching;
    }
    
    public void setTestFieldForMatching(final String testtext) {
        this.testFieldForMatching = testtext;
    }
    
    private Integer testFigureForMatching;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @Named("Cijfer om te matchen")
    public Integer getTestFigureForMatching(){
        return testFigureForMatching;
    }
    
    public void setTestFigureForMatching(final Integer testfigure) {
        this.testFigureForMatching = testfigure;
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
    
    @Named("Bewerk tekst")
    public VacancyProfile EditTestField(
            @Named("Tekst om te matchen")
            @MultiLine
            String newString
            ){
        this.setTestFieldForMatching(newString);
        return this;
    }
    
    public String default0EditTestField() {
        return getTestFieldForMatching();
    }    
    
    @Named("Bewerk cijfer")
    public VacancyProfile EditTestValue(
            @Named("Cijfer om te matchen")
            Integer newInteger
            ){
        this.setTestFigureForMatching(newInteger);
        return this;
    }
    
    public Integer default0EditTestValue() {
        return getTestFigureForMatching();
    }  
    
    // Region> VacanciesProfileElements
        
    private SortedSet<VacancyProfileElement> vacancyProfileElement = new TreeSet<VacancyProfileElement>();
    
    @Hidden
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "vacancyProfileElementOwner", dependentElement = "true")
    public SortedSet<VacancyProfileElement> getVacancyProfileElement() {
        return vacancyProfileElement;
    }
    
    public void setVacancyProfileElement(final SortedSet<VacancyProfileElement> vac){
        this.vacancyProfileElement = vac;
    }
    
    public VacancyProfile newVacancyProfileElement(final String vacancyProfileElementDescription) {
        newVacancyProfileElement(vacancyProfileElementDescription, this, currentUserName());
        return this;
    }
    
    public boolean hideNewVacancyProfileElement(final String vacancyProfileElementDescription){
        return hideNewVacancyProfileElement(vacancyProfileElementDescription, this);
    }
    
    public String validateNewVacancyProfileElement(final String vacancyProfileElementDescription){
        return validateNewVacancyProfileElement(vacancyProfileElementDescription, this);
    }
    
    // helpers
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    public String toString() {
        return "Vacancy profile: " + this.vacancyDescription;
    }
    
    @Programmatic
    public void newVacancyProfileElement(final String vacancyProfileElementDescription, final VacancyProfile vacancyProfileOwner, final String ownedBy) {
        vacancyProfileElements.newVacancyProfileElement(vacancyProfileElementDescription, vacancyProfileOwner, ownedBy);
    }
    
    @Programmatic
    public boolean hideNewVacancyProfileElement(final String vacancyProfileElementDescription, final VacancyProfile vacancyProfileElementOwner){
        // if you have already profile
        QueryDefault<VacancyProfileElement> query = 
                QueryDefault.create(
                        VacancyProfileElement.class, 
                    "findVacancyProfileElementByOwnerVacancy", 
                    "vacancyProfileElementOwner", vacancyProfileElementOwner);
        return container.firstMatch(query) != null?
                true        
                :false;
    }
    
    @Programmatic
    public String validateNewVacancyProfileElement(final String vacancyProfileElementDescription, final VacancyProfile vacancyProfileElementOwner){
        // if you have already profile
        QueryDefault<VacancyProfileElement> query = 
                QueryDefault.create(
                        VacancyProfileElement.class, 
                    "findVacancyProfileElementByOwnerVacancy", 
                    "vacancyProfileElementOwner", vacancyProfileElementOwner);
        return container.firstMatch(query) != null?
                "This vacancy has this element already!"        
                :null;
    }
    
    //Injects
    
    @Inject
    VacancyProfileElements vacancyProfileElements;
        
    @javax.inject.Inject
    private DomainObjectContainer container;

}
