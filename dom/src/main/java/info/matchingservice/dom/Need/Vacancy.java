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
    
    private Need vacancyOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Need getVacancyOwner() {
        return vacancyOwner;
    }
    
    public void setVacancyOwner(final Need vacancyOwner) {
        this.vacancyOwner = vacancyOwner;
    }
    
    // Region> Vacancies
    
    private SortedSet<VacancyProfile> vacancyprofile = new TreeSet<VacancyProfile>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "vacancyProfileOwner", dependentElement = "true")
    public SortedSet<VacancyProfile> getVacancyProfile() {
        return vacancyprofile;
    }
    
    public void setVacancyProfile(final SortedSet<VacancyProfile> vac){
        this.vacancyprofile = vac;
    }
    
    public Vacancy newVacancyProfile(final String vacancyProfileDescription) {
        newVacancyProfile(vacancyProfileDescription, this, currentUserName());
        return this;
    }
    
    public boolean hideNewVacancyProfile(final String vacancyProfileDescription){
        return hideNewVacancyProfile(vacancyProfileDescription, this);
    }
    
    public String validateNewVacancyProfile(final String vacancyProfileDescription){
        return validateNewVacancyProfile(vacancyProfileDescription, this);
    }
    
    // helpers
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    @Programmatic
    public void newVacancyProfile(final String vacancyProfileDescription, final Vacancy vacancyProfileOwner, final String ownedBy) {
        vacancyprofiles.newVacancyProfile(vacancyProfileDescription, vacancyProfileOwner, ownedBy);
    }
    
    @Programmatic
    public boolean hideNewVacancyProfile(final String vacancyProfileDescription, final Vacancy vacancyProfileOwner){
        // if you have already profile
        QueryDefault<VacancyProfile> query = 
                QueryDefault.create(
                        VacancyProfile.class, 
                    "findVacancyProfileByOwnerVacancy", 
                    "vacancyProfileOwner", vacancyProfileOwner);
        return container.firstMatch(query) != null?
                true        
                :false;
    }
    
    @Programmatic
    public String validateNewVacancyProfile(final String vacancyProfileDescription, final Vacancy vacancyProfileOwner){
        // if you have already profile
        QueryDefault<VacancyProfile> query = 
                QueryDefault.create(
                        VacancyProfile.class, 
                    "findVacancyProfileByOwnerVacancy", 
                    "vacancyProfileOwner", vacancyProfileOwner);
        return container.firstMatch(query) != null?
                "This vacancy has a profile already!"        
                :null;
    }
    
    //Injects
    
    @Inject
    VacancyProfiles vacancyprofiles;
        
    @javax.inject.Inject
    private DomainObjectContainer container;

}
