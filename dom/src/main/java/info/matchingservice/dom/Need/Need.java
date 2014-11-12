package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.Party.Person;

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

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
public class Need extends MatchingSecureMutableObject<Need> {

    public Need() {
        super("ownedBy, needDescription");
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
    
    private String needDescription;
    
    @MultiLine
    @javax.jdo.annotations.Column(allowsNull = "false")
    public String getNeedDescription(){
        return needDescription;
    }
    
    public void setNeedDescription(final String description) {
        this.needDescription = description;
    }
    
    private Person needOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Person getNeedOwner() {
        return needOwner;
    }
    
    public void setNeedOwner(final Person needOwner) {
        this.needOwner = needOwner;
    }
    
    // Region> Vacancies
    
    private SortedSet<VacancyProfile> vacancies = new TreeSet<VacancyProfile>();
    
    @Render(Type.EAGERLY)
    @Persistent(mappedBy = "vacancyOwner", dependentElement = "true")
    public SortedSet<VacancyProfile> getVacancies() {
        return vacancies;
    }
    
    public void setVacancies(final SortedSet<VacancyProfile> vac){
        this.vacancies = vac;
    }
    
    public Need newVacancy(final String vacancyDescription, final String testTextForMatching, final Integer testfigure) {
        newVacancy(vacancyDescription, testTextForMatching, testfigure, this, currentUserName());
        return this;
    }
    
    // helpers
    
    private String currentUserName() {
        return container.getUser().getName();
    }
    
    public String toString() {
        return getNeedDescription() + " - " + getNeedOwner().title();
    }
    
    @Programmatic
    public void newVacancy(final String vacancyDescription, final String testTextForMatching, final Integer testfigure, final Need vacancyOwner, final String ownedBy) {
        allvacancies.newVacancy(vacancyDescription, testTextForMatching, testfigure, vacancyOwner, ownedBy);
    }
    
    //Injection
    
    @Inject
    VacancyProfiles allvacancies;
    
    @javax.inject.Inject
    private DomainObjectContainer container;

}
