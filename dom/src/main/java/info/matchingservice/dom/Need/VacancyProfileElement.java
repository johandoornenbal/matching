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
            name = "findVacancyProfileElementByOwnerVacancy", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Need.VacancyProfileElement "
                    + "WHERE vacancyProfileElementOwner == :vacancyProfileElementOwner")
})
public class VacancyProfileElement extends MatchingSecureMutableObject<VacancyProfileElement> {

    public VacancyProfileElement() {
        super("ownedBy, vacancyProfileElementDescription");
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
    
    private String vacancyProfileElementDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MultiLine
    public String getVacancyProfileElementDescription(){
        return vacancyProfileElementDescription;
    }
    
    public void setVacancyProfileElementDescription(final String description) {
        this.vacancyProfileElementDescription = description;
    }
    
    private Vacancy vacancyProfileElementOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    public Vacancy getVacancyProfileElementOwner() {
        return vacancyProfileElementOwner;
    }
    
    public void setVacancyProfileElementOwner(final Vacancy vacancyProfileOwner) {
        this.vacancyProfileElementOwner = vacancyProfileOwner;
    }

}
