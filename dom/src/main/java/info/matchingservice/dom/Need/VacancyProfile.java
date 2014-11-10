package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingSecureMutableObject;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.MultiLine;

@javax.jdo.annotations.PersistenceCapable(identityType = IdentityType.DATASTORE)
@javax.jdo.annotations.DatastoreIdentity(
        strategy = IdGeneratorStrategy.NATIVE,
        column = "id")
@javax.jdo.annotations.Discriminator(
        strategy = DiscriminatorStrategy.CLASS_NAME,
        column = "discriminator")
@javax.jdo.annotations.Queries({
    @javax.jdo.annotations.Query(
            name = "findVacancyProfileByOwnerVacancy", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Need.VacancyProfile "
                    + "WHERE vacancyProfileOwner == :vacancyProfileOwner")
})
public class VacancyProfile extends MatchingSecureMutableObject<VacancyProfile> {

    public VacancyProfile() {
        super("ownedBy, vacancyProfileDescription");
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
    
    private String vacancyProfileDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MultiLine
    public String getVacancyProfileDescription(){
        return vacancyProfileDescription;
    }
    
    public void setVacancyProfileDescription(final String description) {
        this.vacancyProfileDescription = description;
    }
    
    private Vacancy vacancyProfileOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Vacancy getVacancyProfileOwner() {
        return vacancyProfileOwner;
    }
    
    public void setVacancyProfileOwner(final Vacancy vacancyProfileOwner) {
        this.vacancyProfileOwner = vacancyProfileOwner;
    }

}
