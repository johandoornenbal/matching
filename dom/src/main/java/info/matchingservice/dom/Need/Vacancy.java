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
import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MultiLine;
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
public class Vacancy extends MatchingSecureMutableObject<Vacancy> {

    public Vacancy() {
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
    public String getVacancyDescription(){
        return vacancyDescription;
    }
    
    public void setVacancyDescription(final String description) {
        this.vacancyDescription = description;
    }
    
    private String testFieldForMatching;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MultiLine
    public String getTestFieldForMatching(){
        return testFieldForMatching;
    }
    
    public void setTestFieldForMatching(final String testtext) {
        this.testFieldForMatching = testtext;
    }
    
    private Need vacancyOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Need getVacancyOwner() {
        return vacancyOwner;
    }
    
    public void setVacancyOwner(final Need vacancyOwner) {
        this.vacancyOwner = vacancyOwner;
    }
    
    // Region> VacanciesProfiles
        
    private SortedSet<VacancyProfileElement> vacancyprofile = new TreeSet<VacancyProfileElement>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "vacancyProfileElementOwner", dependentElement = "true")
    public SortedSet<VacancyProfileElement> getVacancyProfile() {
        return vacancyprofile;
    }
    
    public void setVacancyProfile(final SortedSet<VacancyProfileElement> vac){
        this.vacancyprofile = vac;
    }
    
    public Vacancy newVacancyProfileElement(final String vacancyProfileElementDescription) {
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
    
    @Programmatic
    public void newVacancyProfileElement(final String vacancyProfileElementDescription, final Vacancy vacancyProfileOwner, final String ownedBy) {
        vacancyprofiles.newVacancyProfileElement(vacancyProfileElementDescription, vacancyProfileOwner, ownedBy);
    }
    
    @Programmatic
    public boolean hideNewVacancyProfileElement(final String vacancyProfileElementDescription, final Vacancy vacancyProfileElementOwner){
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
    public String validateNewVacancyProfileElement(final String vacancyProfileElementDescription, final Vacancy vacancyProfileElementOwner){
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
    VacancyProfileElements vacancyprofiles;
        
    @javax.inject.Inject
    private DomainObjectContainer container;

}
