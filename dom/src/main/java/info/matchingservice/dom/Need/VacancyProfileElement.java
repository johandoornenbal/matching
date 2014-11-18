package info.matchingservice.dom.Need;

import info.matchingservice.dom.MatchingSecureMutableObject;
import info.matchingservice.dom.ProfileElementNature;
import info.matchingservice.dom.ProfileElementType;

import javax.jdo.annotations.DiscriminatorStrategy;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;

import org.apache.isis.applib.annotation.Disabled;
import org.apache.isis.applib.annotation.Hidden;
import org.apache.isis.applib.annotation.Immutable;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.MultiLine;
import org.apache.isis.applib.annotation.Named;
import org.apache.isis.applib.annotation.Where;

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
                    + "WHERE vacancyProfileElementOwner == :vacancyProfileElementOwner"),
    @javax.jdo.annotations.Query(
            name = "findVacancyProfileElementByOwnerVacancyAndNature", language = "JDOQL",
            value = "SELECT "
                    + "FROM info.matchingservice.dom.Need.VacancyProfileElement "
                    + "WHERE vacancyProfileElementOwner == :vacancyProfileElementOwner && profileElementNature == :profileElementNature")
})
@Immutable
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
    
    ///////////////////////////////////////////////////////////////////////////////////////
    
    private ProfileElementNature profileElementNature;
    
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "100")
    public ProfileElementNature getProfileElementNature() {
        return profileElementNature;
    }
    
    public void setProfileElementNature(final ProfileElementNature nature) {
        this.profileElementNature = nature;
    }
    
    private ProfileElementType profileElementType;
    
    @Disabled
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MemberOrder(sequence = "110")
    public ProfileElementType getProfileElementType() {
        return profileElementType;
    }
    
    public void setProfileElementType(final ProfileElementType type) {
        this.profileElementType = type;
    }
    
    private Integer weight;
    
    @javax.jdo.annotations.Column(allowsNull = "true")
    @MemberOrder(sequence = "20")
    public Integer getWeight() {
        return weight;
    }
    
    public void setWeight(final Integer weight) {
        this.weight = weight;
    }
    
    ///////////////////////////////////////////////////////////////////////////////////////
    
    private String vacancyProfileElementDescription;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @MultiLine
    @Named("Profiel element beschrijving")
    @MemberOrder(sequence = "30")
    public String getVacancyProfileElementDescription(){
        return vacancyProfileElementDescription;
    }
    
    public void setVacancyProfileElementDescription(final String description) {
        this.vacancyProfileElementDescription = description;
    }
    
    private VacancyProfile vacancyProfileElementOwner;
    
    @javax.jdo.annotations.Column(allowsNull = "false")
    @Disabled
    @Named("'Stoel'")
    @Hidden(where=Where.PARENTED_TABLES)
    @MemberOrder(sequence = "120")
    public VacancyProfile getVacancyProfileElementOwner() {
        return vacancyProfileElementOwner;
    }
    
    public void setVacancyProfileElementOwner(final VacancyProfile vacancyProfileOwner) {
        this.vacancyProfileElementOwner = vacancyProfileOwner;
    }
    
    /// Helpers
    
    public String toString(){
        return this.vacancyProfileElementDescription;
    }

}
